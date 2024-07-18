package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.client.render.curio.*;
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

public class GlovesItem extends TieredItem implements ICurioItem, ICurioTexture, Vanishable{

    public GlovesItem(Tier tier, AccessoryGem gem, AccessoryMaterial material, Item.Properties properties){
        super(tier, properties);
        this.gem = gem;
        this.material = material;
    }

    public AccessoryGem gem;
    public AccessoryMaterial material;

    public static ResourceLocation getGlovesTexture(String material, boolean slim){
        return slim ? new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + "_slim" + ".png") : new ResourceLocation(Valoria.ID, "textures/entity/gloves/" + material + "_gloves" + ".png");
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.VANISHING_CURSE || enchant == Enchantments.UNBREAKING || enchant == Enchantments.MENDING;
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
        switch(gem){
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
        return switch(material){
            case LEATHER -> getGlovesTexture("leather", !HandsRenderer.isDefault);
            case IRON -> getGlovesTexture("iron", !HandsRenderer.isDefault);
            case GOLD -> getGlovesTexture("golden", !HandsRenderer.isDefault);
            case DIAMOND -> getGlovesTexture("diamond", !HandsRenderer.isDefault);
            case NETHERITE -> getGlovesTexture("netherite", !HandsRenderer.isDefault);
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(gem == AccessoryGem.AMBER){
            tooltip.add(Component.translatable("tooltip.valoria.amber").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb_equip").withStyle(ChatFormatting.GREEN));
    }
}
