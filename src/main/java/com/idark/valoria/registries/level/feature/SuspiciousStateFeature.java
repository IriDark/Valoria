package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.level.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.Heightmap.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.storage.loot.*;

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
        ServerLevel serverLevel = pContext.level().getLevel();

        int i = 0;
        BlockPos pos = worldgenlevel.getHeightmapPos(Types.WORLD_SURFACE_WG, blockpos).below();
        for(int j = 0; j < config.tries; ++j){
            for(SuspiciousStateConfiguration.TargetBlockState target : config.targetStates){
                if(canPlace(worldgenlevel.getBlockState(pos), randomsource, target)){
                    worldgenlevel.setBlock(pos, target.state, 2);

                    BlockEntity blockentity = worldgenlevel.getBlockEntity(pos);
                    if(target.state.is(TagsRegistry.UNPACK_LOOT)){
                        // better to delay that task for later, when the targeted chunk is loaded already
                        serverLevel.getServer().execute(() -> {
                            if (!serverLevel.isLoaded(pos)) return;

                            BlockEntity be = serverLevel.getBlockEntity(pos);
                            if (be instanceof CrushableBlockEntity crushable) {
                                LootTable loot = worldgenlevel.getLevel().getServer().getLootData().getLootTable(config.loot);
                                CrushableBlockEntity.unpackAndSetItem(serverLevel, crushable, loot);
                            }
                        });
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
