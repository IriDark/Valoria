package com.idark.valoria.api.unlockable.types;

import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;

public interface OnMobKilledListener{
    void checkCondition(ServerPlayer player, LivingEntity entity);
}
