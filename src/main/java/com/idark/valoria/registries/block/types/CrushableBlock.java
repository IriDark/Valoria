package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.CrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class CrushableBlock extends BaseEntityBlock {
    public static final IntegerProperty DUSTED = BlockStateProperties.DUSTED;
    private final Block turnsInto;
    private final SoundEvent crushSound;
    private final boolean isIce;

    public CrushableBlock(boolean isIce, Block pTurnsInto, BlockBehaviour.Properties pProperties, SoundEvent pCrushSound) {
        super(pProperties);
        this.isIce = isIce;
        this.turnsInto = pTurnsInto;
        this.crushSound = pCrushSound;
        this.registerDefaultState(this.stateDefinition.any().setValue(DUSTED, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DUSTED);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Deprecated
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, 2);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return this.isIce && (pAdjacentBlockState.is(this) || pAdjacentBlockState.is(Blocks.ICE));
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        pLevel.scheduleTick(pCurrentPos, this, 2);
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof CrushableBlockEntity pBlockEntity) {
            pBlockEntity.checkReset();
        }
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrushableBlockEntity(pPos, pState);
    }

    public Block getTurnsInto() {
        return this.turnsInto;
    }

    public SoundEvent getCrushSound() {
        return this.crushSound;
    }
}