package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.postprocess.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;

import java.lang.Math;
import java.util.*;

public class AcidSpit extends AbstractProjectile{
    public final Set<MobEffectInstance> effects = Sets.newHashSet();
    boolean child = false;

    public AcidSpit(EntityType<? extends AbstractProjectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public AcidSpit(LivingEntity pShooter, Level pLevel){
        super(EntityTypeRegistry.ACID_SPIT.get(), pLevel);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.GENERIC_SPLASH;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.GENERIC_SPLASH;
    }

    private void summonStormCrystal(ServerLevel serverLevel, Vec3 spawnPos, float angle, double speed) {
        AcidSpit shard = EntityTypeRegistry.ACID_SPIT.get().create(this.level());
        if (shard != null) {
            shard.moveTo(spawnPos.x(), spawnPos.y() + 2, spawnPos.z(), 0.0F, 0.0F);
            shard.setOwner(this.getOwner());
            shard.setChild(true);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            shard.setDeltaMovement(vx, 0.4, vz);
            serverLevel.addFreshEntity(shard);
        }
    }

    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        if(!child){
            if(level().isClientSide()) GlowPostProcess.INSTANCE.addInstance(new GlowPostProcessInstance(pResult.getLocation().toVector3f(), new Vector3f((float)92 / 255, (float)219 / 255, (float)70 / 255)).setIntensity(0.325f).setFadeTime(45).setRadius(4));
            if(level() instanceof ServerLevel serv){
                for(int i = 0; i < 6; i++){
                    float angle = (float)((2 * Math.PI / 6) * i);
                    summonStormCrystal(serv, pResult.getLocation(), angle, 0.25);
                }
            }
        }

        this.discard();
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticlesTrail(){
        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            ParticleBuilder.create(ParticleRegistry.ACID_SPIT)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setScaleData(GenericParticleData.create(this.isChild() ? 0.15f : 0.35f).build())
            .setTransparencyData(GenericParticleData.create(1, 0.4f, 0).build())
            .setColorData(ColorParticleData.create(Pal.kiwi).build())
            .setGravity(0)
            .setLifetime(5)
            .spawn(this.level(), this.getX(), this.getY(), this.getZ());
        }
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public void addEffect(MobEffectInstance pEffectInstance){
        this.effects.add(pEffectInstance);
    }

    public void setEffectsFromList(ImmutableList<MobEffectInstance> effects){
        this.effects.addAll(effects);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsChild", this.child);
        if(!this.effects.isEmpty()){
            ListTag listtag = new ListTag();
            for(MobEffectInstance mobeffectinstance : this.effects){
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            pCompound.put("CustomPotionEffects", listtag);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.child = pCompound.getBoolean("IsChild");
        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pCompound)){
            this.addEffect(mobeffectinstance);
        }
    }

    public boolean isChild(){
        return this.child;
    }
}
