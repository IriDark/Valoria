package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadeBranchBlock extends BushBlock {

    public ShadeBranchBlock(BlockBehaviour.Properties p_153000_) {
        super(p_153000_);
    }

    private static final VoxelShape shape = Block.box(3, 4, 3, 15, 16, 15);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shape;
    }

    public float getMaxVerticalOffset() {
        return 0.0F;
    }

    public boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(BlockRegistry.SHADEWOOD_LEAVES.get());
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        if (pState.getBlock() == this)
            return pLevel.getBlockState(blockpos).is(BlockRegistry.SHADEWOOD_LEAVES.get());
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isAir()) {
            if (random.nextFloat() < 0.035) {
                Particles.create(ModParticles.SHADEWOOD_LEAF_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 12), ((random.nextDouble() - 0.87D) / 4), ((random.nextDouble() - 0.5D) / 12))
                        .setAlpha(1f, 1f).setScale(0.072f, 0.02f)
                        .setColor(1f, 1f, 1f)
                        .setLifetime(46)
                        .setSpin(((float) Math.toRadians(random.nextBoolean() ? -2 : 4)))
                        .spawn(world, pos.getX() + 0.5F + ((random.nextFloat() - 0.5f) * 0.9f), pos.getY() - 0.05, pos.getZ() + 0.5F + ((random.nextFloat() - 0.5f * 0.9f)));
            }
        }
    }
}
