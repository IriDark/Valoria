package com.idark.valoria.registries.entity.living;

import net.minecraft.core.particles.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import pro.komaru.tridot.registry.entity.ai.goals.*;

public class HauntedMerchant extends AbstractHauntedMerchant{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    public final AnimationState rangedAttackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public HauntedMerchant(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 5;
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);

        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public void handleEntityEvent(byte pId){
        if(pId == 4) this.meleeAttackAnimationState.start(this.tickCount);
        if(pId == 13) this.addParticlesAroundSelf(ParticleTypes.FLAME);
        if(pId == 14) this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        super.handleEntityEvent(pId);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(0, new DelayedMeleeAttackGoal(this, 1, 60, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
    }

    public void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount){
        boolean flag = super.hurt(pSource, pAmount);
        if(!this.level().isClientSide && !this.isNoAi()){
            Entity entity = pSource.getEntity();
            if(entity instanceof LivingEntity living){
                if(!pSource.isIndirect()) this.setPersistentAngerTarget(living.getUUID());
            }
        }

        return flag;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity){
        HauntedMerchant.this.level().broadcastEntityEvent(HauntedMerchant.this, (byte)4);
        return super.doHurtTarget(pEntity);
    }

    protected void addParticlesAroundSelf(ParticleOptions pParticleOption){
        for(int i = 0; i < 4; ++i){
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(pParticleOption, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }
}