package com.idark.darkrpg.item.curio;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;
import javax.annotation.Nonnull;

public class RPGCurioItem extends Item implements ICurioItem {
    public RPGCurioItem(Properties properties) {
        super(properties);
    }

	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }
}