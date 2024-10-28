package com.idark.valoria.util;

import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.curio.charm.*;
import com.idark.valoria.registries.item.types.ranged.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.fml.loading.*;
import org.joml.*;
import top.theillusivec4.curios.api.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.Random;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class ValoriaUtils {

    /**
     * Checks if the game was started in IDE
     */
    public static boolean isIDE = !FMLLoader.isProduction();

    /**
     * @param pSize   Portal Size
     * @param pPortal Portal State
     * @param pFrame  Portal Frame
     */
    public static void createEndShapedPortal(int pSize, Level pDestination, BlockPos pPos, BlockState pPortal, BlockState pFrame) {
        for (int i = 0; i < pSize - 1; ++i) {
            for (int j = 0; j < pSize - 1; ++j) {
                pDestination.setBlock(pPos.offset(i, 0, j), pPortal, 2);
            }
        }

        for (int i = 0; i < pSize; i++) {
            for (int j = 0; j < pSize; j++) {
                if (i == 0 || i == pSize - 1 || j == 0 || j == pSize - 1) {
                    pDestination.setBlock(pPos.offset(i, 0, j), pFrame, 2);
                }
            }
        }
    }

    /**
     * Performs a spin attack with checking a collision with targets
     */
    public static void spinAttack(Level level, Player player) {
        List<Entity> list = level.getEntities(player, player.getBoundingBox().inflate(1));
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof LivingEntity target) {
                    target.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(player.getUseItem(), target.getMobType())) * 1.35f);
                }
            }
        }
    }


    public static void spinAttack(Level level, Player player, double inflateValue) {
        List<Entity> list = level.getEntities(player, player.getBoundingBox().inflate(inflateValue));
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof LivingEntity target) {
                    target.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(player.getUseItem(), target.getMobType())) * 1.35f);
                }
            }
        }
    }

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
        int i = stack.getEnchantmentLevel(EnchantmentsRegistry.RADIUS.get());
        return i > 0 ? (float) i / 2 : 0.0F;
    }

    /**
     * Performs a circled attack near player
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, ItemStack stack, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            float pRadius = radius + getRadius(stack);
            double X = Math.sin(pitch) * Math.cos(yaw + i) * pRadius;
            double Y = Math.cos(pitch) * pRadius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * pRadius;

            AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)) {
                    hitEntities.add(livingEntity);
                }
            }

            X = Math.sin(pitch) * Math.cos(yaw + i) * pRadius * 0.75F;
            Y = Math.cos(pitch) * pRadius * 0.75F;
            Z = Math.sin(pitch) * Math.sin(yaw + i) * pRadius * 0.75F;
            if (type != null && !level.isClientSide() && level instanceof ServerLevel pServer) {
                pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * Performs a circled attack near player
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw + i) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * radius;

            AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)) {
                    hitEntities.add(livingEntity);
                }
            }

            X = Math.sin(pitch) * Math.cos(yaw + i) * radius * 0.75F;
            Y = Math.cos(pitch) * radius * 0.75F;
            Z = Math.sin(pitch) * Math.sin(yaw + i) * radius * 0.75F;
            if (type != null && !level.isClientSide() && level instanceof ServerLevel pServer) {
                pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * Can be used in projectile tick() method.
     * Projectile will have a homing movement to nearby entity
     *
     * @param pOwner      Owner of Projectile
     * @param boundingBox radius example:
     *                    <p>
     *                    <pre>{@code new AABB(projectile.getX() - 3.5, projectile.getY() - 0.5, projectile.getZ() - 3.5, projectile.getX() + 3.5, projectile.getY() + 0.5, projectile.getZ() + 3.5);
     *                    }</pre>
     */
    public static void inaccurateHomingMovement(double pSpeed, Entity projectile, Level level, Entity pOwner, AABB boundingBox) {
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, boundingBox);
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
                    double dX = targetPos.x - projectile.getX();
                    double dY = targetPos.y - projectile.getY();
                    double dZ = targetPos.z - projectile.getZ();
                    projectile.hurtMarked = true;
                    projectile.setDeltaMovement(projectile.getDeltaMovement().add(dX / Math.sqrt(dX * dX) * pSpeed, dY / Math.sqrt(dY * dY) * pSpeed, dZ / Math.sqrt(dZ * dZ) * pSpeed));
                }
            }
        }
    }

    // Used to calculate knockback like in tnt
    public static float getSeenPercent(Vec3 pVector, Entity pEntity, float pStrength) {
        AABB aabb = pEntity.getBoundingBox();
        double d0 = 1.0D / ((aabb.maxX - aabb.minX) * 2.0D + 1.0D);
        double d1 = 1.0D / ((aabb.maxY - aabb.minY) * 2.0D + 1.0D);
        double d2 = 1.0D / ((aabb.maxZ - aabb.minZ) * 2.0D + 1.0D);
        double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
        double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
        if (!(d0 < 0.0D) && !(d1 < 0.0D) && !(d2 < 0.0D)) {
            int i = 0;
            int j = 0;
            for (double d5 = 0.0D; d5 <= 1.0D; d5 += d0) {
                for (double d6 = 0.0D; d6 <= 1.0D; d6 += d1) {
                    for (double d7 = 0.0D; d7 <= 1.0D; d7 += d2) {
                        double d8 = Mth.lerp(d5, aabb.minX, aabb.maxX);
                        double d9 = Mth.lerp(d6, aabb.minY, aabb.maxY);
                        double d10 = Mth.lerp(d7, aabb.minZ, aabb.maxZ);
                        Vec3 vec3 = new Vec3(d8 + d3, d9, d10 + d4);
                        if (pEntity.level().clip(new ClipContext(vec3, pVector, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pEntity)).getType() == HitResult.Type.MISS) {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return ((float) i / (float) j) * pStrength;
        } else {
            return pStrength;
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
        float pRadius = stack != null ? radius + getRadius(stack) : radius;
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((i + yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw) * pRadius * 0.75F;
            double Y = Math.cos(pitch) * pRadius * 0.75F;
            double Z = Math.sin(pitch) * Math.sin(yaw) * pRadius * 0.75F;
            if (!level.isClientSide() && level instanceof ServerLevel pServer) {
                pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * @param hitEntities List for damaged entities
     * @param type        Particle that will appear at marked mobs
     * @param pos         Position
     * @param radius      Distance in blocks
     * @see BeastScytheItem#finishUsingItem(ItemStack, Level, LivingEntity) Example
     */
    public static void spawnParticlesMark(Level level, Player player, List<LivingEntity> hitEntities, ParticleOptions type, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((i + yawRaw + 90) * Math.PI) / 180;

            double X = Math.sin(pitch) * Math.cos(yaw) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw) * radius;

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
        for (int i = 0; i < 360; i += 10) {
            double X = ((rand.nextDouble() - 0.5D) * distance);
            double Y = ((rand.nextDouble() - 0.5D) * distance);
            double Z = ((rand.nextDouble() - 0.5D) * distance);

            double dX = -X;
            double dY = -Y;
            double dZ = -Z;
            if (!level.isClientSide() && level instanceof ServerLevel pServer) {
                for (int ii = 0; ii < 1 + Mth.nextInt(source, 0, 2); ii += 1) {
                    double yaw = Math.atan2(dZ, dX) + i;
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                    double XX = Math.sin(pitch) * Math.cos(yaw) * speed / (ii + 1);
                    double YY = Math.sin(pitch) * Math.sin(yaw) * speed / (ii + 1);
                    double ZZ = Math.cos(pitch) * speed / (ii + 1);

                    pServer.sendParticles(options, pos.x + X, pos.y + Y, pos.z + Z, 1, XX, YY, ZZ, 0);
                }
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
     */
    public static void spawnParticlesLineToNearbyMobs(Level pLevel, Player pPlayer, ParticleOptions pType, List<LivingEntity> hitEntities, Vec3 pos, float pitchRaw, float yawRaw, float radius) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double X = Math.sin(pitch) * Math.cos(yaw) * radius;
        double Y = Math.cos(pitch) * radius;
        double Z = Math.sin(pitch) * Math.sin(yaw) * radius;
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

        double X = Math.sin(pitch) * Math.cos(yaw) * radius;
        double Y = Math.cos(pitch) * radius;
        double Z = Math.sin(pitch) * Math.sin(yaw) * radius;
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
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw + i) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * radius;

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
    }

    /**
     * @param pHealer     Healer entity
     * @param hitEntities list of Entities
     * @param pos         Position in Vec3
     * @param radius      Radius to check mobs
     */
    public static void healNearbyMobs(float pHeal, Level pLevel, LivingEntity pHealer, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius) {
        for (int i = 0; i < 360; i += 10) {
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw + i) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * radius;

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

        HitResult result = ProjectileUtil.getEntityHitResult(level, entity, from, vec3, entity.getBoundingBox().expandTowards(to).inflate(1.0D), filter);
        if (result != null) {
            hitresult = result;
        }

        return hitresult;
    }

    public static void addContributorTooltip(ItemStack stack, List<Component> tooltip){
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) {
            if(skin instanceof AuthoredItemSkin authored){
                tooltip.remove(0);
                tooltip.add(0, authored.getContributorComponent(stack));
            }

            tooltip.add(1, skin.getSkinComponent());
            tooltip.add(2, Component.empty());
        }
    }

    public static void addEffectsTooltip(ImmutableList<MobEffectInstance> effects, List<Component> pTooltips, float pDurationFactor, float chance) {
        if (!effects.isEmpty()) {
            if (chance > 0 && chance < 1) {
                pTooltips.add(CommonComponents.EMPTY);
                pTooltips.add(Component.translatable("tooltip.valoria.with_chance").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.1f%%", chance * 100))));
            } else {
                pTooltips.add(CommonComponents.EMPTY);
                pTooltips.add(Component.translatable("tooltip.valoria.applies").withStyle(ChatFormatting.GRAY));
            }

            for (MobEffectInstance mobeffectinstance : effects) {
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                MobEffect mobeffect = mobeffectinstance.getEffect();
                if (mobeffectinstance.getAmplifier() > 0) {
                    mutablecomponent = Component.literal(" ").append(Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier())));
                }

                if (!mobeffectinstance.endsWithin(20)) {
                    mutablecomponent = Component.literal(" ").append(Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, pDurationFactor)));
                }

                pTooltips.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
            }

            pTooltips.add(CommonComponents.EMPTY);
        }
    }

    /**
     * @param pPos   Position to start from
     * @param entity Entity that being checked to spawn
     * @return Safe spot pos
     */
    private BlockPos searchSafeSpot(Level pLevel, BlockPos pPos, LivingEntity entity) {
        Random rand = new Random();
        int x = pPos.getX() + (rand.nextInt() - rand.nextInt()) * 6;
        int y = pPos.getY() + rand.nextInt(1, 2);
        int z = pPos.getZ() + (rand.nextInt() - rand.nextInt()) * 6;
        if (pLevel.noCollision(entity, new AABB(x, y, z, x, y, z).inflate(1))) {
            return new BlockPos(x, y, z);
        }

        return null;
    }

    public static ItemStack predicate(Player player, ItemStack pShootable, Predicate<ItemStack> predicate) {
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack ammo = player.getInventory().getItem(i);
            if (predicate.test(ammo)) {
                return net.minecraftforge.common.ForgeHooks.getProjectile(player, pShootable, ammo);
            }
        }

        // why cobblestone? i dunno too
        return player.isCreative() ? Items.COBBLESTONE.getDefaultInstance() : ItemStack.EMPTY;
    }

    /**
     * Searches items in player inventory that equals an instance of GunpowderCharge
     */
    public static ItemStack getProjectile(Player player, ItemStack pShootable) {
        Predicate<ItemStack> predicate = (stack) -> stack.getItem() instanceof GunpowderCharge;
        return predicate(player, pShootable, predicate);
    }

    // same as getProjectile but searches for tagged items
    public static ItemStack getProjectile(Player player, ItemStack pShootable, TagKey<Item> pTag) {
        Predicate<ItemStack> predicate = (stack) -> stack.is(pTag);
        return predicate(player, pShootable, predicate);
    }

    public static void addList(List<Item> list, Item... T) {
        Collections.addAll(list, T);
    }

    public static void configExplode(Player player, ItemStack itemstack, Vec3 pos, Vec3 clipPos, float radius, float damage, float knockback) {
        Level level = player.level();
        RandomSource rand = level.random;
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + clipPos.x - radius, pos.y + clipPos.y - radius, pos.z + clipPos.z - radius, pos.x + clipPos.x + radius, pos.y + clipPos.y + radius, pos.z + clipPos.z + radius));
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity enemy) {
                if (!enemy.equals(player)) {
                    enemy.hurt(level.damageSources().generic(), damage);
                    enemy.knockback(knockback, player.getX() + clipPos.x - entity.getX(), player.getZ() + clipPos.z - entity.getZ());
                    if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                        int i = EnchantmentHelper.getFireAspect(player);
                        enemy.setSecondsOnFire(i * 4);
                    }
                }
            }
        }

        if (level instanceof ServerLevel srv) {
            srv.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.x + clipPos.x, pos.y + clipPos.y, player.getZ() + clipPos.z, 1, 0, 0, 0, radius);
            srv.playSound(null, player.blockPosition().offset((int) clipPos.x, (int) (clipPos.y + player.getEyeHeight()), (int) clipPos.z), SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 10f, 1f);
            srv.sendParticles(ParticleTypes.LARGE_SMOKE, pos.x + clipPos.x + ((rand.nextDouble() - 0.5D) * radius), pos.y + clipPos.y + ((rand.nextDouble() - 0.5D) * radius), pos.z + clipPos.z + ((rand.nextDouble() - 0.5D) * radius), 8, 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.2f);
            srv.sendParticles(ParticleTypes.FLAME, pos.x + clipPos.x + ((rand.nextDouble() - 0.5D) * radius), pos.y + clipPos.y + ((rand.nextDouble() - 0.5D) * radius), pos.z + clipPos.z + ((rand.nextDouble() - 0.5D) * radius), 6, 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.2f);
        }
    }

    @SuppressWarnings("removal")
    public static boolean onePerTypeEquip(SlotContext slotContext, ItemStack stack) {
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(slotContext.getWearer(), stack.getItem());
        for (SlotResult slot : curioSlots) {
            items.add(slot.stack());
        }

        return items.isEmpty() || slotContext.cosmetic();
    }

    public static void chanceEffect(LivingEntity pTarget, ImmutableList<MobEffectInstance> effects, float chance, ArcRandom arcRandom) {
        if (!effects.isEmpty()) {
            if (chance < 1) {
                for (MobEffectInstance effectInstance : effects) {
                    if (arcRandom.chance(chance)) {
                        pTarget.addEffect(new MobEffectInstance(effectInstance));
                    }
                }
            } else {
                for (MobEffectInstance effectInstance : effects) {
                    pTarget.addEffect(new MobEffectInstance(effectInstance));
                }
            }
        }
    }

    public static ToIntFunction<BlockState> setLightValue(int pValue) {
        return (state) -> !state.isAir() ? pValue : 0;
    }

    public static ToIntFunction<BlockState> getLightValueLit() {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0;
    }

    public static ToIntFunction<BlockState> getPlantLightValue() {
        return (state) -> !state.isAir() ? 9 : 0;
    }

    public static class tileEntity {
        public static void SUpdateTileEntityPacket(BlockEntity tile) {
            if (tile.getLevel() instanceof ServerLevel) {
                Packet<?> packet = tile.getUpdatePacket();
                if (packet != null) {
                    BlockPos pos = tile.getBlockPos();
                    ((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap
                            .getPlayers(new ChunkPos(pos), false)
                            .forEach(e -> e.connection.send(packet));
                }
            }
        }
    }

    public static class scheduler {
        private static ScheduledExecutorService scheduler = null;
        private static final HashMultimap<Integer, Runnable> scheduledSynchTasks = HashMultimap.create();

        public static void scheduleAsyncTask(Runnable run, int time, TimeUnit unit) {
            if (scheduler == null) {
                serverStartupTasks();
            }

            scheduler.schedule(run, time, unit);
        }

        public static void serverStartupTasks() {
            if (scheduler != null) {
                scheduler.shutdownNow();
            }

            scheduler = Executors.newScheduledThreadPool(1);
            handleSyncScheduledTasks(null);
        }

        public static void handleSyncScheduledTasks(@Nullable Integer tick) {
            if (scheduledSynchTasks.containsKey(tick)) {
                Iterator<Runnable> tasks = tick == null ? scheduledSynchTasks.values().iterator() : scheduledSynchTasks.get(tick).iterator();
                while (tasks.hasNext()) {
                    try {
                        tasks.next().run();
                    } catch (Exception ex) {
                        System.out.print(ex.getMessage());
                    }

                    tasks.remove();
                }
            }
        }
    }
}