package com.idark.valoria.core.capability;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.common.capabilities.*;

import javax.annotation.*;

public interface INihilityLevel{
    Capability<INihilityLevel> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){
    });

    void modifyAmount(@Nullable LivingEntity entity, float amount);

    void decrease(Player player, float amount);

    void setAmount(@Nullable LivingEntity entity, float amount);

    float getAmount(boolean clientSide) ;

    float getMaxAmount(@Nullable LivingEntity entity, boolean clientSide);
}
