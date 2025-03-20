package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;

public class CrusherBlock extends Block implements EntityBlock{

    public CrusherBlock(Properties properties){
        super(properties);
    }

    private static boolean isValid(ItemStack stack, CrusherBlockEntity tile){
        Optional<CrusherRecipe> recipeOptional = tile.getCurrentRecipe();
        if(recipeOptional.isPresent()){
            CrusherRecipe recipe = recipeOptional.get();
            NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
            for(Ingredient ingredient : recipeIngredients){
                if(ingredient.test(stack)){
                    return true;
                }
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new CrusherBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof BlockSimpleInventory){
                Containers.dropContents(world, pos, ((BlockSimpleInventory)tile).getItemHandler());
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
        CrusherBlockEntity tile = (CrusherBlockEntity)world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();
        var handler = tile.getItemHandler();
        if(handler.getItem(0).isEmpty()){
            handler.setItem(0, stack);
            if(!player.isCreative()){
                player.getInventory().removeItem(player.getItemInHand(handIn));
            }

            ValoriaUtils.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        if(handler.getItem(0).isEmpty()) return InteractionResult.PASS;
        if(stack.getItem() instanceof PickaxeItem && isValid(handler.getItem(0), tile)){
            if(player instanceof ServerPlayer serverPlayer){
                tile.craftItem(serverPlayer);
                player.getItemInHand(handIn).hurtAndBreak(world.getRandom().nextInt(0, 2), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }else{
            if(!player.isCreative()){
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, handler.getItem(0).copy()));
            }

            handler.removeItemNoUpdate(0);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }
}