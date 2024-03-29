package com.idark.valoria.util;


import com.idark.valoria.registries.world.entity.living.NecromancerEntity;
import com.idark.valoria.registries.world.item.enchant.ModEnchantments;
import com.idark.valoria.registries.world.item.types.CoralReefItem;
import com.idark.valoria.registries.world.item.types.HoundItem;
import com.idark.valoria.registries.world.item.types.ScytheItem;
import com.idark.valoria.registries.world.item.types.curio.charm.BloodSight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class ModUtils {

    /**
     * Applies a cooldown to item list
     *
     * @param items         ItemList to apply the cooldown
     * @param cooldownTicks Time of cooldown
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
     * @see ScytheItem#releaseUsing
     * Scythe Item as Example
     */
    public static void radiusHit(Level level, ItemStack stack, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
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
        if (type != null && !level.isClientSide() && level instanceof ServerLevel pServer) {
            pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
        }
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
     * @param stack  Stack to add radius enchantment levels (can be null)
     * @param type   Particle that will spawn at radius
     * @param pos    Position
     * @see CoralReefItem#releaseUsing(ItemStack, Level, LivingEntity, int) ItemExample
     * @see NecromancerEntity.HealTargetSpellGoal MobExample
     */
    public static void spawnParticlesInRadius(Level level, @Nullable ItemStack stack, ParticleOptions type, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        float pRadius = stack != null ? radius + getRadius(stack) : radius;
        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
        double Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;

        if (!level.isClientSide() && level instanceof ServerLevel pServer) {
            pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
        }
    }

    /**
     * @param hitEntities List for damaged entities
     * @param type        Particle that will appear at marked mobs
     * @param pos         Position
     * @param radius      Distance in blocks
     * @see com.idark.valoria.registries.world.item.types.BeastScytheItem#finishUsingItem(ItemStack, Level, LivingEntity) Example
     */
    public static void spawnParticlesMark(Level level, Player player, List<LivingEntity> hitEntities, ParticleOptions type, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * radius;
        double Y = Math.cos(locPitch + pitch) * radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * radius;

        AABB boundingBox = new AABB(pos.x, pos.y - 8 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                if (!level.isClientSide() && level instanceof ServerLevel pServer) {
                    pServer.sendParticles(type, livingEntity.getX(), livingEntity.getY() + 2 + ((Math.random() - 0.5D) * 0.2F), livingEntity.getZ(), 1, 0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * Spawns particles around position
     *
     * @param distance Distance in blocks
     * @param options  Particle that will spawn at radius
     * @param speed    Speed of particles
     * @param pos      Position
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
        if (!level.isClientSide() && level instanceof ServerLevel pServer) {
            for (int ii = 0; ii < count; ii += 1) {
                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                double XX = Math.sin(pitch) * Math.cos(yaw) * speed / (ii + 1);
                double YY = Math.sin(pitch) * Math.sin(yaw) * speed / (ii + 1);
                double ZZ = Math.cos(pitch) * speed / (ii + 1);

                pServer.sendParticles(options, pos.x + X, pos.y + Y, pos.z + Z, 1, XX, YY, ZZ, 0);
            }
        }
    }

    /**
     * Spawns particles line to attacked mob position
     *
     * @param pPlayer Player pos for calculating Attacked mob and positions
     * @param pType   Particle that will spawn line
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

    /**
     * Spawns particles line to attacked mob position
     *
     * @param pPlayer   Player pos for calculating Attacked mob and positions
     * @param pType     Particle that will spawn line
     * @param pDuration cooldown
     * @see BloodSight#curioTick(SlotContext, ItemStack)   Example
     */
    public static void spawnParticlesLineToAttackedMobWithCooldown(Level pLevel, Player pPlayer, ParticleOptions pType, int pDuration) {
        LivingEntity lastHurtMob = pPlayer.getLastAttacker();
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
            if (lastHurtMob == null) {
                return;
            }

            Vec3 pos = new Vec3(pPlayer.getX(), pPlayer.getY() + 0.5f, pPlayer.getZ());
            Vec3 EndPos = new Vec3(lastHurtMob.getX(), lastHurtMob.getY() + 0.5f, lastHurtMob.getZ());
            double distance = pos.distanceTo(EndPos);
            double distanceInBlocks = Math.floor(distance);
            for (pDuration = 0; pDuration < distanceInBlocks; pDuration++) {
                double dX = pos.x - EndPos.x;
                double dY = pos.y - EndPos.y;
                double dZ = pos.z - EndPos.z;
                float x = (float) (dX / distanceInBlocks);
                float y = (float) (dY / distanceInBlocks);
                float z = (float) (dZ / distanceInBlocks);

                pServer.sendParticles(pType, pos.x - (x * pDuration), pos.y - (y * pDuration), pos.z - (z * pDuration), 1, 0, 0, 0, 0);
            }
        }
    }

    public static void damageLastAttackedMob(Level pLevel, Player pPlayer, float pAmount) {
        LivingEntity lastHurtMob = pPlayer.getLastAttacker();
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
            if (lastHurtMob == null) {
                return;
            }

            lastHurtMob.hurt(pServer.damageSources().playerAttack(pPlayer), pAmount);
        }
    }

    /**
     * Spawns particle lines to nearby Mobs
     *
     * @param pPlayer     Player for reciving pos from
     * @param pType       Particle type to spawn
     * @param hitEntities list of Entities
     * @param pos         Position in Vec3
     * @param radius      Radius to spawn
     * @see HoundItem#finishUsingItem(ItemStack, Level, LivingEntity) ItemExample
     */
    public static void spawnParticlesLineToNearbyMobs(Level pLevel, Player pPlayer, ParticleOptions pType, List<LivingEntity> hitEntities, Vec3 pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * radius;
        double Y = Math.cos(locPitch + pitch) * radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * radius;

        AABB boundingBox = new AABB(pos.x, pos.y - 8 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(pPlayer)) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                Vec3 pTo = new Vec3(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                double distance = pos.distanceTo(pTo);
                double distanceInBlocks = Math.floor(distance);
                for (int i = 0; i < distanceInBlocks; i++) {
                    double dX = pos.x - pTo.x;
                    double dY = pos.y - pTo.y;
                    double dZ = pos.z - pTo.z;

                    float x = (float) (dX / distanceInBlocks);
                    float y = (float) (dY / distanceInBlocks);
                    float z = (float) (dZ / distanceInBlocks);

                    if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
                        pServer.sendParticles(pType, pos.x - (x * i), pos.y - (y * i), pos.z - (z * i), 1, 0, 0, 0, 0);
                    }
                }

                for (int i = 0; i < 3; i++) {
                    pLevel.addParticle(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            }
        }
    }

    /**
     * @param pPlayer     Player for reciving pos from
     * @param hitEntities list of Entities
     * @param pos         Position in Vec3
     * @param radius      Radius to check mobs
     */
    public static void markNearbyMobs(Level pLevel, Player pPlayer, List<LivingEntity> hitEntities, Vec3 pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * radius;
        double Y = Math.cos(locPitch + pitch) * radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * radius;

        AABB boundingBox = new AABB(pos.x, pos.y - 8 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(pPlayer)) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, false));
            }
        }
    }

    /**
     * @param pType       EntityType to heal
     * @param pHealer     Healer entity
     * @param hitEntities list of Entities
     * @param pos         Position in Vec3
     * @param radius      Radius to check mobs
     */
    public static void healNearbyTypedMobs(MobCategory pType, Float pHeal, Level pLevel, LivingEntity pHealer, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;
        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * radius;
        double Y = Math.cos(locPitch + pitch) * radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * radius;

        AABB boundingBox = new AABB(pos.x, pos.y - 8 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(pHealer) && pType.equals(entity.getType().getCategory())) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                livingEntity.heal(pHeal);
            }
        }
    }

    /**
     * @param pHealer     Healer entity
     * @param hitEntities list of Entities
     * @param pos         Position in Vec3
     * @param radius      Radius to check mobs
     */
    public static void healNearbyMobs(Float pHeal, Level pLevel, LivingEntity pHealer, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;
        double locYaw = 0;
        double locPitch = 0;
        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * radius;
        double Y = Math.cos(locPitch + pitch) * radius;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * radius;

        AABB boundingBox = new AABB(pos.x, pos.y - 8 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(pHealer)) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                livingEntity.heal(pHeal);
            }
        }
    }

    /**
     * Spawns particles line
     *
     * @param pType Particle that will spawn line
     * @param pFrom Position From
     * @param pTo   Position To
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

            if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
                pServer.sendParticles(pType, pFrom.x - (x * i), pFrom.y - (y * i), pFrom.z - (z * i), 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * @param from   pos from
     * @param entity entity (projectile, player etc.
     * @param filter (e) -> true as default
     * @param to     pos to
     * @param level  level
     * @return HitResult
     */
    public static HitResult getHitResult(Vec3 from, Entity entity, Predicate<Entity> filter, Vec3 to, Level level) {
        Vec3 vec3 = from.add(to);
        HitResult hitresult = level.clip(new ClipContext(from, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level, entity, from, vec3, entity.getBoundingBox().expandTowards(to).inflate(1.0D), filter);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }
}