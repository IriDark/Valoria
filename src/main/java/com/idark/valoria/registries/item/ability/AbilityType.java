package com.idark.valoria.registries.item.ability;

import net.minecraft.resources.*;

public abstract class AbilityType<T extends AbilityComponent> {
    public final ResourceLocation id;

    public AbilityType(ResourceLocation id) {
        this.id = id;
    }

    public abstract T createInstance();
}