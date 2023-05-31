package com.idark.darkrpg.block.types;

import net.minecraft.block.*;

public class JewelerBlock extends Block {
   // private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.jewelers_table");
	
	public JewelerBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
/*/	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			player.openContainer(state.getContainer(worldIn, pos));
			return ActionResultType.CONSUME;
		}
	}
		
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new CartographyContainer(id, inventory, IWorldPosCallable.of(worldIn, pos));
		}, CONTAINER_NAME);
	}
	/*/
}