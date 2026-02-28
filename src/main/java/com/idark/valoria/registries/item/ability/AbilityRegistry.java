package com.idark.valoria.registries.item.ability;

import net.minecraft.resources.*;

import javax.annotation.*;
import java.util.*;

public class AbilityRegistry{
    private static final Map<ResourceLocation, AbilityType<?>> REGISTRY = new HashMap<>();
    public static <T extends AbilityType<?>> T register(T type) {
        if (REGISTRY.containsKey(type.id)) {
            throw new IllegalArgumentException("Ability Type " + type.id + " is already registered!");
        }
        REGISTRY.put(type.id, type);
        return type;
    }

    @Nullable
    public static AbilityType<?> getType(ResourceLocation id) {
        return REGISTRY.get(id);
    }
}
