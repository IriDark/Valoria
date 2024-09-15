package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ParticleEffects;
import com.idark.valoria.core.interfaces.IProjectileTexture;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.util.Pal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

public class WickedArrow extends AbstractValoriaArrow implements IProjectileTexture {

    public WickedArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public WickedArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown) {
        super(EntityTypeRegistry.WICKED_ARROW.get(), pLevel, pShooter, thrown, 0, 4);
    }

    @Override
    public void spawnParticlesTrail() {
        if (!this.inGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            Vec3 pos = new Vec3(this.getX() + a3 * 0.5f, this.getY() + a4 * 0.5f, this.getZ() + a0 * 0.5f);
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Pal.vividPink, Pal.darkViolet).build()).spawnParticles();
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/wicked_arrow.png");
    }
}