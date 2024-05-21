package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.*;

public class DropItemProperty extends Item{
    public DropType type;

    public DropItemProperty(DropType type, Properties properties){
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand){
        ItemStack heldStack = player.getItemInHand(hand);
        worldIn.playSound(null, player.blockPosition(), SoundsRegistry.BAG_OPEN.get(), SoundSource.AMBIENT, 10f, 1f);
        if(player instanceof ServerPlayer serverPlayer){
            Vec3 playerPos = serverPlayer.position();
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, heldStack);
            if(!serverPlayer.isCreative()){
                heldStack.shrink(1);
            }

            switch(type){
                case MINERS -> LootUtil.DropLoot(serverPlayer, LootUtil.createLoot(new ResourceLocation(Valoria.ID, "items/miners_bag"), LootUtil.getGiftParameters((ServerLevel)worldIn, playerPos, serverPlayer)));
                case GEM -> LootUtil.DropLoot(serverPlayer, LootUtil.createLoot(new ResourceLocation(Valoria.ID, "items/gem_bag"), LootUtil.getGiftParameters((ServerLevel)worldIn, playerPos, serverPlayer)));
            }

            return InteractionResultHolder.consume(heldStack);
        }

        return InteractionResultHolder.consume(heldStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.treasure").withStyle(ChatFormatting.GRAY));
        list.add(2, Component.empty());
        list.add(3, Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
    }
}