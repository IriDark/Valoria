package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpiderBlock extends Block {

   public SpiderBlock(AbstractBlock.Properties properties) {
      super(properties);
   }
   private static final VoxelShape shape = Block.box(3, 0, 3, 13, 15, 13);

   private void spawnSpider(ServerWorld world, BlockPos pos) {
      CaveSpiderEntity cavespiderentity = EntityType.CAVE_SPIDER.create(world);
      cavespiderentity.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addFreshEntity(cavespiderentity);
   }

   @Override
   public void wasExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
      if(!worldIn.isClientSide())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
      ISelectionContext ctx) {
      return shape;
    }
   
   @Override
   public void destroy(IWorld worldIn, BlockPos pos, BlockState state) {
      if(!worldIn.isClientSide())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
   public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
	  entityIn.causeFallDamage(fallDistance, 3.0F);
	  worldIn.destroyBlock(pos, true);
	  if(!worldIn.isClientSide())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
   public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
	  worldIn.destroyBlock(hit.getBlockPos(), true);
	  if(!worldIn.isClientSide())
      this.spawnSpider((ServerWorld)worldIn, hit.getBlockPos());
   }
	
	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, int fortune, int silktouch) {
      return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(5);
   }
}