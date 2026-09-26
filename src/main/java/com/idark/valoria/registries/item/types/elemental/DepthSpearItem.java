package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.component.*;
import pro.komaru.tridot.common.registry.item.*;

import static com.idark.valoria.Valoria.BASE_ENTITY_REACH_ID;
import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_ID;

public class DepthSpearItem extends SpearItem{

    public DepthSpearItem(Builder builder){
        super(builder);
    }

    public ItemAttributeModifiers createAttributes(){
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(AttributeReg.DEPTH_DAMAGE, new AttributeModifier(Valoria.BASE_DEPTH_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.builder.attackDamageIn - 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        if(this.builder.projectileDamageIn > 0) builder.add(AttributeRegistry.PROJECTILE_DAMAGE, new AttributeModifier(BASE_PROJECTILE_DAMAGE_ID, this.builder.projectileDamageIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, this.builder.attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_REACH_ID, this.builder.entityReach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }
}
