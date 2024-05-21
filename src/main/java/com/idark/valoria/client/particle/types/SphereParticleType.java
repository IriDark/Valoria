package com.idark.valoria.client.particle.types;

import com.mojang.serialization.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;

public class SphereParticleType extends ParticleType<GenericParticleData>{

    public SphereParticleType(){
        super(true, GenericParticleData.DESERIALIZER);
    }

    @Override
    public Codec<GenericParticleData> codec(){
        return GenericParticleData.codecFor(this);
    }

    public static class Factory implements ParticleProvider<GenericParticleData>{
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite){
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(GenericParticleData data, ClientLevel world, double x, double y, double z, double mx, double my, double mz){
            SphereParticle ret = new SphereParticle(world, data, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}