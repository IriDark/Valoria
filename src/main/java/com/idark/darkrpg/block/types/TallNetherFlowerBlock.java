package com.idark.darkrpg.block.types;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TallNetherFlowerBlock extends DoublePlantBlock {
   public TallNetherFlowerBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
	
   protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, worldIn, pos);
   }
	
   public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
      return false;
   }
}