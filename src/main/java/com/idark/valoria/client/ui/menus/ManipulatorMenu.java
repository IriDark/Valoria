package com.idark.valoria.client.ui.menus;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import pro.komaru.tridot.client.render.gui.screen.ResultSlot;
import pro.komaru.tridot.client.render.gui.screen.*;

public class ManipulatorMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;

    public ManipulatorMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player){
        super(MenuRegistry.MANIPULATOR_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 84);
        if(blockEntity != null){
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, 0, 27, 53));
                this.addSlot(new SlotItemHandler(h, 1, 76, 53));

                this.addSlot(new ResultSlot(h, 2, 134, 53));
            });
        }

    }

    @Override
    public int getInventorySize(){
        return 2;
    }

    @Override
    public boolean stillValid(Player playerIn){
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.elementalManipulator.get());
    }
}