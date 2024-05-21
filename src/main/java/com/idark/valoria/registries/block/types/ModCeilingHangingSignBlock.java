package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

import javax.annotation.*;

public class ModCeilingHangingSignBlock extends CeilingHangingSignBlock{
    public ModCeilingHangingSignBlock(Properties properties, WoodType type){
        super(properties, type);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new ModHangingSignBlockEntity(pPos, pState);
    }
}