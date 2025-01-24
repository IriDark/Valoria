package com.idark.valoria.registries.block.types;

import com.google.common.base.Predicates;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;

import javax.annotation.Nullable;

public class ValoriaPortalFrame extends Block{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static BlockPattern portalShape;
    public static final BooleanProperty GENERATED = BooleanProperty.create("generated");

    public ValoriaPortalFrame(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(GENERATED, false));
    }

    public static BlockPattern getOrCreatePortalShape(){
        if(portalShape == null){
            portalShape = BlockPatternBuilder.start().aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?").where('?', BlockInWorld.hasState(BlockStatePredicate.ANY)).where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.valoriaPortalFrame.get()).where(FACING, Predicates.equalTo(Direction.SOUTH)))).where('>', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.valoriaPortalFrame.get()).where(FACING, Predicates.equalTo(Direction.WEST)))).where('v', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.valoriaPortalFrame.get()).where(FACING, Predicates.equalTo(Direction.NORTH)))).where('<', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.valoriaPortalFrame.get()).where(FACING, Predicates.equalTo(Direction.EAST)))).build();
        }

        return portalShape;
    }

    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool){
        pPlayer.awardStat(Stats.BLOCK_MINED.get(this));
        pPlayer.causeFoodExhaustion(0.005F);
        if(!pState.getValue(GENERATED)){
            dropResources(pState, pLevel, pPos, pBlockEntity, pPlayer, pTool, false);
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_){
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_){
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_){
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
        builder.add(GENERATED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos2, boolean unknown){
        DebugPackets.sendNeighborsUpdatePacket(world, pos);
        if(world.getBlockState(pos2).getBlock().defaultBlockState() == BlockRegistry.valoriaPortal.get().defaultBlockState() || world.getBlockState(pos2).getBlock().defaultBlockState() == BlockRegistry.valoriaPortalFrame.get().defaultBlockState()){
            BlockPattern.BlockPatternMatch frame = getOrCreatePortalShape().find(world, pos);
            if(frame != null){
                BlockPos blockpos1 = frame.getFrontTopLeft().offset(-3, 0, -3);
                for(int i = 0; i < 3; ++i){
                    for(int j = 0; j < 3; ++j){
                        world.setBlock(blockpos1.offset(i, 0, j), BlockRegistry.valoriaPortal.get().defaultBlockState(), 2);
                    }
                }

                BlockPos soundPos = blockpos1.offset(1, 0, 1);
                world.playSound(null, soundPos.getX(), soundPos.getY(), soundPos.getZ(), SoundsRegistry.VALORIA_PORTAL_SPAWN.get(), SoundSource.HOSTILE, 1, 1);
            }
        }
    }

    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_){
        return false;
    }
}