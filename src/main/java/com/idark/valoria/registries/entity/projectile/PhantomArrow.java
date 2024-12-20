package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

public class PhantomArrow extends AbstractValoriaArrow implements TexturedArrow {

    public PhantomArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PhantomArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown) {
        super(EntityTypeRegistry.PHANTOM_ARROW.get(), pLevel, pShooter, thrown, 6);
    }

    @Override
    public void spawnParticlesTrail() {
        if (!this.inGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            Vec3 pos = new Vec3(this.getX() + a3 * 0.5f, this.getY() + a4 * 0.5f, this.getZ() + a0 * 0.5f);
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(ColorUtil.valueOf("193ce1"), ColorUtil.valueOf("201aeb")).build());
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/phantom_arrow.png");
    }
}
