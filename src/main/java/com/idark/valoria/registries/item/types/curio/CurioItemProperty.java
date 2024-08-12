package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.curio.enums.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioItemProperty extends AbstractTieredAccessory implements ICurioTexture{
    private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/leather_belt.png");

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties, MobEffectInstance... pEffects){
        super(tier, type, gem, material, pProperties, pEffects);
    }

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties){
        super(tier, type, gem, material, pProperties);
    }

    private static ResourceLocation getNecklaceTexture(String material, String gem){
        return new ResourceLocation(Valoria.ID, "textures/entity/necklace/" + material + "_necklace_" + gem + ".png");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> attribute = LinkedHashMultimap.create();
        switch(gem){
            case NONE:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                break;
            case AMBER:
                attribute.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "bonus", 0.025f + (tier.getLevel() * 0.005), AttributeModifier.Operation.ADDITION));
                break;
            case DIAMOND:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2 + (tier.getLevel() * 0.5), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                break;
            case EMERALD:
                attribute.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1 + (tier.getLevel() * 0.5), AttributeModifier.Operation.ADDITION));
                break;
            case RUBY:
                attribute.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 0.5 + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case SAPPHIRE:
                attribute.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.05 + (tier.getLevel() * 0.025), AttributeModifier.Operation.MULTIPLY_TOTAL));
                break;

            case HEALTH:
                attribute.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1.5f + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                if(material == AccessoryMaterial.IRON){
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                }else{
                    attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 3.6f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                    attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                }
                break;
            case TOUGH:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.25 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2.5f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                attribute.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.5f, AttributeModifier.Operation.ADDITION));
                break;
            case WEALTH:
                attribute.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1.5 + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case BELT:
                attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                CuriosApi.addSlotModifier(attribute, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
                break;
        }

        return attribute;
    }

    //todo redo this shit
    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        return switch(type){
            case NECKLACE -> switch(material){
                case IRON -> switch(gem){
                    case NONE, BELT, TANK, TOUGH -> null;
                    case AMBER -> getNecklaceTexture("iron", "amber");
                    case DIAMOND -> getNecklaceTexture("iron", "diamond");
                    case EMERALD -> getNecklaceTexture("iron", "emerald");
                    case RUBY -> getNecklaceTexture("iron", "ruby");
                    case SAPPHIRE -> getNecklaceTexture("iron", "sapphire");
                    case ARMOR -> getNecklaceTexture("iron", "armor");
                    case HEALTH -> getNecklaceTexture("iron", "health");
                    case WEALTH -> getNecklaceTexture("iron", "wealth");
                };

                case GOLD -> switch(gem){
                    case NONE, BELT, TANK, TOUGH -> null;
                    case AMBER -> getNecklaceTexture("golden", "amber");
                    case DIAMOND -> getNecklaceTexture("golden", "diamond");
                    case EMERALD -> getNecklaceTexture("golden", "emerald");
                    case RUBY -> getNecklaceTexture("golden", "ruby");
                    case SAPPHIRE -> getNecklaceTexture("golden", "sapphire");
                    case ARMOR -> getNecklaceTexture("golden", "armor");
                    case HEALTH -> getNecklaceTexture("golden", "health");
                    case WEALTH -> getNecklaceTexture("golden", "wealth");
                };

                case NETHERITE -> switch(gem){
                    case NONE, BELT, TANK, TOUGH -> null;
                    case AMBER -> getNecklaceTexture("netherite", "amber");
                    case DIAMOND -> getNecklaceTexture("netherite", "diamond");
                    case EMERALD -> getNecklaceTexture("netherite", "emerald");
                    case RUBY -> getNecklaceTexture("netherite", "ruby");
                    case SAPPHIRE -> getNecklaceTexture("netherite", "sapphire");
                    case ARMOR -> getNecklaceTexture("netherite", "armor");
                    case HEALTH -> getNecklaceTexture("netherite", "health");
                    case WEALTH -> getNecklaceTexture("netherite", "wealth");
                };

                default -> null;
            };
            case BELT -> BELT_TEXTURE;
            default -> null;
        };
    }
}