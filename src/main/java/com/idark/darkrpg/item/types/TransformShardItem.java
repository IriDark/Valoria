package com.idark.darkrpg.item.types;

import com.idark.darkrpg.block.*;
import com.idark.darkrpg.item.*;
import com.idark.darkrpg.util.particle.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Random;

public class TransformShardItem extends Item {
    Random rand = new Random();
	public TransformType type;
	
    public TransformShardItem(TransformType type, Properties properties) {
        super(properties);
		this.type = type;		
    }
	
	public TransformType getTransformType() {
		return this.type;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

	@Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World worldIn = context.getWorld();		
        BlockState state = worldIn.getBlockState(context.getPos());
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();

        rightClickOnCertainBlockState(player, worldIn, state, pos);
        if (!player.isCreative()) {
            stack.shrink(1);
		}
		
        return super.onItemUseFirst(stack, context);		
	}

    private void rightClickOnCertainBlockState(PlayerEntity player, World worldIn, BlockState state, BlockPos pos) {
        if (state.matchesBlock(ModBlocks.VOID_PILLAR.get())) {
			worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 10f, 1f);
			worldIn.playSound(player, player.getPosition(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		switch(type) {
		case WICKED:
			for (int i = 0; i < 26; i++) {
				Particles.create(ModParticles.TRANSFORM_PARTICLE)
				.addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
				.setAlpha(1.0f, 0)
				.setScale(0.3f, 0)
				.setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
				.setLifetime(36)
				.setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
				.spawn(worldIn, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
			}

			worldIn.setBlockState(pos, ModBlocks.VOID_PILLAR_AMETHYST.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));		
			break;
		case SOUL:	
			for (int i = 0;i<10;i++) {
				worldIn.addParticle(ParticleTypes.SOUL, pos.getX(), pos.getY() + 0.5F + rand.nextDouble() * 0.75, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			}
			
			worldIn.setBlockState(pos, ModBlocks.CHARGED_VOID_PILLAR.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)));
			break;
			}
		}
	}
}