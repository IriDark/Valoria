package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class QuickSandBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public QuickSandBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.block();
	}

	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		worldIn.getBlockTicks().scheduleTick(pos, this, 20);
	}

	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
   
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.makeStuckInBlock(state, new Vector3d(0.25D, (double)0.05F, 0.25D));
        entityIn.hurt(DamageSource.GENERIC, 1.0F);
	}
	//TODO Completely edit this shit lol
}