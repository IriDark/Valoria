package com.idark.valoria.registries.block.entity;

import com.idark.valoria.compat.quark.QuarkIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModTrappedChestBlockEntity extends ChestBlockEntity {

    public ModTrappedChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(QuarkIntegration.LoadedOnly.TRAPPED_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    protected void signalOpenCount(Level p_155865_, BlockPos p_155866_, BlockState p_155867_, int p_155868_, int p_155869_) {
        super.signalOpenCount(p_155865_, p_155866_, p_155867_, p_155868_, p_155869_);
        if (p_155868_ != p_155869_) {
            Block block = p_155867_.getBlock();
            p_155865_.updateNeighborsAt(p_155866_, block);
            p_155865_.updateNeighborsAt(p_155866_.below(), block);
        }
    }

    @Override
    public BlockEntityType<?> getType() {
        return QuarkIntegration.LoadedOnly.TRAPPED_CHEST_BLOCK_ENTITY.get();
    }
}
