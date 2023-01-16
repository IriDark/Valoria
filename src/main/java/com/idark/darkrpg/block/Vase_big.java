package com.idark.darkrpg.block;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Vase_big extends Block {
	
	public static final VoxelShape SHAPE =Block.makeCuboidShape(0, 0, 0, 10, 12, 10);

	public Vase_big() {
        super(
			Properties.create(Material.GLASS)
                .zeroHardnessAndResistance()
    );
}
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
            ISelectionContext ctx) {
        return SHAPE;
    }
}