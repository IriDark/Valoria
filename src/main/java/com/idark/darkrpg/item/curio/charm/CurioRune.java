package com.idark.darkrpg.item.curio.charm;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;


import net.minecraft.item.Item.Properties;

public class CurioRune extends Item {

    public CurioRune(Properties properties) {
        super(properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.appendHoverText(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.rune").withStyle(TextFormatting.GRAY));
	}
}