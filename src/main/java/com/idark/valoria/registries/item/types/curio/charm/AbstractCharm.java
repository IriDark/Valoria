package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.core.math.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;

public abstract class AbstractCharm extends Item implements ICurioItem{
    public ArcRandom arcRandom = new ArcRandom();

    public AbstractCharm(Properties properties){
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        return ValoriaUtils.onePerTypeEquip(slotContext, stack);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }
}