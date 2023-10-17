package com.idark.darkrpg.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignTileEntity extends SignBlockEntity {
    public ModSignTileEntity(BlockPos pPos, BlockState pState) {
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModTileEntities.SIGN_TILE_ENTITIES.get();
    }
}