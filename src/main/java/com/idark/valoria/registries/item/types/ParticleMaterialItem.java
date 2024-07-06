package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.interfaces.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.awt.*;

public class ParticleMaterialItem extends Item implements IParticleItemEntity, ParticleEmitterHandler.ItemParticleSupplier{
    public ParticleType<?> particle;
    public ColorParticleData color;
    public float alpha;

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, Color pColor, Color pColorTo, float pAlpha, Properties pProperties){
        super(pProperties);
        particle = pType;
        color = ColorParticleData.create(pColor, pColorTo).build();
        alpha = pAlpha;
    }

    public ParticleMaterialItem(@NotNull ParticleType<?> pType, Color pColor, Color pColorTo, Properties pProperties){
        super(pProperties);
        particle = pType;
        color = ColorParticleData.create(pColor, pColorTo).build();
        alpha = 1f;
    }

    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        RandomSource rand = level.getRandom();
        Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
        if(particle != null && color != null){
            if(!entity.isInWater()){
                Color particleColor = new Color(color.r1, color.g1, color.b1);
                Color particleColorTo = new Color(color.r2, color.g2, color.b2);

                ParticleEffects.itemParticles(level, pos, ColorParticleData.create(particleColor, particleColorTo).build()).spawnParticles();
            }

            if(entity.getItem().is(TagsRegistry.SMOKE_PARTICLE)){
                ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Color.black, Pal.smoke).build()).getBuilder().setLifetime(16).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(level, pos.x, pos.y, pos.z);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleRegistry.spawnLightParticles(target, color);
    }
}