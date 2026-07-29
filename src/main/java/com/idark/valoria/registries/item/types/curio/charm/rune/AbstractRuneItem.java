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
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.cosmetic()) return true;

        if (stack.getItem() instanceof AbstractRuneItem toEquip) {
            return CuriosApi.getCuriosHelper().findCurios(slotContext.entity(), i -> i.getItem() instanceof AbstractRuneItem)
                    .stream().filter(result -> ((AbstractRuneItem) result.stack().getItem()).runeType() == toEquip.runeType())
                    .allMatch(result -> result.slotContext().identifier().equals(slotContext.identifier())
                                    && result.slotContext().index() == slotContext.index());
        }

        return super.canEquip(slotContext, stack);
    }
}
