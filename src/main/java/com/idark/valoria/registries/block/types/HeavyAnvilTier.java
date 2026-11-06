package com.idark.valoria.registries.block.types;

import com.mojang.serialization.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;

public enum HeavyAnvilTier implements StringRepresentable {
    BRONZE("bronze", 0, 0),
    BLACK_GOLD("black_gold", 1, 1),
    INFERNAL("infernal", 2, 2),
    VOID("void", 3, 3);

    public static final Codec<HeavyAnvilTier> CODEC = StringRepresentable.fromEnum(HeavyAnvilTier::values);

    private final String name;
    private final int level;
    private final int instabilityBonus;

    HeavyAnvilTier(String name, int level, int instabilityBonus) {
        this.name = name;
        this.level = level;
        this.instabilityBonus = instabilityBonus;
    }

    public int getLevel() {
        return this.level;
    }

    public int getInstabilityBonus() {
        return this.instabilityBonus;
    }

    public Component getDisplayName() {
        return Component.translatable("block.valoria." + this.name + "_heavy_anvil");
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static HeavyAnvilTier fromString(String name) {
        if (name != null) {
            for (HeavyAnvilTier tier : values()) {
                if (tier.name.equalsIgnoreCase(name)) {
                    return tier;
                }
            }
        }
        return BRONZE;
    }
}
