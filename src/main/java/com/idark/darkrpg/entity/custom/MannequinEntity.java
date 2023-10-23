package com.idark.darkrpg.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeEntity;
  
public class MannequinEntity extends Mob implements IForgeEntity {

    private static final EntityDataAccessor<Float> LAST_DAMAGE = SynchedEntityData.defineId(MannequinEntity.class, EntityDataSerializers.FLOAT);

    public float lastDamageOffset = 0;
    public float lastDamageOffsetPrev = 0;

    public MannequinEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LAST_DAMAGE,0f);
    }

    /*public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 0.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }*/

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (hurtTime == 0){
            entityData.set(LAST_DAMAGE,amount);
        }
	return super.hurt(source, amount);
	}

    public float getLastDamage(){
        return entityData.get(LAST_DAMAGE);
    }
}