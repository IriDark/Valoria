package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.common.registry.entity.*;

import java.util.*;

public class WickedScorpion extends MultiAttackMob implements RangedAttackMob, Enemy{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState tailAttackAnimationState = new AnimationState();
    public final AnimationState spitAttackAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    public int tailAttackAnimationTime = 0;
    public int spitAttackAnimationTime = 0;
    private int animatedDeathTime;

    public WickedScorpion(EntityType<? extends WickedScorpion> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        this.xpReward = Enemy.XP_REWARD_HUGE;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof WickedScorpion;
    }

    @Override
    public void tick(){
        super.tick();
        if(attackAnimationTick > 0 && this.getTarget() != null) {
            this.lookAt(this.getTarget(), 180, 360);
        }

        if(this.spitAttackAnimationTime > 0) {
            this.spitAttackAnimationTime--;
            if (this.spitAttackAnimationTime == this.attackDelay() && this.getTarget() != null && this.getTarget().isAlive() && this.isWithinAttackRange(this.getTarget(), 15)) {
                LivingEntity livingentity = WickedScorpion.this.getTarget();
                if(livingentity != null){
                    double d0 = this.distanceToSqr(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
                    float f = (float)Math.sqrt(d0) / 16;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    this.performRangedAttack(this.getTarget(), f1);
                }
            }
        }

        if(this.tailAttackAnimationTime > 0) {
            this.tailAttackAnimationTime--;
            if (this.tailAttackAnimationTime == this.attackDelay() && this.getTarget() != null && this.getTarget().isAlive() && this.isWithinAttackRange(this.getTarget(), 4)) {
                LivingEntity livingentity = WickedScorpion.this.getTarget();
                if(livingentity != null){
                    doHurtTarget(livingentity);
                    livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0));
                }
            }
        }

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        AcidSpit spit = new AcidSpit(this, this.level());
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - spit.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spit.setBaseDamage(4);
        spit.addEffect(new MobEffectInstance(MobEffects.POISON, 50, 0));
        spit.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.LLAMA_SPIT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(spit);
    }

    public double getAttackRangeSqr(LivingEntity pEntity, double range) {
        return (WickedScorpion.this.getBbWidth() * range * WickedScorpion.this.getBbWidth() * range + pEntity.getBbWidth());
    }

    public double getPerceivedTargetDistanceSquareForAttack(LivingEntity pEntity) {
        return WickedScorpion.this.distanceToSqr(pEntity.position());
    }

    public boolean isWithinAttackRange(LivingEntity pEntity, double range) {
        double d0 = this.getPerceivedTargetDistanceSquareForAttack(pEntity);
        return d0 <= this.getAttackRangeSqr(pEntity, range);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 4){
            this.attackAnimationState.start(this.tickCount);
        } else if(pId == 3){
            this.deathAnimationState.start(this.tickCount);

            SoundEvent soundevent = this.getDeathSound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.setHealth(0.0F);
            this.die(this.damageSources().generic());
        } else if(pId == 60){
            this.tailAttackAnimationState.start(this.tickCount);
        } else if(pId == 61) {
            this.spitAttackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new ScorpionTailAttack());
        this.goalSelector.addGoal(0, new ScorpionSpitAttack());
        this.goalSelector.addGoal(3, new ScorpionAttack(this, 1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, Objects::nonNull));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ShadewoodSpider.class, 10, true, false, Objects::nonNull));
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getPassengersRidingOffset(){
        return this.getBbHeight() * 0.75F;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock){
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    /**
     * Static predicate for determining whether a monster can spawn at the provided location, incorporating a check of
     * the current light level at the location.
     */
    public static boolean checkMonsterSpawnRules(EntityType<? extends WickedScorpion> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL;
    }

    public MobType getMobType(){
        return MobType.ARTHROPOD;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
        return 0.65F;
    }

    public class ScorpionSpitAttack extends AttackGoal{

        @Override
        public void onPrepare(){
            WickedScorpion.this.level().broadcastEntityEvent(WickedScorpion.this, (byte)61);
            WickedScorpion.this.spitAttackAnimationTime = 10;
        }

        @Override
        public boolean canUse(){
            return super.canUse() && WickedScorpion.this.distanceToSqr(WickedScorpion.this.getTarget()) > 12.0D && isWithinAttackRange(WickedScorpion.this.getTarget(), 12);
        }

        @Override
        protected void performAttack(){
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 160;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return SoundEvents.HOGLIN_ATTACK;
        }

        @Override
        public SoundEvent getAttackSound(){
            return SoundEvents.LLAMA_SPIT;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.THROW;
        }
    }

    public class ScorpionTailAttack extends AttackGoal{

        @Override
        public void onPrepare(){
            WickedScorpion.this.level().broadcastEntityEvent(WickedScorpion.this, (byte)60);
            WickedScorpion.this.tailAttackAnimationTime = 20;
        }

        @Override
        public boolean canUse(){
            return super.canUse() && WickedScorpion.this.distanceToSqr(WickedScorpion.this.getTarget()) > 6.0D && isWithinAttackRange(WickedScorpion.this.getTarget(), 3) && WickedScorpion.this.hasLineOfSight(WickedScorpion.this.getTarget());
        }

        @Override
        protected void performAttack(){
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 120;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public SoundEvent getAttackSound(){
            return SoundEvents.CONDUIT_ATTACK_TARGET;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.MELEE;
        }
    }

    public class ScorpionAttack extends TridotMeleeAttackGoal{
        public ScorpionAttack(MultiAttackMob mob, double speedModifier) {
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
            return null;
        }  //todo

        @Override
        public SoundEvent getAttackSound() {
            return SoundEvents.PHANTOM_BITE;
        } // todo

        @Override
        public AttackRegistry getAttack() {
            return EntityStatsRegistry.MELEE;
        }
    }
}