package com.idark.valoria.client.particle;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;

import javax.annotation.Nonnull;

public class ShadewoodLeafParticle extends GenericParticle {

    public ShadewoodLeafParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}