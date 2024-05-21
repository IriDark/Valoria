package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.item.interfaces.*;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ParticleMaterialItem extends Item implements IParticleItem{
    public ParticleType<?> particle;
    public int[] color;
    public int[] colorTo;
    public float alpha;

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, int @NotNull [] pColor, int @NotNull [] pColorTo, float pAlpha, Properties pProperties){
        super(pProperties);
        particle = pType;
        color = pColor;
        colorTo = pColorTo;
        alpha = pAlpha;
    }

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, int @NotNull [] pColor, float pAlpha, Properties pProperties){
        super(pProperties);
        particle = pType;
        color = pColor;
        colorTo = new int[]{0, 0, 0};
        alpha = pAlpha;
    }

    @Override
    public void addParticles(Level level, ItemEntity entity){
        RandomSource rand = level.getRandom();
        if(particle != null && color != null && colorTo != null){
            if(!entity.isInWater()){
                Particles.create(particle)
                .addVelocity((rand.nextDouble() / 32), 0.08f, (rand.nextDouble() / 32))
                .setAlpha(alpha, 0)
                .setScale(RandomUtil.randomValueUpTo(0.15f), 0)
                .setColor(color[0], color[1], color[2], colorTo[0], colorTo[1], colorTo[2])
                .setLifetime(6)
                .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
                .spawn(level, entity.getX() + (rand.nextDouble() - 0.5f) / 4, entity.getY() + 0.35F, entity.getZ());
            }

            if(entity.getItem().is(ItemsRegistry.INFERNAL_STONE.get())){
                Particles.create(ParticleRegistry.SPHERE)
                .addVelocity((rand.nextDouble() / 32), 0.062f, (rand.nextDouble() / 32))
                .setAlpha(alpha, 0)
                .setScale(RandomUtil.randomValueUpTo(0.1f), RandomUtil.randomValueUpTo(0.1f))
                .setColor(0, 0, 0, 0, 0, 0)
                .setLifetime(7)
                .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
                .spawn(level, entity.getX() + (rand.nextDouble() - 0.5f) / 4, entity.getY() + 0.55F, entity.getZ());
            }
        }
    }
}