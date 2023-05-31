package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.block.types.*;
import com.idark.darkrpg.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Random;

public class KeyPadBlock extends Block {
    private static IntegerProperty STATE = IntegerProperty.create("key", 0, 1);
    Random rand = new Random();

    public KeyPadBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STATE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.fillStateContainer(builder);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(STATE);
        if (i == 1) {
            if (keyOpen(player, rand, worldIn, pos, state))
                return ActionResultType.SUCCESS;
        }

        if (i == 0) {
            ItemStack itemstack = player.getHeldItem(handIn);
            if (handIn == Hand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
                return ActionResultType.PASS;
            } else if (isValidFuel(itemstack) && itemKey(player, rand, worldIn, pos, state)) {
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
			for (int i = -4; i <= 4; i++) {
			for (int j = -4; j <= 4; j++) {
            for (int k = -4; k <= 4; k++) {
                BlockPos currentPos = pos.add(i, j, k);
                if (currentPos.distanceSq(pos) <= 4 * 4 && canConnect(worldIn.getBlockState(currentPos), state)) {
                    continue;
					} else {
                    return;
					}
				}
			}
		}
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	}

    private static boolean canConnect(BlockState neighborState, BlockState state) {
        return neighborState.matchesBlock(ModBlocks.KEYBLOCK.get()) && neighborState.get(STATE) == 1;
    }

    private static boolean isValidFuel(ItemStack stack) {
        return stack.getItem() == ModItems.VOID_KEY.get();
    }

	private static boolean keyOpen (PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
		worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
		
		for(Direction dir : Direction.values()) {
		BlockPos neighborPos = pos.offset(dir);
		BlockState neighborState = worldIn.getBlockState(neighborPos);
			if (neighborState.getBlock() instanceof KeyBlock) {
				worldIn.setBlockState(neighborPos, Blocks.AIR.getDefaultState());
			}
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	return false;
	}
	
	private static boolean itemKey(PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
		worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
		
		worldIn.setBlockState(pos, state.with(STATE, Integer.valueOf(1)));
		return true;
	}
}