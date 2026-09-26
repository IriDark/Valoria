package com.idark.valoria.registries.item.types.shield;

import com.idark.valoria.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.component.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.types.*;

public class DraugrShieldItem extends ConfiguredShield{
    public final ItemAttributeModifiers defaultModifiers;

    public DraugrShieldItem(AbstractShieldBuilder<? extends ConfiguredShield> builder){
        super(builder);
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(Attributes.MOVEMENT_SPEED, new AttributeModifier(Valoria.loc("draugr_shield_speed"), 0.1, Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.OFFHAND)
            .build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.defaultModifiers;
    }

    public static class Builder extends AbstractShieldBuilder<DraugrShieldItem>{

        public Builder(Properties itemProperties) {
            super(itemProperties);
        }

        public Builder(float defPercent, Properties itemProperties) {
            super(defPercent, itemProperties);
        }

        @Override
        public DraugrShieldItem build(){
            return new DraugrShieldItem(this);
        }
    }
}
