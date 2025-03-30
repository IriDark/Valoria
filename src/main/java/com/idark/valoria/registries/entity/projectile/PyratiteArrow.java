package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.Level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class PyratiteArrow extends AbstractTridotArrow implements TexturedArrow{
    public PyratiteArrow(EntityType<? extends AbstractTridotArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public PyratiteArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.PYRATITE_ARROW.get(), pLevel, pShooter, thrown, 4);
    }

    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        if(!this.level().isClientSide){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2, ExplosionInteraction.MOB);
            ScreenshakeHandler.add(new ScreenshakeInstance(3).intensity(0.45f));
        }

        this.discard();
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

            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Pal.pyratiteBright, Pal.pyratite).build());
            ParticleEffects.fireParticles(this.level(), pos, ColorParticleData.create(Col.white, Pal.pyratite).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/pyratite_arrow.png");
    }
}