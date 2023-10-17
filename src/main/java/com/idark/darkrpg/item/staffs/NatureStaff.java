package com.idark.darkrpg.item.staffs;

import com.idark.darkrpg.util.particle.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NatureStaff extends Item {
    Random rand = new Random();
    public NatureStaff(Properties properties) {
      super(properties);
	}
	
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
		BlockPos pos = context.getPos();
        if(world.isRemote) {
			for (int i = 0; i < 15; i++) {
				Particles.create(ModParticles.SPARKLE_PARTICLE)
				.addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
				.setAlpha(0.25f, 0)
				.setScale(0.3f, 0)
				.setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
				.setLifetime(30)
				.setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
				.spawn(world, pos.getX() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
			}
			
			Particles.create(ModParticles.PHANTOM_SLASH)
				.addVelocity((0), (0), (0))
				.setAlpha(0.45f, 0)
				.setScale(3f, 0)
				.setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
				.setLifetime(30)
				.spawn(world, pos.getX() + 0.5F, pos.getY() + 2.5F, pos.getZ());
		}

        return super.onItemUseFirst(stack, context);
    }
}