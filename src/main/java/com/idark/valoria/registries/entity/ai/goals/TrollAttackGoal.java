package com.idark.valoria.registries.entity.ai.goals;

import com.idark.valoria.registries.SoundsRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TrollAttackGoal extends MeleeAttackGoal{
    protected int attackWarmupDelay;

    public TrollAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen){
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Nullable
    protected SoundEvent getSound(){
        return SoundsRegistry.TROLL_DISAPPEAR.get();
    }

    public void playSound(){
        SoundEvent soundevent = this.getSound();
        if(soundevent != null){
            mob.playSound(soundevent, 1f, mob.getVoicePitch());
        }
    }

    @Override
    public void tick(){
        --this.attackWarmupDelay;
        super.tick();
    }

    @Override
    public boolean canUse(){
        LivingEntity target = mob.getTarget();
        Level level = mob.level();
        RandomSource random = level.random;
        if(level instanceof ServerLevel server){
            if(target != null && this.attackWarmupDelay == 0){
                server.sendParticles(ParticleTypes.SMOKE, mob.getX(), mob.getY(), mob.getZ(), 6, random.nextFloat() / 2, random.nextFloat() / 2, random.nextFloat() / 2, 0.12f);
                mob.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120, 0, false, false));
                this.attackWarmupDelay = 140;
                this.playSound();
            }
        }

        return super.canUse();
    }

    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr){
        double d0 = this.getAttackReachSqr(pEnemy);
        if(pDistToEnemySqr <= d0 && isTimeToAttack()){
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(pEnemy);
            this.mob.removeEffect(MobEffects.INVISIBILITY);
            this.attackWarmupDelay = 40;
        }
    }
}