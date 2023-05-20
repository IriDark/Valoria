package com.idark.darkrpg.item.staffs;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.util.*;
import com.idark.darkrpg.util.particle.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.*;
import net.minecraft.world.World;
import java.util.Objects;
import java.util.Random;

public class NatureStaff extends Item {
    Random rand = new Random();
    public NatureStaff(Properties properties) {
      super(properties);
	}
	
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        if(!world.isRemote) {
            PlayerEntity playerEntity = Objects.requireNonNull(context.getPlayer());
            BlockState clickedBlock = world.getBlockState(context.getPos());
			BlockPos pos = context.getPos();

            rightClickOnCertainBlockState(clickedBlock, context, playerEntity);
            stack.damageItem(1, playerEntity, player -> player.sendBreakAnimation(context.getHand()));
			if (world.isRemote()) {
				for (int i = 0; i < 15; i++) {
				Particles.create(ModParticles.SPARKLE_PARTICLE)
				.addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
				.setAlpha(0.25f, 0).setScale(0.3f, 0)
				.setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
				.setLifetime(30)
				.setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
				.spawn(world, pos.getX() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
				}
			}
		}

        return super.onItemUseFirst(stack, context);
    }

     private void rightClickOnCertainBlockState(BlockState clickedBlock, ItemUseContext context, PlayerEntity playerEntity) {
        if (clickedBlock.matchesBlock(Blocks.STONE)) {
            blockChangeState(playerEntity, context.getWorld(), context.getPos());
        }
    }

    private void blockChangeState(PlayerEntity playerEntity, World world, BlockPos pos) {		
		world.setBlockState(pos, ModBlocks.VOID_CRYSTAL.get().getDefaultState());
    }
}