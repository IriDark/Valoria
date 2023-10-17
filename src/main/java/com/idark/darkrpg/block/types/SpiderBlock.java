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
   private static final VoxelShape shape = Block.makeCuboidShape(3, 0, 3, 13, 15, 13);

   private void spawnSpider(ServerWorld world, BlockPos pos) {
      CaveSpiderEntity cavespiderentity = EntityType.CAVE_SPIDER.create(world);
      cavespiderentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addEntity(cavespiderentity);
   }

   @Override
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if(!worldIn.isRemote())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
      ISelectionContext ctx) {
      return shape;
    }
   
   @Override
   public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
      if(!worldIn.isRemote())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
	  entityIn.onLivingFall(fallDistance, 3.0F);
	  worldIn.destroyBlock(pos, true);
	  if(!worldIn.isRemote())
	  this.spawnSpider((ServerWorld)worldIn, pos);
   }
   
   @Override
   public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
	  worldIn.destroyBlock(hit.getPos(), true);
	  if(!worldIn.isRemote())
      this.spawnSpider((ServerWorld)worldIn, hit.getPos());
   }
	
	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, int fortune, int silktouch) {
      return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(5);
   }
}