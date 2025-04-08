package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class CaveRootFeature extends Feature<NoneFeatureConfiguration>{
   private static final Direction[] DIRECTIONS = Direction.values();

   public CaveRootFeature(Codec<NoneFeatureConfiguration> pCodec) {
      super(pCodec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param pContext A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
      WorldGenLevel worldgenlevel = pContext.level();
      BlockPos blockpos = pContext.origin();
      RandomSource randomsource = pContext.random();
      if (!worldgenlevel.isEmptyBlock(blockpos)) {
         return false;
      } else {
         BlockState blockstate = worldgenlevel.getBlockState(blockpos.above());
         if (!blockstate.is(TagsRegistry.VOID_STONES)) {
            return false;
         } else {
            this.placeRoof(worldgenlevel, randomsource, blockpos);
            this.placeRoofVines(worldgenlevel, randomsource, blockpos);
            return true;
         }
      }
   }

   private void placeRoof(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos) {
      pLevel.setBlock(pPos, BlockRegistry.voidStone.get().defaultBlockState(), 2);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 200; ++i) {
         blockpos$mutableblockpos.setWithOffset(pPos, pRandom.nextInt(6) - pRandom.nextInt(6), pRandom.nextInt(2) - pRandom.nextInt(5), pRandom.nextInt(6) - pRandom.nextInt(6));
         if (pLevel.isEmptyBlock(blockpos$mutableblockpos)) {
            int j = 0;

            for(Direction direction : DIRECTIONS) {
               BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, direction));
               if (blockstate.is(TagsRegistry.VOID_STONES)) {
                  ++j;
               }

               if (j > 1) {
                  break;
               }
            }

            if (j == 1) {
               pLevel.setBlock(blockpos$mutableblockpos, BlockRegistry.voidStone.get().defaultBlockState(), 2);
            }
         }
      }

   }

   private void placeRoofVines(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 100; ++i) {
         blockpos$mutableblockpos.setWithOffset(pPos, pRandom.nextInt(8) - pRandom.nextInt(8), pRandom.nextInt(2) - pRandom.nextInt(7), pRandom.nextInt(8) - pRandom.nextInt(8));
         if (pLevel.isEmptyBlock(blockpos$mutableblockpos)) {
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos.above());
            if (blockstate.is(TagsRegistry.VOID_STONES)) {
               int j = Mth.nextInt(pRandom, 1, 8);
               if (pRandom.nextInt(6) == 0) {
                  j *= 2;
               }

               if (pRandom.nextInt(5) == 0) {
                  j = 1;
               }

               placeVinesColumn(pLevel, pRandom, blockpos$mutableblockpos, j, 17, 25);
            }
         }
      }

   }

   public static void placeVinesColumn(LevelAccessor pLevel, RandomSource pRandom, BlockPos.MutableBlockPos pPos, int pHeight, int pMinAge, int pMaxAge) {
      for(int i = 0; i <= pHeight; ++i) {
         if (pLevel.isEmptyBlock(pPos)) {
            if (i == pHeight || !pLevel.isEmptyBlock(pPos.below())) {
               pLevel.setBlock(pPos, BlockRegistry.caveRoot.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(pRandom, pMinAge, pMaxAge)), 2);
               break;
            }

            pLevel.setBlock(pPos, BlockRegistry.caveRootPlant.get().defaultBlockState(), 2);
         }

         pPos.move(Direction.DOWN);
      }

   }
}