package com.idark.darkrpg.block.types;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TallNetherFlowerBlock extends DoublePlantBlock {
   public TallNetherFlowerBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
	
   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.isIn(BlockTags.NYLIUM) || state.matchesBlock(Blocks.SOUL_SOIL) || super.isValidGround(state, worldIn, pos);
   }
	
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return false;
   }
}