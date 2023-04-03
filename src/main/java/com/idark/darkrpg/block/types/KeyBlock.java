package com.idark.darkrpg.block.types;

import com.idark.darkrpg.item.*;
import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
	
public class KeyBlock extends Block {

    public KeyBlock(AbstractBlock.Properties properties) {
    super(properties);
    }
	
	/*private static boolean canConnect(BlockState state) {
    return state.isAir() || state.matchesBlock(ModBlocks.KEYBLOCK || state.matchesBlock(ModBlocks.KEYBLOCK_RUNE));
    }
	
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    ItemStack stack = getItem(ModItems.VOID_KEY.get()).copy();
	
    if (stack.getItem(ModItems.VOID_KEY.get())) {
		player.inventory.deleteStack(getItem(ModItems.VOID_KEY.get()));
		new BlockPos(pos.x, pos.y + 1, pos.z);
		world.getBlockState(pos);
		world.removeBlock(pos, false);
		return ActionResultType.SUCCESS;
		}
    }*/
}