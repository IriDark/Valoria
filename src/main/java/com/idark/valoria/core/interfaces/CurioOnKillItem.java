package com.idark.valoria.core.interfaces;

import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface CurioOnKillItem{
    void onKill(ItemStack stack, ServerPlayer killer, LivingEntity target);
}
