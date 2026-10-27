package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class NatureScytheItem extends ScytheItem{
    public NatureScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(new ScytheItem.Builder(attackDamageIn - 2, attackSpeedIn, builderIn)
            .setTier(tier)
            .addAttribute(AttributeReg.NATURE_DAMAGE, new AttributeModifier(Valoria.BASE_NATURE_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE)));
    }
}
