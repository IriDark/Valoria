package com.idark.darkrpg.tileentity;

import com.google.common.base.Preconditions;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

public abstract class TileSimpleInventory extends TileEntity {

    private final Inventory itemHandler = createItemHandler();

    public TileSimpleInventory(TileEntityType<?> type) {
        super(type);
        itemHandler.addListener(i -> setChanged());
    }

    private static void copyToInv(NonNullList<ItemStack> src, IInventory dest) {
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for (int i = 0; i < src.size(); i++) {
            dest.setItem(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(IInventory inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        ItemStackHelper.saveAllItems(tag, copyFromInv(itemHandler));
        CompoundNBT ret = super.save(tag);
        return ret;
    }

    public final int inventorySize() {
        return getItemHandler().getContainerSize();
    }
	
	@Override
    public final SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        save(tag);
        return new SUpdateTileEntityPacket(worldPosition, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        load(this.getBlockState(),packet.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.save(new CompoundNBT());
    }	

    protected abstract Inventory createItemHandler();

    public final IInventory getItemHandler() {
        return itemHandler;
    }
}