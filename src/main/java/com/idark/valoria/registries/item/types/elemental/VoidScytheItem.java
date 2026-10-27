package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.world.entity.ai.attributes.*;

public class VoidScytheItem extends ScytheItem{
    public VoidScytheItem(AbstractScytheBuilder<? extends VoidScytheItem> scytheBuilder){
        super(scytheBuilder);
    }

    public static class Builder extends AbstractScytheBuilder<VoidScytheItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn - 2, attackSpeedIn, itemProperties);
            addAttribute(AttributeReg.NIHILITY_DAMAGE, new AttributeModifier(Valoria.BASE_NIHILITY_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE));
        }

        @Override
        public VoidScytheItem build(){
            return new VoidScytheItem(this);
        }
    }
}
