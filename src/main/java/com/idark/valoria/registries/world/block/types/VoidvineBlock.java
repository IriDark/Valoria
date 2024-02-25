package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoidvineBlock extends BushBlock {
    private static final VoxelShape shape = Block.box(3, 0, 3, 13, 8, 13);

    public VoidvineBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shape;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == ModBlocks.VOID_TAINT.get() || block == ModBlocks.VOID_STONE.get();
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }
}