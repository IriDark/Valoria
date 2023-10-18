package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TallWaterFlowerBlock extends DoublePlantBlock implements net.minecraftforge.common.IPlantable {

	public TallWaterFlowerBlock(BlockBehaviour.Properties properties) {
      super(properties);
	}
  
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		if (!state.canSurvive(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}
   
	/*public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!stateIn.canSurvive(worldIn, currentPos)) {
			worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
		}

		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}*/
   
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.PODZOL;
	}
   
	@Override
	public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return net.minecraftforge.common.PlantType.BEACH;
	}
   
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		return false;
	}

	@Override
	public BlockState getPlant(BlockGetter world, BlockPos pos) {
		return defaultBlockState();
    }
}