package com.idark.darkrpg.tileentity;

import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntityType;

public class CrusherTileEntity extends TileSimpleInventory {
    public CrusherTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CrusherTileEntity() {
        this(ModTileEntities.CRUSHER_TILE_ENTITY.get());
    }

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(1) {
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }
        };
    }
}