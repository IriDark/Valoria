package com.idark.valoria.registries.entity.living.decoration;

import net.minecraft.core.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.vehicle.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.extensions.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public abstract class AbstractDecorationMob extends Mob implements IForgeEntity {

    private static final Predicate<Entity> RIDABLE_MINECARTS = (p_31582_) -> p_31582_ instanceof AbstractMinecart && ((AbstractMinecart) p_31582_).canBeRidden();

    protected AbstractDecorationMob(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public boolean canBeLeashed(@NotNull Player pPlayer) {
        return false;
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

    protected float tickHeadTurn(float pYRot, float pAnimStep) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return 0.0F;
    }

    public @NotNull PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public boolean skipAttackInteraction(@NotNull Entity pEntity) {
        return pEntity instanceof Player && !this.level().mayInteract((Player) pEntity, this.blockPosition());
    }

    public @NotNull Vec3 getLightProbePosition(float pPartialTicks) {
        return super.getLightProbePosition(pPartialTicks);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
