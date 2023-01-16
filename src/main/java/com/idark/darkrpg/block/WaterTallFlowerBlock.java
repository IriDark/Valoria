package com.idark.darkrpg.block;

import java.util.Random;
import net.minecraft.fluid.FluidState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WaterTallFlowerBlock extends DoublePlantBlock implements net.minecraftforge.common.IPlantable {
   public WaterTallFlowerBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
  
   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
      if (!state.isValidPosition(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
      }

   }
   
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (!stateIn.isValidPosition(worldIn, currentPos)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }
   
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      BlockState soil = worldIn.getBlockState(pos.down());
      if (soil.canSustainPlant(worldIn, pos.down(), Direction.UP, this)) return true;
      BlockState blockstate = worldIn.getBlockState(pos.down());
      if (blockstate.getBlock() == this) {
         return true;
      } else {
	  if (blockstate.matchesBlock(Blocks.GRASS_BLOCK) || blockstate.matchesBlock(Blocks.DIRT) || blockstate.matchesBlock(Blocks.COARSE_DIRT) || blockstate.matchesBlock(Blocks.PODZOL) || super.isValidGround(state, worldIn, pos)); 
      BlockPos blockpos = pos.down();

	  for(Direction direction : Direction.Plane.HORIZONTAL) {
               BlockState blockstate1 = worldIn.getBlockState(blockpos.offset(direction));
               FluidState fluidstate = worldIn.getFluidState(blockpos.offset(direction));
               if (fluidstate.isTagged(FluidTags.WATER) || blockstate1.matchesBlock(Blocks.FROSTED_ICE)) {
                  return true;
			 }
		 }
         return false;

	   }
   }
   
   @Override
   public net.minecraftforge.common.PlantType getPlantType(IBlockReader world, BlockPos pos) {
       return net.minecraftforge.common.PlantType.BEACH;
   }
   
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return false;
   }

 @Override
   public BlockState getPlant(IBlockReader world, BlockPos pos) {
      return getDefaultState();
    }
}