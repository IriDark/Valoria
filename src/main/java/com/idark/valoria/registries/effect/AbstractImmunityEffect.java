package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public abstract class AbstractImmunityEffect extends MobEffect{
    protected AbstractImmunityEffect(MobEffectCategory pCategory, int pColor){
        super(pCategory, pColor);
    }

    public abstract Predicate<ItemStack> getImmunityItem();

    public boolean effectRemoveReason(LivingEntity pEntity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(getImmunityItem(), pEntity).isPresent();
    }

    @Override
    public void applyEffectTick(LivingEntity pEntity, int amplifier){
        if(effectRemoveReason(pEntity)) pEntity.removeEffect(this);
    }
}
