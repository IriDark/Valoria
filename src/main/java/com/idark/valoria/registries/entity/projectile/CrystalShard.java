package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class CrystalShard extends AbstractProjectile{
    public CrystalShard(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        discardOnHit = true;
    }

    public CrystalShard(Level pLevel, LivingEntity thrower, double damage){
        super(EntityTypeRegistry.CRYSTAL_SHARD.get(), pLevel, thrower, damage);
        discardOnHit = true;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.CRYSTAL_FALL.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void spawnParticlesTrail(){
        if(!this.inGround){
            Vec3 delta = this.getDeltaMovement().normalize();
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
            final Vec3[] cachePos = {new Vec3(pos.x, pos.y, pos.z)};
            final Consumer<GenericParticle> target = p -> {
                Vec3 arrowPos = new Vec3(getX(), getY(), getZ());
                float lenBetweenArrowAndParticle = (float)(arrowPos.subtract(cachePos[0])).length();
                Vec3 vector = (arrowPos.subtract(cachePos[0]));
                if(lenBetweenArrowAndParticle > 0){
                    cachePos[0] = cachePos[0].add(vector);
                    p.setPosition(cachePos[0]);
                }
            };

            ParticleBuilder.create(TridotParticles.TRAIL)
            .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
            .setBehavior(TrailParticleBehavior.create().build())
            .setColorData(ColorParticleData.create(Pal.verySoftPink, Pal.darkMagenta).build())
            .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.sineOut).build())
            .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.exp5In).build())
            .addTickActor(target)
            .setGravity(0)
            .setLifetime(12)
            .spawn(this.level(), pos.x, pos.y, pos.z);
        }
    }
}