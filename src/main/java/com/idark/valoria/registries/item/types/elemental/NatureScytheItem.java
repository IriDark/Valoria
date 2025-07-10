package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class NatureScytheItem extends ScytheItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public NatureScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.NATURE_DAMAGE.get(), new AttributeModifier(Valoria.BASE_NATURE_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage - 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}
