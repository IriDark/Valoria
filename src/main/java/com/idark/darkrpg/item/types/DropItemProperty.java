package com.idark.darkrpg.item.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.util.LootUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand) {
		ItemStack heldStack = player.getItemInHand(hand);
		worldIn.playSound(player, player.blockPosition(), ModSoundRegistry.BAG_OPEN.get(), SoundSource.AMBIENT, 10f, 1f);

		if (player instanceof ServerPlayer) {
			ServerPlayer serverPlayer = (ServerPlayer) player;
			Vec3 playerPos = serverPlayer.position();
		
		switch(type) {
			case MINERS:
				LootUtil.DropLoot(serverPlayer, LootUtil.generateLoot(new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftParameters((ServerLevel) worldIn, playerPos, serverPlayer)));
				break;
			case GEM:
				LootUtil.DropLoot(serverPlayer, LootUtil.generateLoot(new ResourceLocation(DarkRPG.MOD_ID, "items/gem_bag"), LootUtil.getGiftParameters((ServerLevel) worldIn, playerPos, serverPlayer)));
				break;
			}
			
			serverPlayer.awardStat(Stats.ITEM_USED.get(this));
			if (!serverPlayer.isCreative()) {
				heldStack.shrink(1);
			}

			return InteractionResultHolder.consume(heldStack);
		}

		return InteractionResultHolder.consume(heldStack);
	}
 
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
		list.add(1, Component.translatable("tooltip.darkrpg.treasure").withStyle(ChatFormatting.GRAY));
		list.add(2, Component.empty());
		list.add(3, Component.translatable("tooltip.darkrpg.rmb").withStyle(ChatFormatting.GREEN));
	}
}