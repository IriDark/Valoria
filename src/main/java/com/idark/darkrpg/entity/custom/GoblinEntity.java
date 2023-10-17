package com.idark.darkrpg.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GoblinEntity extends ZombieEntity {
    public GoblinEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.18D)
        .add(Attributes.ATTACK_DAMAGE, 5.0D)
        .add(Attributes.FOLLOW_RANGE, 20.0D)
        .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, MobEntity.class, 8.0F));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
    }
	
    protected int getExperienceReward(PlayerEntity player) {
        return 3 + this.level.random.nextInt(5);
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof PlayerEntity) {
            this.setTarget((PlayerEntity) entity);
        }
        return super.hurt(source, amount);
    }
    
    @Override
    public boolean isOnFire() {
        return false;
    }
}