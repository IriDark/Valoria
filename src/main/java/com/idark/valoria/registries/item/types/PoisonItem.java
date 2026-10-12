package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.api.*;

import java.util.*;

public class PoisonItem extends Item{
    private final int toxinHits;
    public PoisonItem(int hits, Properties pProperties){
        super(pProperties);
        this.toxinHits = hits;
    }

    public static boolean isPoisoned(ItemStack stack){
        return DataComponentsRegistry.has(stack, DataComponentsRegistry.POISON_HITS.get());
    }

    public static int getPoisonHits(ItemStack stack){
        return DataComponentsRegistry.getInt(stack, DataComponentsRegistry.POISON_HITS.get());
    }

    public static void setPoisonHits(ItemStack stack, int hits){
        if(hits <= 0) {
            stack.remove(DataComponentsRegistry.POISON_HITS.get());
        } else {
            stack.set(DataComponentsRegistry.POISON_HITS, hits);
        }
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack stack = pSlot.getItem();
            if (stack.is(ItemTags.SWORDS) && !isPoisoned(stack)) {
                this.playSound(pPlayer);
                setPoisonHits(stack, toxinHits);
                pStack.shrink(1);
            }

            return true;
        }
    }

    public void appendHoverText(ItemStack pStack, Item.TooltipContext pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.valoria.toxins").withStyle(ChatFormatting.GRAY));
        ImmutableList<MobEffectInstance> list = ImmutableList.of(new MobEffectInstance(MobEffects.POISON, 120, 0));
        Utils.Items.effectTooltip(list, pTooltipComponents, 1, 1);
    }

    private void playSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BOTTLE_FILL, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }
}
