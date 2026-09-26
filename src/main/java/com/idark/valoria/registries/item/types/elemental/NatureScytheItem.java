package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_ID;

public class NatureScytheItem extends ScytheItem{
    private final float attackDamage;
    public NatureScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(AttributeReg.NATURE_DAMAGE, new AttributeModifier(Valoria.BASE_NATURE_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.attackDamage - 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }
}
