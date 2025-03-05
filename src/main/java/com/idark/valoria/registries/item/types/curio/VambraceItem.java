package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class VambraceItem extends GlovesItem{
    public String name;
    public VambraceItem(String name, Tier tier, Properties properties){
        super(tier, properties);
        this.name = name;
    }

    public static ResourceLocation getGlovesTexture(String material, boolean slim){
        return slim ? new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_vambrace" + "_slim" + ".png") : new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_vambrace" + ".png");
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(entity instanceof AbstractClientPlayer player){
            return getGlovesTexture(name, !player.getModelName().equals("default"));
        }

        return getGlovesTexture(name, false);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "bonus", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
}
