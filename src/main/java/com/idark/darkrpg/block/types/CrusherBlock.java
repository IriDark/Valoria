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
    public TileEntity newBlockEntity(@Nonnull IBlockReader world) {
        return new CrusherTileEntity();
    }	

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.inventory.InventoryHelper.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
	}
	
	// TODO: FIX RENDER (Item render in, weird tbh)
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        CrusherTileEntity tile = (CrusherTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();

        if ((!stack.isEmpty()) && isValid(stack) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getMainHandItem().setCount(stack.getCount() - 1);
                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                return ActionResultType.SUCCESS;
            } else {
                tile.getItemHandler().setItem(0, stack);
                player.inventory.removeItem(player.getItemInHand(handIn));
                return ActionResultType.SUCCESS;
            }
        }

		if ((stack.getItem() instanceof PickaxeItem) && (!tile.getItemHandler().getItem(0).isEmpty())) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
				Vector3d playerPos = serverPlayer.position();	
				LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerWorld) world, new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftContext((ServerWorld) world, playerPos, serverPlayer)));
			}
            tile.getItemHandler().removeItemNoUpdate(0);
			for (int i = 0; i < 26; i++) {
				Particles.create(ModParticles.GEODE_PARTICLE)
				.setAlpha(1.0f, 0)
				.setScale(0.3f, 0)
				.setLifetime(50)
				.setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
				.spawn(world, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
			}
		} else if (stack.isEmpty() && !tile.getItemHandler().getItem(0).isEmpty()) {
			player.inventory.add(tile.getItemHandler().getItem(0).copy());
			tile.getItemHandler().removeItemNoUpdate(0);
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;	
	}
	
	private static boolean isValid(ItemStack stack) {
		return stack.getItem() == ModItems.DIRT_GEODE.get();
	}
	
    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }	
}