package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

public class ShadeBlossomBlock extends CropBlock implements BonemealableBlock{
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ShadeBlossomBlock(Properties pProperties){
        super(pProperties);
    }

    public int getMaxAge() {
        return 4;
    }

    protected int getBonemealAgeIncrease(Level pLevel) {
        return Mth.nextInt(pLevel.random, 1, 3);
    }

    @NotNull
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !this.canSurvive(pState, pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos){
        return Block.canSupportCenter(level, pos.below(), Direction.UP) && !level.isWaterAt(pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rnd){
        if(!isMaxAge(state)) return;

        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d0 = (double)i + rnd.nextDouble();
        double d1 = (double)j + 0.7D;
        double d2 = (double)k + rnd.nextDouble();

        level.addParticle(ParticleRegistry.FIREFLY.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean client) {
        return true;
    }

    @NotNull
    protected ItemLike getBaseSeedId() {
        return ItemsRegistry.shadeBlossomSeeds.get();
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource rnd, BlockPos pos, BlockState state) {
        if(this.isMaxAge(state)) {
            dropResources(state, level, pos);
        } else {
            growCrops(level, pos, state);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context){
        return SHAPE;
    }
}
