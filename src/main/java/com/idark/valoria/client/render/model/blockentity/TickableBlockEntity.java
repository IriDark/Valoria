package com.idark.valoria.client.render.model.blockentity;

import net.minecraft.world.level.block.entity.*;

public interface TickableBlockEntity{
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper(){
        return (level0, pos0, state0, blockEntity) -> ((TickableBlockEntity)blockEntity).tick();
    }
}