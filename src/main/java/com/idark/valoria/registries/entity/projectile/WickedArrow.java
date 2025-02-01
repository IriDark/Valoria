package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;
import pro.komaru.tridot.core.interfaces.*;

import java.util.function.*;

public class WickedArrow extends AbstractValoriaArrow implements TexturedArrow{

    public WickedArrow(EntityType<? extends AbstractValoriaArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public WickedArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.WICKED_ARROW.get(), pLevel, pShooter, thrown, 4);
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

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Pal.vividPink, Pal.darkViolet).build());
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Pal.vividPink, Pal.darkViolet).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/wicked_arrow.png");
    }
}