package com.idark.valoria.registries.effect;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;

import java.awt.*;
import java.util.*;

public class BleedingEffect extends MobEffect {

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, ColorUtil.hexToDecimal("e02c2c"));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", -0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pEntity, int amplifier) {
        DamageSource dmg = DamageSourceRegistry.source(pEntity.level(), DamageSourceRegistry.BLEEDING);
        if (!pEntity.hasEffect(EffectsRegistry.ALOEREGEN.get()) && !pEntity.hasEffect(MobEffects.REGENERATION)) {
            if (amplifier < 1) {
                if (pEntity.getHealth() > 2) {
                    pEntity.hurt(dmg, 1);
                }
            } else {
                pEntity.hurt(dmg, 1);
            }

            if (pEntity.level().isClientSide()) {
                spawnParticles(pEntity);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticles(LivingEntity pEntity) {
        Vec3 pos = new Vec3(pEntity.getX() + (new Random().nextDouble() - 0.5f) / 2, pEntity.getY() + (new Random().nextDouble() + 1f) / 2, pEntity.getZ());
        ParticleBuilder.create(FluffyFurParticles.WISP)
        .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
        .setBehavior(SparkParticleBehavior.create().build())
        .setTransparencyData(GenericParticleData.create(1F, 0.6F, 0.0F).setEasing(Easing.EXPO_IN_OUT).build())
        .setScaleData(GenericParticleData.create(0.06F, 0.2F, 0.0F).setEasing(Easing.BOUNCE_IN_OUT).build())
        .randomVelocity(0.2f)
        .addVelocity(0.0f, 0.2f, 0.0f)
        .randomOffset(0.05f)
        .setFriction(1f)
        .enablePhysics()
        .setGravity(1f)
        .setColorData(ColorParticleData.create(Color.red, Pal.darkRed).build())
        .setLifetime(22)
        .repeat(pEntity.level(), pos.x, pos.y, pos.z, 6);
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