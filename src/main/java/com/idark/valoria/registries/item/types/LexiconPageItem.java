package com.idark.valoria.registries.item.types;

import com.idark.valoria.api.unlockable.UnlockUtils;
import com.idark.valoria.api.unlockable.Unlockable;
import net.minecraft.ChatFormatting;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LexiconPageItem extends Item{
    public Unlockable unlockable;
    public String lang;

    public LexiconPageItem(Properties props, @NotNull Unlockable pUnlockable, String pPageName){
        super(props);
        this.unlockable = pUnlockable;
        this.lang = pPageName;
    }

    public LexiconPageItem(Properties props, @NotNull Unlockable pUnlockable){
        super(props);
        this.unlockable = pUnlockable;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        if(!world.isClientSide){
            if(!UnlockUtils.isUnlocked(player, unlockable)){
                player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0);
                player.getInventory().removeItem(stack);
                UnlockUtils.addUnlockable(player, unlockable);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }else{
                world.playSound(null, player.getOnPos(), SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 0.7f, 0.2f);
                player.displayClientMessage(Component.translatable("lexicon.valoria.obtained").withStyle(ChatFormatting.GRAY), true);
                return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(!lang.isEmpty()){
            tooltip.add(Component.translatable("tooltip.valoria.page").withStyle(ChatFormatting.GRAY).append(Component.translatable(lang).withStyle(ChatFormatting.BLUE)));
        }
    }
}