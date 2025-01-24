package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.block.types.VoidTaintBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TaintTransformBlockItem extends BlockItem{
    public TaintTransformBlockItem(Block pBlock, Item.Properties pProperties){
        super(pBlock, pProperties);
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext pContext){
        Player player = pContext.getPlayer();
        if(player != null && !player.isShiftKeyDown()){
            return this.place(new BlockPlaceContext(pContext));
        }

        return InteractionResult.FAIL;
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
