package com.idark.valoria.util;

import com.idark.valoria.enchant.ModEnchantments;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModUtils {

    /**
     * @param stack being checked
     * @see #radiusHit
     * @return 0.5 per level if true
     */
    public static float getRadius(ItemStack stack) {
        int i = stack.getEnchantmentLevel(ModEnchantments.RADIUS.get());
        return i > 0 ? (float) i /2 : 0.0F;
    }

    /**
     * Doing a circled attack near player with:
     * @param radius Attack radius
     * @param type Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos Position
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

        AABB boundingBox = new AABB(pos.x, pos.y - pRadius + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
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
    * @param entityNear List for nearby entities
    * @param pOwner Owner of Projectile
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
        Spawns particles around position
        @param distance Distance in blocks
        @param options Particle that will spawn at radius
        @param speed Speed of particles
        @param pos Position
        @see com.idark.valoria.item.types.MurasamaItem#onUseTick(Level, LivingEntity, ItemStack, int) Example
     */
    public static void spawnParticlesAroundPosition(Vec3 pos, float distance, float speed, Level level, ParticleOptions options) {
        Random rand = new Random();
        RandomSource source = RandomSource.create();

        double X = ((rand.nextDouble() - 0.5D) * distance);
        double Y = ((rand.nextDouble() - 0.5D) * distance);
        double Z = ((rand.nextDouble() - 0.5D) * distance);

        double dX = -X;
        double dY = -Y;
        double dZ = -Z;

        int count = 1 + Mth.nextInt(source, 0,2);

        for (int ii = 0; ii < count; ii += 1) {
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            double XX = Math.sin(pitch) * Math.cos(yaw) * speed / (ii + 1);
            double YY = Math.sin(pitch) * Math.sin(yaw) * speed / (ii + 1);
            double ZZ = Math.cos(pitch) * speed / (ii + 1);

            level.addParticle(options, pos.x + X, pos.y + Y, pos.z + Z, XX, YY, ZZ);
        }
    }

    // TODO: Do this thing
    public static void spawnParticlesAtNearMobs(Level level, Player player, ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
    }
}