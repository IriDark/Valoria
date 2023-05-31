package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TallRootsBlock extends DoublePlantBlock {
   public TallRootsBlock(AbstractBlock.Properties properties) {
      super(properties);
   }

   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block block = state.getBlock();
      return block == Blocks.GRASS || block == Blocks.PODZOL || block == Blocks.DIRT || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG;
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return false;
   }
}