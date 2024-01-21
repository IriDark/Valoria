package com.idark.valoria.client.render.model.tileentity;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableTileEntity {
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper() {
        return (level0, pos0, state0, blockEntity) -> ((TickableTileEntity)blockEntity).tick();
    }
}