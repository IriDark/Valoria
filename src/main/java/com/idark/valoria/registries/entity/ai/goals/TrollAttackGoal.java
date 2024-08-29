package com.idark.valoria.registries.entity.ai.goals;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.*;

import javax.annotation.*;

public class TrollAttackGoal extends MeleeAttackGoal{
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
    public boolean canUse(){
        LivingEntity target = mob.getTarget();
        Level level = mob.level();
        RandomSource random = level.random;
        if(level instanceof ServerLevel server){
            if(target != null){
                server.sendParticles(ParticleTypes.SMOKE, mob.getX(), mob.getY(), mob.getZ(), 6, random.nextFloat() / 2, random.nextFloat() / 2, random.nextFloat() / 2, 0.12f);
                mob.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120, 0, false, false));
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
        }
    }
}