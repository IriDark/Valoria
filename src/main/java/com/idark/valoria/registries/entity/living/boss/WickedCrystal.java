package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.ui.bossbars.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WickedCrystal extends AbstractBoss{
    public WickedCrystal(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel, (ServerBossBarEvent)(new ServerBossBarEvent(pEntityType.getDescription(), "Wicked Crystal")).setDarkenScreen(true));
    }

    private boolean hasPhysics(){
        return true;
    }

    public boolean isEffectiveAi(){
        return super.isEffectiveAi() && this.hasPhysics();
    }

    public boolean isPushable(){
        return false;
    }

    protected void doPush(@NotNull Entity pEntity){
    }

    @Override
    public void knockback(double strength, double x, double z){
    }

    protected void pushEntities(){
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), (p_31582_) -> p_31582_ instanceof LivingEntity);
        for(Entity entity : list){
            if(this.distanceToSqr(entity) <= 1){
                entity.push(this);
            }
        }
    }

    @Override
    protected boolean isImmobile(){
        return true;
    }
}