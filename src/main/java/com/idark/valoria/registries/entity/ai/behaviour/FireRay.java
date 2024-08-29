package com.idark.valoria.registries.entity.ai.behaviour;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.ai.brains.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.phys.*;

public class FireRay extends Behavior<Succubus>{
    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.ceil(34.0D);
    private static final int DURATION = Mth.ceil(60.0F);

    //TODO
    public FireRay(){
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), 1200);
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, Succubus pOwner){
        return pOwner.closerThan(pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get(), 15.0D, 20.0D);
    }

    protected boolean canStillUse(ServerLevel pLevel, Succubus pEntity, long pGameTime){
        return true;
    }

    protected void start(ServerLevel pLevel, Succubus pEntity, long pGameTime){
        SuccubusAI.stopWalking(pEntity);
        pEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, DURATION);
        pEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, TICKS_BEFORE_PLAYING_SOUND);
        pLevel.broadcastEntityEvent(pEntity, (byte)62);
        pEntity.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
    }

    protected void tick(ServerLevel pLevel, Succubus pOwner, long pGameTime){
        pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent((p_289393_) -> {
            pOwner.getLookControl().setLookAt(p_289393_.position());
        });
        if(!pOwner.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) && !pOwner.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)){
            pOwner.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, DURATION - TICKS_BEFORE_PLAYING_SOUND);
            pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(pOwner::canTargetEntity).filter((p_217707_) -> {
                return pOwner.closerThan(p_217707_, 15.0D, 20.0D);
            }).ifPresent((p_217704_) -> {
                Vec3 vec3 = pOwner.position().add(0.0D, 1.6F, 0.0D);
                Vec3 vec31 = p_217704_.getEyePosition().subtract(vec3);
                Vec3 vec32 = vec31.normalize();

                for(int i = 1; i < Mth.floor(vec31.length()) + 7; ++i){
                    Vec3 vec33 = vec3.add(vec32.scale(i));
                    pLevel.sendParticles(ParticleTypes.FLAME, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }

                pOwner.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
                p_217704_.hurt(pLevel.damageSources().generic(), 5F);
            });
        }
    }

    protected void stop(ServerLevel pLevel, Succubus pEntity, long pGameTime){
        setCooldown(pEntity, 200);
    }

    public static void setCooldown(LivingEntity pEntity, int pCooldown){
        pEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_COOLDOWN, Unit.INSTANCE, (long)pCooldown);
    }
}