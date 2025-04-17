package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.ui.screen.book.lexicon.*;
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
            openGui(player);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Player player){
        Codex.getInstance().open(player);
    }
}