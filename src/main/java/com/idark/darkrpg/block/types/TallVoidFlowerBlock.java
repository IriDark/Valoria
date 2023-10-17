package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TallVoidFlowerBlock extends DoublePlantBlock {
	public TallVoidFlowerBlock(AbstractBlock.Properties properties) {
		super(properties);
    }
	
	protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == ModBlocks.VOID_STONE.get() || block == ModBlocks.VOID_GRASS.get();
	}
	
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		return false;
	}
   
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.empty());
		Vector3d vector3d = voxelshape.bounds().getCenter();
		double d0 = (double)pos.getX() + vector3d.x;
		double d1 = (double)pos.getZ() + vector3d.z;
		for(int i = 0; i < 3; ++i) {
			if (rand.nextBoolean()) {
				worldIn.addParticle(ParticleTypes.SMOKE, d0 + rand.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - rand.nextDouble()), d1 + rand.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}