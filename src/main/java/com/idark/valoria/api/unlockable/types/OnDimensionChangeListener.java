package com.idark.valoria.api.unlockable.types;

import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;

public interface OnDimensionChangeListener{
    void checkCondition(ServerPlayer player, ResourceKey<Level> dim);
}
