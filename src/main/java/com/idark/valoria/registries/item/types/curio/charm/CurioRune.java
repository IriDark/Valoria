package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;

public class CurioRune extends Item implements ICurioItem {
    public ArcRandom arcRandom = new ArcRandom();

    public CurioRune(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ValoriaUtils.onePerTypeEquip(slotContext, stack);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }
}