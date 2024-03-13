package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.levelgen.LevelGen;
import com.idark.valoria.registries.world.levelgen.portal.ValoriaTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ValoriaPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final int AABB_OFFSET = 2;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public ValoriaPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(AXIS) == Direction.Axis.Z ? Z_AXIS_AABB : X_AXIS_AABB;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        Direction.Axis direction$axis = pFacing.getAxis();
        Direction.Axis direction$axis1 = pState.getValue(AXIS);
        boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
        return !flag && !pFacingState.is(this) && !(new PortalShape(pLevel, pCurrentPos, direction$axis1)).isComplete() ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.canChangeDimensions()) {
            handlePortal(pEntity, pPos);
        }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(100) == 0) {
            pLevel.playLocalSound((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, pRandom.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d0 = (double) pPos.getX() + pRandom.nextDouble();
            double d1 = (double) pPos.getY() + pRandom.nextDouble();
            double d2 = (double) pPos.getZ() + pRandom.nextDouble();
            double d3 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            int j = pRandom.nextInt(2) * 2 - 1;
            if (!pLevel.getBlockState(pPos.west()).is(this) && !pLevel.getBlockState(pPos.east()).is(this)) {
                d0 = (double) pPos.getX() + 0.5D + 0.25D * (double) j;
                d3 = (double) pRandom.nextFloat() * 2.0F * (float) j;
            } else {
                d2 = (double) pPos.getZ() + 0.5D + 0.25D * (double) j;
                d5 = (double) pRandom.nextFloat() * 2.0F * (float) j;
            }

            pLevel.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getAxis());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return switch (pRot) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 ->
                    pState.getValue(AXIS) == Direction.Axis.X ? pState.setValue(AXIS, Direction.Axis.Z) : pState;
            default -> pState;
        };
    }

    private void handlePortal(Entity player, BlockPos pPos) {
        if (player.isOnPortalCooldown()) {
            player.setPortalCooldown();
        } else if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == LevelGen.VALORIA_KEY ? Level.OVERWORLD : LevelGen.VALORIA_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if (resourcekey == LevelGen.VALORIA_KEY) {
                    player.changeDimension(portalDimension, new ValoriaTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new ValoriaTeleporter(pPos, false));
                }

                player.setPortalCooldown();
            }
        }
    }
}