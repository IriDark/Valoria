package com.idark.valoria.registries;

import net.minecraft.core.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.items.*;
import net.neoforged.neoforge.items.wrapper.*;

import javax.annotation.*;

public final class BlockCapabilities{
    private BlockCapabilities(){}

    public static void register(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (be, side) -> sided(be.itemHandler, be.itemOutputHandler, side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (be, side) -> sided(be.itemHandler, be.itemOutputHandler, side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (be, side) -> sided(be.itemHandler, be.itemOutputHandler, side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.SOUL_INFUSER_BLOCK_ENTITY.get(), (be, side) -> sided(be.itemHandler, be.itemOutputHandler, side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.KILN.get(), (be, side) -> side == null ? null : new SidedInvWrapper(be, side));
    }

    private static IItemHandler sided(ItemStackHandler input, ItemStackHandler output, @Nullable Direction side){
        if(side == null) return new CombinedInvWrapper(input, output);
        return side == Direction.DOWN ? output : input;
    }
}
