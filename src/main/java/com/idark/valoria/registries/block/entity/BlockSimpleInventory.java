package com.idark.valoria.registries.block.entity;

import com.google.common.base.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.items.*;

public abstract class BlockSimpleInventory extends BlockEntity{
    private final SimpleContainer itemHandler = createItemHandler();

    public BlockSimpleInventory(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
        itemHandler.addListener(i -> setChanged());
    }

    private static void copyToInv(NonNullList<ItemStack> src, Container dest){
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for(int i = 0; i < src.size(); i++){
            dest.setItem(i, src.get(i));
        }
    }

    public static void addHandPlayerItem(Level level, Player player, InteractionHand hand, ItemStack stack, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) >= 0) {
            addPlayerItem(level, player, addStack);
        } else if (stack.isEmpty()) {
            player.setItemInHand(hand, addStack.copy());
        } else if (ItemHandlerHelper.canItemStacksStack(stack, addStack) && (stack.getCount() + addStack.getCount() <= addStack.getMaxStackSize())) {
            stack.setCount(stack.getCount() + addStack.getCount());
            player.setItemInHand(hand, stack);
        } else {
            addPlayerItem(level, player, addStack);
        }
    }

    public static void addPlayerItem(Level level, Player player, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) != -1 || player.getInventory().getFreeSlot() > -1) {
            player.getInventory().add(addStack.copy());
        } else {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), addStack.copy()));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(Container inv){
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for(int i = 0; i < inv.getContainerSize(); i++){
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    @Override
    public void load(CompoundTag tag){
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag){
        ContainerHelper.saveAllItems(tag, copyFromInv(itemHandler));
    }

    public final int inventorySize(){
        return getItemHandler().getContainerSize();
    }

    protected abstract SimpleContainer createItemHandler();

    public final Container getItemHandler(){
        return itemHandler;
    }
}