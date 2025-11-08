package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class PhantomArrow extends AbstractProjectile implements TexturedArrow{
    private boolean child;
    public boolean burst;
    public float spread = 6;
    public PhantomArrow(EntityType<? extends AbstractProjectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public PhantomArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.PHANTOM_ARROW.get(), pLevel, pShooter, thrown, 6);
    }

    @Override
    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        var pos = pResult.getLocation();
        if(this.level() instanceof ServerLevel serverLevel){
            if(!this.child){
                if(!this.burst) return;
                for(int i = 0; i < 12; i++){
                    PhantomArrow arrow = EntityTypeRegistry.PHANTOM_ARROW.get().create(serverLevel);
                    if(arrow != null){
                        double offsetX = (random.nextDouble() * spread * 2) - spread;
                        double offsetZ = (random.nextDouble() * spread * 2) - spread;

                        double x = pos.x + offsetX;
                        double z = pos.z + offsetZ;
                        double y = pos.y + 16 + random.nextDouble() * 8;

                        arrow.moveTo(x, y, z, 0.0F, 0.0F);
                        arrow.setDeltaMovement(0, -1.5 - random.nextDouble(), 0);
                        arrow.child = true;
                        if(this.getOwner() != null) arrow.setOwner(this.getOwner());

                        serverLevel.addFreshEntity(arrow);
                        serverLevel.playSound(null, x, y, z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.9F + random.nextFloat() * 0.2F);
                    }
                }
            }else{
                serverLevel.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0.5f);
                this.removeAfterChangingDimensions();
            }
        }

        burst = false;
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

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Col.fromHex("193ce1"), Col.fromHex("201aeb")).build());
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Col.fromHex("193ce1"), Col.fromHex("201aeb")).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/phantom_arrow.png");
    }
}
