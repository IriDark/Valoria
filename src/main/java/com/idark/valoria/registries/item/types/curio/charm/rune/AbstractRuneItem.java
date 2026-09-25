package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;

public abstract class AbstractRuneItem extends ValoriaCurioItem{
    public AbstractRuneItem(Properties properties){
        super(properties);
    }

    public abstract RuneType runeType();

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.CALCITE_PLACE, 1.0f, 1.0f);
    }
}
