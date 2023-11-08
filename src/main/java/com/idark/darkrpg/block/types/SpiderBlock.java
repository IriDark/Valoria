package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiderBlock extends Block {
   public SpiderBlock(BlockBehaviour.Properties properties) {
      super(properties);
   }
   private static final VoxelShape shape = Block.box(1, 0, 0, 15, 13, 15);

   private void spawnSpider(ServerLevel world, BlockPos pos) {
      CaveSpider spider = EntityType.CAVE_SPIDER.create(world);
      spider.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addFreshEntity(spider);
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
      if(!worldIn.isClientSide()) {
         this.spawnSpider((ServerLevel) worldIn, pos);
      }
   }
   
   @Override
   public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
      if(pFallDistance > 1f) {
         pLevel.destroyBlock(pPos, false);
	     if(!pLevel.isClientSide()) {
            this.spawnSpider((ServerLevel) pLevel, pPos);
         }
      }
   }
   
   @Override
   public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
      pLevel.destroyBlock(pHit.getBlockPos(), true);
	  if(!pLevel.isClientSide()) {
         this.spawnSpider((ServerLevel) pLevel, pHit.getBlockPos());
      }
   }

   @Override
   public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
      return 5 + randomSource.nextInt(5) + randomSource.nextInt(5);
   }
}