package com.idark.darkrpg.block.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import java.util.Random;
import net.minecraft.block.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class VoidRootsBlock extends BushBlock {
   public VoidRootsBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
	
   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block block = state.getBlock();
      return block == ModBlocks.VOID_STONE.get() || block == ModBlocks.VOID_GRASS.get();
   }
	
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return false;
   }
}