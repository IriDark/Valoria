package com.idark.valoria.util;


import com.idark.valoria.enchant.ModEnchantments;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModUtils {

    /**
     * Applies a cooldown to item list
     * @param items ItemList to apply the cooldown
     * @param cooldownTicks Time of cooldown
     * @see com.idark.valoria.item.types.ScytheItem#finishUsingItem(ItemStack, Level, LivingEntity) Example
     */
    public static void applyCooldownToItemList(Player player, List<Item> items, int cooldownTicks) {
        for (Item pItems : items) {
            player.getCooldowns().addCooldown(pItems, cooldownTicks);
        }
    }

    /**
     * @param stack being checked
     * @return 0.5 per level if true
     * @see #radiusHit
     */
    public static float getRadius(ItemStack stack) {
        int i = stack.getEnchantmentLevel(ModEnchantments.RADIUS.get());
        return i > 0 ? (float) i / 2 : 0.0F;
    }

    /**
     * Doing a circled attack near player with:
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     * @see com.idark.valoria.item.types.ScytheItem#releaseUsing
     * Scythe Item as Example (line 68)
     */
    public static void radiusHit(Level level, ItemStack stack, Player player, ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        float pRadius = radius + getRadius(stack);
        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius;
        double Y = Math.cos(locPitch + pitch) * pRadius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius;

        AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)) {
                hitEntities.add(livingEntity);
            }
        }

        X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
        Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
        Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;
        level.addParticle(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 0d, 0d, 0d);
    }


    /**
     * Can be used in projectile tick() method.
     * Projectile will have a homing movement to nearby entity
     *
     * @param entityNear List for nearby entities
     * @param pOwner     Owner of Projectile
     */
    public static void homingMovement(Entity projectile, Level level, List<Entity> entityNear, Entity pOwner) {
        AABB boundingBox = new AABB(projectile.getX() - 3.5, projectile.getY() - 0.5, projectile.getZ() - 3.5, projectile.getX() + 3.5, projectile.getY() + 0.5, projectile.getZ() + 3.5);
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, boundingBox);

        if (entityNear == null) {
            entityNear = new ArrayList<>();
        }

        if (!level.isClientSide) {
            if (!livingEntities.isEmpty()) {
                LivingEntity nearestEntity = null;
                double nearestDistance = Double.MAX_VALUE;

                for (LivingEntity livingEntity : livingEntities) {
                    double distance = projectile.distanceTo(livingEntity);

                    if (livingEntity != pOwner) {
                        if (distance < nearestDistance) {
                            nearestEntity = livingEntity;
                            nearestDistance = distance;
                        }
                    }
                }

                if (nearestEntity != null) {
                    Vec3 targetPos = nearestEntity.position();
                    Vec3 currentPos = projectile.position();
                    Vec3 direction = targetPos.subtract(currentPos).normalize().scale(0.5f);
                    projectile.setDeltaMovement(direction.x, direction.y, direction.z);
                }
            }
        }
    }

    /**
     * Spawns particles in radius like in radiusHit
     *
     * @param radius Distance in blocks
     * @param type   Particle that will spawn at radius
     * @param pos    Position
     * @see com.idark.valoria.item.types.CoralReefItem#releaseUsing(ItemStack, Level, LivingEntity, int) Example
     */
    public static void spawnParticlesInRadius(Level level, ItemStack stack, ParticleOptions type, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        float pRadius = radius + getRadius(stack);
        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius;
        double Y = Math.cos(locPitch + pitch) * pRadius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius;

        X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
        Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
        Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;
        level.addParticle(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 0d, 0d, 0d);
    }

    /**
     * Spawns particles around position
     *
     * @param distance Distance in blocks
     * @param options  Particle that will spawn at radius
     * @param speed    Speed of particles
     * @param pos      Position
     * @see com.idark.valoria.item.types.MurasamaItem#onUseTick(Level, LivingEntity, ItemStack, int) Example
     */
    public static void spawnParticlesAroundPosition(Vector3d pos, float distance, float speed, Level level, ParticleOptions options) {
        Random rand = new Random();
        RandomSource source = RandomSource.create();

        double X = ((rand.nextDouble() - 0.5D) * distance);
        double Y = ((rand.nextDouble() - 0.5D) * distance);
        double Z = ((rand.nextDouble() - 0.5D) * distance);

        double dX = -X;
        double dY = -Y;
        double dZ = -Z;

        int count = 1 + Mth.nextInt(source, 0, 2);

        for (int ii = 0; ii < count; ii += 1) {
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            double XX = Math.sin(pitch) * Math.cos(yaw) * speed / (ii + 1);
            double YY = Math.sin(pitch) * Math.sin(yaw) * speed / (ii + 1);
            double ZZ = Math.cos(pitch) * speed / (ii + 1);

            level.addParticle(options, pos.x + X, pos.y + Y, pos.z + Z, XX, YY, ZZ);
        }
    }

    /**
     * Spawns particles line to attacked mob position
     *
     * @param pPlayer Player pos for calculating Attacked mob and positions
     * @param pType Particle that will spawn line
     * @see com.idark.valoria.item.curio.charm.BloodSight#curioTick(String, int, LivingEntity, ItemStack)  Example
     */
    public static void spawnParticlesLineToAttackedMob(Level pLevel, Player pPlayer, ParticleOptions pType) {
        LivingEntity lastHurtMob = pPlayer.getLastAttacker();
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
            if (lastHurtMob == null) {
                return;
            }

            Vec3 pos = new Vec3(pPlayer.getX(), pPlayer.getY() + 0.5f, pPlayer.getZ());
            Vec3 EndPos = new Vec3(lastHurtMob.getX(), lastHurtMob.getY() + 0.5f, lastHurtMob.getZ());
            double distance = pos.distanceTo(EndPos);
            double distanceInBlocks = Math.floor(distance);
            for (int i = 0; i < distanceInBlocks; i++) {
                double dX = pos.x - EndPos.x;
                double dY = pos.y - EndPos.y;
                double dZ = pos.z - EndPos.z;

                float x = (float) (dX / distanceInBlocks);
                float y = (float) (dY / distanceInBlocks);
                float z = (float) (dZ / distanceInBlocks);

                pServer.sendParticles(pType, pos.x - (x * i), pos.y - (y * i), pos.z - (z * i), 1, 0, 0, 0, 0);
            }
        }
    }

    public static void damageLastAttackedMob(Level pLevel, Player pPlayer, float pAmount) {
        LivingEntity lastHurtMob = pPlayer.getLastAttacker();
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
            if (lastHurtMob == null) {
                return;
            }

            lastHurtMob.hurt(pServer.damageSources().generic(), pAmount);
        }
    }

    /**
     * Spawns particles line
     *
     * @param pType Particle that will spawn line
     * @param pFrom Position From
     * @param pTo Position To
     */
    public static void spawnParticlesLine(Level pLevel, Vec3 pFrom, Vec3 pTo, ParticleOptions pType) {
        double distance = pFrom.distanceTo(pTo);
        double distanceInBlocks = Math.floor(distance);
        for (int i = 0; i < distanceInBlocks; i++) {
            double dX = pFrom.x - pTo.x;
            double dY = pFrom.y - pTo.y;
            double dZ = pFrom.z - pTo.z;

            float x = (float) (dX / distanceInBlocks);
            float y = (float) (dY / distanceInBlocks);
            float z = (float) (dZ / distanceInBlocks);

            pLevel.addParticle(pType, pFrom.x - (x * i), pFrom.y - (y * i), pFrom.z - (z * i), 0, 0, 0);
        }
    }
}