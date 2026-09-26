package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
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

public class GasMaskItem extends ValoriaCurioItem{
    public GasMaskItem(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        super.curioTick(slotContext, stack);
        LivingEntity wearer = slotContext.entity();
        if(wearer instanceof Player player){
            if(player.isCreative()) return;
            INihilityLevel.of(player).ifPresent((nihilityLevel) -> {
                if(nihilityLevel.getAmount() > 0) {
                    if(slotContext.entity().tickCount % 160 == 0){
                        if(Tmp.rnd.chance(0.75f)) accessoryHurt(wearer, stack);
                    }
                }
            });
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack){
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(AttributeReg.NIHILITY_RESISTANCE, new AttributeModifier(uuid, 50, AttributeModifier.Operation.ADD_VALUE));
        atts.put(AttributeReg.NIHILITY_RESILIENCE, new AttributeModifier(uuid, 0.05, Operation.ADD_MULTIPLIED_TOTAL));
        atts.put(AttributeReg.MAX_NIHILITY, new AttributeModifier(uuid, 0.15, Operation.ADD_MULTIPLIED_TOTAL));
        return atts;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE.value(), 1.0f, 1.0f);
    }
}