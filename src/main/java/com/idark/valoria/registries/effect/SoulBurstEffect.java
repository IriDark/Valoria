package com.idark.valoria.registries.effect;

import com.idark.valoria.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class SoulBurstEffect extends MobEffect{

    public SoulBurstEffect(){
        super(MobEffectCategory.BENEFICIAL, 0xd3fffd);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "a8b392d9-f483-4878-b392-d9f4839878aa", 0.25, Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "15369de5-d2c1-427f-b69d-e5d2c1727fb7", 0.05, Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier){
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if(pLivingEntity.level().isClientSide()){
            spawnParticles(pLivingEntity);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticles(LivingEntity pEntity){
        Vec3 pos = new Vec3(pEntity.getX() + (new Random().nextDouble() - 0.5f) / 2, pEntity.getY() + (new Random().nextDouble() + 1f) / 2, pEntity.getZ());
        ParticleBuilder.create(TridotParticles.WISP)
        .setBehavior(SparkParticleBehavior.create().build())
        .setTransparencyData(GenericParticleData.create(0.5F, 0.0F).setEasing(Interp.bounceOut).build())
        .setScaleData(GenericParticleData.create(0.05F, 0.2F, 0.0F).setEasing(Interp.bounce).build())
        .randomVelocity(0.125f)
        .addVelocity(0.0f, 0.10f, 0.0f)
        .randomOffset(0.025f)
        .setColorData(ColorParticleData.create(Col.blue, Pal.lightCarminePink).build())
        .setLifetime(10)
        .repeat(pEntity.level(), pos.x, pos.y, pos.z, 6);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}