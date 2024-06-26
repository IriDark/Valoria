package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PhantomArrow extends AbstractValoriaArrow implements IProjectileTexture{

    public PhantomArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public PhantomArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.PHANTOM_ARROW.get(), pLevel, pShooter, thrown, 2, 6);
    }

    @Override
    public void spawnParticlesTrail(){
        if(!this.inGround){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            Random rand = new Random();
            for(int a = 0; a < 1; ++a){
                Particles.create(ParticleRegistry.GLOWING_SPHERE)
                .addVelocity((rand.nextDouble() / 32), 0.02f, (rand.nextDouble() / 32))
                .setAlpha(1, 0)
                .setScale(RandomUtil.randomValueUpTo(0.15f), 0)
                .setColor(25, 60, 225, 32, 26, 235)
                .setLifetime(6)
                .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
                .spawn(this.level(), this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D);
            }
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/phantom_arrow.png");
    }
}
