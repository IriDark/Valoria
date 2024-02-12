package com.idark.valoria.block.types;

import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.item.ModItems;
import com.idark.valoria.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class KeyPadBlock extends Block {
    private static final IntegerProperty STATE = IntegerProperty.create("key", 0, 1);
    Random rand = new Random();

    public KeyPadBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int i = state.getValue(STATE);
        if (i == 1) {
            if (keyOpen(player, world, pos))
                return InteractionResult.SUCCESS;
        }

        if (i == 0) {
            ItemStack itemstack = player.getItemInHand(handIn);
            if (handIn == InteractionHand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getItemInHand(InteractionHand.OFF_HAND))) {
                return InteractionResult.PASS;
            } else if (isValidFuel(itemstack) && itemKey(player, rand, world, pos, state)) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    private static boolean isValidFuel(ItemStack stack) {
        return stack.getItem() == ModItems.VOID_KEY.get();
    }

	private static boolean keyOpen(Player player, Level worldIn, BlockPos pos) {
		worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.8F, 1.2F);
        worldIn.playSound(player, player.blockPosition(), SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.8F, 1.0F);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockPos targetPos = pos.offset(i, j, k);
                    BlockState targetState = worldIn.getBlockState(targetPos);
                    if (targetState.is(ModTags.KEY_BLOCKS)) {
                        worldIn.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }

        worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        return false;
    }
	
	private static boolean itemKey(Player player, Random rand, Level worldIn, BlockPos pos, BlockState state) {
		worldIn.playSound(player, player.blockPosition(), SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS, 1.0F, 0.95F);
		for (int i = 0;i<25;i++) {
			double d2 = rand.nextGaussian() * 0.02D;
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = 0.5D - 2.0D;
            double d6 = (double)pos.getX() + d5 + rand.nextDouble() * 3.0D;
            double d7 = (double)pos.getY() + rand.nextDouble() + 0.5D;
            double d8 = (double)pos.getZ() + d5 + rand.nextDouble() * 3.0D;		
			worldIn.addParticle(ParticleTypes.POOF, d6, d7, d8, d2, d3, d4);
		}
		
		worldIn.setBlockAndUpdate(pos, state.setValue(STATE, 1));
		return true;
	}
}