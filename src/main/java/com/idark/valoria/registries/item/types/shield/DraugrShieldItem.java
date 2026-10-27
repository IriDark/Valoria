package com.idark.valoria.registries.item.types.shield;

import com.idark.valoria.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.types.*;

public class DraugrShieldItem extends ConfiguredShield{

    public DraugrShieldItem(AbstractShieldBuilder<? extends ConfiguredShield> builder){
        super(builder);
    }

    public static class Builder extends AbstractShieldBuilder<DraugrShieldItem>{

        public Builder(Properties itemProperties) {
            super(itemProperties);
            addAttribute(Attributes.MOVEMENT_SPEED, new AttributeModifier(Valoria.loc("draugr_shield_speed"), 0.1, Operation.ADD_MULTIPLIED_TOTAL));
        }

        public Builder(float defPercent, Properties itemProperties) {
            super(defPercent, itemProperties);
            addAttribute(Attributes.MOVEMENT_SPEED, new AttributeModifier(Valoria.loc("draugr_shield_speed"), 0.1, Operation.ADD_MULTIPLIED_TOTAL));
        }

        @Override
        public DraugrShieldItem build(){
            return new DraugrShieldItem(this);
        }
    }
}
