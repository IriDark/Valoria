package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;

import java.util.concurrent.*;

//TODO: FIX
public class UmbralBlock extends Block{
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty RETURN = BooleanProperty.create("return");

    public UmbralBlock(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(RETURN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE, RETURN);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState otherState, Direction direction){
        return state.getValue(ACTIVE);
    }

    @Deprecated
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return pState.getValue(ACTIVE) ? Shapes.empty() : pState.getShape(pLevel, pPos);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos){
        return state.getValue(ACTIVE) ? Shapes.empty() : super.getOcclusionShape(state, getter, pos);
    }

    @Deprecated
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return this.getShape(pState, pLevel, pPos, pContext);
    }

    public void onActivation(Level level, BlockPos pos, BlockState state){
        if(!state.getValue(ACTIVE) && state.getBlock() instanceof UmbralBlock){
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true).setValue(RETURN, true));
            level.scheduleTick(pos, state.getBlock(), 2 + level.getRandom().nextInt(5));
        }
    }

    public void activateDoor(ServerLevel level, BlockPos pos){
        BlockState state = level.getBlockState(pos);
        if(state.getBlock() instanceof UmbralBlock && !state.getValue(ACTIVE) && !state.getValue(RETURN)){
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true).setValue(RETURN, true));
            level.scheduleTick(pos, state.getBlock(), 2 + level.getRandom().nextInt(5));
        }
    }

    public void deactivateDoor(ServerLevel level, BlockPos pos){
        BlockState state = level.getBlockState(pos);
        level.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
        for(Direction e : Direction.values()){
            BlockState checkedState = level.getBlockState(pos.relative(e));
            if(checkedState.getBlock() instanceof UmbralBlock && checkedState.getValue(ACTIVE)){
                level.setBlockAndUpdate(pos.relative(e), checkedState.setValue(ACTIVE, false).setValue(RETURN, false));
            }
        }
    }

    // TODO: Fix scheduling
    // When player leaves world / server closes on returning state, block cant tick, and will be opened until there's a neighbor update
    @Deprecated
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom){
        if(pState.getValue(ACTIVE)){
            PacketHandler.sendToTracking(pLevel, pPos, new KeypadParticlePacket(pPos.getX(), pPos.getY(), pPos.getZ(), pPos.getX(), pPos.getY(), pPos.getZ()));
            for(Direction e : Direction.values()){
                this.activateDoor(pLevel, pPos.relative(e));
            }
        }

        if(pState.getValue(ACTIVE) && pState.getValue(RETURN)){
            ValoriaUtils.scheduler.scheduleAsyncTask(() -> deactivateDoor(pLevel, pPos), 30, TimeUnit.SECONDS);
        }
    }
}