package com.idark.valoria.core.interfaces;

import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.*;

/**
 * Should be added to your BossEntity implementing class
 * <pre><code>
 *     public final List<UUID> nearbyPlayers = new ArrayList<>();
 *     public final Map<UUID, Float> damageMap = new HashMap<>();
 *
 *    {@literal @}Override
 *     public void onAddedToWorld() {
 *         super.onAddedToWorld();
 *         CompoundTag data = this.getPersistentData();
 *         if (!data.getBoolean("NearbyPlayerHealthBoost")){
 *             initializeNearbyPlayers(this.level(), this);
 *             applyHealthBoost(this);
 *         }
 *     }
 *
 *    {@literal @}Override
 *     public boolean hurt(DamageSource source, float amount) {
 *         if (source.getDirectEntity() instanceof Player player) {
 *             UUID playerUUID = player.getUUID();
 *             getDamageMap().put(playerUUID, getDamageMap().getOrDefault(playerUUID, 0.0f) + amount);
 *         }
 *
 *         return super.hurt(source, amount);
 *     }
 *
 *    {@literal @}Nullable
 *     public ItemEntity spawnAtLocation(ItemStack stack, float offsetY) {
 *         if (stack.isEmpty() || this.level().isClientSide) return null;
 *         initializeLoot(this.level(), stack, this.getOnPos(), offsetY);
 *         return null;
 *     }</code></pre>
 */
public interface BossEntity {
    List<UUID> getNearbyPlayers();
    Map<UUID, Float> getDamageMap();
    default int getRadius() {
        return 16;
    }

    default int scalingFactor() {
        return 200;
    }

    /**
     * Health boost value
     */
    default int getHealthScale(Mob mob) {
        return (int)(mob.getMaxHealth() + (getNearbyPlayers().size() - 1) * scalingFactor() * Math.log(getNearbyPlayers().size()));
    }

    default double getRequiredDamage() {
        return 50;
    }

    default void initializeLoot(Level level, ItemStack stack, BlockPos pos, float offsetY){
        for(UUID playerUUID : getNearbyPlayers()){
            if(getDamageMap().getOrDefault(playerUUID, 0.0f) >= getRequiredDamage()){
                ItemEntity itemEntity = new ItemEntity(
                level, pos.getX(), pos.getY() + offsetY, pos.getZ(), stack.copy()
                );
                itemEntity.setExtendedLifetime();
                itemEntity.setInvulnerable(true);
                itemEntity.setDefaultPickUpDelay();
                itemEntity.setTarget(playerUUID);
                level.addFreshEntity(itemEntity);
            }
        }
    }
    default void initializeNearbyPlayers(Level level, Entity entity) {
        List<Player> players = level.getEntitiesOfClass(Player.class, entity.getBoundingBox().inflate(getRadius()));
        for (Player player : players) {
            getNearbyPlayers().add(player.getUUID());
            getDamageMap().put(player.getUUID(), 0.0f);
        }
    }

    default void applyHealthBoost(Mob mob) {
        if (mob.getAttribute(Attributes.MAX_HEALTH) != null) {
            UUID healthModifierId = UUID.fromString("39ba0d18-24f3-4ea8-ba0d-1824f3fea88b");
            AttributeModifier existingModifier = mob.getAttribute(Attributes.MAX_HEALTH).getModifier(healthModifierId);
            if (existingModifier != null) {
                mob.getAttribute(Attributes.MAX_HEALTH).removeModifier(existingModifier);
            }

            AttributeModifier healthModifier = new AttributeModifier(healthModifierId, "nearby_player_boost", getHealthScale(mob), AttributeModifier.Operation.ADDITION);
            mob.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthModifier);
            mob.setHealth((float)mob.getAttribute(Attributes.MAX_HEALTH).getValue());
            mob.getPersistentData().putBoolean("NearbyPlayerHealthBoost", true);
        }
    }
}