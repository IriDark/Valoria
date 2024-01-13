package com.idark.valoria.item.types;

import com.idark.valoria.capability.IPage;
import com.idark.valoria.capability.PageCapability;
import com.idark.valoria.client.render.gui.book.LexiconGui;
import com.idark.valoria.client.render.gui.book.LexiconPages;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.PageUpdatePacket;
import com.idark.valoria.toast.ModToast;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class LexiconPageItem extends Item {

    public LexiconPages pages;

    public LexiconPageItem(LexiconPages pages, Properties props) {
        super(props);
        this.pages = pages;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!world.isClientSide) {
            if (!LexiconGui.pageIsOpen(player, pages)) {
                player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0);
                player.getInventory().removeItem(stack);
                Minecraft.getInstance().getToasts().addToast(new ModToast(true));
                player.getCapability(IPage.INSTANCE, null).ifPresent((k) -> {
                    k.makeOpen(pages, true);
                });

                PacketHandler.sendTo(player, new PageUpdatePacket(player));
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            } else {
                player.playSound(SoundEvents.PLAYER_BURP, 1, 0);
                player.displayClientMessage(Component.translatable("gui.valoria.obtained").withStyle(ChatFormatting.GRAY), true);
                return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    public String getModeString() {
        switch (pages) {
            case MAIN, MEDICINE, GEMS, GEMS_ABOUT, THANKS -> {
                return null;
            }

            case CRYPT -> {
                return "gui.valoria.crypt";
            }
        }

        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        if (pages != null) {
            tooltip.add(1, Component.translatable("tooltip.valoria.page").withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable(getModeString()).withStyle(ChatFormatting.BLUE)));
        }
    }
}