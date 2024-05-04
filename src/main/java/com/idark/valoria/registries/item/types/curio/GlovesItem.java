package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.registries.item.types.curio.enums.AccessoryGem;
import com.idark.valoria.registries.item.types.curio.enums.AccessoryMaterial;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GlovesItem extends TieredItem implements ICurioItem, ICurioTexture, Vanishable {

    public static ResourceLocation getGlovesTexture(String material, boolean slim) {
        return slim ? new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + "_slim" + ".png") : new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + ".png");
    }

    public AccessoryGem gem;
    public AccessoryMaterial material;

    public GlovesItem(Tier tier, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties) {
        super(tier, properties);
        this.gem = gem;
        this.material = material;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant == Enchantments.VANISHING_CURSE || enchant == Enchantments.UNBREAKING || enchant == Enchantments.MENDING;
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
        switch (gem) {
            case NONE:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 0.2, AttributeModifier.Operation.ADDITION));
                break;
            case DIAMOND:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 2f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1, AttributeModifier.Operation.ADDITION));
                break;
            case ARMOR:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
                break;
            case TOUGH:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 1.7f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 1.2, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
                break;
            case TANK:
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 2.2f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", 2.65f, AttributeModifier.Operation.ADDITION));
                atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 1.2f, AttributeModifier.Operation.ADDITION));
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
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        if (gem == AccessoryGem.AMBER) {
            tooltip.add(Component.translatable("tooltip.valoria.amber").withStyle(ChatFormatting.GRAY));
        } else if (material == AccessoryMaterial.GOLD) {
            tooltip.add(Component.translatable("tooltip.valoria.golden").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb_equip").withStyle(ChatFormatting.GREEN));
    }
}
