package com.idark.valoria.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignTileEntity extends HangingSignBlockEntity {
    public ModHangingSignTileEntity(BlockPos pPos, BlockState pState) {
        super(pPos, pState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModTileEntities.HANGING_SIGN_TILE_ENTITIES.get();
    }
}