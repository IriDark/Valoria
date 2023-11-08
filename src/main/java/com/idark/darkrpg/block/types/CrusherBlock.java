package com.idark.darkrpg.block.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.idark.darkrpg.tileentity.TileSimpleInventory;
import com.idark.darkrpg.util.PacketUtils;
import com.idark.darkrpg.util.LootUtil;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.util.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
	
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        CrusherTileEntity tile = (CrusherTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();
        if ((!stack.isEmpty()) && isValid(stack) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                if (!player.isCreative()) {
                    player.getItemInHand(handIn).setCount(stack.getCount() - 1);
                }

                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
            } else {
                tile.getItemHandler().setItem(0, stack);
                if (!player.isCreative()) {
                    player.getInventory().removeItem(player.getItemInHand(handIn));
                }

                PacketUtils.SUpdateTileEntityPacket(tile);
            }
            return InteractionResult.SUCCESS;
        }

		if ((stack.getItem() instanceof PickaxeItem) && (!tile.getItemHandler().getItem(0).isEmpty())) {
			if (player instanceof ServerPlayer serverPlayer) {
                Vec3 block = new Vec3(pos.getX() + 0.5f, pos.getY() + 1.5f, pos.getZ() + 0.5f);
                LootUtil.SpawnLoot(world, pos.above(), LootUtil.generateLoot(new ResourceLocation(DarkRPG.MOD_ID, "items/crusher"), LootUtil.getGiftParameters((ServerLevel) world, block, serverPlayer)));
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

            world.playSound(player, pos, SoundEvents.CALCITE_BREAK , SoundSource.BLOCKS, 1.0f, 1.0f);
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
		return stack.getItem() == ModItems.DIRT_GEODE.get() || stack.getItem() == ModItems.STONE_GEODE.get();
	}
	
    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }
}