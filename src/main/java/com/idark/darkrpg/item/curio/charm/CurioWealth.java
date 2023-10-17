package com.idark.darkrpg.item.curio.charm;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;


import net.minecraft.item.Item.Properties;

public class CurioWealth extends Item implements ICurioItem {

    public CurioWealth(Properties properties) {
        super(properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.appendHoverText(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.wip").withStyle(TextFormatting.RED));
	}
}