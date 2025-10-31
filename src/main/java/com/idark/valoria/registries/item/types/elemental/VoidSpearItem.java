package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import pro.komaru.tridot.common.registry.item.*;

import static com.idark.valoria.Valoria.BASE_ENTITY_REACH_UUID;
import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_UUID;

public class VoidSpearItem extends SpearItem{

    public VoidSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn, pEffects);
    }

    public VoidSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, boolean pThrowable, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, pThrowable, builderIn);
    }

    public VoidSpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, float pChance, Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, pChance, builderIn, pEffects);
    }

    public Multimap<Attribute, AttributeModifier> createAttributes(){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.NIHILITY_DAMAGE.get(), new AttributeModifier(Valoria.BASE_NIHILITY_DAMAGE_UUID, "Weapon modifier", 3, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.builder.attackDamageIn - 3, AttributeModifier.Operation.ADDITION));
        if(this.builder.projectileDamageIn > 0) builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_PROJECTILE_DAMAGE_UUID, "Tool modifier", this.builder.projectileDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.builder.attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Spear modifier", this.builder.entityReach, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
