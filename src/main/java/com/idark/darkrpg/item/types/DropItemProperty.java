package com.idark.darkrpg.item.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.util.LootUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class DropItemProperty extends Item {
	public DropType type;

    public DropItemProperty(DropType type, Properties properties) {
		super(properties);
		this.type = type;
	}

	public DropType getDropType() {
		return this.type;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
		ItemStack heldStack = player.getHeldItem(hand);

		if (player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			Vector3d playerPos = serverPlayer.getPositionVec();
		
		switch(type) {
			case MINERS:
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) worldIn, new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftContext((ServerWorld) worldIn, playerPos, serverPlayer)));
				break;
			case GEM:
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) worldIn, new ResourceLocation(DarkRPG.MOD_ID, "items/gem_bag"), LootUtil.getGiftContext((ServerWorld) worldIn, playerPos, serverPlayer)));
				break;
			}
			
			serverPlayer.addStat(Stats.ITEM_USED.get(this));
			if (!serverPlayer.isCreative()) {
				heldStack.shrink(1);
			}

		return new ActionResult<>(ActionResultType.CONSUME, heldStack);
		}

	return new ActionResult<>(ActionResultType.CONSUME, heldStack);
	}
 
	@Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        tooltip.add(1, new TranslationTextComponent("tooltip.darkrpg.treasure").mergeStyle(TextFormatting.GRAY));
        tooltip.add(2, new StringTextComponent("                "));
		tooltip.add(3, new TranslationTextComponent("tooltip.darkrpg.rmb").mergeStyle(TextFormatting.GREEN));
	}
}