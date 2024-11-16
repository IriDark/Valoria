package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioItemProperty extends AbstractTieredAccessory implements ICurioTexture {
    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties, MobEffectInstance... pEffects) {
        super(tier, type, gem, material, pProperties, pEffects);
    }

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties) {
        super(tier, type, gem, material, pProperties);
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


    //todo redo, most likely will be done though accessory builder (priority: LOW)
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
                attribute.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.05 + (bonus * 0.025), AttributeModifier.Operation.ADDITION));
                break;
            case HEALTH:
                attribute.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1.5f + (bonus * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                if (material == AccessoryMaterial.IRON) {
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.6f + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
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
                if(stack.is(ItemsRegistry.samuraiBelt.get())) {
                    attribute.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.15 + (bonus * 0.05), Operation.MULTIPLY_TOTAL));
                    attribute.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "bonus", 0.05 + (bonus * 0.05), Operation.MULTIPLY_TOTAL));
                } else {
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (bonus * 0.05), AttributeModifier.Operation.ADDITION));
                    CuriosApi.addSlotModifier(attribute, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
                }
                break;
        }

        return attribute;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return switch (type) {
            case NECKLACE -> getTexture("textures/entity/necklace/", stack);
            case BELT -> getTexture("textures/entity/", stack);
            default -> null;
        };
    }

    private ResourceLocation getTexture(String path, ItemStack stack) {
        return new ResourceLocation(Valoria.ID, path + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png");
    }
}