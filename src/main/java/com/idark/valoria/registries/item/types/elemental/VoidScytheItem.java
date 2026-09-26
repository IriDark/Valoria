package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.component.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_ID;

public class VoidScytheItem extends ScytheItem{
    private final float attackDamage;
    public VoidScytheItem(Builder scytheBuilder){
        super(scytheBuilder);
        this.attackDamage = scytheBuilder.attackDamageIn + scytheBuilder.tier.getAttackDamageBonus();
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(AttributeReg.NIHILITY_DAMAGE, new AttributeModifier(Valoria.BASE_NIHILITY_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.attackDamage - 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, scytheBuilder.attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }
}
