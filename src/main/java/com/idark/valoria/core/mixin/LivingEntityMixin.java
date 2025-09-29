package com.idark.valoria.core.mixin;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.network.syncher.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import javax.annotation.*;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements ILivingEntityData{
    @Unique private static final EntityDataAccessor<Float> LAST_DAMAGE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
    @Unique private static final EntityDataAccessor<Integer> MISS_TIME = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
    @Unique private static final EntityDataAccessor<Integer> DODGE_TIME = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

    @Unique public float valoria$lastDamageOffset = 0;
    @Unique public float valoria$lastDamageOffsetPrev = 0;
    @Shadow public int hurtTime;

    @Shadow
    @Nullable
    private DamageSource lastDamageSource;

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    protected void defineSynchedData(CallbackInfo ci){
        valoria$getEntityData().define(LAST_DAMAGE, 0f);
        valoria$getEntityData().define(MISS_TIME, 0);
        valoria$getEntityData().define(DODGE_TIME, 0);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.valoria$getMissTime() > 0) {
            this.valoria$missTime(this.valoria$getMissTime() - 1);
        }

        if (this.valoria$getDodgeTime() > 0) {
            this.valoria$dodgeTime(this.valoria$getDodgeTime() - 1);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    public void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir){
        if(!pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
            valoria$getEntityData().set(LAST_DAMAGE, 0f);
            if(hurtTime == 0){
                valoria$getEntityData().set(LAST_DAMAGE, pAmount);
            }
        }
    }

    @Override
    public void valoria$missTime(int value){
        valoria$entity().getEntityData().set(MISS_TIME, value);
    }

    @Override
    public int valoria$getMissTime(){
        return valoria$entity().getEntityData().get(MISS_TIME);
    }

    @Override
    public void valoria$dodgeTime(int value){
        valoria$entity().getEntityData().set(DODGE_TIME, value);
    }

    @Override
    public int valoria$getDodgeTime(){
        return valoria$entity().getEntityData().get(DODGE_TIME);
    }

    @Override
    public void valoria$setTextOffset(float value){
        this.valoria$lastDamageOffset = value;
    }

    @Override
    public float valoria$getTextOffset(){
        return this.valoria$lastDamageOffset;
    }

    @Override
    public void valoria$setTextOffsetPrev(float value){
        this.valoria$lastDamageOffsetPrev = value;
    }

    @Override
    public float valoria$getTextOffsetPrev(){
        return this.valoria$lastDamageOffsetPrev;
    }

    @Override
    public void valoria$setLastDamageWithSource(DamageSource source, float value){
        valoria$entity().getEntityData().set(LAST_DAMAGE, value);
        lastDamageSource = source;
    }

    @Override
    public void valoria$setLastDamage(float value){
        valoria$entity().getEntityData().set(LAST_DAMAGE, value);
    }

    @Override
    public float valoria$getLastDamage(){
        return valoria$entity().getEntityData().get(LAST_DAMAGE);
    }

    @Override
    public DamageSource valoria$getLastDamageSource(){
        return lastDamageSource;
    }

    @Unique
    public SynchedEntityData valoria$getEntityData(){
        return valoria$entity().getEntityData();
    }

    @Unique
    public LivingEntity valoria$living(){
        return (LivingEntity)(Object)this;
    }

    @Unique
    public Entity valoria$entity(){
        return (Entity)(Object)this;
    }
}