package com.idark.valoria.registries.level.events;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.TickEvent.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class NihilityEvent{
    public static float damagingLevel = 0.5f;
    public static float criticalLevel = 0.75f;

    public static void tick(PlayerTickEvent event, INihilityLevel nihilityLevel, ServerPlayer player){
        float max = nihilityLevel.getMaxAmount(player);
        float amount = nihilityLevel.getAmount();
        if(player.level().dimension() == LevelGen.VALORIA_KEY){
            valoriaTick(nihilityLevel, player);
        }else{
            if(player.tickCount % ServerConfig.NIHILITY_DECAY_INTERVAL.get() * 20 == 0 && amount > 0){
                nihilityLevel.decrease(player, Tmp.rnd.nextInt(1, 5));
            }
        }

        if(isDamagingLevel(player, amount, max)){
            float ratio = amount / max;
            boolean flag = ratio >= damagingLevel;
            int segments = (int)((ratio - damagingLevel) / 0.1f);
            float damage = (float)(1 + segments * ServerConfig.NIHILITY_DAMAGE_MULTIPLIER.get());

            if(ratio >= 0.95f) {
                Valoria.LOGGER.debug(ServerConfig.MAX_NIHILITY_ACTION.get().name());
                switch(ServerConfig.MAX_NIHILITY_ACTION.get()) {
                    case DAMAGE -> {
                        player.hurt(DamageSourceRegistry.voidHarm(player.level()), damage);
                        break;
                    }
                    case TELEPORT -> {
                        MinecraftServer server = player.getServer();
                        if(server != null){
                            ResourceKey<Level> respawnDim = player.getRespawnDimension();
                            ServerLevel targetLevel = server.getLevel(respawnDim);
                            if(targetLevel != null){
                                Optional<Vec3> respawnPos = Optional.empty();
                                if(player.getRespawnPosition() != null){
                                    respawnPos = Player.findRespawnPositionAndUseSpawnBlock(
                                    targetLevel, player.getRespawnPosition(), player.getRespawnAngle(), player.isRespawnForced(), true);
                                }

                                Vec3 target = respawnPos.orElseGet(() -> {
                                    BlockPos worldSpawn = targetLevel.getSharedSpawnPos();
                                    return new Vec3(worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ());
                                });

                                player.teleportTo(targetLevel, target.x, target.y, target.z, player.getYRot(), player.getXRot());
                                nihilityLevel.setAmount(0);
                            }
                        }
                        break;
                    }

                    default -> player.kill();
                }
            } else if(flag){
                player.hurt(DamageSourceRegistry.voidHarm(player.level()), damage);
            }

            boolean criticalFlag = amount > max * criticalLevel;
            if(criticalFlag){
                if(Tmp.rnd.chance(0.05f)){
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.WARDEN_HEARTBEAT, net.minecraft.sounds.SoundSource.PLAYERS,
                    1.0f, 0.8f);
                }

                if(ServerConfig.CRITICAL_NIHILITY_BLINDNESS.get()){
                    if(!player.hasEffect(MobEffects.BLINDNESS)){
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
                    }
                }
            }
        }
    }

    public static void clientTick(INihilityLevel nihilityLevel, Player player) {

    }

    private static boolean isDamagingLevel(Player player, float amountClient, float maxClient){
        return player.tickCount % (amountClient < maxClient * criticalLevel ? 40 : 20) == 0;
    }

    private static void valoriaTick(INihilityLevel nihilityLevel, Player player){
        if(player.tickCount % (int)(player.getAttributeValue(AttributeReg.NIHILITY_RESILIENCE.get()) * 20) == 0){
            double resistance = player.getAttributeValue(AttributeReg.NIHILITY_RESISTANCE.get());
            double factor = Math.max(0.05, 1.0 - (resistance * 0.05));
            nihilityLevel.modifyAmount(player, (int)factor);
        }
    }
}