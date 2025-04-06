package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class ThrownSpearEntity extends AbstractSupplierProjectile{
    private float explosive_radius;
    private boolean shouldExplode;
    private boolean isExploded;
    private boolean isPyratiteHit;
    private Level.ExplosionInteraction interaction;

    public ThrownSpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.SPEAR.get(), worldIn, thrower, thrownStackIn);
        this.setItem(thrownStackIn);
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level worldIn){
        super(type, worldIn);
    }

    @Override
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
            .setColorData(ColorParticleData.create(Pal.darkerGray.brighter()).build())
            .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Interp.sineOut).build())
            .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
            .addTickActor(target)
            .setGravity(0)
            .setLifetime(10)
            .repeat(this.level(), pos.x, pos.y, pos.z, 1);
        }
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public SoundEvent getReturnSound(){
        return SoundsRegistry.SPEAR_RETURN.get();
    }

    public void setExplode(Level.ExplosionInteraction pInteraction, float radius){
        this.shouldExplode = true;
        interaction = pInteraction;
        explosive_radius = radius;
    }

    private void summonStormCrystal(ServerLevel serverLevel, BlockPos spawnPos, float angle, double speed) {
        PyratiteShard shard = EntityTypeRegistry.PYRATITE_SHARD.get().create(this.level());
        if (shard != null) {
            shard.moveTo(spawnPos.getX(), spawnPos.getY() + 2, spawnPos.getZ(), 0.0F, 0.0F);
            shard.setOwner(this.getOwner());
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            shard.setDeltaMovement(vx, 0.4, vz);
            serverLevel.addFreshEntity(shard);
        }
    }

    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        if(!isPyratiteHit){
            if(this.getItem().is(ItemsRegistry.pyratiteSpear.get()) && level() instanceof ServerLevel serv){
                BlockPos center = BlockPos.containing(pResult.getLocation());
                for(int i = 0; i < 4; i++){
                    float angle = (float)((2 * Math.PI / 4) * i);
                    summonStormCrystal(serv, center, angle, 0.05);
                }

                this.isPyratiteHit = true;
            }
        }

        if(this.shouldExplode && !this.isExploded){
            if(!this.level().isClientSide){
                this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), explosive_radius, interaction);
                ScreenshakeHandler.add(new ScreenshakeInstance(3).intensity(0.75f));
            }

            this.isExploded = true;
        }
    }
}