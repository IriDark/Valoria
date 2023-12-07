package com.idark.darkrpg.effect;

import com.idark.darkrpg.damage.ModDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BleedingEffect extends MobEffect {

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 16262179);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", (double) -0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pEntity, int amplifier) {
        super.applyEffectTick(pEntity, amplifier);
        if (amplifier < 1) {
            if (pEntity.getHealth() > 2.0F) {
                pEntity.hurt(new DamageSource(ModDamageSources.source(pEntity.level(), ModDamageSources.BLEEDING).typeHolder()), 1.0F);
            }
        } else {
            pEntity.hurt(new DamageSource(ModDamageSources.source(pEntity.level(), ModDamageSources.BLEEDING).typeHolder()), 1.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i;
        i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}