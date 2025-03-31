package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;

public class PotBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    private final boolean potLong;

    public PotBlock(boolean pLong, BlockBehaviour.Properties properties){
        super(properties);
        potLong = pLong;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public PotBlock(BlockBehaviour.Properties properties){
        super(properties);
        potLong = false;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile){
        BlockPos blockpos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, blockpos) && pProjectile.getDeltaMovement().length() > 0.6D) {
            pLevel.destroyBlock(blockpos, true);
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState){
        super.destroy(pLevel, pPos, pState);
        if(pLevel instanceof ServerLevel serverLevel){
            if(Tmp.rnd.chance(0.125)){
                var mob = EntityType.SILVERFISH.create(serverLevel);
                if(mob != null){
                    mob.moveTo((double)pPos.getX() + 0.5D, pPos.getY(), (double)pPos.getZ() + 0.5D, 0.0F, 0.0F);
                    serverLevel.addFreshEntity(mob);
                }
            }
        }
    }

    public VoxelShape getPotLong(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.5, 0.25, 0.75, 0.5, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 0.875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.84375, 0.3125, 0.6875, 0.96875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.25, 0.0625, 0.5, 0.625, 0.25), BooleanOp.OR);

        return shape;
    }

    public VoxelShape getPotSmall(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.375, 0.5, 0.375, 0.625, 0.625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.59375, 0.3125, 0.6875, 0.71875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.5, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.0625, 0.5, 0.5, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.75, 0.5, 0.5, 0.9375), BooleanOp.OR);

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        Vec3 vec3 = state.getOffset(worldIn, pos);
        return potLong ? getPotLong().move(vec3.x, vec3.y, vec3.z) : getPotSmall().move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}