package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

import javax.annotation.*;
import java.util.function.*;

public class ModChestBlock extends ChestBlock{

    public ModChestBlock(Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType){
        super(pProperties, pBlockEntityType);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new ModChestBlockEntity(pPos, pState);
    }
}
