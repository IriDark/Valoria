package com.idark.valoria.core.capability;

import net.minecraft.world.entity.*;
import net.minecraftforge.common.capabilities.*;

import javax.annotation.*;

public interface INihilityLevel{
    Capability<INihilityLevel> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){
    });

    void modifyAmount(@Nullable LivingEntity entity, float amount);

    void decrease(@Nullable LivingEntity entity, float amount);

    void setAmount(float amount);

    void setAmountFromServer(@Nullable LivingEntity entity, float amount);

    float getAmount() ;

    void setMaxAmount(float amount);

    float getMaxAmount(@Nullable LivingEntity entity);
}
