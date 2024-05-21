package com.idark.valoria.registries.levelgen.portal;

import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraftforge.common.util.*;

public class BaseTeleporter implements ITeleporter{

    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;
    protected static ResourceKey<PoiType> poi;

    public BaseTeleporter(BlockPos pos, boolean insideDim, ResourceKey<PoiType> pPoi){
        thisPos = pos;
        insideDimension = insideDim;
        poi = pPoi;
    }

    protected int getHeight(ServerLevel level, int height, int posX, int posZ, Block pBlock){
        for(int y = level.getHeight(); y > height; y--){
            BlockState block = level.getBlockState(new BlockPos(posX, y, posZ));
            if(block.is(pBlock)){
                return y;
            }
            if(block.is(pBlock)){
                return ++y;
            }
        }
        return level.getHeight(Heightmap.Types.MOTION_BLOCKING, posX, posZ);
    }
}
