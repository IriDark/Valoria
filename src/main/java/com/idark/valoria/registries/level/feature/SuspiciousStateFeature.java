package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.block.entity.CrushableBlockEntity;
import com.idark.valoria.registries.level.configurations.SuspiciousStateConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.storage.loot.LootTable;

public class SuspiciousStateFeature extends Feature<SuspiciousStateConfiguration>{
    public SuspiciousStateFeature(Codec<SuspiciousStateConfiguration> pCodec){
        super(pCodec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     *
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<SuspiciousStateConfiguration> pContext){
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();
        SuspiciousStateConfiguration config = pContext.config();

        int i = 0;
        BlockPos pos = worldgenlevel.getHeightmapPos(Types.WORLD_SURFACE_WG, blockpos).below();
        for(int j = 0; j < config.tries; ++j){
            for(SuspiciousStateConfiguration.TargetBlockState target : config.targetStates){
                if(canPlace(worldgenlevel.getBlockState(pos), randomsource, target)){
                    worldgenlevel.setBlock(pos, target.state, 2);

                    // prevents NPE`s and synchronises positions with tile entity
                    BlockEntity blockentity = worldgenlevel.getBlockEntity(pos);
                    if(target.state.is(TagsRegistry.UNPACK_LOOT)){
                        LootTable loot = worldgenlevel.getLevel().getServer().getLootData().getLootTable(config.loot);
                        CrushableBlockEntity.unpackAndSetItem(worldgenlevel.getLevel(), blockentity, loot);
                    }else{
                        CrushableBlockEntity.setLootTable(randomsource, blockentity, config.loot);
                    }

                    ++i;
                    break;
                }
            }
        }

        return i > 0;
    }

    public static boolean canPlace(BlockState pState, RandomSource pRandom, SuspiciousStateConfiguration.TargetBlockState pTargetState){
        return pTargetState.target.test(pState, pRandom);
    }
}
