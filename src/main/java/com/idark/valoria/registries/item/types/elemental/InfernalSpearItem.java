package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class InfernalSpearItem extends SpearItem{

    public InfernalSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn, MobEffectInstance... pEffects){
        super(new SpearItem.Builder(attackDamageIn - 3, attackSpeedIn, builderIn)
            .setTier(tier)
            .setEffects(pEffects)
            .addAttribute(AttributeReg.INFERNAL_DAMAGE, new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE)));
    }

    public InfernalSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, float pChance, Properties builderIn, MobEffectInstance... pEffects){
        super(new SpearItem.Builder(attackDamageIn - 3, attackSpeedIn, builderIn)
            .setTier(tier)
            .setEffects(pChance, pEffects)
            .addAttribute(AttributeReg.INFERNAL_DAMAGE, new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE)));
    }

    public InfernalSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, boolean pThrowable, Properties builderIn){
        super(new SpearItem.Builder(attackDamageIn - 3, attackSpeedIn, builderIn)
            .setTier(tier)
            .setThrowable(pThrowable)
            .addAttribute(AttributeReg.INFERNAL_DAMAGE, new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE)));
    }
}
