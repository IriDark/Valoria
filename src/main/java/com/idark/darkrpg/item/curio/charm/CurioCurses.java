package com.idark.darkrpg.item.curio.charm;

import com.idark.darkrpg.item.curio.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;


public class CurioCurses extends Item implements ICurioItem {

    public CurioCurses(Properties properties) {
        super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.addInformation(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.curses").mergeStyle(TextFormatting.GRAY));
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.wip").mergeStyle(TextFormatting.RED));
	}
}