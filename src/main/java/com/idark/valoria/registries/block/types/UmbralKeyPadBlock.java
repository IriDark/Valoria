package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;

public class UmbralKeyPadBlock extends UmbralBlock{
    public static final BooleanProperty KEY_CLICKED = BooleanProperty.create("key");
    public UmbralKeyPadBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KEY_CLICKED, false).setValue(ACTIVE, false).setValue(RETURN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(KEY_CLICKED);
    }

    @Override
    public InteractionResult use(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
        var rand = pLevel.random;
        if(state.getValue(KEY_CLICKED)){
            if(!state.getValue(ACTIVE)){
                pLevel.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.8F, 1.2F);
                pLevel.playSound(player, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.2F, 1.0F);
                if(state.getBlock() instanceof UmbralBlock block){
                    block.onActivation(pLevel, pos, state);
                }

                pLevel.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        }

        if(!state.getValue(KEY_CLICKED)){
            ItemStack itemstack = player.getItemInHand(handIn);
            if(itemstack.getItem() != ItemsRegistry.VOID_KEY.get()){
                return InteractionResult.PASS;
            }else if(itemstack.getItem() == ItemsRegistry.VOID_KEY.get()){
                pLevel.playSound(player, player.blockPosition(), SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS, 1.0F, 0.95F);
                for(int i = 0; i < 25; i++){
                    double d2 = rand.nextGaussian() * 0.02D;
                    double d3 = rand.nextGaussian() * 0.02D;
                    double d4 = rand.nextGaussian() * 0.02D;
                    double d5 = 0.5D - 2.0D;
                    double d6 = (double)pos.getX() + d5 + rand.nextDouble() * 3.0D;
                    double d7 = (double)pos.getY() + rand.nextDouble() + 0.5D;
                    double d8 = (double)pos.getZ() + d5 + rand.nextDouble() * 3.0D;
                    pLevel.addParticle(ParticleTypes.POOF, d6, d7, d8, d2, d3, d4);
                }

                pLevel.setBlockAndUpdate(pos, state.setValue(KEY_CLICKED, true));
                if(!player.getAbilities().instabuild){
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }
}