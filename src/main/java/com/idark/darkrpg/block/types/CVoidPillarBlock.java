package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.*;
import com.idark.darkrpg.item.*;
import com.idark.darkrpg.item.types.SpearItem;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Random;

;

public class CVoidPillarBlock extends RotatedPillarBlock {
    Random rand = new Random();
	public CVoidPillarBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (handIn == Hand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
			return ActionResultType.PASS;
		} else if (isValidFuel(itemstack) && collector(itemstack, player, rand, worldIn, pos, state)) {
			if (!player.abilities.isCreativeMode) {
				if (itemstack.getItem() instanceof SpearItem) {
					worldIn.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					itemstack.damageItem(10, player, (playerEntity) -> {
					playerEntity.sendBreakAnimation(handIn);
					});
				}
			}
			worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return ActionResultType.CONSUME;
		}
		return ActionResultType.SUCCESS;
	}

	private static boolean isValidFuel(ItemStack stack) {
	return stack.getItem() instanceof SpearItem;
	}
	
	private static boolean collector(ItemStack stack, PlayerEntity player, Random rand, World worldIn, BlockPos pos, BlockState state) {
		worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
		for (int i = 0;i<16;i++) {
			worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5F + rand.nextDouble() * 1.1, pos.getZ() + 0.5F + rand.nextDouble(), 0d, 0.05d, 0d);
        }
		worldIn.setBlockState(pos, ModBlocks.VOID_PILLAR.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));		
		if (!worldIn.isRemote) {
			if (player == null || !player.abilities.isCreativeMode) {
				spawnAsEntity(worldIn, pos, new ItemStack(ModItems.UNCHARGED_STONE.get()));
				return true;
				}
			}
		return true;
	}
}