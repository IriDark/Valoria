package com.idark.valoria.registries.entity.living;

import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.SmokeParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

//todo: rework pathfinding
public class UndeadEntity extends AbstractMinionEntity {
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(UndeadEntity.class, EntityDataSerializers.BYTE);

    public UndeadEntity(EntityType<? extends UndeadEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.xpReward = 3;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FLYING_SPEED, 1)
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.ATTACK_DAMAGE, 4.25);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height - 0.28125F;
    }

    public boolean isFlapping() {
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    @Override
    public void spawnDisappearParticles(ServerLevel serverLevel) {
        double posX = this.getOnPos().getCenter().x;
        double posY = this.getOnPos().above().getCenter().y;
        double posZ = this.getOnPos().getCenter().z;
        PacketHandler.sendToTracking(serverLevel, this.getOnPos(), new SmokeParticlePacket(3, posX, posY - 0.5f, posZ, 0.005f, 0.025f, 0.005f, 255, 255, 255));
    }

    public void spawnParticlesTrail() {
        if (this.isCharging()) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for (int a = 0; a < 0.05f; ++a) {
                this.level().addParticle(ParticleTypes.SCRAPE, this.getX() + a3 * (double) a / 4.0D, this.getY() + a4 * (double) a / 4.0D, this.getZ() + a0 * (double) a / 4.0D, -a3, -a4 + 0.2D, -a0);
            }
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new UndeadEntity.UndeadChargeAttackGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new CopyOwnerTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    private boolean getUndeadFlag(int pMask) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        return (i & pMask) != 0;
    }

    private void setUndeadFlag(int pMask, boolean pValue) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        if (pValue) {
            i |= pMask;
        } else {
            i &= ~pMask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte) (i & 255));
    }

    public boolean isCharging() {
        return this.getUndeadFlag(1);
    }

    public void setIsCharging(boolean pCharging) {
        this.setUndeadFlag(1, pCharging);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    protected void customServerAiStep() {
        BlockPos blockpos = UndeadEntity.this.getBoundOrigin();
        if (blockpos == null) {
            blockpos = UndeadEntity.this.blockPosition();
        }

        BlockPos pos = blockpos.offset(this.random.nextInt(15), this.random.nextInt(5), this.random.nextInt(15));
        if (UndeadEntity.this.level().isEmptyBlock(pos)) {
            UndeadEntity.this.moveControl.setWantedPosition((double) pos.getX() + this.random.nextDouble(), (double) pos.getY() + this.random.nextDouble(), (double) pos.getZ() + this.random.nextDouble(), 1);
        }
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getMyRidingOffset() {
        return 0.4D;
    }

    class UndeadChargeAttackGoal extends Goal {
        public UndeadChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = UndeadEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive() && !UndeadEntity.this.getMoveControl().hasWanted() && UndeadEntity.this.random.nextInt(reducedTickDelay(7)) == 0) {
                return UndeadEntity.this.distanceToSqr(livingentity) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return UndeadEntity.this.getMoveControl().hasWanted() && UndeadEntity.this.isCharging() && UndeadEntity.this.getTarget() != null && UndeadEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity livingentity = UndeadEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                UndeadEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            UndeadEntity.this.setIsCharging(true);
            UndeadEntity.this.playSound(SoundEvents.SKELETON_CONVERTED_TO_STRAY, 1.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            UndeadEntity.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = UndeadEntity.this.getTarget();
            if (livingentity != null) {
                if (UndeadEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    UndeadEntity.this.doHurtTarget(livingentity);
                    UndeadEntity.this.setIsCharging(false);
                } else {
                    double d0 = UndeadEntity.this.distanceToSqr(livingentity);
                    if (d0 < 9.0D) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        UndeadEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }
                }
            }
        }
    }
}