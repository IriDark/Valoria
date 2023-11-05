package com.idark.darkrpg.block.types;

import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.idark.darkrpg.tileentity.TileSimpleInventory;
import com.idark.darkrpg.util.PacketUtils;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.util.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Random;

public class CrusherBlock extends Block implements EntityBlock {
    Random rand = new Random();
    public CrusherBlock(BlockBehaviour.Properties properties) {
		super(properties);
    }
	
    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrusherTileEntity(pPos, pState);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                Containers.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
	}
	
	// TODO: FIX RENDER (Item render in, weird tbh)
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        CrusherTileEntity tile = (CrusherTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();

        if ((!stack.isEmpty()) && isValid(stack) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                if (!player.isCreative()) {
                    player.getMainHandItem().setCount(stack.getCount() - 1);
                }

                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                return InteractionResult.SUCCESS;
            } else {
                tile.getItemHandler().setItem(0, stack);
                if (!player.isCreative()) {
                    player.getInventory().removeItem(player.getItemInHand(handIn));
                }

                PacketUtils.SUpdateTileEntityPacket(tile);
                return InteractionResult.SUCCESS;
            }
        }

		if ((stack.getItem() instanceof PickaxeItem) && (!tile.getItemHandler().getItem(0).isEmpty())) {
			if (player instanceof ServerPlayer) {
				ServerPlayer serverPlayer = (ServerPlayer) player;
                Vec3 playerPos = serverPlayer.position();
				//LootUtil.givePlayerMultipleItems(serverPlayer, LootUtil.generateLoot((ServerLevel) world, new ResourceLocation(DarkRPG.MOD_ID, "items/miners_bag"), LootUtil.getGiftContext((ServerLevel) world, playerPos, serverPlayer)));
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
            if (!player.isCreative()) {
                player.getInventory().add(tile.getItemHandler().getItem(0).copy());
            }

			tile.getItemHandler().removeItemNoUpdate(0);
			return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.PASS;
	}
	
	private static boolean isValid(ItemStack stack) {
		return stack.getItem() == ModItems.DIRT_GEODE.get();
	}
	
    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }
}