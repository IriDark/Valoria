package com.idark.darkrpg.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GoblinEntity extends Zombie {
    public GoblinEntity(EntityType<? extends Zombie> type, Level worldIn) {
        super(type, worldIn);
    }

    /*public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.18D)
        .add(Attributes.ATTACK_DAMAGE, 5.0D)
        .add(Attributes.FOLLOW_RANGE, 20.0D)
        .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }*/

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(10, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        //this.goalSelector.addGoal(11, new LookAtGoal(this, MobEntity.class, 8.0F));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }
	
    protected int getExperienceReward(Player player) {
        return 3 + this.level().random.nextInt(5);
    }
    
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player) {
            this.setTarget((Player) entity);
        }
        return super.hurt(source, amount);
    }
    
    @Override
    public boolean isOnFire() {
        return false;
    }
}