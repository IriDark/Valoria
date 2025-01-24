package com.idark.valoria.client.ui.menus;

import com.idark.valoria.client.ui.menus.slots.GemSlot;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.MenuRegistry;
import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.client.gui.screen.ResultSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class JewelryMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;

    public JewelryMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player){
        super(MenuRegistry.JEWELRY_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        if(blockEntity != null){
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, 0, 27, 47));
                this.addSlot(new GemSlot(h, 1, 76, 47));

                this.addSlot(new ResultSlot(h, 2, 134, 47));
            });
        }

        this.layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean stillValid(Player playerIn){
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.jewelerTable.get());
    }

    @Override
    public int getInventorySize(){
        return 2;
    }
}