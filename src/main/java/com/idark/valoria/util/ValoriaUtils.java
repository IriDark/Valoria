package com.idark.valoria.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.idark.valoria.registries.EnchantmentsRegistry;
import com.idark.valoria.registries.entity.living.NecromancerEntity;
import com.idark.valoria.registries.item.types.BeastScytheItem;
import com.idark.valoria.registries.item.types.CoralReefItem;
import com.idark.valoria.registries.item.types.curio.charm.BloodSight;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLLoader;
import org.joml.Vector3d;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

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

    /**
     * Performs a spin attack with checking a collision with targets
     */

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
         * @param pOwner     Owner of Projectile
         * @param boundingBox radius example:
         * <p>
         * <pre>{@code new AABB(projectile.getX() - 3.5, projectile.getY() - 0.5, projectile.getZ() - 3.5, projectile.getX() + 3.5, projectile.getY() + 0.5, projectile.getZ() + 3.5);
         *}</pre>
         */
    public static void homingMovement(double pSpeed, Entity projectile, Level level, Entity pOwner, AABB boundingBox) {
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
                    double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
                    projectile.setDeltaMovement(dX / distance * pSpeed, dY / distance * pSpeed, dZ / distance * pSpeed);
                    //projectile.addDeltaMovement(new Vec3(dX / distance * pSpeed, dY / distance * pSpeed, dZ / distance * pSpeed));
                }
            }
        }
    }

    /**
     * Can be used in projectile tick() method.
     * Projectile will have a homing movement to nearby entity
     * @param pOwner     Owner of Projectile
     * @param boundingBox radius example:
     * <p>
     * <pre>{@code new AABB(projectile.getX() - 3.5, projectile.getY() - 0.5, projectile.getZ() - 3.5, projectile.getX() + 3.5, projectile.getY() + 0.5, projectile.getZ() + 3.5);
     *}</pre>
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
                    double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
                    projectile.addDeltaMovement(new Vec3(dX / distance * pSpeed, dY / distance * pSpeed, dZ / distance * pSpeed));
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

    public static void addEffectsTooltip(ImmutableList<MobEffectInstance> effects, List<Component> pTooltips, float pDurationFactor, float chance) {
        List<Pair<Attribute, AttributeModifier>> list = Lists.newArrayList();
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
                Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier entryValue = entry.getValue();
                        AttributeModifier attributeModifier = new AttributeModifier(entryValue.getName(), mobeffect.getAttributeModifierValue(mobeffectinstance.getAmplifier(), entryValue), entryValue.getOperation());
                        list.add(new Pair<>(entry.getKey(), attributeModifier));
                    }
                }

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
     *
     * @param pPos Position to start from
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

    public static class color {
        public static int getAlpha(int packedColor) {
            return packedColor >>> 24;
        }

        public static int getRed(int packedColor) {
            return packedColor >> 16 & 255;
        }

        public static int getGreen(int packedColor) {
            return packedColor >> 8 & 255;
        }

        public static int getBlue(int packedColor) {
            return packedColor & 255;
        }

        public static int packColor(int alpha, int red, int green, int blue) {
            return alpha << 24 | red << 16 | green << 8 | blue;
        }

        public static int packColor(float red, float green, float blue) {
            return ((int)(red * 255.0F) & 255) << 16 | ((int)(green * 255.0F) & 255) << 8 | (int)(blue * 255.0F) & 255;
        }

        public static int packColor(float alpha, float red, float green, float blue) {
            return ((int)(alpha * 255.0F) & 255) << 24 | ((int)(red * 255.0F) & 255) << 16 | ((int)(green * 255.0F) & 255) << 8 | (int)(blue * 255.0F) & 255;
        }

        public static int hexToDecimal(String hex) {
            return Integer.parseInt(hex, 16);
        }
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