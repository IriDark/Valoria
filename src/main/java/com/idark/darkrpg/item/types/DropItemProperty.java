package com.idark.darkrpg.item.types;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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

	/*@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		worldIn.playSound(player, player.blockPosition(), ModSoundRegistry.BAG_OPEN.get(), SoundCategory.AMBIENT, 10f, 1f);

		if (player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			Vector3d playerPos = serverPlayer.position();
		
		switch(type) {
			case MINERS:
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) worldIn, new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftContext((ServerWorld) worldIn, playerPos, serverPlayer)));
				break;
			case GEM:
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) worldIn, new ResourceLocation(DarkRPG.MOD_ID, "items/gem_bag"), LootUtil.getGiftContext((ServerWorld) worldIn, playerPos, serverPlayer)));
				break;
			}
			
			serverPlayer.awardStat(Stats.ITEM_USED.get(this));
			if (!serverPlayer.isCreative()) {
				heldStack.shrink(1);
			}

		return new ActionResult<>(ActionResultType.CONSUME, heldStack);
		}

	return new ActionResult<>(ActionResultType.CONSUME, heldStack);
	}*/
 
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
		list.add(1, Component.translatable("tooltip.darkrpg.treasure").withStyle(ChatFormatting.GRAY));
		list.add(2, Component.empty());
		list.add(3,  Component.translatable("tooltip.darkrpg.rmb").withStyle(ChatFormatting.GREEN));
	}
}