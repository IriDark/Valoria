package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.entity.living.decoration.MannequinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class MannequinSpawnItem extends Item{
    public MannequinSpawnItem(Item.Properties properties){
        super(properties);
    }

    public InteractionResult useOn(UseOnContext pContext){
        Direction direction = pContext.getClickedFace();
        if(direction == Direction.DOWN){
            return InteractionResult.FAIL;
        }else{
            Level level = pContext.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(pContext);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
            AABB aabb = EntityTypeRegistry.MANNEQUIN.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if(level.noCollision(null, aabb) && level.getEntities(null, aabb).isEmpty() && !level.getBlockState(blockpos.below()).isAir()){
                if(level instanceof ServerLevel serverlevel){
                    Consumer<MannequinEntity> consumer = EntityType.createDefaultStackConfig(serverlevel, itemstack, pContext.getPlayer());
                    MannequinEntity mannequin = EntityTypeRegistry.MANNEQUIN.get().create(serverlevel, itemstack.getTag(), consumer, blockpos, MobSpawnType.SPAWN_EGG, true, true);
                    if(mannequin == null){
                        return InteractionResult.FAIL;
                    }

                    float f = (float)Mth.floor((Mth.wrapDegrees(pContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    mannequin.moveTo(mannequin.getX(), mannequin.getY(), mannequin.getZ(), f, 0.0F);
                    serverlevel.addFreshEntityWithPassengers(mannequin);
                    level.playSound(null, mannequin.getX(), mannequin.getY(), mannequin.getZ(), SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 0.35F, 0.9F);
                    mannequin.gameEvent(GameEvent.ENTITY_PLACE, pContext.getPlayer());
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }else{
                return InteractionResult.FAIL;
            }
        }
    }
}
