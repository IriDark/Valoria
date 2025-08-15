package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class KingCrabEntity extends MultiAttackMob{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState splashAttackAnimationState = new AnimationState();
    public final AnimationState hookAttackAnimationState = new AnimationState();
    public final AnimationState hideAnimationState = new AnimationState();
    public final AnimationState revealAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> HITS_NEEDED = SynchedEntityData.defineId(KingCrabEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HIDE_ANIMATION_STARTED = SynchedEntityData.defineId(KingCrabEntity.class, EntityDataSerializers.BOOLEAN);

    private int idleAnimationTimeout = 0;
    public int shieldHurtTime = 0;
    public int hookAttackAnimationTime = 0;
    public int splashAttackAnimationTime = 0;
    private int animatedDeathTime;
    private boolean initAnim;

    public KingCrabEntity(EntityType<? extends KingCrabEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        this.xpReward = 25;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof KingCrabEntity;
    }

    public void setHits(int hitsNeeded) {
        this.entityData.set(HITS_NEEDED, hitsNeeded);
    }

    public void setHideAnimationState(boolean bool) {
        this.entityData.set(HIDE_ANIMATION_STARTED, bool);
    }

    public boolean getHideAnimationState() {
        return this.entityData.get(HIDE_ANIMATION_STARTED);
    }

    public int getHits() {
        return this.entityData.get(HITS_NEEDED);
    }

    @Override
    public void tick(){
        super.tick();
        shieldHurtTime--;
        if(getHits() > 0){
            if(this.tickCount % 20 == 0){
                this.setHits(getHits() - 1);
                this.heal(0.5f);
            }
        }

        if(this.hookAttackAnimationTime > 0) {
            this.hookAttackAnimationTime--;
            if (this.hookAttackAnimationTime == this.attackDelay() && this.getTarget() != null && this.getTarget().isAlive() && this.isWithinAttackRange(this.getTarget(), 3)) {
                KingCrabEntity entity = KingCrabEntity.this;
                LivingEntity target = KingCrabEntity.this.getTarget();
                entity.doHurtTarget(target);
                Vec3 direction = entity.position().subtract(target.position()).normalize();
                double strength = Mth.clamp(entity.distanceTo(target) * 0.2D, 0.25D, 1.5D);

                target.setDeltaMovement(direction.scale(strength));
                target.hurtMarked = true;
            }
        }

        if(this.splashAttackAnimationTime > 0) {
            this.splashAttackAnimationTime--;
            if (this.splashAttackAnimationTime == this.attackDelay() && this.getTarget() != null && this.getTarget().isAlive() && this.isWithinAttackRange(this.getTarget(), 5)) {
                LivingEntity livingentity = KingCrabEntity.this.getTarget();
                if(livingentity != null){
                    double d0 = Math.min(livingentity.getY(), KingCrabEntity.this.getY());
                    double d1 = Math.max(livingentity.getY(), KingCrabEntity.this.getY()) + 1.0D;
                    float f = (float)Mth.atan2(livingentity.getZ() - KingCrabEntity.this.getZ(), livingentity.getX() - KingCrabEntity.this.getX());
                    if(KingCrabEntity.this.distanceToSqr(livingentity) > 6.0D){
                        for(int i = 0; i < 6; ++i){
                            float f1 = f + (float)i * (float)Math.PI * 0.4F;
                            this.createSpellEntity(KingCrabEntity.this.getX() + (double)Mth.cos(f1) * 2.5D, KingCrabEntity.this.getZ() + (double)Mth.sin(f1) * 2.5D, d0, d1, f1, 0);
                        }

                        for(int k = 0; k < 12; ++k){
                            float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
                            this.createSpellEntity(KingCrabEntity.this.getX() + (double)Mth.cos(f2) * 4.5D, KingCrabEntity.this.getZ() + (double)Mth.sin(f2) * 4.5D, d0, d1, f2, 3);
                        }
                    }
                }
            }
        }

        if (getHits() == 0 && getHideAnimationState() && this.tickCount % 20 == 0) {
            this.hideAnimationState.stop();
            this.revealAnimationState.start(this.tickCount);
            this.setHideAnimationState(false);
        }

        if (level().isClientSide && !initAnim && tickCount > 10) {
            if (getHits() > 0) hideAnimationState.start(tickCount);
            initAnim = true;
        }

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void createSpellEntity(double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay){
        BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
        boolean flag = false;
        double d0 = 0.0D;
        do{
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = KingCrabEntity.this.level().getBlockState(blockpos1);
            if(blockstate.isFaceSturdy(KingCrabEntity.this.level(), blockpos1, Direction.UP)){
                if(!KingCrabEntity.this.level().isEmptyBlock(blockpos)){
                    BlockState blockstate1 = KingCrabEntity.this.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(KingCrabEntity.this.level(), blockpos);
                    if(!voxelshape.isEmpty()){
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        }while(blockpos.getY() >= Mth.floor(pMinY) - 1);
        if(flag){
            KingCrabEntity.this.level().addFreshEntity(new WaterBubble(KingCrabEntity.this.level(), pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, 4, KingCrabEntity.this));
        }
    }

    public double getAttackRangeSqr(LivingEntity pEntity, double range) {
        return (KingCrabEntity.this.getBbWidth() * range * KingCrabEntity.this.getBbWidth() * range + pEntity.getBbWidth());
    }

    public double getPerceivedTargetDistanceSquareForAttack(LivingEntity pEntity) {
        return KingCrabEntity.this.distanceToSqr(pEntity.position());
    }

    public boolean isWithinAttackRange(LivingEntity pEntity, double range) {
        double d0 = this.getPerceivedTargetDistanceSquareForAttack(pEntity);
        return d0 <= this.getAttackRangeSqr(pEntity, range);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            if(this.getHits() == 0){
                this.idleAnimationTimeout = 50;
                this.idleAnimationState.start(this.tickCount);
            }
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 4){
            this.attackAnimationState.start(this.tickCount);
        } else if(pId == 3){
            this.hideAnimationState.stop();
            this.deathAnimationState.start(this.tickCount);

            SoundEvent soundevent = this.getDeathSound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.setHealth(0.0F);
            this.die(this.damageSources().generic());
        } else if(pId == 60){
            this.setHideAnimationState(true);
            this.hideAnimationState.start(this.tickCount);
        } else if(pId == 61){
            this.setHideAnimationState(false);
            this.hideAnimationState.stop();
            this.revealAnimationState.start(this.tickCount);
        } else if(pId == 62) {
            shieldHurtTime = 10;
            for(int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.SMOKE, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        } else if(pId == 65) {
            this.splashAttackAnimationState.start(this.tickCount);
        } else if(pId == 64) {
            this.hookAttackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    /**
     * Handles entity death timer, experience orb, and particle creation.
     */
    protected void tickDeath() {
        ++this.animatedDeathTime;
        if (this.animatedDeathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource){
        if(this.getHits() > 0) return !pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        return super.isInvulnerableTo(pSource);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount){
        if(getHits() > 0){
            this.setHits(getHits() - 1);
            this.level().playSound(null, this.blockPosition(), SoundEvents.SNIFFER_EGG_CRACK, SoundSource.HOSTILE, 2, 1);
            this.level().broadcastEntityEvent(this, (byte)62);

            if(pSource.getDirectEntity() != null) pSource.getDirectEntity().hurt(this.level().damageSources().thorns(this), pAmount * 0.15f);
        }

        return super.hurt(pSource, pAmount);
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new KingCrabSplashAttack());
        this.goalSelector.addGoal(0, new HideInShell(25));
        this.goalSelector.addGoal(0, new KingCrabHookAttack());
        this.goalSelector.addGoal(4, new KingCrabAttack(this, 1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, Objects::nonNull));
        this.targetSelector.addGoal(3, new AvoidEntityGoal<>(this, IronGolem.class, 16, 1, 2));
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getPassengersRidingOffset(){
        return this.getBbHeight() * 0.75F;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("HitsNeeded", this.getHits());
        pCompound.putBoolean("AnimationStarted", this.getHideAnimationState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.setHits(pCompound.getInt("HitsNeeded"));
        this.setHideAnimationState(pCompound.getBoolean("AnimationStarted"));
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(HITS_NEEDED, 0);
        this.entityData.define(HIDE_ANIMATION_STARTED, false);
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock){
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    /**
     * Static predicate for determining whether a monster can spawn at the provided location, incorporating a check of
     * the current light level at the location.
     */
    public static boolean checkMonsterSpawnRules(EntityType<? extends KingCrabEntity> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && isBrightEnoughToSpawn(pLevel, pPos);
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos){
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    public MobType getMobType(){
        return MobType.ARTHROPOD;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
        return 0.65F;
    }

    public boolean isVisuallySwimming(){
        return this.isSwimming();
    }

    public boolean checkSpawnObstruction(LevelReader pLevel){
        return pLevel.isUnobstructed(this);
    }

    @Override
    protected boolean isImmobile(){
        return super.isImmobile() || this.getHits() > 0;
    }

    @Override
    public boolean isEffectiveAi(){
        return super.isEffectiveAi() || this.getHits() > 0;
    }

    public class HideInShell extends AttackGoal {
        public int hits;
        public HideInShell(int hits) {
            this.hits = hits;
        }

        @Override
        public void onPrepare(){
        }

        @Override
        public boolean canUse(){
            return super.canUse() && KingCrabEntity.this.getHealth() < KingCrabEntity.this.getMaxHealth() * 0.3f && Tmp.rnd.chance(0.25f);
        }

        @Override
        public void start(){
            KingCrabEntity.this.setHits(hits);
            KingCrabEntity.this.setHideAnimationState(true);
            super.start();
        }

        @Override
        protected void performAttack(){
            KingCrabEntity.this.level().broadcastEntityEvent(KingCrabEntity.this, (byte)60);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 600;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.BLOCK;
        }
    }

    public class KingCrabHookAttack extends AttackGoal {

        @Override
        public void onPrepare(){
            KingCrabEntity.this.hookAttackAnimationTime = 20;
            KingCrabEntity.this.level().broadcastEntityEvent(KingCrabEntity.this, (byte) 64);
        }

        @Override
        protected void performAttack(){
        }

        @Override
        public boolean canUse(){
            return super.canUse() && KingCrabEntity.this.distanceTo(KingCrabEntity.this.getTarget()) > 2 && isWithinAttackRange(KingCrabEntity.this.getTarget(), 3);
        }

        @Override
        public int getPreparingTime(){
            return 30;
        }

        @Override
        public int getAttackInterval(){
            return 250;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.THROW;
        }
    }

    public class KingCrabSplashAttack extends AttackGoal {

        @Override
        public void onPrepare(){
            KingCrabEntity.this.level().broadcastEntityEvent(KingCrabEntity.this, (byte)65);
            KingCrabEntity.this.splashAttackAnimationTime = 30;
        }

        @Override
        public boolean canUse(){
            return super.canUse() && KingCrabEntity.this.distanceToSqr(KingCrabEntity.this.getTarget()) > 6.0D && isWithinAttackRange(KingCrabEntity.this.getTarget(), 5);
        }

        @Override
        protected void performAttack(){
        }

        @Override
        public int getPreparingTime(){
            return 40;
        }

        @Override
        public int getAttackInterval(){
            return 320;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.RADIAL;
        }
    }

    public class KingCrabAttack extends TridotMeleeAttackGoal{
        public KingCrabAttack(MultiAttackMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        public int attackAnimationTick(){
            return 15;
        }

        @Override
        public void beforeAttack() {
            super.beforeAttack();
            mob.level().broadcastEntityEvent(mob, (byte) 4);
        }

        @Override
        public int getPreparingTime() {
            return 20;
        }

        @Override
        public int getAttackInterval() {
            return 20;
        }

        @Override
        public SoundEvent getPrepareSound() {
            return SoundEvents.IRON_GOLEM_ATTACK;
        }

        @Override
        public SoundEvent getAttackSound() {
            return SoundEvents.PLAYER_ATTACK_STRONG;
        }

        @Override
        public AttackRegistry getAttack() {
            return EntityStatsRegistry.MELEE;
        }
    }
}