package com.idark.valoria.registries.item.types;

import net.minecraft.server.level.*;
import net.minecraft.world.item.*;

public interface InputListener{

    void onInput(ServerPlayer player, ItemStack stack, int event);
}
