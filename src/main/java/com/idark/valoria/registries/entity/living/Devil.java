package com.idark.valoria.registries.entity.living;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.goals.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;
import java.util.*;

public class Devil extends AbstractDevil implements RangedAttackMob{
    public static final AttackRegistry FIRE_RAY = new AttackRegistry(Valoria.ID, "fire_ray");
    public final AnimationState idleAnimationState = new AnimationState();
    public AnimationState fireballAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public Devil(EntityType<? extends Devil> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 62) this.fireballAnimationState.start(this.tickCount);
        super.handleEntityEvent(pId);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 120;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // panic reason
    public final boolean isLowHP() {
        return this.getHealth() < 10;
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemsRegistry.infernalSpear.get().getDefaultInstance());
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(0, new FireRayGoal(this, 1.0D, 10.0F));
        this.goalSelector.addGoal(0, new ReasonableAvoidEntityGoal<>(this, Player.class, 15, 1.25, 2, isLowHP()));

        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.2));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity){
        ThrownSpearEntity spear = new ThrownSpearEntity(this.level(), this, new ItemStack(ItemsRegistry.infernalSpear.get()));
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - spear.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spear.setBaseDamage(6);
        spear.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(spear);
    }

    public class FireRayGoal extends AttackGoal {
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private LivingEntity target;
        private final double speedModifier;
        private int seeTime;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public FireRayGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, float pAttackRadius){
            this.rangedAttackMob = pRangedAttackMob;
            this.mob = (Mob)pRangedAttackMob;
            this.speedModifier = pSpeedModifier;
            this.attackRadius = pAttackRadius;
            this.attackRadiusSqr = pAttackRadius * pAttackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                this.target = livingentity;
                return true;
            } else {
                return false;
            }
        }
        @Override
        public void start(){
            super.start();
            Devil.this.getNavigation().stop();
        }

        @Override
        protected void performAttack(){
            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
            if (flag) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }

            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, f1);
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        }

        @OnlyIn(Dist.CLIENT)
        public ParticleOptions getParticles() {
            return ParticleBuilder.create(FluffyFurParticles.WISP)
            .setColorData(ColorParticleData.create(Pal.amber).build())
            .randomVelocity(0.5f)
            .getParticleOptions();
        }

        @Override
        public void tick(){
            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 5) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }

            super.tick();
            this.mob.getLookControl().setLookAt(this.target.position());
            if(this.attackWarmupDelay == 0){
                Vec3 vec3 = this.mob.position().add(0.0D, 1.6F, 0.0D);
                Vec3 vec31 = this.mob.getTarget().getEyePosition().subtract(vec3);
                Vec3 vec32 = vec31.normalize();
                if(this.mob.level().isClientSide()){
                    for(int i = 1; i < Mth.floor(vec31.length()) + 7; ++i){
                        Vec3 vec33 = vec3.add(vec32.scale(i));
                        this.mob.level().addParticle(getParticles(), vec33.x, vec33.y, vec33.z, 0.0D, 0.0D, 0.0D);
                    }
                }

                Devil.this.level().broadcastEntityEvent(Devil.this, (byte) 62);
            }
        }

        @Override
        public int getPreparingTime(){
            return 120;
        }

        @Override
        public int getAttackInterval(){
            return 400;
        }

        @Override
        public SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return FIRE_RAY;
        }
    }
}
