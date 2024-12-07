package com.idark.valoria.registries.entity.living;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.goals.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;

public class Succubus extends AbstractSuccubus {
    public static final AttackRegistry FIRE_RAY = new AttackRegistry(Valoria.ID, "fire_ray");
    public final AnimationState idleAnimationState = new AnimationState();
    public AnimationState fireballAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public Succubus(EntityType<? extends Succubus> pEntityType, Level pLevel) {
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
            this.idleAnimationTimeout = this.random.nextInt(17) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // panic reason
    public final boolean isLowHP() {
        return this.getHealth() < 10;
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(0, new FireRayGoal());
        this.goalSelector.addGoal(0, new ReasonableAvoidEntityGoal<>(this, Player.class, 15, 1.25, 2, isLowHP()));

        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.2));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public class FireRayGoal extends AttackGoal {

        @Override
        public void start(){
            super.start();
            Succubus.this.getNavigation().stop();
        }

        @Override
        protected void performAttack(){
            Succubus.this.level().broadcastEntityEvent(Succubus.this, (byte) 62);
            Succubus.this.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
        }

        protected boolean checkExtraStartConditions(Succubus pOwner) {
            if (!pOwner.hasTarget()) return false;
            LivingEntity target = pOwner.getTarget();
            double distance = pOwner.distanceTo(target);
            if (distance > 15.0D) return false;
            return (pOwner.getNavigation().getPath() != null && !pOwner.getNavigation().getPath().canReach());
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
            Succubus pOwner = Succubus.this;
            if(!checkExtraStartConditions(pOwner)) return;
            super.tick();
            pOwner.getLookControl().setLookAt(pOwner.getTarget().position());
            if(this.attackWarmupDelay == 0){
                Vec3 vec3 = pOwner.position().add(0.0D, 1.6F, 0.0D);
                Vec3 vec31 = pOwner.getTarget().getEyePosition().subtract(vec3);
                Vec3 vec32 = vec31.normalize();
                if(pOwner.level().isClientSide()){
                    for(int i = 1; i < Mth.floor(vec31.length()) + 7; ++i){
                        Vec3 vec33 = vec3.add(vec32.scale(i));
                        pOwner.level().addParticle(getParticles(), vec33.x, vec33.y, vec33.z, 0.0D, 0.0D, 0.0D);
                    }
                }

                pOwner.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                if(pOwner.level() instanceof ServerLevel pLevel) {
                    pOwner.getTarget().hurt(pLevel.damageSources().generic(), 4F);
                }
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
