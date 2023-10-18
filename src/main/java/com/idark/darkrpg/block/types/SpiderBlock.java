package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiderBlock extends Block {
   public SpiderBlock(BlockBehaviour.Properties properties) {
      super(properties);
   }
   private static final VoxelShape shape = Block.box(3, 0, 3, 13, 15, 13);

   private void spawnSpider(ServerLevel world, BlockPos pos) {
      CaveSpider cavespiderentity = EntityType.CAVE_SPIDER.create(world);
      cavespiderentity.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addFreshEntity(cavespiderentity);
   }

   @Override
   public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
      if(!worldIn.isClientSide()) {
          this.spawnSpider((ServerLevel) worldIn, pos);
      }
   }
   
   @Override
   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
       return shape;
   }
   
   @Override
   public void destroy(LevelAccessor worldIn, BlockPos pos, BlockState state) {
      if(!worldIn.isClientSide())
	  this.spawnSpider((ServerLevel) worldIn, pos);
   }
   
    /*@Override
   public void fallOn(Level worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
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
   }*/
}