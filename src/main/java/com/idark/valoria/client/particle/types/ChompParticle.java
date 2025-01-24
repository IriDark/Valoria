package com.idark.valoria.client.particle.types;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class ChompParticle extends TextureSheetParticle{
    private final SpriteSet sprites;

    protected ChompParticle(ClientLevel level, double posX, double posY, double posZ, SpriteSet pSprites){
        super(level, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.xd *= 0.01F;
        this.yd *= 0.01F;
        this.zd *= 0.01F;
        this.yd += 0.1D;
        this.sprites = pSprites;
        this.quadSize *= 2.5F;
        this.lifetime = 16;
        this.hasPhysics = false;
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float scaleFactor){
        return this.quadSize * Mth.clamp(((float)this.age + scaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick(){
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if(this.age++ >= this.lifetime){
            this.remove();
        }else{
            this.setSpriteFromAge(this.sprites);
            this.setAlpha(this.lifetime);
            this.move(0, yd, 0);
            if(this.y == this.yo){
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.yd *= 0.56F;
            if(this.onGround){
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Factory(SpriteSet sprite){
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            ChompParticle particle = new ChompParticle(level, x, y + 0.3D, z, this.spriteSet);
            particle.pickSprite(this.spriteSet);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}