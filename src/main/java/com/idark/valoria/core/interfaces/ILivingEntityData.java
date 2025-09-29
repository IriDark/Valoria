package com.idark.valoria.core.interfaces;

import net.minecraft.world.damagesource.*;

public interface ILivingEntityData {
    void valoria$missTime(int value);
    int valoria$getMissTime();

    void valoria$dodgeTime(int value);
    int valoria$getDodgeTime();

    void valoria$setTextOffset(float value);
    float valoria$getTextOffset();

    void valoria$setTextOffsetPrev(float value);
    float valoria$getTextOffsetPrev();

    void valoria$setLastDamage(float value);
    void valoria$setLastDamageWithSource(DamageSource source, float value);
    float valoria$getLastDamage();
    DamageSource valoria$getLastDamageSource();
}