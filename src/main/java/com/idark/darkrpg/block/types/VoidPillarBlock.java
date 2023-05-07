package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.*;
import com.idark.darkrpg.item.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.PlayerEntity;;
import net.minecraft.world.World;
import java.util.Random;

public class VoidPillarBlock extends RotatedPillarBlock {
    Random rand = new Random();
	public VoidPillarBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (handIn == Hand.MAIN_HAND && !isValidFuelA(itemstack) && isValidFuelA(player.getHeldItem(Hand.OFF_HAND))) {
			return ActionResultType.PASS;
		} else if (isValidFuelA(itemstack) && Amethyst(player, rand, worldIn, pos, state)) {
			if (player == null || !player.abilities.isCreativeMode) {
				if (itemstack.getItem() == ModItems.AMETHYST.get()) {
					itemstack.shrink(1);
				}
			}
			return ActionResultType.CONSUME;
		}

		if (handIn == Hand.MAIN_HAND && !isValidFuelS(itemstack) && isValidFuelS(player.getHeldItem(Hand.OFF_HAND))) {
			return ActionResultType.PASS;
		} else if (isValidFuelS(itemstack) && Soulstone(player, rand, worldIn, pos, state)) {
			if (player == null || !player.abilities.isCreativeMode) {
				if (itemstack.getItem() == ModItems.SOULSTONE.get()) {	
					itemstack.shrink(1);
				}
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.SUCCESS;
	}
	
	private static boolean isValidFuelA(ItemStack stack) {
		return stack.getItem() == ModItems.AMETHYST.get();
	}
	
	private static boolean isValidFuelS(ItemStack stack) {
		return stack.getItem() == ModItems.SOULSTONE.get();
	}
	
	private static boolean Amethyst(PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
		worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		for (int i = 0;i<25;i++) {
			double d2 = rand.nextGaussian() * 0.02D;
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = 0.5D - 3.0D;
            double d6 = (double)pos.getX() + d5 + rand.nextDouble() * 3.0D * 2.0D;
            double d7 = (double)pos.getY() + rand.nextDouble() + 0.5D;
            double d8 = (double)pos.getZ() + d5 + rand.nextDouble() * 3.0D * 2.0D;		
			worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, d6, d7, d8, d2, d3, d4);
        }
		worldIn.setBlockState(pos, ModBlocks.VOID_PILLAR_AMETHYST.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));		
		return true;
	}
	
	private static boolean Soulstone(PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
		worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		for (int i = 0;i<25;i++) {
			double d2 = rand.nextGaussian() * 0.02D;
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = 0.5D - 3.0D;
            double d6 = (double)pos.getX() + d5 + rand.nextDouble() * 3.0D * 2.0D;
            double d7 = (double)pos.getY() + rand.nextDouble() + 0.5D;
            double d8 = (double)pos.getZ() + d5 + rand.nextDouble() * 3.0D * 2.0D;
			worldIn.addParticle(ParticleTypes.SOUL, d6, d7, d8, d2, d3, d4);
        }
		worldIn.setBlockState(pos, ModBlocks.CHARGED_VOID_PILLAR.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));
		return true;
	}
}