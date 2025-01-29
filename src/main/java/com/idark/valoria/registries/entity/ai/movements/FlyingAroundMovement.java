package com.idark.valoria.registries.entity.ai.movements;

import net.minecraft.commands.arguments.*;
import net.minecraft.world.entity.*;

import javax.annotation.*;

public class FlyingAroundMovement{
    public Mob mob;
    @Nullable
    public Entity owner;
    public float angle;
    public float radius = 5.0F;
    public float heightOffset = 2.0F;

    public FlyingAroundMovement setHeightOffset(float heightOffset){
        this.heightOffset = heightOffset;
        return this;
    }

    public FlyingAroundMovement setAngularVelocity(float angularVelocity){
        this.angularVelocity = angularVelocity;
        return this;
    }

    public FlyingAroundMovement setRadius(float radius){
        this.radius = radius;
        return this;
    }

    public FlyingAroundMovement setAngle(float angle){
        this.angle = angle;
        return this;
    }

    public float angularVelocity = 2.0F;

    public FlyingAroundMovement(Mob mob, @Nullable Entity owner){
        this.mob = mob;
        this.owner = owner;
    }

    public void setupMovement() {
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
            mob.setPos(targetX, targetY, targetZ);
            mob.lookAt(EntityAnchorArgument.Anchor.EYES, owner.position());
        }
    }
}
