package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;

public class VoidthornBlock extends DoublePlantBlock implements net.minecraftforge.common.IPlantable{
    public VoidthornBlock(Properties properties){
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos){
        BlockPos floorPos = pos.below();
        if(state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER){
            return super.canSurvive(state, level, pos) && this.mayPlaceOn(level.getBlockState(floorPos), level, floorPos);
        }
        return super.canSurvive(state, level, pos) && level.getBlockState(pos.below()).getBlock() == BlockRegistry.voidthorn.get();
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        pEntity.makeStuckInBlock(pState, new Vec3(0.8F, 0.75D, 0.8F));
        if (!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
            double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
            double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                pEntity.hurt(DamageSourceRegistry.voidthorn(pLevel), 3.5F);
            }
        }
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand){
        if(!state.canSurvive(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos){
        Block block = state.getBlock();
        return block == BlockRegistry.voidSand.get() || block == BlockRegistry.smoothVoidSandstone.get();
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos){
        DoubleBlockHalf half = stateIn.getValue(HALF);
        if(facing.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(HALF) != half){
            return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
        }else{
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos){
        return PlantType.DESERT;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext){
        return false;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos){
        return defaultBlockState();
    }
}