package com.idark.valoria.api.events;

import com.idark.valoria.registries.entity.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class RiftTeleportEvent extends EntityEvent implements ICancellableEvent{
    private final RiftEntity rift;
    private final RiftEntity connection;
    private double targetX;
    private double targetY;
    private double targetZ;
    private Level targetLevel;

    public RiftTeleportEvent(Entity teleportingEntity, RiftEntity rift, RiftEntity connection, double targetX, double targetY, double targetZ, Level targetLevel) {
        super(teleportingEntity);
        this.rift = rift;
        this.connection = connection;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.targetLevel = targetLevel;
    }

    public RiftEntity getRift() {
        return rift;
    }

    public RiftEntity getConnection() {
        return connection;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }

    public void setTargetZ(double targetZ) {
        this.targetZ = targetZ;
    }

    public Level getTargetLevel() {
        return targetLevel;
    }

    public void setTargetLevel(Level targetLevel) {
        this.targetLevel = targetLevel;
    }
}
