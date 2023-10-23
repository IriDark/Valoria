package com.idark.darkrpg.item.curio.charm;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CurioRune extends Item {

    public CurioRune(Properties properties) {
        super(properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(Component.translatable("tooltip.darkrpg.rune").withStyle(ChatFormatting.GRAY));
	}
}