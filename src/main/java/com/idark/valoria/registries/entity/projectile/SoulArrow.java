package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.graphics.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;
import pro.komaru.tridot.core.interfaces.*;
import pro.komaru.tridot.utilities.*;

import java.util.function.*;

public class SoulArrow extends AbstractValoriaArrow implements TexturedArrow{

    public SoulArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public SoulArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.SOUL_ARROW.get(), pLevel, pShooter, thrown, 1);
    }

    @Override
    public void tick(){
        super.tick();
        Utils.Physics.homingTo(0.05f, this, this.level(), this.getOwner(), new AABB(this.getX() - 4.5f, this.getY() - 4.5f, this.getZ() - 4.5f, this.getX() + 4.5f, this.getY() + 4.5f, this.getZ() + 4.5f));
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


            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Clr.valueOf("19419b"), Clr.valueOf("0000af")).build());
            ParticleEffects.trailMotionSparks(this.level(), pos, ColorParticleData.create(Clr.valueOf("19419b"), Clr.valueOf("0000af")).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/soul_arrow.png");
    }
}
