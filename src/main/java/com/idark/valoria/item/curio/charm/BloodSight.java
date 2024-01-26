package com.idark.valoria.item.curio.charm;

import com.idark.valoria.item.ModItems;
import com.idark.valoria.util.ModSoundRegistry;
import com.idark.valoria.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;
        ModUtils.spawnParticlesLineToAttackedMob(player.level(), player, ParticleTypes.ASH);

        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return stack.getItem() == ModItems.BLOODSIGHT_MONOCLE.get() ? new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_CHAIN, 1.0f, 1.0f) : new ICurio.SoundInfo(ModSoundRegistry.EQUIP_CURSE.get(), 1.0f, 1.0f);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.bloodsight").withStyle(ChatFormatting.GRAY));
        if (stack.getItem() == ModItems.BROKEN_BLOODSIGHT_MONOCLE.get()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.bloodsight_curse").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        }
    }
}