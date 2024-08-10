package com.idark.valoria.registries.levelgen.feature;

import com.idark.valoria.util.*;
import com.mojang.serialization.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.Heightmap.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.stream.*;

public class PotFeature extends Feature<SimpleBlockConfiguration>{
    public PotFeature(Codec<SimpleBlockConfiguration> p_65299_){
        super(p_65299_);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> p_159477_){
        SimpleBlockConfiguration simpleblockconfiguration = p_159477_.config();
        RandomSource randomsource = p_159477_.random();
        WorldGenLevel worldgenlevel = p_159477_.level();
        ChunkPos chunkpos = new ChunkPos(p_159477_.origin());
        IntArrayList intarraylist = Util.toShuffledList(IntStream.rangeClosed(chunkpos.getMinBlockX(), chunkpos.getMaxBlockX()), randomsource);
        IntArrayList intarraylist1 = Util.toShuffledList(IntStream.rangeClosed(chunkpos.getMinBlockZ(), chunkpos.getMaxBlockZ()), randomsource);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for(Integer integer : intarraylist) {
            for(Integer integer1 : intarraylist1) {
                blockpos$mutableblockpos.set(integer, 0, integer1);
                BlockPos blockpos = worldgenlevel.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos);
                if (worldgenlevel.isEmptyBlock(blockpos) || worldgenlevel.getBlockState(blockpos).getCollisionShape(worldgenlevel, blockpos).isEmpty()) {
                    BlockState blockstate = simpleblockconfiguration.toPlace().getState(p_159477_.random(), blockpos);
                    if (blockstate.canSurvive(worldgenlevel, blockpos) && worldgenlevel.getBlockState(blockpos.below()).isSolid()){
                        if(RandomUtil.percentChance(0.05f)) worldgenlevel.setBlock(blockpos, blockstate, 2);
                    }

                    return true;
                }
            }
        }

        return false;
    }
}