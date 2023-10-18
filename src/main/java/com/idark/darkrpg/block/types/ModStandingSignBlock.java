package com.idark.darkrpg.block.types;

import com.idark.darkrpg.tileentity.ModSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class ModStandingSignBlock extends StandingSignBlock {
    public ModStandingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModSignTileEntity(pPos, pState);
    }
}