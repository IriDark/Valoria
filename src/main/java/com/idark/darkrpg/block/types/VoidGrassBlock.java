package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class VoidGrassBlock extends SpreadableSnowyDirtBlock implements IGrowable {
   public VoidGrassBlock(AbstractBlock.Properties properties) {
      super(properties);
    }
	
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return worldIn.getBlockState(pos.above()).isAir();
    }

   public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
    }
 
   public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    }
}	