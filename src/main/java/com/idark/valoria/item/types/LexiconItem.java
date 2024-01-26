package com.idark.valoria.item.types;

import com.idark.valoria.client.render.gui.book.LexiconGui;
import com.idark.valoria.client.render.gui.book.LexiconPages;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class LexiconItem extends Item {

	public LexiconItem(Properties props) {
		super(props);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.awardStat(Stats.ITEM_USED.get(this));

		if (world.isClientSide) {
			openGui();
		}

		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGui() {
		Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
		Minecraft.getInstance().setScreen(new LexiconGui(LexiconPages.MAIN));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
			tooltip.add(1, Component.literal("Very wip item because of capabilities, will be reworked sometime...").withStyle(ChatFormatting.RED));
	}
}