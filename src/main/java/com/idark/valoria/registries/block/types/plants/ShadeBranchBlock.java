package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class ShadeBranchBlock extends GrowingPlantHeadBlock{
    private static final VoxelShape shape = Block.box(3, 4, 3, 15, 16, 15);
    private final double growPerTickProbability;
    public ShadeBranchBlock(BlockBehaviour.Properties p_153000_){
        super(p_153000_, Direction.DOWN, shape, false, 0.14D);
        this.growPerTickProbability = 0.14D;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || blockstate.getBlock() instanceof LeavesBlock;
        }
    }

    /**
     * @return whether this block needs random ticking.
     */
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < 3;
    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(AGE) < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos.relative(this.growthDirection), pLevel.getBlockState(pPos.relative(this.growthDirection)),pRandom.nextDouble() < this.growPerTickProbability)) {
            BlockPos blockpos = pPos.relative(this.growthDirection);
            if (this.canGrowInto(pLevel.getBlockState(blockpos))) {
                pLevel.setBlockAndUpdate(blockpos, this.getGrowIntoState(pState, pLevel.random));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
    }

    public BlockState getMaxAgeState(BlockState pState) {
        return pState.setValue(AGE, 3);
    }

    public boolean isMaxAge(BlockState pState) {
        return pState.getValue(AGE) == 3;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockPos blockpos = pPos.relative(this.growthDirection);
        int i = Math.min(pState.getValue(AGE) + 1, 3);
        int j = this.getBlocksToGrowWhenBonemealed(pRandom);

        for(int k = 0; k < j && this.canGrowInto(pLevel.getBlockState(blockpos)); ++k) {
            pLevel.setBlockAndUpdate(blockpos, pState.setValue(AGE, i));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, 3);
        }
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom){
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState pState){
        return pState.isAir();
    }

    @Override
    protected Block getBodyBlock(){
        return BlockRegistry.shadewoodBranchVine.get();
    }
}