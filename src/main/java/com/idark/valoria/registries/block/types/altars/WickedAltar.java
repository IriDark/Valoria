package com.idark.valoria.registries.block.types.altars;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class WickedAltar extends AbstractBossAltar{
    public WickedAltar(Properties pProperties){
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        world.playSound(null, pos, SoundEvents.EMPTY, SoundSource.BLOCKS, 1, 1); //todo
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new WickedAltarBlockEntity(pPos, pState);
    }

    @Override
    public Item getSummonItem(){
        return ItemsRegistry.suspciousGem.get();
    }
}
