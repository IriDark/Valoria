package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import net.minecraft.commands.arguments.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

import javax.annotation.*;

public abstract class AbstractFlyingAroundMob extends FlyingMob implements TraceableEntity, Allied{
    @Nullable
    public Entity owner;
    private float angle;
    private float radius = 5.0F;
    private float heightOffset = 2.0F;
    private float angularVelocity = 2.0F;
    public int limitedLifeTicks = 60;

    public AbstractFlyingAroundMob(EntityType<? extends FlyingMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount){
        if(pSource.getDirectEntity() instanceof Allied  && !(this.owner instanceof Player)) return false;
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof Allied;
    }

    public boolean canAttack(LivingEntity pTarget){
        return super.canAttack(pTarget) && !isAlliedTo(pTarget);
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

        if(owner == null && --this.limitedLifeTicks <= 0){
            if(this.level() instanceof ServerLevel serv){
                spawnDisappearParticles(serv);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    public void spawnDisappearParticles(ServerLevel serverLevel){
        double posX = this.getOnPos().getCenter().x;
        double posY = this.getOnPos().above().getCenter().y;
        double posZ = this.getOnPos().getCenter().z;
        PacketHandler.sendToTracking(serverLevel, this.getOnPos(), new SmokeParticlePacket(3, posX, posY - 0.5f, posZ, 0.005f, 0.025f, 0.005f, 255, 255, 255));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("LifeTicks", this.limitedLifeTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        if(pCompound.contains("LifeTicks")){
            this.setLimitedLife(pCompound.getInt("LifeTicks"));
        }
    }

    public void setLimitedLife(int pLimitedLifeTicks){
        this.limitedLifeTicks = pLimitedLifeTicks;
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

    public void setOwner(Entity pOwner){
        this.owner = pOwner;
    }
}