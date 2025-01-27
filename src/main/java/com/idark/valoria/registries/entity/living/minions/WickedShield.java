package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.commands.arguments.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;

import javax.annotation.*;
import java.util.*;

public class WickedShield extends FlyingMob implements TraceableEntity{
    @Nullable
    public Entity owner;
    private float angle;
    private float radius = 5.0F;
    private float heightOffset = 2.0F;
    private float angularVelocity = 2.0F;
    public WickedShield(EntityType<? extends FlyingMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public WickedShield(Level pLevel){
        super(EntityTypeRegistry.WICKED_SHIELD.get(), pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (owner != null && !owner.isRemoved()) {
            angle += angularVelocity * (Math.PI / 180.0);
            if (angle > Math.PI * 2) {
                angle -= Math.PI * 2;
            }

            double centerX = owner.getX();
            double centerY = owner.getY() + heightOffset;
            double centerZ = owner.getZ();

            double targetX = centerX + radius * Math.cos(angle);
            double targetZ = centerZ + radius * Math.sin(angle);
            double targetY = centerY;
            this.setPos(targetX, targetY, targetZ);
            this.lookAt(EntityAnchorArgument.Anchor.EYES, owner.position());
        }
    }

    public void setAngularVelocity(float angularVelocity){
        this.angularVelocity = angularVelocity;
    }

    public void setHeightOffset(float heightOffset){
        this.heightOffset = heightOffset;
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    public void setRadius(float radius){
        this.radius = radius;
    }

    @Nullable
    public Entity getOwner(){
        return this.owner;
    }

    public void setOwner(UUID id){
        Entity entity = ((ServerLevel)this.level()).getEntity(id);
        setOwner(entity);
        Valoria.LOGGER.info("Owner {}", entity);
    }

    public void setOwner(Entity pOwner){
        this.owner = pOwner;
    }
}
