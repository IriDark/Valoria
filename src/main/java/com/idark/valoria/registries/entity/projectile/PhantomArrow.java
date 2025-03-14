package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.phys.*;

import java.util.function.*;

public class PhantomArrow extends AbstractTridotArrow implements TexturedArrow{

    public PhantomArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public PhantomArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.PHANTOM_ARROW.get(), pLevel, pShooter, thrown, 6);
    }

    @Override
    public void spawnParticlesTrail(){
        if(!this.inGround){
            Vec3 delta = Vec3.from(this.getDeltaMovement().normalize());
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
            final Vec3[] cachePos = {new Vec3(pos.x, pos.y, pos.z)};
            final Consumer<GenericParticle> target = p -> {
                Vec3 arrowPos = new Vec3(getX(), getY(), getZ());
                float lenBetweenArrowAndParticle = (arrowPos.sub(cachePos[0])).len();
                Vec3 vector = (arrowPos.sub(cachePos[0]));
                if(lenBetweenArrowAndParticle > 0){
                    cachePos[0] = cachePos[0].add(vector);
                    p.setPosition(cachePos[0]);
                }
            };

            ParticleEffects.smoothTrail(this.level(), target, pos.mcVec(), ColorParticleData.create(Col.fromHex("193ce1"), Col.fromHex("201aeb")).build());
            ParticleEffects.trailMotionSparks(this.level(), pos.mcVec(), ColorParticleData.create(Col.fromHex("193ce1"), Col.fromHex("201aeb")).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/phantom_arrow.png");
    }
}
