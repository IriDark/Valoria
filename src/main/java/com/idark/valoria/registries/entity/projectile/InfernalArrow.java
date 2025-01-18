package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

public class InfernalArrow extends AbstractValoriaArrow implements TexturedArrow {

    public InfernalArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void spawnParticlesTrail() {
        if (!this.inGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            Vec3 pos = new Vec3(this.getX() + a3 * 0.00015, this.getY() + a4 * 0.00015, this.getZ() + a0 * 0.00015);
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Pal.infernal, Pal.darkMagenta).build());
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/infernal_arrow.png");
    }
}
