package com.idark.valoria.registries.item.types.curio.hands;

import com.google.common.collect.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class MagmaticGauntletItem extends GlovesItem{
    public MagmaticGauntletItem(Tier tier, Properties properties){
        super(tier, properties);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(entity instanceof AbstractClientPlayer player){
            return getGlovesTexture("magmatic", !player.getModelName().equals("default"));
        }

        return getGlovesTexture("magmatic", false);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "bonus", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 2.5f, AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 5f, Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.0f, AttributeModifier.Operation.ADDITION));
        return atts;
    }

}
