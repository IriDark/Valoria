package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class Scavenger extends MultiAttackMob implements NeutralMob{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState idleEatingAnimationState = new AnimationState();
    public final AnimationState idleDiggingAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState angryAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    @Nullable
    public UUID persistentAngerTarget;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Scavenger.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 80);
    public static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Scavenger.class, EntityDataSerializers.BOOLEAN);

    public Scavenger(EntityType<? extends Scavenger> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);

        this.xpReward = 5;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof Scavenger;
    }

    public final boolean isLowHP(){
        return this.getHealth() < this.getMaxHealth() * 0.35f;
    }

    @Override
    public void aiStep(){
        super.aiStep();
        if (this.level() instanceof ServerLevel serverLevel){
            this.updatePersistentAnger(serverLevel, true);
            LivingEntity livingentity = this.getTarget();
            UUID uuid = this.getPersistentAngerTarget();
            if((livingentity instanceof Player plr && (plr.isCreative() || isLowHP())) && uuid != null && serverLevel.getEntity(uuid) instanceof LivingEntity){
                this.stopBeingAngry();
            }
        }
    }

    @Override
    public void tick(){
        super.tick();
        if(walkAnimation.isMoving() || this.angryAnimationState.isStarted()){ // prevents bone flying
            this.idleEatingAnimationState.stop();
            this.idleDiggingAnimationState.stop();
            this.idleAnimationState.stop();
        }

        if(walkAnimation.isMoving() || hasTarget()){ // prevents bone flying
            this.idleEatingAnimationState.stop();
            this.idleDiggingAnimationState.stop();
            this.idleAnimationState.stop();
            this.angryAnimationState.stop();
        }else{
            Player player = this.level().getNearestPlayer(this, 12d);
            if(player != null && !player.isCreative() && !this.angryAnimationState.isStarted()){
                this.angryAnimationState.start(this.tickCount);
                this.lookAt(player, 180, 360);
                this.getNavigation().stop();
            }else if((player == null || player.isCreative()) && this.angryAnimationState.isStarted()){
                this.angryAnimationState.stop();
            }
        }

        if(attackAnimationTick > 0 && this.hasTarget()) {
            this.lookAt(this.getTarget(), 180, 360);
        }

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    public int getRemainingPersistentAngerTime(){
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime){
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer(){
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget(){
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID pTarget){
        this.persistentAngerTarget = pTarget;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey){
        if(DATA_BABY_ID.equals(pKey)){
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.getEntityData().define(DATA_BABY_ID, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    @Override
    public float getScale(){
        return this.isBaby() ? 1f : 1.45F;
    }

    public boolean isBaby(){
        return this.getEntityData().get(DATA_BABY_ID);
    }

    public void setBaby(boolean Baby){
        this.getEntityData().set(DATA_BABY_ID, Baby);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleEatingAnimationState.stop();
            this.idleDiggingAnimationState.stop();
            this.idleAnimationState.stop();
            if(Tmp.rnd.chance(0.25)){
                this.idleAnimationTimeout = 180;
                this.idleEatingAnimationState.start(this.tickCount);
            } else if(Tmp.rnd.chance(0.4)) {
                this.idleAnimationTimeout = 120;
                this.idleDiggingAnimationState.start(this.tickCount);
            } else {
                this.idleAnimationTimeout = 60;
                this.idleAnimationState.start(this.tickCount);
            }
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 4){
            this.angryAnimationState.stop();
            this.attackAnimationState.start(this.tickCount);
        }else if(pId == 64){
            var blockParticle = new BlockParticleOptions(TridotParticles.BLOCK.get(), this.level().getBlockState(this.blockPosition().below()));

            ParticleBuilder.create(blockParticle)
            .setGravity(0.75f)
            .setLifetime(15)
            .setScaleData(GenericParticleData.create(0.35f, 0, 0).build())
            .setTransparencyData(GenericParticleData.create(1, 0, 0).setEasing(Interp.exp5Out).build())
            .randomVelocity(0.15f, 0.75f, 0.15f)
            .repeat(this.level(), this.getX(), this.getY(), this.getZ(), 4);
        }else{
            super.handleEntityEvent(pId);
        }
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Scavenger> pAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return isBrightEnoughToSpawn(pLevel, pPos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos) {
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity){
        if (pEntity instanceof LivingEntity entity){
            entity.addEffect(new MobEffectInstance(EffectsRegistry.BLEEDING.get(), 60, 0));
        }

        return super.doHurtTarget(pEntity);
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(0, new AngryGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ScavengerAttackGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, WickedScorpion.class, 6, 1.25, 1.4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, ShadewoodSpider.class, 6, 1.25, 1.4));
        this.goalSelector.addGoal(0, new ReasonableAvoidEntityGoal<>(this, Player.class, 8, 1.25, 1.4, isLowHP()));
        this.goalSelector.addGoal(0, new ReasonablePanicGoal(this, 1.45f));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getPassengersRidingOffset(){
        return this.getBbHeight() * 0.75F;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock){
        this.playSound(SoundEvents.SNIFFER_STEP, 0.15F, 1.0F);
    }

    public class ReasonablePanicGoal extends PanicGoal{
        public ReasonablePanicGoal(PathfinderMob mob, double pSpeedModifier){
            super(mob, pSpeedModifier);
        }

        protected boolean shouldPanic(){
            return this.mob.isFreezing() || this.mob.isOnFire() || Scavenger.this.isLowHP();
        }
    }

    public class ReasonableAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T>{
        public ReasonableAvoidEntityGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, boolean pReason){
            super(pMob, pEntityClassToAvoid, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier);
        }

        public boolean canUse(){
            return super.canUse() && Scavenger.this.isLowHP();
        }
    }

    public class AngryGoal extends Goal {
        private final Scavenger scavenger;
        private @Nullable Player targetPlayer;

        public AngryGoal(Scavenger scavenger) {
            this.scavenger = scavenger;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            if(scavenger.hasTarget() || isLowHP()) return false;
            this.targetPlayer = scavenger.level().getNearestPlayer(scavenger, 6.0D);
            return this.targetPlayer != null && !targetPlayer.isCreative();
        }

        @Override
        public void start() {
            this.scavenger.setTarget(this.targetPlayer);
            this.scavenger.setPersistentAngerTarget(this.targetPlayer.getUUID());
            this.scavenger.getNavigation().stop();
        }
    }

    public class ScavengerAttackGoal extends TridotMeleeAttackGoal{
        public ScavengerAttackGoal(MultiAttackMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        public int attackAnimationTick(){
            return 15;
        }

        @Override
        public boolean canUse(){
            return super.canUse() && !isLowHP();
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
        }

        @Override
        public SoundEvent getAttackSound() {
            return SoundEvents.PHANTOM_BITE;
        }

        @Override
        public AttackRegistry getAttack() {
            return EntityStatsRegistry.MELEE;
        }
    }
}