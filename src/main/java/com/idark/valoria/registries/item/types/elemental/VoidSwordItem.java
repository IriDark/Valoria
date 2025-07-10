package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;

import java.util.*;

public class VoidSwordItem extends SwordItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public VoidSwordItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, (int)pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.attackDamage = pAttackDamageModifier + pTier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.VOID_DAMAGE.get(), new AttributeModifier(Valoria.BASE_VOID_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage - 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(2, pAttacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.addContributorTooltip(stack, tooltip);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}
