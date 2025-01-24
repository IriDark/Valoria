package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.function.*;

public class SpellProjectile extends AbstractProjectile {
    public SpellProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        discardOnHit = true;
    }

    public SpellProjectile(Level pLevel, LivingEntity thrower, int damage) {
        super(EntityTypeRegistry.SPELL.get(), pLevel, thrower, damage);
        discardOnHit = true;
    }

    @Override
    public void spawnParticlesTrail() {
        if (!this.inGround) {
            Vec3 delta = this.getDeltaMovement().normalize();
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
            final Vec3[] cachePos = {new Vec3(pos.x, pos.y, pos.z)};
            final Consumer<GenericParticle> target = p -> {
                Vec3 arrowPos = new Vec3(getX(),getY(),getZ());
                float lenBetweenArrowAndParticle = (float)(arrowPos.subtract(cachePos[0])).length();
                Vec3 vector = (arrowPos.subtract(cachePos[0]));
                if (lenBetweenArrowAndParticle > 0) {
                    cachePos[0] = cachePos[0].add(vector);
                    p.setPosition(cachePos[0]);
                }
            };

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Pal.mindaro, Pal.seaGreen).build());
        }
    }
}