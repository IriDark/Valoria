package com.idark.valoria.registries.entity.living;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AbstractMinionEntity extends Monster implements TraceableEntity {
    @Nullable
    public Mob owner;
    @Nullable
    public BlockPos boundOrigin;
    public boolean hasLimitedLife;
    public int limitedLifeTicks;

    protected AbstractMinionEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.hurt(this.damageSources().starve(), 1.0F);
        }

        if (this.shouldRenderAtSqrDistance(4)) {
            spawnParticlesTrail();
        }
    }

    public void spawnParticlesTrail() {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
        }

        if (pCompound.contains("LifeTicks")) {
            this.setLimitedLife(pCompound.getInt("LifeTicks"));
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.boundOrigin != null) {
            pCompound.putInt("BoundX", this.boundOrigin.getX());
            pCompound.putInt("BoundY", this.boundOrigin.getY());
            pCompound.putInt("BoundZ", this.boundOrigin.getZ());
        }

        if (this.hasLimitedLife) {
            pCompound.putInt("LifeTicks", this.limitedLifeTicks);
        }
    }

    @Nullable
    public Mob getOwner() {
        return this.owner;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
        this.boundOrigin = pBoundOrigin;
    }

    public void setOwner(Mob pOwner) {
        this.owner = pOwner;
    }

    public void setLimitedLife(int pLimitedLifeTicks) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = pLimitedLifeTicks;
    }

    class CopyOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();


        public CopyOwnerTargetGoal(PathfinderMob pMob) {
            super(pMob, false);
        }

        public boolean canUse() {
            return AbstractMinionEntity.this.owner != null && AbstractMinionEntity.this.owner.getTarget() != null && this.canAttack(AbstractMinionEntity.this.owner.getTarget(), this.copyOwnerTargeting);
        }

        public void start() {
            AbstractMinionEntity.this.setTarget(AbstractMinionEntity.this.owner.getTarget());
            super.start();
        }
    }
}
