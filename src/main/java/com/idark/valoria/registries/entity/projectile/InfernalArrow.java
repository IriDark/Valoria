package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.*;

public class InfernalArrow extends AbstractValoriaArrow implements IProjectileTexture{

    public InfernalArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public InfernalArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.INFERNAL_ARROW.get(), pLevel, pShooter, thrown, 0, 1);
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
                .setAlpha(0.75f, 0)
                .setScale(RandomUtil.randomValueUpTo(0.15f), 0)
                .setColor(235, 12, 65, 98, 24, 115)
                .setLifetime(6)
                .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
                .spawn(this.level(), this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D);
            }
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/infernal_arrow.png");
    }
}
