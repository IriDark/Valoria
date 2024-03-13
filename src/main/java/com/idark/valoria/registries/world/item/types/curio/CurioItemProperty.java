package com.idark.valoria.registries.world.item.types.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryGem;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryMaterial;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CurioItemProperty extends Item implements ICurioItem, ICurioTexture, Vanishable {

    private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/entity/leather_belt.png");

    private static ResourceLocation getNecklaceTexture(String material, String gem) {
        return new ResourceLocation(Valoria.MOD_ID, "textures/entity/necklace/" + material + "_necklace_" + gem + ".png");
    }

    private static ResourceLocation getGlovesTexture(String material, boolean slim) {
        return slim ? new ResourceLocation(Valoria.MOD_ID, "textures/entity/gloves/" + material + "_gloves" + "_slim" + ".png") : new ResourceLocation(Valoria.MOD_ID, "textures/entity/gloves/" + material + "_gloves" + ".png");
    }

    public AccessoryType type;
    public AccessoryGem gem;
    public AccessoryMaterial material;

    public CurioItemProperty(AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties) {
        super(properties);
        this.type = type;
        this.gem = gem;
        this.material = material;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        // Reciving Gem Type & gives atts
        switch (gem) {
            case NONE:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
                break;
            case AMBER:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case DIAMOND:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 4, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 2, AttributeModifier.Operation.ADDITION));
                break;
            case EMERALD:
                atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case RUBY:
                atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case SAPPHIRE:
                atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "bonus", 0.1, AttributeModifier.Operation.ADDITION));
                break;

            case HEALTH:
                if (material == AccessoryMaterial.IRON) {
                    atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                } else {
                    atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "bonus", 2.5, AttributeModifier.Operation.ADDITION));
                }
                break;
            case ARMOR:
                if (material == AccessoryMaterial.IRON) {
                    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
                } else {
                    atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 6, AttributeModifier.Operation.ADDITION));
                    atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 2, AttributeModifier.Operation.ADDITION));
                }
                break;
            case TOUGH:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 5, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 2, AttributeModifier.Operation.ADDITION));
                break;
            case WEALTH:
                if (material == AccessoryMaterial.IRON) {
                    atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 1.5, AttributeModifier.Operation.ADDITION));
                } else {
                    atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 3, AttributeModifier.Operation.ADDITION));
                }
                break;
            case BELT:
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
                CuriosApi.addSlotModifier(atts, "charm", uuid, 2.0, AttributeModifier.Operation.ADDITION);
                break;
        }

        return atts;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        int pGoldDamage = new Random().nextInt(0, 8);
        int pDefaultDamage = new Random().nextInt(0, 3);
        if (gem == AccessoryGem.AMBER && !player.level().isClientSide() && !player.hasEffect(MobEffects.DIG_SPEED)) {
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200));
                player.getCooldowns().addCooldown(stack.getItem(), 300);
                stack.hurtAndBreak(material == AccessoryMaterial.GOLD ? pGoldDamage : pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        if (player.hurtMarked) {
            stack.hurtAndBreak(material == AccessoryMaterial.GOLD ? pGoldDamage : pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return material == AccessoryMaterial.GOLD;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        switch (type) {
            case NECKLACE:
                return switch (material) {
                    case IRON -> switch (gem) {
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

                    case GOLD -> switch (gem) {
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

                    case NETHERITE -> switch (gem) {
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

            case GLOVES:
                if (!HandsRenderer.isDefault) {
                    return switch (material) {
                        case LEATHER -> getGlovesTexture("leather", true);
                        case IRON -> getGlovesTexture("iron", true);
                        case GOLD -> getGlovesTexture("golden", true);
                        case DIAMOND -> getGlovesTexture("diamond", true);
                        case NETHERITE -> getGlovesTexture("netherite", true);
                    };
                } else {
                    return switch (material) {
                        case LEATHER -> getGlovesTexture("leather", false);
                        case IRON -> getGlovesTexture("iron", false);
                        case GOLD -> getGlovesTexture("golden", false);
                        case DIAMOND -> getGlovesTexture("diamond", false);
                        case NETHERITE -> getGlovesTexture("netherite", false);
                    };
                }
            case BELT:
                return BELT_TEXTURE;
        }

        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        if (gem == AccessoryGem.AMBER) {
            tooltip.add(Component.translatable("tooltip.valoria.amber").withStyle(ChatFormatting.GRAY));
        } else if (material == AccessoryMaterial.GOLD) {
            tooltip.add(Component.translatable("tooltip.valoria.golden").withStyle(ChatFormatting.GRAY));
        } else if (type == AccessoryType.BELT) {
            tooltip.add(Component.translatable("tooltip.valoria.belt").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb_equip").withStyle(ChatFormatting.GREEN));
    }
}