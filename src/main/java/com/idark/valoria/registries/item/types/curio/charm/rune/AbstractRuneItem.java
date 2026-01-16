package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public abstract class AbstractRuneItem extends ValoriaCurioItem{
    public AbstractRuneItem(Properties properties){
        super(properties);
    }

    public abstract RuneType runeType();

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.CALCITE_PLACE, 1.0f, 1.0f);
    }

    @Override
    @SuppressWarnings({"removal", "UnstableApiUsage", "deprecation"})
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        if (stack.getItem() instanceof AbstractRuneItem toEquip){
            List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(slotContext.getWearer(), (i) -> i.getItem() instanceof AbstractRuneItem);
            for(SlotResult slot : curioSlots){
                ItemStack otherStack = slot.stack();
                AbstractRuneItem otherRune = (AbstractRuneItem)otherStack.getItem();

                if(otherRune.runeType() == toEquip.runeType()) return false;
            }
        }

        return true;
    }
}
