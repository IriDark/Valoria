package com.idark.valoria.registries.block.types;

//public class ModTrappedChestBlock extends ChestBlock {
//
//    public ModTrappedChestBlock(BlockBehaviour.Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType) {
//        super(pProperties, pBlockEntityType);
//    }
//
//    protected Stat<ResourceLocation> getOpenChestStat() {
//        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
//    }
//
//    /**
//     * Can this block provide power. Only wire currently seems to have this change based on its state.
//     *
//     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#isSignalSource}
//     * whenever possible. Implementing/overriding is fine.
//     */
//    @Deprecated
//    public boolean isSignalSource(BlockState pState) {
//        return true;
//    }
//
//    /**
//     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getSignal}
//     * whenever possible. Implementing/overriding is fine.
//     */
//    @Deprecated
//    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
//        return Mth.clamp(ChestBlockEntity.getOpenCount(pBlockAccess, pPos), 0, 15);
//    }
//
//    /**
//     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getDirectSignal}
//     * whenever possible. Implementing/overriding is fine.
//     */
//    @Deprecated
//    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
//        return pSide == Direction.UP ? pBlockState.getSignal(pBlockAccess, pPos, pSide) : 0;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
//        return new ModTrappedChestBlockEntity(pPos, pState);
//    }
//}
