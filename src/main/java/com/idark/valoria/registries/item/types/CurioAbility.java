package com.idark.valoria.registries.item.types;

import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public record CurioAbility(int event, @Nullable Component name, @Nullable ResourceLocation icon, BiConsumer<ServerPlayer, ItemStack> action) {
    public CurioAbility(int event, @Nullable Component name, BiConsumer<ServerPlayer, ItemStack> action) {
        this(event, name, null, action);
    }

    public CurioAbility(int event, BiConsumer<ServerPlayer, ItemStack> action) {
        this(event, null, null, action);
    }
}
