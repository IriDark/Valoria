package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ScreenParticleRegistry;
import com.idark.valoria.core.interfaces.IParticleItemEntity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import java.awt.*;

import static com.idark.valoria.client.particle.ParticleEffects.spawnItemParticles;

public class ParticleMaterialItem extends Item implements IParticleItemEntity, ParticleEmitterHandler.ItemParticleSupplier {
    public ParticleType<?> particle;
    public ColorParticleData color;
    public float alpha;

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, Color pColor, Color pColorTo, float pAlpha, Properties pProperties) {
        super(pProperties);
        particle = pType;
        color = ColorParticleData.create(pColor, pColorTo).build();
        alpha = pAlpha;
    }

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, Color pColor, Color pColorTo, Properties pProperties) {
        super(pProperties);
        particle = pType;
        color = ColorParticleData.create(pColor, pColorTo).build();
        alpha = 1f;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity) {
        spawnItemParticles(level, entity, particle, color);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleRegistry.spawnLightParticles(target, color);
    }
}