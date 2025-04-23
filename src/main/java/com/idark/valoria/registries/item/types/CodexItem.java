package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.ui.screen.book.codex.*;
import net.minecraft.client.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;

public class CodexItem extends Item{
    public CodexItem(Properties props){
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
    public void openGui(Level level, ItemStack stack) {
        openGui(Minecraft.getInstance().player);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, Vec3 pos, ItemStack stack) {
        openGui(Minecraft.getInstance().player);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Player player){
        Codex.getInstance().open(player);
    }
}