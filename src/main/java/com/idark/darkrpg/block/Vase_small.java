package com.idark.darkrpg.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.shapes.IBooleanFunction;
import javax.annotation.Nonnull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Vase_small extends DirectionalBlock {
    
	public Vase_small() {
        super(
            Properties.create(Material.GLASS)
                .zeroHardnessAndResistance()
    );
 }
    private static final VoxelShape TOP = Block.makeCuboidShape(5, 11, 5, 11, 12, 11);
    private static final VoxelShape CENTER_1 = Block.makeCuboidShape(4, 2, 4, 12, 11, 12);
    private static final VoxelShape CENTER_2 = Block.makeCuboidShape(3, 1, 3, 13, 2, 13);
    private static final VoxelShape BOTTOM = Block.makeCuboidShape(2, 0, 2, 14, 1, 14);
    private static final VoxelShape BOTTOM_SHAPE = VoxelShapes.combineAndSimplify(CENTER_2, BOTTOM, IBooleanFunction.OR);
    private static final VoxelShape CENTER_SHAPE = VoxelShapes.combineAndSimplify(CENTER_1, BOTTOM_SHAPE, IBooleanFunction.OR);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(TOP, CENTER_SHAPE, IBooleanFunction.OR);

    @Nonnull
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }
}