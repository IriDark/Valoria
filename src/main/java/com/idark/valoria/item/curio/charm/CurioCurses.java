package com.idark.valoria.item.curio.charm;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.sounds.ModSoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import java.util.UUID;

public class CurioCurses extends Item implements ICurioItem {
	public CurioCurses(Properties properties) {
        super(properties);
	}

	RandomSource rand = RandomSource.create();
	MobEffect[] effects = {
		MobEffects.DARKNESS, MobEffects.WEAKNESS, MobEffects.WITHER, MobEffects.POISON, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
	};

	@Override
	public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
		return false;
	}

	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
		return new ICurio.SoundInfo(SoundEvents.CALCITE_PLACE, 1.0f, 1.0f);
	}

	// TODO: Fix (Called two times instead 1 for some reason)
	// Calamity sounds used
	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
		Player player = (Player) livingEntity;
		if (player.getActiveEffects().isEmpty() && !player.getCooldowns().isOnCooldown(this)) {
			player.playSound(ModSoundRegistry.EQUIP_CURSE.get(), 0.5f, 1f);
			player.addEffect(new MobEffectInstance(effects[Mth.nextInt(rand, 0, 5)], 60, 0, false, true));
			player.getCooldowns().addCooldown(this, 300);
		}

		ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
		atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 5, AttributeModifier.Operation.ADDITION));
        return atts;
    }

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(Component.translatable("tooltip.valoria.curses").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.valoria.wip").withStyle(ChatFormatting.RED));
	}
}