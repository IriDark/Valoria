package com.idark.valoria.registries.entity.decoration;

import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class MannequinEntity extends Mob implements IForgeEntity {

    private static final EntityDataAccessor<Float> LAST_DAMAGE = SynchedEntityData.defineId(MannequinEntity.class, EntityDataSerializers.FLOAT);
    private static final Predicate<Entity> RIDABLE_MINECARTS = (p_31582_) -> p_31582_ instanceof AbstractMinecart && ((AbstractMinecart)p_31582_).canBeRidden();

    public float lastDamageOffset = 0;
    public float lastDamageOffsetPrev = 0;

    public MannequinEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
        this.setMaxUpStep(0.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LAST_DAMAGE, 0f);
    }

    private boolean hasPhysics() {
        return true;
    }

    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && this.hasPhysics();
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity pEntity) {
    }

    protected void pushEntities() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), RIDABLE_MINECARTS);
        for (Entity entity : list) {
            if (this.distanceToSqr(entity) <= 0.2D) {
                entity.push(this);
            }
        }
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0) || d0 == 0.0D) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    public boolean isPickable() {
        return true;
    }

    public boolean isAffectedByPotions() {
        return false;
    }

    public boolean attackable() {
        return false;
    }

    public boolean canBeSeenAsEnemy() {
        return false;
    }

    public boolean canBeSeenByAnyone() {
        return false;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        Level level = pPlayer.level();
        if (pPlayer.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                if (!pPlayer.isCreative()) {
                    level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY() + 0.7D, this.getZ(), ItemsRegistry.MANNEQUIN_SPAWN_EGG.get().getDefaultInstance()));
                }

                this.removeAfterChangingDimensions();
            } else {
                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.POOF, this.getX(), this.getY() + 0.7D, this.getZ(), 0d, 0.02d, 0d);
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 0.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    public boolean skipAttackInteraction(@NotNull Entity pEntity) {
        return pEntity instanceof Player && !this.level().mayInteract((Player)pEntity, this.blockPosition());
    }

    public @NotNull Vec3 getLightProbePosition(float pPartialTicks) {
        return super.getLightProbePosition(pPartialTicks);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        } else {
            BlockState state = Blocks.HAY_BLOCK.defaultBlockState();
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX(), this.getY() + 0.7D, this.getZ(), 0d, 0.02d, 0d);
            }

            if (hurtTime == 0) {
                entityData.set(LAST_DAMAGE, 0f);
                entityData.set(LAST_DAMAGE, amount);
                this.heal(getMaxHealth());
            }

            return super.hurt(source, amount);
        }
    }

    public float getLastDamage() {
        return entityData.get(LAST_DAMAGE);
    }
}