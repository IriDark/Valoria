package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.types.BlockSimpleInventory;
import com.idark.valoria.registries.block.entity.types.CrusherBlockEntity;
import com.idark.valoria.registries.recipe.CrusherRecipe;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CrusherBlock extends Block implements EntityBlock {

    public CrusherBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrusherBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BlockSimpleInventory) {
                Containers.dropContents(world, pos, ((BlockSimpleInventory) tile).getItemHandler());
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        CrusherBlockEntity tile = (CrusherBlockEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();
        if (tile.getItemHandler().getItem(0).isEmpty()) {
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

                ValoriaUtils.tileEntity.SUpdateTileEntityPacket(tile);
            }

            return InteractionResult.SUCCESS;
        }

        if ((isValid(tile.getItemHandler().getItem(0), tile) && stack.getItem() instanceof PickaxeItem) && (!tile.getItemHandler().getItem(0).isEmpty())) {
            if (player instanceof ServerPlayer serverPlayer) {
                tile.craftItem(serverPlayer);
                player.getItemInHand(handIn).hurtAndBreak(5, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            tile.getItemHandler().removeItemNoUpdate(0);
        } else if (!(stack.getItem() instanceof PickaxeItem) && !tile.getItemHandler().getItem(0).isEmpty()) {
            if (!player.isCreative()) {
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }

            tile.getItemHandler().removeItemNoUpdate(0);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    private static boolean isValid(ItemStack stack, CrusherBlockEntity tile) {
        Optional<CrusherRecipe> recipeOptional = tile.getCurrentRecipe();
        if (recipeOptional.isPresent()) {
            CrusherRecipe recipe = recipeOptional.get();
            NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
            for (Ingredient ingredient : recipeIngredients) {
                if (ingredient.test(stack)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }
}