package com.idark.darkrpg.tileentity;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.inventory.Inventory;
import net.minecraft.tileentity.TileEntityType;

public class PedestalTileEntity extends TileSimpleInventory {
    public PedestalTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public PedestalTileEntity() {
        this(ModTileEntities.PEDESTAL_TILE_ENTITY.get());
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