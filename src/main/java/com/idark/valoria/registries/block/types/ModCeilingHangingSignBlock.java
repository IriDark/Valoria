package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.ModHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

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