package com.idark.valoria.client.ui.menus.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;

public class MaterialSlot extends SlotItemHandler{

    public MaterialSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return !stack.is(Tags.Items.TOOLS) && !stack.is(Tags.Items.ARMORS) && !(stack.getItem() instanceof ICurioItem);
    }
}
