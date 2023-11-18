package com.idark.darkrpg.item.curio.charm;

import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;

public class BloodSight extends Item implements ICurioItem {

    public BloodSight(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        if (stack.getItem() == ModItems.BLOODSIGHT_MONOCLE.get()) {
            return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_CHAIN, 1.0f, 1.0f);
        } else {
            return new ICurio.SoundInfo(ModSoundRegistry.EQUIP_CURSE.get(), 1.0f, 1.0f);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.darkrpg.bloodsight").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        if (stack.getItem() == ModItems.BROKEN_BLOODSIGHT_MONOCLE.get()) {
            tooltip.add(Component.translatable("tooltip.darkrpg.bloodsight_curse").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        }
    }
}