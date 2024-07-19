package com.idark.valoria.registries.effect;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;
import java.util.*;

public class BleedingEffect extends MobEffect{

    public BleedingEffect(){
        super(MobEffectCategory.HARMFUL, ColorUtil.hexToDecimal("e02c2c"));
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

            if(pEntity.level().isClientSide()){
                for(int a = 0; a < 5; a++){
                    spawnParticles(pEntity);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticles(LivingEntity pEntity){
        Vec3 pos = new Vec3(pEntity.getX() + (new Random().nextDouble() - 0.5f) / 2, pEntity.getY() + (new Random().nextDouble() + 1f) / 2, pEntity.getZ());
        ParticleEffects.particles(pEntity.level(), pos, ColorParticleData.create(Pal.darkRed, Color.red).build()).getBuilder().setRandomMotion(0.5f, 0, 0.5f).setRandomOffset(0.7f, 0f, 0.7f).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).setGravityStrength(0.75f).spawn(pEntity.level(), pos.x, pos.y, pos.z);
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