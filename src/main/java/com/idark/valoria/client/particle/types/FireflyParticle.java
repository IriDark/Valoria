package com.idark.valoria.client.particle.types;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class FireflyParticle extends TextureSheetParticle{
    protected FireflyParticle(ClientLevel level, double posX, double posY, double posZ){
        super(level, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.scale(0.35F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = this.random.nextInt(20) + 40;
        this.gravity = 3.0E-6F;

        this.xd = this.random.nextFloat() / 50;
        this.yd = 0.0015 + (double)(this.random.nextFloat() / 500.0F);
        this.zd = this.random.nextFloat() / 50;
    }

    @Override
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    protected int getLightColor(float pPartialTick){
        return 15728880;
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
        if(this.age++ < this.lifetime && !(this.alpha <= 0.0F)){
            this.xd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.zd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if(this.age >= this.lifetime - 60 && this.alpha > 0.01F){
                this.alpha -= 0.025F;
                this.quadSize *= 1F;
            }
        }else{
            this.remove();
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Factory(SpriteSet sprite){
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            FireflyParticle particle = new FireflyParticle(level, x, y + 0.3D, z);
            particle.pickSprite(this.spriteSet);
            particle.setAlpha(0.9F);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}