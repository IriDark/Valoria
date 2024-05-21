package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.curio.enums.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class CurioItemProperty extends TieredItem implements ICurioItem, ICurioTexture, Vanishable{

    private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/leather_belt.png");

    public CurioItemProperty(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties){
        super(tier, properties);
        this.tier = tier;
        this.type = type;
        this.gem = gem;
        this.material = material;
    }

    public AccessoryType type;
    public AccessoryGem gem;
    public AccessoryMaterial material;
    public Tier tier;

    private static ResourceLocation getNecklaceTexture(String material, String gem){
        return new ResourceLocation(Valoria.ID, "textures/entity/necklace/" + material + "_necklace_" + gem + ".png");
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        // Reciving Gem Type & gives atts
        switch(gem){
            case NONE:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                break;
            case AMBER:
                atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "bonus", 0.025f + (tier.getLevel() * 0.005), AttributeModifier.Operation.ADDITION));
                break;
            case DIAMOND:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2 + (tier.getLevel() * 0.5), AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                break;
            case EMERALD:
                atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1 + (tier.getLevel() * 0.5), AttributeModifier.Operation.ADDITION));
                break;
            case RUBY:
                atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 0.5 + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case SAPPHIRE:
                atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.05 + (tier.getLevel() * 0.025), AttributeModifier.Operation.MULTIPLY_TOTAL));
                break;

            case HEALTH:
                atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1.5f + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                if(material == AccessoryMaterial.IRON){
                    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                }else{
                    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 3.6f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                    atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                }
                break;
            case TOUGH:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.25 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2.5f + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.5f, AttributeModifier.Operation.ADDITION));
                break;
            case WEALTH:
                atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1.5 + (tier.getLevel() * 0.25), AttributeModifier.Operation.ADDITION));
                break;
            case BELT:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2 + (tier.getLevel() * 0.05), AttributeModifier.Operation.ADDITION));
                CuriosApi.addSlotModifier(atts, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
                break;
        }

        return atts;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.VANISHING_CURSE || enchant == Enchantments.UNBREAKING || enchant == Enchantments.MENDING;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        int pGoldDamage = new Random().nextInt(0, 8);
        int pDefaultDamage = new Random().nextInt(0, 3);
        if(gem == AccessoryGem.AMBER && !player.level().isClientSide() && !player.hasEffect(MobEffects.DIG_SPEED)){
            if(!player.getCooldowns().isOnCooldown(stack.getItem())){
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200));
                player.getCooldowns().addCooldown(stack.getItem(), 300);
                stack.hurtAndBreak(material == AccessoryMaterial.GOLD ? pGoldDamage : pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        if(player.hurtMarked){
            stack.hurtAndBreak(material == AccessoryMaterial.GOLD ? pGoldDamage : pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack){
        return material == AccessoryMaterial.GOLD;
    }

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

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(gem == AccessoryGem.AMBER){
            tooltip.add(Component.translatable("tooltip.valoria.amber").withStyle(ChatFormatting.GRAY));
        }else if(type == AccessoryType.BELT){
            tooltip.add(Component.translatable("tooltip.valoria.belt").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb_equip").withStyle(ChatFormatting.GREEN));
    }
}