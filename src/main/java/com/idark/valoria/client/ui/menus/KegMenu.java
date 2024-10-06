package com.idark.valoria.client.ui.menus;

import com.idark.valoria.client.ui.menus.slots.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import mod.maxbogomol.fluffy_fur.client.gui.screen.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;

public class KegMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;
    public KegMenu(int windowId, Level world, BlockPos pos, Player player, Inventory inventory){
        super(MenuRegistry.KEG_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(inventory);
        this.layoutPlayerInventorySlots(8, 84);
        if(blockEntity != null && blockEntity instanceof KegBlockEntity keg){
            if (blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                this.addSlot(new SlotItemHandler(keg.itemHandler, 0, 44, 33));
                this.addSlot(new KegResultSlot(keg, keg.itemOutputHandler, 0, 116, 33));
            }
        }
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.KEG.get());
    }
}
