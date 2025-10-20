package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class GasMaskItem extends Item implements ICurioItem, Vanishable{
    public GasMaskItem(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity wearer = slotContext.entity();
        if(wearer instanceof Player player){
            if(player.isCreative()) return;
            player.getCapability(INihilityLevel.INSTANCE).ifPresent((nihilityLevel) -> {
                if(nihilityLevel.getAmount() > 0) {
                    if(slotContext.entity().tickCount % 160 == 0){
                        if(Tmp.rnd.chance(0.75f)) stack.hurtAndBreak(Tmp.rnd.nextInt(1, 4), slotContext.entity(), (plr) -> plr.broadcastBreakEvent(EquipmentSlot.HEAD));
                    }
                }
            });
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(AttributeReg.NIHILITY_RESISTANCE.get(), new AttributeModifier(uuid, "bonus", 50, AttributeModifier.Operation.ADDITION));
        atts.put(AttributeReg.NIHILITY_RESILIENCE.get(), new AttributeModifier(uuid, "bonus", 0.05, Operation.MULTIPLY_TOTAL));
        atts.put(AttributeReg.MAX_NIHILITY.get(), new AttributeModifier(uuid, "bonus", 0.15, Operation.MULTIPLY_TOTAL));
        return atts;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
    }
}