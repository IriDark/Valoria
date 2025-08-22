package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;

import java.util.*;

public class PoisonItem extends Item{
    public PoisonItem(Properties pProperties){
        super(pProperties);
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack stack = pSlot.getItem();
            if (stack.is(ItemTags.SWORDS) && (stack.getTag() != null && !stack.getTag().contains("poison_hits"))) {
                this.playSound(pPlayer);
                stack.getOrCreateTag().putInt("poison_hits", 10);
                pStack.shrink(1);
            }

            return true;
        }
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.valoria.toxins").withStyle(ChatFormatting.GRAY));
        ImmutableList<MobEffectInstance> list = ImmutableList.of(new MobEffectInstance(MobEffects.POISON, 120, 0));
        Utils.Items.effectTooltip(list, pTooltipComponents, 1, 1);
    }

    private void playSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BOTTLE_FILL, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }
}