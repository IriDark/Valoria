package com.idark.valoria.registries.item.types.consumables;

import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.component.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.gameevent.*;
import pro.komaru.tridot.util.struct.data.*;

public class PlaceableDrinkItem extends BlockItem implements TooltipComponentItem{
    private final ItemStack item;
    private final ImmutableList<MobEffectInstance> effects;

    public PlaceableDrinkItem(Block block, int stackSize, Item pItem, MobEffectInstance... pEffects){
        super(block, new Properties().stacksTo(stackSize));
        this.item = pItem.getDefaultInstance();
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity){
        Player playerEntity = entity instanceof Player ? (Player)entity : null;
        if(!world.isClientSide){
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)playerEntity, stack);
            playerEntity.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            for(MobEffectInstance mobeffectinstance : effects){
                entity.addEffect(new MobEffectInstance(mobeffectinstance));
            }

            if(!playerEntity.getAbilities().instabuild){
                stack.shrink(1);
                playerEntity.getInventory().add(new ItemStack(item.getItem()));
            }
        }

        entity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack pStack){
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.DRINK;
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(new ClientEffectsListClientComponent(effects, Component.translatable("tooltip.tridot.applies").withStyle(ChatFormatting.GRAY)));
    }
}
