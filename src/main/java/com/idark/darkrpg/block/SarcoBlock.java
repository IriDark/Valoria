package com.idark.darkrpg.block;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.particles.*;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import java.util.Random;

public class SarcoBlock extends Block {
   Random rand = new Random();
   public SarcoBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
   private static final VoxelShape shape = Block.makeCuboidShape(3, 0, 3, 32, 13, 32);

   private void spawnSkeleton(ServerWorld world, BlockPos pos) {
      SkeletonEntity skeletonentity = EntityType.SKELETON.create(world);
      skeletonentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addEntity(skeletonentity);
   }

   @Override
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if(!worldIn.isRemote())
	  this.spawnSkeleton((ServerWorld)worldIn, pos);
	  for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
	}
   
   @Override
   public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
      ISelectionContext ctx) {
      return shape;
    }
   
   @Override
   public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
      if(!worldIn.isRemote())
	  this.spawnSkeleton((ServerWorld)worldIn, pos);
	  for (int i = 0;i<5;i++) {
        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
	}

	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, int fortune, int silktouch) {
      return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(5);
   }
   	//TODO Animation
}
