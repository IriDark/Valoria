package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class TaintTransformBlockItem extends BlockItem{
    public TaintTransformBlockItem(Block pBlock, Item.Properties pProperties){
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
        Level worldIn = context.getLevel();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if(player != null && player.isShiftKeyDown()){
            if(state.is(BlockRegistry.voidTaint.get()) && state.getValue(VoidTaintBlock.TAINT) != 1){
                worldIn.playSound(player, player.blockPosition(), SoundEvents.FROG_LAY_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockAndUpdate(pos, BlockRegistry.voidTaint.get().defaultBlockState().setValue(VoidTaintBlock.TAINT, 1));
                for(int i = 0; i < 6; i++){
                    worldIn.addParticle(ParticleTypes.END_ROD, pos.getX() + worldIn.random.nextDouble(), pos.getY() + 1f, pos.getZ() + worldIn.random.nextDouble(), 0d, 0.05d, 0d);
                }

                if(!player.isCreative()){
                    stack.shrink(1);
                }
            }
        }

        return super.onItemUseFirst(stack, context);
    }
}
