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
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;

import java.util.function.*;

public class NatureArrow extends AbstractTridotArrow implements TexturedArrow{

    public NatureArrow(EntityType<? extends AbstractTridotArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public NatureArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.NATURE_ARROW.get(), pLevel, pShooter, thrown, 3);
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

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Pal.nature, Pal.seaGreen).build());
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Pal.nature, Pal.seaGreen).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/nature_arrow.png");
    }
}