package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class QuickSandBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public QuickSandBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	//public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
	//	return VoxelShapes.block();
	//}

	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		//worldIn.getBlockTicks().scheduleTick(pos, this, 20);
	}

	//public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
	//	return false;
	//}
   
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		entityIn.makeStuckInBlock(state, new Vec3(0.25D, (double)0.05F, 0.25D));
        entityIn.hurt(entityIn.damageSources().generic(), 1.0F);
	}
	//TODO Completely edit this shit lol
}