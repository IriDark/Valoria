package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.entity.ModEntityTypes;
import com.idark.valoria.util.RandomUtil;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SoulArrow extends AbstractValoriaArrow implements IProjectileTexture {

    public SoulArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SoulArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown) {
        super(ModEntityTypes.SOUL_ARROW.get(), pLevel, pShooter, thrown, 0, 1);
    }

    @Override
    public void tick() {
        super.tick();
        ValoriaUtils.inaccurateHomingMovement(0.2f, this, this.level(), this.getOwner(), new AABB(this.getX() - 4, this.getY() - 4, this.getZ() - 4, this.getX() + 4, this.getY() + 4, this.getZ() + 4));
    }

    @Override
    public void spawnParticlesTrail() {
        if(!this.inGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            Random rand = new Random();
            for (int a = 0; a < 1; ++a) {
                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity((rand.nextDouble() / 32), 0.02f, (rand.nextDouble() / 32))
                        .setAlpha(1, 0)
                        .setScale(RandomUtil.randomValueUpTo(0.15f), 0)
                        .setColor(25, 65, 155, 0, 0, 175)
                        .setLifetime(6)
                        .setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
                        .spawn(this.level(), this.getX() + a3 * (double) a / 4.0D, this.getY() + a4 * (double) a / 4.0D, this.getZ() + a0 * (double) a / 4.0D);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Valoria.MOD_ID, "textures/entity/projectile/arrow/soul_arrow.png");
    }
}