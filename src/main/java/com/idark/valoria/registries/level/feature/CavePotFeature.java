package com.idark.valoria.registries.level.feature;

import com.idark.valoria.core.config.*;
import com.mojang.serialization.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import pro.komaru.tridot.util.*;

import java.util.stream.*;

public class CavePotFeature extends Feature<SimpleBlockConfiguration>{
    public CavePotFeature(Codec<SimpleBlockConfiguration> p_65299_){
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
        int startY = p_159477_.origin().getY();
        
        for(Integer integer : intarraylist){
            for(Integer integer1 : intarraylist1){
                blockpos$mutableblockpos.set(integer, startY, integer1);
                
                for(int i = 0; i < 32; i++) {
                    if(worldgenlevel.isEmptyBlock(blockpos$mutableblockpos) || worldgenlevel.getBlockState(blockpos$mutableblockpos).getCollisionShape(worldgenlevel, blockpos$mutableblockpos).isEmpty()){
                        BlockState blockstate = simpleblockconfiguration.toPlace().getState(randomsource, blockpos$mutableblockpos);
                        if(blockstate.canSurvive(worldgenlevel, blockpos$mutableblockpos) && worldgenlevel.getBlockState(blockpos$mutableblockpos.below()).isSolid()){
                            if(Tmp.rnd.chance(ServerConfig.CAVE_POT_SPAWN_CHANCE.get())) {
                                worldgenlevel.setBlock(blockpos$mutableblockpos, blockstate, 2);
                            }
                            return true;
                        }
                    }
                    blockpos$mutableblockpos.move(Direction.DOWN);
                    if(blockpos$mutableblockpos.getY() <= worldgenlevel.getMinBuildHeight()) break;
                }
            }
        }

        return false;
    }
}