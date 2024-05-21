package com.idark.valoria.registries.effect;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.*;

public class BleedingEffect extends MobEffect{

    public BleedingEffect(){
        super(MobEffectCategory.HARMFUL, ValoriaUtils.color.hexToDecimal("e02c2c"));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", -0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pEntity, int amplifier){
        DamageSource dmg = DamageSourceRegistry.source(pEntity.level(), DamageSourceRegistry.BLEEDING);
        if(!pEntity.hasEffect(EffectsRegistry.ALOEREGEN.get()) && !pEntity.hasEffect(MobEffects.REGENERATION)){
            if(amplifier < 1){
                if(pEntity.getHealth() > 2){
                    pEntity.hurt(dmg, 1);
                }
            }else{
                pEntity.hurt(dmg, 1);
            }

            for(int a = 0; a < 5; a++){
                Particles.create(ParticleRegistry.SPHERE)
                .randomOffset(0.7f, 0f, 0.7f)
                .randomVelocity(0.5f, 0, 0.5f)
                .enableGravity()
                .setAlpha(1f, 0)
                .setScale(0.1f, 0)
                .setColor(145, 0, 20, 255, 0, 0)
                .setLifetime(6)
                .spawn(pEntity.level(), pEntity.getX() + (new Random().nextDouble() - 0.5f) / 2, pEntity.getY() + (new Random().nextDouble() + 1f) / 2, pEntity.getZ());
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        int i;
        i = 50 >> amplifier;
        if(i > 0){
            return duration % i == 0;
        }else{
            return true;
        }
    }
}