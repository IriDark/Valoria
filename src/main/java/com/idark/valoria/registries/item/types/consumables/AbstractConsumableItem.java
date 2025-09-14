package com.idark.valoria.registries.item.types.consumables;

import net.minecraft.advancements.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public abstract class AbstractConsumableItem extends Item{
    public int useDuration = 32;

    public AbstractConsumableItem(Properties pProperties){
        super(pProperties);
    }

    public int getUseDuration(ItemStack pStack) {
        return useDuration;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    public abstract void onConsume(ItemStack pStack, Level pLevel, Player player);

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity){
        if (pLivingEntity instanceof Player player){
            onConsume(pStack, pLevel, player);
            if(player instanceof ServerPlayer){
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pStack);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if(!player.getAbilities().instabuild){
                pStack.shrink(1);
            }
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}