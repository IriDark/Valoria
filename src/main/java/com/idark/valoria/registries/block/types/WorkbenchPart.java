package com.idark.valoria.registries.block.types;

import net.minecraft.util.*;

enum WorkbenchPart implements StringRepresentable{
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    TOP_LEFT,
    TOP_RIGHT;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
