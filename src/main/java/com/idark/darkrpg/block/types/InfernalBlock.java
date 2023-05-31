package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.*;
import com.idark.darkrpg.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Random;

;
	
public class InfernalBlock extends Block {
    private static IntegerProperty STATE = IntegerProperty.create("awakened", 0, 1);
    Random rand = new Random();
    public InfernalBlock(AbstractBlock.Properties properties) {
		super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STATE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.fillStateContainer(builder);
    }
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (handIn == Hand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
			return ActionResultType.PASS;
		} else if (isValidFuel(itemstack) && Infernal(player, rand, worldIn, pos, state)) {
			if (!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}
	
	private static boolean isValidFuel(ItemStack stack) {
		return stack.getItem() == ModItems.INFERNAL_STONE.get();
	}
	
	private static boolean Infernal(PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
        if (state.get(STATE) == 0) {
			worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
		
		for (int i = 0;i<25;i++) {
			double d2 = rand.nextGaussian() * 0.02D;
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = 0.5D - 3.0D;
            double d6 = (double)pos.getX() + d5 + rand.nextDouble() * 3.0D * 2.0D;
            double d7 = (double)pos.getY() + rand.nextDouble() + 0.5D;
            double d8 = (double)pos.getZ() + d5 + rand.nextDouble() * 3.0D * 2.0D;		
			worldIn.addParticle(ParticleTypes.LAVA, d6, d7, d8, d2, d3, d4);
        }
		worldIn.setBlockState(pos, state.with(STATE, 1));		
		return false;
		}
		
		return false;
	}
}