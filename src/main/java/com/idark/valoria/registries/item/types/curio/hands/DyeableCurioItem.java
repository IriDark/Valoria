package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;

public class DyeableCurioItem extends AbstractCurioItem implements DyeableLeatherItem{
    public DyeableCurioItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack){
        return false;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }
}
