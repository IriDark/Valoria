package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ParticleEffects;
import com.idark.valoria.core.interfaces.TexturedArrow;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class PhantomArrow extends AbstractValoriaArrow implements TexturedArrow{

    public PhantomArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public PhantomArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.PHANTOM_ARROW.get(), pLevel, pShooter, thrown, 6);
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

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(ColorUtil.valueOf("193ce1"), ColorUtil.valueOf("201aeb")).build());
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(ColorUtil.valueOf("193ce1"), ColorUtil.valueOf("201aeb")).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/phantom_arrow.png");
    }
}
