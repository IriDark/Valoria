package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.function.*;

public abstract class AbstractImmunityEffect extends MobEffect{
    protected AbstractImmunityEffect(MobEffectCategory pCategory, int pColor){
        super(pCategory, pColor);
    }

    public abstract Predicate<ItemStack> getImmunityItem();

    public boolean effectRemoveReason(LivingEntity pEntity) {
        if(getImmunityItem() instanceof ICurioItem){
            return CuriosApi.getCuriosHelper().findEquippedCurio(getImmunityItem(), pEntity).isPresent();
        }

        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity pEntity, int amplifier){
        if(effectRemoveReason(pEntity)) pEntity.removeEffect(this);
    }
}
