package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;

import java.util.Random;

public class ManipulatorBlock extends Block {
	
	private static final VoxelShape shape = Block.makeCuboidShape(0, 0, 0, 16, 17, 16);

	public ManipulatorBlock(AbstractBlock.Properties properties) {
		super(properties);
}

	@Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
        ISelectionContext ctx) {
        return shape;
    }

	@Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        float chance = 0.35f;
        if(chance < rand.nextFloat()) {
            worldIn.addParticle(ParticleTypes.PORTAL, pos.getX() + rand.nextDouble(),
                    pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(),
                    0d,0.05d,0d);
        }

        super.animateTick(stateIn, worldIn, pos, rand);
    }
}