package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.interfaces.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import static com.idark.valoria.client.particle.ParticleEffects.spawnItemParticles;

//todo
public class ParticleMaterialItem extends Item implements IParticleItemEntity {
    public ParticleType<?> particle;
    public ColorParticleData color;
    public float alpha = 1f;
    public ParticleMaterialItem(Properties pProperties) {
        super(pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity) {
        spawnItemParticles(level, entity, particle, color);
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y){
//        ScreenParticleRegistry.spawnLightParticles(target, color);
//    }
}