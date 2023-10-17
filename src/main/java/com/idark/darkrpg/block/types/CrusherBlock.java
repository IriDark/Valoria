package com.idark.darkrpg.block.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.idark.darkrpg.tileentity.TileSimpleInventory;
import com.idark.darkrpg.util.LootUtil;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.util.particle.Particles;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class CrusherBlock extends Block implements ITileEntityProvider {
    Random rand = new Random();
    public CrusherBlock(AbstractBlock.Properties properties) {
		super(properties);
    }
	
    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new CrusherTileEntity();
    }	

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
	}
	
	// TODO: FIX RENDER (Item render in, weird tbh)
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        CrusherTileEntity tile = (CrusherTileEntity) world.getTileEntity(pos);
        ItemStack stack = player.getHeldItem(handIn).copy();

        if ((!stack.isEmpty()) && isValid(stack) && (tile.getItemHandler().getStackInSlot(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getHeldItemMainhand().setCount(stack.getCount() - 1);
                stack.setCount(1);
                tile.getItemHandler().setInventorySlotContents(0, stack);
                return ActionResultType.SUCCESS;
            } else {
                tile.getItemHandler().setInventorySlotContents(0, stack);
                player.inventory.deleteStack(player.getHeldItem(handIn));
                return ActionResultType.SUCCESS;
            }
        }

		if ((stack.getItem() instanceof PickaxeItem) && (!tile.getItemHandler().getStackInSlot(0).isEmpty())) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
				Vector3d playerPos = serverPlayer.getPositionVec();	
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) world, new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftContext((ServerWorld) world, playerPos, serverPlayer)));
			}
            tile.getItemHandler().removeStackFromSlot(0);
			for (int i = 0; i < 26; i++) {
				Particles.create(ModParticles.GEODE_PARTICLE)
				.setAlpha(1.0f, 0)
				.setScale(0.3f, 0)
				.setLifetime(50)
				.setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
				.spawn(world, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
			}
		} else if (stack.isEmpty() && !tile.getItemHandler().getStackInSlot(0).isEmpty()) {
			player.inventory.addItemStackToInventory(tile.getItemHandler().getStackInSlot(0).copy());
			tile.getItemHandler().removeStackFromSlot(0);
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;	
	}
	
	private static boolean isValid(ItemStack stack) {
		return stack.getItem() == ModItems.DIRT_GEODE.get();
	}
	
    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }	
}