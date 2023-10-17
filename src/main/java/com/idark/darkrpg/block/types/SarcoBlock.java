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

public class SarcoBlock extends HorizontalBlock {
	Random rand = new Random();
    private static IntegerProperty OPEN = IntegerProperty.create("open", 0, 2);	
	public SarcoBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, 0));
	}
	
	private static final VoxelShape shape = Block.box(0, 0, 0, 16, 12, 16);

	private void spawnSkeleton(ServerWorld world, BlockPos pos) {
		SkeletonEntity skeletonentity = EntityType.SKELETON.create(world);
		skeletonentity.moveTo((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
		world.addFreshEntity(skeletonentity);
		world.addFreshEntity(skeletonentity);
		world.addFreshEntity(skeletonentity);
	}

   	public void sarcoDestroy(World worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.getValue(HorizontalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState sarco = ModBlocks.SARCO_HALF.get().defaultBlockState().setValue(HorizontalBlock.FACING,direction).setValue(OPEN, 0);
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		worldIn.setBlockAndUpdate(newPos, Blocks.AIR.defaultBlockState());
		if(!worldIn.isClientSide())
			this.spawnSkeleton((ServerWorld)worldIn, pos);
		for (int i = 0;i<5;i++) {
			worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
        }
	}

	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		Direction direction = state.getValue(HorizontalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState sarco = ModBlocks.SARCO_HALF.get().defaultBlockState().setValue(HorizontalBlock.FACING,direction).setValue(OPEN, 0);
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isClientSide) {
			worldIn.setBlock(newPos,sarco, 3);
			worldIn.blockUpdated(pos, Blocks.AIR);
			worldIn.blockUpdated(newPos, Blocks.AIR);
			state.updateNeighbourShapes(worldIn, pos, 3);
		}
	}

	@Override
	public void wasExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
		BlockState state = worldIn.getBlockState(pos);
		Direction direction = state.getValue(HorizontalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState sarco = ModBlocks.SARCO_HALF.get().defaultBlockState().setValue(HorizontalBlock.FACING,direction).setValue(OPEN, 0);
		worldIn.setBlockAndUpdate(newPos, Blocks.AIR.defaultBlockState());
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());		
		sarcoDestroy(worldIn,pos,state);
	}
   
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
		ISelectionContext ctx) {
		return shape;
    }
   
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
	    builder.add(FACING);
    }   
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
   
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Direction direction = state.getValue(HorizontalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState sarco = ModBlocks.SARCO_HALF.get().defaultBlockState().setValue(HorizontalBlock.FACING,direction).setValue(OPEN, 0);
		worldIn.setBlockAndUpdate(newPos, Blocks.AIR.defaultBlockState());
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		sarcoDestroy(worldIn,pos,state);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		Direction direction = state.getValue(HorizontalBlock.FACING);
		BlockPos newPos = pos.offset(direction.getNormal());
		BlockState sarco = ModBlocks.SARCO_HALF.get().defaultBlockState().setValue(HorizontalBlock.FACING,direction).setValue(OPEN, 0);
		int o = state.getValue(OPEN);
		if	(o == 0) {
			if(!worldIn.isClientSide())
				this.spawnSkeleton((ServerWorld)worldIn, pos);
		for (int i = 0;i<5;i++) {
			worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + rand.nextDouble(), pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
			worldIn.setBlockAndUpdate(pos, state.setValue(OPEN, Integer.valueOf(1)));
			worldIn.setBlockAndUpdate(newPos, state.setValue(OPEN, Integer.valueOf(1)));
			}
		}
		if (o == 1) {
			if(!worldIn.isClientSide())
				this.spawnSkeleton((ServerWorld)worldIn, pos);
				worldIn.setBlockAndUpdate(pos, state.setValue(OPEN, Integer.valueOf(2)));
				worldIn.setBlockAndUpdate(newPos, state.setValue(OPEN, Integer.valueOf(2)));
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