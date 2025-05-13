package com.idark.valoria.registries.block.types.altars;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.*;

public class WickedAltar extends AbstractBossAltar{
    public WickedAltar(Properties pProperties){
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public SoundEvent getSummonSound(){
        return SoundsRegistry.WICKED_CRYSTAL_ALTAR.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new WickedAltarBlockEntity(pPos, pState);
    }

    @Override
    public Item getSummonItem(){
        return ItemsRegistry.suspiciousGem.get();
    }
}
