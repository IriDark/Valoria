package com.idark.darkrpg.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
  
public class MannequinEntity extends MobEntity implements IForgeEntity {

    private static final DataParameter<Float> LAST_DAMAGE = EntityDataManager.defineId(MannequinEntity.class, DataSerializers.FLOAT);

    public float lastDamageOffset = 0;
    public float lastDamageOffsetPrev = 0;

    public MannequinEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LAST_DAMAGE,0f);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 0.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

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