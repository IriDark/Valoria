package com.idark.valoria.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TallNetherFlowerBlock extends DoublePlantBlock {
   public TallNetherFlowerBlock(BlockBehaviour.Properties properties) {
      super(properties);
   }
	
   protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, worldIn, pos);
   }
	
   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      return false;
   }
}