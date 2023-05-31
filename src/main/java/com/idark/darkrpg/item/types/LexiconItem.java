package com.idark.darkrpg.item.types;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import java.util.List;

public class LexiconItem extends Item {

	public LexiconItem(Properties props) {
		super(props);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		if (playerIn instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
			PatchouliAPI.get().openBookGUI((ServerPlayerEntity) playerIn, Registry.ITEM.getKey(this));
		}

		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	// Random item to expose this as public
	public static BlockRayTraceResult doRayTrace(World world, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
		return Item.rayTrace(world, player, fluidMode);
	}
	
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
    super.addInformation(stack, world, tooltip, flags);
    tooltip.add(new TranslationTextComponent("tooltip.darkrpg.lexicon").mergeStyle(TextFormatting.GRAY));
	}
}