package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

public class SearchForItemsGoal extends Goal{

    public PathfinderMob mob;
    public Predicate<ItemEntity> allowed;

    public SearchForItemsGoal(PathfinderMob pMob, Predicate<ItemEntity> pAllowedItems){
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        mob = pMob;
        allowed = pAllowedItems;
    }

    public boolean canUse(){
        List<ItemEntity> list = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), allowed);
        if(!mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
            return false;
        }else if(mob.getTarget() == null && mob.getLastHurtByMob() == null){
            if(mob.getRandom().nextInt(reducedTickDelay(2)) != 0){
                return false;
            }else{
                return !list.isEmpty() && mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            }
        }else{
            return false;
        }
    }

    public void tick(){
        List<ItemEntity> list = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), allowed);
        ItemStack itemstack = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if(itemstack.isEmpty() && !list.isEmpty()){
            mob.getNavigation().moveTo(list.get(0), 1.2F);
        }

    }

    public void start(){
        List<ItemEntity> list = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), allowed);
        if(!list.isEmpty()){
            mob.getNavigation().moveTo(list.get(0), 1.2F);
        }

    }
}
