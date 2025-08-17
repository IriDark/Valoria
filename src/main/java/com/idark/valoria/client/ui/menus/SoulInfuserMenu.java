package com.idark.valoria.client.ui.menus;

import com.idark.valoria.client.ui.menus.slots.*;
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

public class SoulInfuserMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;

    public SoulInfuserMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player){
        super(MenuRegistry.SOUL_INFUSER_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        if(blockEntity != null){
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, 0, 27, 27));
                this.addSlot(new SoulCollectorSlot(h, 1, 76, 27));

                this.addSlot(new ResultSlot(h, 2, 134, 27));
            });
        }

        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public int getInventorySize(){
        return 2;
    }

    @Override
    public boolean stillValid(Player playerIn){
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.soulInfuser.get());
    }
}