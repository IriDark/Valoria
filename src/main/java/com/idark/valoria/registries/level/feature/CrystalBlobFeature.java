package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.level.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.feature.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class CrystalBlobFeature extends Feature<CrystalBlobConfiguration>{
    public CrystalBlobFeature(Codec<CrystalBlobConfiguration> codec){
        super(codec);
    }

    protected static boolean isEmptyOrWaterOrLava(LevelAccessor pLevel, BlockPos pPos) {
        return pLevel.isStateAtPosition(pPos, DripstoneUtils::isEmptyOrWaterOrLava);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrystalBlobConfiguration> context){
        BlockPos origin = context.origin();
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        CrystalBlobConfiguration config = context.config();
        if(!isEmptyOrWaterOrLava(world, origin)){
            return false;
        }else{
            while(origin.getY() > world.getMinBuildHeight() + 3 && world.isEmptyBlock(origin.below())){
                origin = origin.below();
            }

            if(origin.getY() <= world.getMinBuildHeight() + 3){
                return false;
            }

            int baseRadius = 1 + random.nextInt(2);
            int height = 6 + random.nextInt(4);
            List<BlockPos> blobPositions = new ArrayList<>();
            for(int y = 0; y < height; y++){
                float radius = baseRadius * (1.0f - (y / (float)height)) + 0.5f;
                for(BlockPos pos : BlockPos.betweenClosed(origin.offset(-baseRadius, y, -baseRadius), origin.offset(baseRadius, y, baseRadius))){
                    if(pos.distSqr(new Vec3i(origin.getX(), origin.getY() + y, origin.getZ())) <= radius * radius){
                        world.setBlock(pos, config.state, 3);
                        blobPositions.add(pos.immutable());
                    }

                    if(random.nextFloat() < 0.5){
                        BlockPos sideOffset = origin.offset(random.nextInt(3) - 1, y, random.nextInt(3) - 1);
                        world.setBlock(sideOffset, config.state, 3);
                        blobPositions.add(sideOffset.immutable());
                    }
                }
            }

            List<BlockState> crystals = config.outerDecoration;
            for (BlockPos pos : blobPositions) {
                for (Direction dir : Direction.values()) {
                    BlockPos targetPos = pos.relative(dir);
                    if (!world.getBlockState(targetPos).isAir()) continue;

                    if(Tmp.rnd.chance(config.chance)){
                        BlockState crystal = Util.getRandom(crystals, random);
                        if(crystal.hasProperty(BlockStateProperties.FACING)){
                            crystal = crystal.setValue(BlockStateProperties.FACING, dir);
                        }

                        world.setBlock(targetPos, crystal, 2);
                    }

                    break;
                }
            }

            return true;
        }
    }
}