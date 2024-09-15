package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.core.enums.AccessoryGem;
import com.idark.valoria.core.enums.AccessoryMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Random;
import java.util.UUID;

public class DyeableGlovesItem extends GlovesItem implements ICurioItem, ICurioTexture, DyeableLeatherItem, Vanishable {
    private final float damage, armor;

    public DyeableGlovesItem(Tier tier, Item.Properties properties, float damage, float armor) {
        super(tier, AccessoryGem.NONE, AccessoryMaterial.LEATHER, properties);
        this.damage = damage;
        this.armor = armor;
    }

    public DyeableGlovesItem(Tier tier, Item.Properties properties) {
        super(tier, AccessoryGem.NONE, AccessoryMaterial.LEATHER, properties);
        this.damage = 0.5f;
        this.armor = 0.2f;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attribute = LinkedHashMultimap.create();
        attribute.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", this.damage, AttributeModifier.Operation.ADDITION));
        attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", this.armor, AttributeModifier.Operation.ADDITION));
        return attribute;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        int pDefaultDamage = new Random().nextInt(0, 2);
        if (player.hurtMarked) {
            stack.hurtAndBreak(pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return getGlovesTexture("leather", !HandsRenderer.isDefault);
    }
}
