package com.idark.valoria.client.ui.menus;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import pro.komaru.tridot.client.render.gui.screen.*;

import java.util.*;
import java.util.function.*;


public class AlchemyStationMenu extends ContainerMenuBase{
    public final List<AlchemyRecipe> allRecipes;
    public final int blockLevel;
    private final Player player;
    private final BlockPos pos;
    private final Level level;
    private final ContainerLevelAccess access;

    public AlchemyStationMenu(int windowId, Inventory playerInventory){
        this(windowId, BlockPos.ZERO, playerInventory, ContainerLevelAccess.NULL, 0);
    }

    public AlchemyStationMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data){
        this(windowId, data.readBlockPos(), playerInventory, ContainerLevelAccess.create(playerInventory.player.level(), data.readBlockPos()), data.readInt());
    }

    public AlchemyStationMenu(int windowId, BlockPos pos, Inventory playerInventory, ContainerLevelAccess access, int blockLevel){
        super(MenuRegistry.ALCHEMY.get(), windowId);
        this.pos = pos;
        this.player = playerInventory.player;
        this.level = playerInventory.player.level();
        this.access = access;
        this.allRecipes = this.level.getRecipeManager().getAllRecipesFor(AlchemyRecipe.Type.INSTANCE);
        this.blockLevel = blockLevel;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 114 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 172));
        }
    }

    public BlockPos getPos(){
        return pos;
    }

    public int getBlockLevel(){
        return blockLevel;
    }

    public Level getLevel(){
        return level;
    }

    public Player getPlayer(){
        return player;
    }

    public List<AlchemyRecipe> getAllRecipes(){
        return allRecipes;
    }

    @Override
    public boolean stillValid(Player pPlayer){
        return stillValid(this.access, pPlayer, (b) -> b instanceof AlchemyStationBlock);
    }

    private boolean stillValid(ContainerLevelAccess pAccess, Player pPlayer, Predicate<Block> pTargetBlock){
        return pAccess.evaluate((p_38916_, p_38917_) -> pTargetBlock.test(p_38916_.getBlockState(p_38917_).getBlock()) && pPlayer.distanceToSqr((double)p_38917_.getX() + 0.5D, (double)p_38917_.getY() + 0.5D, (double)p_38917_.getZ() + 0.5D) <= 64.0D, true);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex){
        return ItemStack.EMPTY;
    }

    /**
     * Checks if the player has the required ingredients for a given recipe.
     * This can be called on both client and server.
     */
    public boolean checkAndSetAvailability(AlchemyRecipe recipe){
        Inventory inv = this.player.getInventory();
        boolean isEnough = true;
        for(var entry : recipe.getInputs()){
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            int currentCount = 0;

            for(int i = 0; i < inv.getContainerSize(); i++){
                ItemStack stack = inv.getItem(i);
                if(ingredient.test(stack)){
                    currentCount += stack.getCount();
                }
            }

            entry.getSecond().setCurrent(currentCount);
            entry.getSecond().setEnough(currentCount >= requiredCount);
            if(!entry.getSecond().isEnough || this.getBlockLevel() < recipe.getLevel()){
                isEnough = false;
            }
        }

        return isEnough;
    }

    /**
     * Consumes the required materials from the player's inventory.
     */
    private void consumeMaterials(AlchemyRecipe recipe){
        Inventory inv = this.player.getInventory();
        for(var entry : recipe.getInputs()){
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            for(int i = 0; i < inv.getContainerSize(); i++){
                if(requiredCount <= 0) break;
                ItemStack stack = inv.getItem(i);
                if(ingredient.test(stack)){
                    int consumeAmount = Math.min(requiredCount, stack.getCount());
                    stack.shrink(consumeAmount);
                    requiredCount -= consumeAmount;
                }
            }
        }
    }

    public boolean checkAndSetAvailability(AlchemyUpgradeRecipe recipe){
        Inventory inv = this.player.getInventory();
        boolean isEnough = true;
        for(var entry : recipe.getInputs()){
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            int currentCount = 0;

            for(int i = 0; i < inv.getContainerSize(); i++){
                ItemStack stack = inv.getItem(i);
                if(ingredient.test(stack)){
                    currentCount += stack.getCount();
                }
            }

            entry.getSecond().setCurrent(currentCount);
            entry.getSecond().setEnough(currentCount >= requiredCount);
            if(!entry.getSecond().isEnough){
                isEnough = false;
            }
        }

        return isEnough;
    }

    private void consumeMaterials(AlchemyUpgradeRecipe recipe){
        Inventory inv = this.player.getInventory();
        for(var entry : recipe.getInputs()){
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            for(int i = 0; i < inv.getContainerSize(); i++){
                if(requiredCount <= 0) break;
                ItemStack stack = inv.getItem(i);
                if(ingredient.test(stack)){
                    int consumeAmount = Math.min(requiredCount, stack.getCount());
                    stack.shrink(consumeAmount);
                    requiredCount -= consumeAmount;
                }
            }
        }
    }

    public void tryUpgrade(ServerPlayer player, ResourceLocation recipeId){
        Optional<AlchemyUpgradeRecipe> recipeHolder = level.getRecipeManager().byKey(recipeId)
        .filter(r -> r instanceof AlchemyUpgradeRecipe)
        .map(r -> (AlchemyUpgradeRecipe)r);

        if(recipeHolder.isPresent()){
            AlchemyUpgradeRecipe recipe = recipeHolder.get();
            if(checkAndSetAvailability(recipe)){
                consumeMaterials(recipe);
                PacketHandler.sendToTracking(player.serverLevel(), pos, new AlchemyUpgradeParticlePacket(this.blockLevel + 1, pos.getX(), pos.getY(), pos.getZ()));
                upgrade(player);
                this.broadcastChanges();
            }
        }
    }

    private void upgrade(ServerPlayer player){
        if (player.containerMenu instanceof AlchemyStationMenu){
            player.closeContainer();
            BlockState state = level.getBlockState(this.pos);
            if(state.getBlock() instanceof AlchemyStationBlock stationBlock){
                BlockState toState = switch(stationBlock.level){
                    case 1 -> BlockRegistry.alchemyStationTier2.get().defaultBlockState();
                    case 2 -> BlockRegistry.alchemyStationTier3.get().defaultBlockState();
                    case 3 -> BlockRegistry.alchemyStationTier4.get().defaultBlockState();
                    default -> BlockRegistry.alchemyStationTier1.get().defaultBlockState();
                };

                stationBlock.upgrade(this.pos, state, level, toState);
            }
        }
    }

    public Optional<AlchemyUpgradeRecipe> getUpgrade(ResourceLocation recipeId) {
        return level.getRecipeManager().byKey(recipeId)
        .filter(r -> r instanceof AlchemyUpgradeRecipe)
        .map(r -> (AlchemyUpgradeRecipe)r);
    }

    public void tryCraftRecipe(ServerPlayer player, ResourceLocation recipeId){
        Optional<AlchemyRecipe> recipeHolder = level.getRecipeManager().byKey(recipeId)
        .filter(r -> r instanceof AlchemyRecipe)
        .map(r -> (AlchemyRecipe)r);

        if(recipeHolder.isPresent()){
            AlchemyRecipe recipe = recipeHolder.get();
            if(checkAndSetAvailability(recipe)){
                consumeMaterials(recipe);
                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY).copy();
                player.getInventory().placeItemBackInInventory(result);
                this.broadcastChanges();
            }
        }
    }
}