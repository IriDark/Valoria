package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.dimension.*;
import net.minecraft.world.level.pathfinder.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import java.lang.Math;
import java.util.*;

public class SorcererEntity extends MultiAttackMob implements Enemy, RangedAttackMob{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackHatAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState healAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public SkeletonMovement movement = new SkeletonMovement(this);

    public SorcererEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public SorcererEntity(Level pLevel){
        this(EntityTypeRegistry.SORCERER.get(), pLevel);
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }

        movement.setupMovement();
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public boolean isAlliedTo(Entity pEntity){
        if(super.isAlliedTo(pEntity)){
            return true;
        }else if(pEntity instanceof LivingEntity && ((LivingEntity)pEntity).getMobType() == MobType.UNDEAD){
            return this.getTeam() == null && pEntity.getTeam() == null;
        }else{
            return false;
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(0, new CastSpellGoal(this, 16));
        this.goalSelector.addGoal(0, new HealTargetSpell());

        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Wolf.class, 8, 1.5, 1.5));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 62){
            if(Tmp.rnd.chance(0.75f)){
                this.attackAnimationState.start(this.tickCount);
            }else{
                this.attackHatAnimationState.start(this.tickCount);
            }
        }else if(pId == 64){
            this.healAnimationState.start(this.tickCount);
        }else{
            super.handleEntityEvent(pId);
        }
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity){
        SpellProjectile spell = new SpellProjectile(this.level(), this, 6);
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - spell.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spell.setColor(Col.fromHex("ffab51"));
        spell.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.level().addFreshEntity(spell);
    }

    public class HealTargetSpell extends AttackGoal{
        private final TargetingConditions targeting = TargetingConditions.forCombat().range(6.0D);

        public HealTargetSpell(){
            this.setFlags(EnumSet.of(Flag.TARGET, Goal.Flag.LOOK));
        }

        public boolean requiresUpdateEveryTick(){
            return true;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(!super.canUse()){
                return false;
            }else{
                List<Monster> targets = SorcererEntity.this.level().getNearbyEntities(Monster.class, this.targeting, SorcererEntity.this, SorcererEntity.this.getBoundingBox().inflate(6.0D));
                return !targets.isEmpty();
            }
        }

        @Override
        protected void performAttack(){
            ServerLevel serverLevel = (ServerLevel)SorcererEntity.this.level();
            List<Monster> targets = serverLevel.getNearbyEntities(Monster.class, this.targeting, SorcererEntity.this, SorcererEntity.this.getBoundingBox().inflate(6.0D));
            List<LivingEntity> toHeal = new ArrayList<>();
            for(Monster target : targets){
                if(target.getHealth() < target.getMaxHealth()){
                    Vector3d pos = new Vector3d(SorcererEntity.this.getX(), SorcererEntity.this.getY(), SorcererEntity.this.getZ());
                    Utils.Particles.inRadius(serverLevel, null, ParticleTypes.HAPPY_VILLAGER, pos, 0, SorcererEntity.this.getRotationVector().y, 6);
                    Utils.Hit.healNearbyMobs(MobCategory.MONSTER, 4.0F, serverLevel, SorcererEntity.this, toHeal, pos, 0, SorcererEntity.this.getRotationVector().y, 6);
                    serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.EVOKER_CAST_SPELL, target.getSoundSource(), 0.42F, 1.23F);
                    break;
                }
            }
        }

        public boolean isInterruptable(){
            return false;
        }

        @Override
        public void onPrepare(){
            SorcererEntity.this.level().broadcastEntityEvent(SorcererEntity.this, (byte)64);
        }

        @Override
        public int getPreparingTime(){
            return 80;
        }

        @Override
        public int getAttackInterval(){
            return 160;
        }

        @Override
        public SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.HEAL;
        }
    }

    public class CastSpellGoal extends AttackGoal{
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private final float attackRadius;

        public CastSpellGoal(RangedAttackMob pRangedAttackMob, float pAttackRadius){
            this.rangedAttackMob = pRangedAttackMob;
            this.mob = (Mob)pRangedAttackMob;
            this.attackRadius = pAttackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean isInterruptable(){
            return false;
        }

        public boolean requiresUpdateEveryTick(){
            return true;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            LivingEntity livingentity = this.mob.getTarget();
            return super.canUse() && livingentity != null;
        }

        @Override
        protected void performAttack(){
            double d0 = this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ());
            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.mob.getTarget(), f1);
        }

        @Override
        public void onPrepare(){
            SorcererEntity.this.level().broadcastEntityEvent(SorcererEntity.this, (byte)62);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 70;
        }

        @Override
        public SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.MAGIC;
        }
    }

    /**
     * Static predicate for determining whether a monster can spawn at the provided location, incorporating a check of
     * the current light level at the location.
     */
    public static boolean checkMonsterSpawnRules(EntityType<? extends SorcererEntity> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom);
    }

    /**
     * Static predicate for determining if the current light level and environmental conditions allow for a monster to
     * spawn.
     */
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor pLevel, BlockPos pPos, RandomSource pRandom){
        if(pLevel.getBrightness(LightLayer.SKY, pPos) > pRandom.nextInt(32)){
            return false;
        }else{
            DimensionType dimensiontype = pLevel.dimensionType();
            int i = dimensiontype.monsterSpawnBlockLightLimit();
            if(i < 15 && pLevel.getBrightness(LightLayer.BLOCK, pPos) > i){
                return false;
            }else{
                int j = pLevel.getLevel().isThundering() ? pLevel.getMaxLocalRawBrightness(pPos, 10) : pLevel.getMaxLocalRawBrightness(pPos);
                return j <= dimensiontype.monsterSpawnLightTest().sample(pRandom);
            }
        }
    }
}
