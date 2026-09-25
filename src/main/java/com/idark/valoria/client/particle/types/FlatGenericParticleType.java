package com.idark.valoria.client.particle.types;

import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.gfx.particle.type.*;

public class FlatGenericParticleType extends GenericParticleType {
    public FlatGenericParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<GenericParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(GenericParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new FlatGenericParticle(level, options, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}
