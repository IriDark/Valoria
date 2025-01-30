package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

import javax.annotation.*;

public class WickedShield extends FlyingMob implements TraceableEntity, Allied{
    @Nullable
    public Entity owner;
    public int limitedLifeTicks = 60;
    public FlyingAroundMovement movement = new FlyingAroundMovement(this, null);

    public WickedShield(EntityType<? extends FlyingMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }
    //todo sounds

    @Override
    public boolean hurt(DamageSource pSource, float pAmount){
        if(pSource.getEntity() instanceof Allied && !(this.owner instanceof Player)) return false;
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
    public void tick(){
        super.tick();
        movement.setupMovement();
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

    public void setOwner(@Nullable Entity owner){
        this.owner = owner;
    }

    @Override
    public @org.jetbrains.annotations.Nullable Entity getOwner(){
        return owner;
    }
}