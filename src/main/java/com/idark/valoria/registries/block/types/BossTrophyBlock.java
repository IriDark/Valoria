package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.mixin.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class BossTrophyBlock extends Block implements EntityBlock, SimpleWaterloggedBlock{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private Supplier<EntityType<?>> entity;
    public BossTrophyBlock(Supplier<EntityType<?>> entity, Properties pProperties){
        super(pProperties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
        this.entity = entity;
    }

    public BossTrophyBlock(Properties pProperties){
        super(pProperties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
        this.entity = null;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(WATERLOGGED);
    }

    public Supplier<EntityType<?>> getEntity(){
        return entity;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return createShape();
    }

    private static VoxelShape createShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.1875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.25, 0.0625, 0.9375, 0.5625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.0625, 0.625, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.375, 0.9375, 0.25, 0.625), BooleanOp.OR);
        return shape;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack){
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (pLevel.getBlockEntity(pPos) instanceof BossTrophyBlockEntity trophyBlockEntity && entity != null) {
            trophyBlockEntity.setEntity(entity.get().create(pLevel));
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        ItemStack heldStack = pPlayer.getItemInHand(pHand);
        BlockEntity tile = pLevel.getBlockEntity(pPos);
        if(tile instanceof BossTrophyBlockEntity trophyBlockEntity){
            if(pPlayer.isCreative()){
                if(heldStack.getItem() instanceof SpawnEggItem egg){
                    trophyBlockEntity.setEntity(egg.getType(heldStack.getTag()).create(pLevel)); // replacing entity with entity from spawn egg instead
                    return InteractionResult.SUCCESS;
                }
            }

            if(!(heldStack.getItem() instanceof SpawnEggItem)){
                if(trophyBlockEntity.entity == null) return InteractionResult.FAIL;

                Entity entity;
                var tag = new CompoundTag();
                tag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(trophyBlockEntity.entity).toString());
                entity = EntityType.loadEntityRecursive(tag, pLevel, Function.identity());
                if(entity != null && entity instanceof Mob mob){
                    if(((MobAmbientSoundInvoker)mob).invokeGetAmbientSound() == null) return InteractionResult.FAIL; // no sound
                    pLevel.playSound(null, pPos, ((MobAmbientSoundInvoker)mob).invokeGetAmbientSound(), SoundSource.AMBIENT);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new BossTrophyBlockEntity(pPos, pState);
    }
}
