package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

public class QuickSandBlock extends Block{
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);

    public QuickSandBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection){
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos){
        return Shapes.empty();
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity){
        RandomSource randomsource = pLevel.getRandom();
        if(!(pEntity instanceof LivingEntity) || pEntity.getFeetBlockState().is(this)){
            pEntity.makeStuckInBlock(pState, new Vec3(0.9F, 1.5D, 0.9F));
            if(pLevel.isClientSide){
                boolean flag = pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ();
                if(flag && randomsource.nextBoolean()){
                    BlockState state = Blocks.SAND.defaultBlockState();
                    pLevel.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), pEntity.getX(), pPos.getY() + 1, pEntity.getZ(), (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), 0.05F, (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }

                if(pEntity.isOnFire() && (pLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || pEntity instanceof Player) && pEntity.mayInteract(pLevel, pPos)){
                    pLevel.destroyBlock(pPos, false);
                    BlockState state = BlockRegistry.quicksand.get().defaultBlockState();
                    pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), pEntity.getX(), pPos.getY() + 1, pEntity.getZ(), (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), 0.05F, (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }

                pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(0.4, 0.05, 0.4));
            }

            pEntity.setSharedFlagOnFire(false);
        }

        if(randomsource.nextFloat() < 0.06f){
            pEntity.hurt(DamageSourceRegistry.quicksand(pLevel), 1.0F);
        }
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance){
        if(!((double)pFallDistance < 4.0D) && pEntity instanceof LivingEntity livingentity){
            LivingEntity.Fallsounds $$7 = livingentity.getFallSounds();
            SoundEvent soundevent = (double)pFallDistance < 7.0D ? $$7.small() : $$7.big();
            pEntity.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        if(pContext instanceof EntityCollisionContext entitycollisioncontext){
            Entity entity = entitycollisioncontext.getEntity();
            if(entity != null){
                if(entity.fallDistance > 2.5F){
                    return FALLING_COLLISION_SHAPE;
                }
            }
        }

        return Shapes.empty();
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return Shapes.empty();
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType){
        return true;
    }
}
