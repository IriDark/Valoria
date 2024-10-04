package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.enums.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioItemProperty extends AbstractTieredAccessory implements ICurioTexture {
    private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/leather_belt.png");

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties, MobEffectInstance... pEffects) {
        super(tier, type, gem, material, pProperties, pEffects);
    }

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties) {
        super(tier, type, gem, material, pProperties);
    }

    private static ResourceLocation getNecklaceTexture(String material, String gem) {
        return new ResourceLocation(Valoria.ID, "textures/entity/necklace/" + material + "_necklace_" + gem + ".png");
    }

    public float getTierBonus() {
        if(tier == Tiers.IRON) {
            return 0.9f;
        }

        if(tier == Tiers.GOLD) {
            return 1.9f;
        }

        return tier.getLevel();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attribute = LinkedHashMultimap.create();
        float bonus = getTierBonus();
        switch (gem) {
            case NONE:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                break;
            case AMBER:
                attribute.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "bonus", 0.025f + (bonus * 0.005), AttributeModifier.Operation.ADDITION));
                break;
            case DIAMOND:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2 + (bonus * 0.5), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                break;
            case EMERALD:
                attribute.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1 + (bonus * 0.5), AttributeModifier.Operation.ADDITION));
                break;
            case RUBY:
                attribute.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 0.5 + (bonus * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case SAPPHIRE:
                attribute.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.05 + (bonus * 0.025), AttributeModifier.Operation.MULTIPLY_TOTAL));
                break;

            case HEALTH:
                attribute.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1.5f + (bonus * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                if (material == AccessoryMaterial.IRON) {
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5f + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                } else {
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 3.6f + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                    attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                }
                break;
            case TOUGH:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.25 + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2.5f + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.5f, AttributeModifier.Operation.ADDITION));
                break;
            case WEALTH:
                attribute.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1.5 + (bonus * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case BELT:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                CuriosApi.addSlotModifier(attribute, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
                break;
        }

        return attribute;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return switch (type) {
            case NECKLACE -> getNecklaceTexture(material, gem);
            case BELT -> BELT_TEXTURE;
            default -> null;
        };
    }

    private ResourceLocation getNecklaceTexture(AccessoryMaterial material, AccessoryGem gem) {
        if (!gem.isTextureApplicable() || material.isInvalid()) {
            return null;
        }

        String materialName = material.getName();
        String gemName = gem.getGemName();
        if (materialName == null || gemName == null) {
            return null;
        }

        return getNecklaceTexture(materialName, gemName);
    }
}