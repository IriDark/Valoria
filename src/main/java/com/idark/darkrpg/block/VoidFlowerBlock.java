package com.idark.darkrpg.block;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import java.util.Random;
import net.minecraft.potion.Effect;
import net.minecraft.block.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VoidFlowerBlock extends BushBlock {
   private final Effect stewEffect;
   private final int stewEffectDuration;
   public VoidFlowerBlock(Effect effect, int effectDuration, AbstractBlock.Properties properties) {
      super(properties);
	  this.stewEffect = effect;
      if (effect.isInstant()) {
        this.stewEffectDuration = effectDuration;
      } else {
        this.stewEffectDuration = effectDuration * 20;
      }
   }
	
   protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block block = state.getBlock();
      return block == ModBlocks.VOID_STONE.get() || block == ModBlocks.VOID_GRASS.get();
   }
	
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return false;
   }
   
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.dummy());
      Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
      double d0 = (double)pos.getX() + vector3d.x;
      double d1 = (double)pos.getZ() + vector3d.z;
	  for(int i = 0; i < 3; ++i) {
         if (rand.nextBoolean()) {
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + rand.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - rand.nextDouble()), d1 + rand.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
         }
      }
   }
   
   public Effect getStewEffect() {
      return this.stewEffect;
   }
   
   public int getStewEffectDuration() {
      return this.stewEffectDuration;
   }
}