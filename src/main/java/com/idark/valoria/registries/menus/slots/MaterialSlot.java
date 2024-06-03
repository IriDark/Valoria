package com.idark.valoria.registries.menus.slots;

import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.items.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;

public class MaterialSlot extends SlotItemHandler{

    public MaterialSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return !stack.is(Tags.Items.TOOLS) && !stack.is(Tags.Items.ARMORS) && !(stack.getItem() instanceof ICurioItem);
    }
}
