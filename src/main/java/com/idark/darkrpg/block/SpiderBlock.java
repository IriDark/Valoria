package com.idark.darkrpg.block;

import com.google.common.collect.Maps;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;

public class SpiderBlock extends Block {
   private final Block mimickedBlock;
   private static final Map<Block, Block> normalToInfectedMap = Maps.newIdentityHashMap();

   public SpiderBlock(Block blockIn, AbstractBlock.Properties properties) {
      super(properties);
      this.mimickedBlock = blockIn;
      normalToInfectedMap.put(blockIn, this);
   }

   public Block getMimickedBlock() {
      return this.mimickedBlock;
   }

   public static boolean canSpider(BlockState state) {
      return normalToInfectedMap.containsKey(state.getBlock());
   }

   private void spawnSpider(ServerWorld world, BlockPos pos) {
      SpiderEntity spiderentity = EntityType.SPIDER.create(world);
      spiderentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
      world.addEntity(spiderentity);
      spiderentity.spawnExplosionParticle();
   }

   /**
    * Perform side-effects from block dropping, such as creating silverfish
    */
   public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
      super.spawnAdditionalDrops(state, worldIn, pos, stack);
      if (worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
         this.spawnSpider(worldIn, pos);
      }

   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (worldIn instanceof ServerWorld) {
         this.spawnSpider((ServerWorld)worldIn, pos);
      }

   }

   public static BlockState infest(Block blockIn) {
      return normalToInfectedMap.get(blockIn).getDefaultState();
   }
}