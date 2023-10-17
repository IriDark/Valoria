package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class SarcoHalfBlock extends HorizontalBlock {
	Random rand = new Random();
    private static IntegerProperty OPEN = IntegerProperty.create("open", 0, 2);	
	public SarcoHalfBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(OPEN, 0));
	}
	
	private static final VoxelShape shape = Block.makeCuboidShape(0, 0, 0, 16, 12, 16);

	private void spawnSkeleton(ServerWorld world, BlockPos pos) {
		SkeletonEntity skeletonentity = EntityType.SKELETON.create(world);
		skeletonentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
		world.addEntity(skeletonentity);
		world.addEntity(skeletonentity);
		world.addEntity(skeletonentity);
	}

   	public void sarcoDestroy(World worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		if(!worldIn.isRemote())
			this.spawnSkeleton((ServerWorld)worldIn, pos);
		for (int i = 0;i<5;i++) {
			worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
	}

	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());		
	}
   
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
		ISelectionContext ctx) {
		return shape;
    }
   
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
	    builder.add(HORIZONTAL_FACING);
    }   
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
   
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Direction direction = state.get(HorizontalBlock.HORIZONTAL_FACING);
		BlockPos newPos = pos.add(direction.getDirectionVec());
		BlockState sarco = ModBlocks.SARCOPHAGUS.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,direction).with(OPEN, 0);
		worldIn.setBlockState(newPos, Blocks.AIR.getDefaultState());
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		sarcoDestroy(worldIn,pos,state);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		Direction direction = state.get(HorizontalBlock.HORIZONTAL_FACING);
		BlockPos newPos = pos.add(direction.getDirectionVec());
		BlockState sarco = ModBlocks.SARCOPHAGUS.get().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,direction).with(OPEN, 0);
		int o = state.get(OPEN);
		if	(o == 0) {
			if(!worldIn.isRemote())
				this.spawnSkeleton((ServerWorld)worldIn, pos);
		for (int i = 0;i<5;i++) {
			worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			worldIn.setBlockState(pos, state.with(OPEN, Integer.valueOf(1)));
			worldIn.setBlockState(newPos, state.with(OPEN, Integer.valueOf(1)));
			}
		}
		if (o == 1) {
			if(!worldIn.isRemote())
				this.spawnSkeleton((ServerWorld)worldIn, pos);
				worldIn.setBlockState(pos, state.with(OPEN, Integer.valueOf(2)));
				worldIn.setBlockState(newPos, state.with(OPEN, Integer.valueOf(2)));
			}	
		
		if (o == 2) {
			return ActionResultType.PASS;
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, int fortune, int silktouch) {
      return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(5);
    }
}