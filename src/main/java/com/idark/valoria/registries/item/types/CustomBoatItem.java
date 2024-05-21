package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.entity.decoration.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;

import java.util.*;
import java.util.function.*;

public class CustomBoatItem extends Item{
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final CustomBoatEntity.Type type;
    private final boolean chest;

    public CustomBoatItem(boolean chest, CustomBoatEntity.Type type, Item.Properties properties){
        super(properties);
        this.chest = chest;
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if(result.getType() == HitResult.Type.MISS){
            return InteractionResultHolder.pass(itemstack);
        }else{
            Vec3 vector3d = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if(!list.isEmpty()){
                Vec3 vector3d1 = player.getEyePosition(1.0F);
                for(Entity entity : list){
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if(aabb.contains(vector3d1)){
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if(result.getType() == HitResult.Type.BLOCK){
                CustomBoatEntity boat = this.getBoat(level, result);
                boat.setCustomBoatEntityType(this.type);
                boat.setYRot(player.getYRot());
                if(!level.noCollision(boat, boat.getBoundingBox())){
                    return InteractionResultHolder.fail(itemstack);
                }else{
                    if(!level.isClientSide()){
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, result.getLocation());
                        if(!player.getAbilities().instabuild){
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }else{
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private CustomBoatEntity getBoat(Level level, HitResult result){
        return this.chest ? new CustomChestBoatEntity(level, result.getLocation().x(), result.getLocation().y(), result.getLocation().z()) : new CustomBoatEntity(level, result.getLocation().x(), result.getLocation().y(), result.getLocation().z());
    }
}