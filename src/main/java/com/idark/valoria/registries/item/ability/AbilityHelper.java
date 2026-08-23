package com.idark.valoria.registries.item.ability;

import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.*;

public class AbilityHelper {
    // Client-side cache
    private static final Map<String, Integer> CLIENT_USAGES = new ConcurrentHashMap<>();
    private static final Map<String, Long> CLIENT_COOLDOWNS = new ConcurrentHashMap<>();
    private static final Map<String, Integer> CLIENT_MAX_COOLDOWNS = new ConcurrentHashMap<>();

    @Nullable
    public static AbilityComponent getAbility(ItemStack stack, CastType type) {
        if (stack.getItem() instanceof ModularWeaponItem modularItem) {
            return modularItem.getInnateAbilities().get(type);
        }
        
        return null;
    }

    public record ActiveAbility(CastType type, AbilityComponent ability) {}
    
    public static List<ActiveAbility> getActiveAbilities(ItemStack stack) {
        List<ActiveAbility> list = new ArrayList<>();
        if (stack.getItem() instanceof ModularWeaponItem modularItem) {
            for (Map.Entry<CastType, AbilityComponent> entry : modularItem.getInnateAbilities().entrySet()) {
                list.add(new ActiveAbility(entry.getKey(), entry.getValue()));
            }
        }
        
        return list;
    }

    public static int getUsages(Player player, AbilityComponent ability) {
        if (player.level().isClientSide) {
            return CLIENT_USAGES.getOrDefault(ability.type.id.toString(), 0);
        }
        
        return player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
                .map(cap -> cap.getUsages(ability.type.id.toString()))
                .orElse(0);
    }

    public static void setUsages(ServerPlayer player, AbilityComponent ability, int usages) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
            .ifPresent(cap -> cap.setUsages(ability.type.id.toString(), usages));
        syncState(player, ability.type.id.toString());
    }

    public static void tryCast(ServerPlayer player, ItemStack stack, CastType type) {
        AbilityComponent ability = getAbility(stack, type);
        if (ability != null && ability.canCast(player, stack)) {
            int cd = ability.onCastStart(player, player.level(), stack);
            stack.hurtAndBreak(ability.durabilityUsage, player, (p) -> p.broadcastBreakEvent(net.minecraft.world.InteractionHand.MAIN_HAND));
            
            if (stack.isEmpty()) return;

            int usages = getUsages(player, ability) + 1;
            if (usages >= ability.maxUsages) {
                if (cd > 0) setCooldown(player, ability, cd);
                if (ability.itemCooldown > 0) player.getCooldowns().addCooldown(stack.getItem(), ability.itemCooldown);
                usages = 0;
            }
            
            setUsages(player, ability, usages);
        }
    }

    public static boolean handleUse(ItemStack stack, Level level, Player player, InteractionHand hand) {
        boolean consumed = false;
        for (ActiveAbility active : getActiveAbilities(stack)) {
            if (level.isClientSide || active.ability.canCast((ServerPlayer) player, stack)) {
                if (active.ability.onUse(level, player, stack, hand)) {
                    consumed = true;
                }
            }
        }
        return consumed;
    }

    public static void handleUseTick(ItemStack stack, Level level, LivingEntity player, int count) {
        for (ActiveAbility active : getActiveAbilities(stack)) {
            active.ability.onUseTick(level, player, stack, count);
        }
    }

    public static void handleReleaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        for (ActiveAbility active : getActiveAbilities(stack)) {
            active.ability.onReleaseUsing(stack, level, entity, timeLeft);
        }
    }

    public static void handleHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (ActiveAbility active : getActiveAbilities(stack)) {
            active.ability.onHurtEnemy(stack, target, attacker);
        }
    }
    
    public static boolean isOnCooldown(Player player, AbilityComponent ability) {
        String abilityId = ability.type.id.toString();
        long endTime;
        if (player.level().isClientSide) {
            endTime = CLIENT_COOLDOWNS.getOrDefault(abilityId, 0L);
        } else {
            endTime = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
                .map(cap -> cap.getCooldown(abilityId))
                .orElse(0L);
        }
        
        return player.level().getGameTime() < endTime;
    }

    public static long getCooldown(Player player, AbilityComponent ability) {
        String abilityId = ability.type.id.toString();
        if (player.level().isClientSide) {
            return CLIENT_COOLDOWNS.getOrDefault(abilityId, 0L);
        }
        
        return player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
                .map(cap -> cap.getCooldown(abilityId))
                .orElse(0L);
    }

    public static int getMaxCooldown(Player player, AbilityComponent ability) {
        String abilityId = ability.type.id.toString();
        if (player.level().isClientSide) {
            return CLIENT_MAX_COOLDOWNS.getOrDefault(abilityId, 1);
        }
        
        return player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
                .map(cap -> cap.getMaxCooldown(abilityId))
                .orElse(1);
    }

    public static void setCooldown(ServerPlayer player, AbilityComponent ability, int ticks) {
        String id = ability.type.id.toString();
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES)
            .ifPresent(cap -> cap.setCooldown(id, player.level().getGameTime() + ticks, ticks));
        syncState(player, id);
    }

    private static void syncState(ServerPlayer player, String abilityId) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            long endTime = cap.getCooldown(abilityId);
            int maxTicks = cap.getMaxCooldown(abilityId);
            int usages = cap.getUsages(abilityId);
            PacketHandler.sendTo(player, new SyncAbilityStatePacket(abilityId, endTime, maxTicks, usages));
        });
    }

    @ApiStatus.Internal
    public static void updateClientState(String abilityId, long cooldownEndTime, int maxCooldownTicks, int usages) {
        CLIENT_COOLDOWNS.put(abilityId, cooldownEndTime);
        CLIENT_MAX_COOLDOWNS.put(abilityId, maxCooldownTicks);
        CLIENT_USAGES.put(abilityId, usages);
    }
}