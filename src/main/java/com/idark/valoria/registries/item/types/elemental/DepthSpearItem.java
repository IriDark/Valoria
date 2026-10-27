package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.entity.ai.attributes.*;

public class DepthSpearItem extends SpearItem{

    public DepthSpearItem(AbstractSpearBuilder<? extends DepthSpearItem> builder){
        super(builder);
    }

    public static class Builder extends AbstractSpearBuilder<DepthSpearItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn - 3, attackSpeedIn, itemProperties);
            addAttribute(AttributeReg.DEPTH_DAMAGE, new AttributeModifier(Valoria.BASE_DEPTH_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE));
        }

        @Override
        public DepthSpearItem build(){
            return new DepthSpearItem(this);
        }
    }
}
