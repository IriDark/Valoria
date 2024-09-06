package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.client.render.curio.*;
import com.idark.valoria.registries.item.enums.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;

public class DyeableGlovesItem extends GlovesItem implements ICurioItem, ICurioTexture, DyeableLeatherItem, Vanishable{
    private final float damage, armor;

    public DyeableGlovesItem(Tier tier, Item.Properties properties, float damage, float armor){
        super(tier, AccessoryGem.NONE, AccessoryMaterial.LEATHER, properties);
        this.damage = damage;
        this.armor = armor;
    }

    public DyeableGlovesItem(Tier tier, Item.Properties properties){
        super(tier, AccessoryGem.NONE, AccessoryMaterial.LEATHER, properties);
        this.damage = 0.5f;
        this.armor = 0.2f;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> attribute = LinkedHashMultimap.create();
        attribute.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", this.damage, AttributeModifier.Operation.ADDITION));
        attribute.put(Attributes.ARMOR, new AttributeModifier(uuid, "bonus", this.armor, AttributeModifier.Operation.ADDITION));
        return attribute;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        int pDefaultDamage = new Random().nextInt(0, 2);
        if(player.hurtMarked){
            stack.hurtAndBreak(pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        return getGlovesTexture("leather", !HandsRenderer.isDefault);
    }
}
