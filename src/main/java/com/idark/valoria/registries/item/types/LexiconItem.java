package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.gui.screen.book.*;
import net.minecraft.client.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

public class LexiconItem extends Item{
    public LexiconItem(Properties props){
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        if(world.isClientSide){
            openGui();
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(){
        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
        Minecraft.getInstance().setScreen(new LexiconGui());
    }
}