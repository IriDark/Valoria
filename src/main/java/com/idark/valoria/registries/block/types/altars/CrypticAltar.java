package com.idark.valoria.registries.block.types.altars;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class CrypticAltar extends AbstractBossAltar{
    public CrypticAltar(Properties pProperties){
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new CrypticAltarBlockEntity(pPos, pState);
    }

    @Override
    public Item getSummonItem(){
        return ItemsRegistry.necromancerGrimoire.get();
    }
}
