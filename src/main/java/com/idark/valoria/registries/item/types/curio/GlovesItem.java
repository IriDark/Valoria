package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.Valoria;
import com.idark.valoria.core.enums.AccessoryGem;
import com.idark.valoria.core.enums.AccessoryMaterial;
import com.idark.valoria.core.enums.AccessoryType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class GlovesItem extends AbstractTieredAccessory implements ICurioTexture{
    public GlovesItem(Tier tier, Item.Properties properties){
        super(tier, properties);
    }

    public GlovesItem(Tier tier, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties){
        super(tier, AccessoryType.GLOVES, gem, material, properties);
    }

    public GlovesItem(Tier tier, AccessoryGem gem, AccessoryMaterial material, Properties pProperties, MobEffectInstance... pEffects){
        super(tier, AccessoryType.GLOVES, gem, material, pProperties, pEffects);
    }

    public static ResourceLocation getGlovesTexture(String material, boolean slim){
        return slim ? new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + "_slim" + ".png") : new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + ".png");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        switch(gem){
            case NONE:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.025, AttributeModifier.Operation.MULTIPLY_TOTAL));
                break;
            case DIAMOND:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 2f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 1.2f, Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                break;
            case TOUGH:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 1.7f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 2.2f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                break;
        }

        return atts;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(entity instanceof AbstractClientPlayer player){
            return getGloveTexture(material, !player.getModelName().equals("default"));
        }

        return getGloveTexture(material, false);
    }

    public ResourceLocation getGloveTexture(AccessoryMaterial material, boolean render){
        if(material.isInvalid()){
            return null;
        }

        String materialName = material.getName();
        if(materialName == null){
            return null;
        }

        return getGlovesTexture(materialName, render);
    }
}
