package com.idark.valoria.registries.block.types;

import com.mojang.serialization.*;
import io.netty.buffer.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;

public enum HeavyAnvilTier implements StringRepresentable {
    BRONZE("bronze", 0, 0),
    BLACK_GOLD("black_gold", 1, 1),
    INFERNAL("infernal", 2, 2),
    VOID("void", 3, 4);

    public static final Codec<HeavyAnvilTier> CODEC = StringRepresentable.fromEnum(HeavyAnvilTier::values);
    public static final StreamCodec<ByteBuf, HeavyAnvilTier> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private final String name;
    private final int level;
    private final int instabilityBonus;

    HeavyAnvilTier(String name, int level, int instabilityBonus) {
        this.name = name;
        this.level = level;
        this.instabilityBonus = instabilityBonus;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getInstabilityBonus() {
        return instabilityBonus;
    }

    public Component getDisplayName() {
        return Component.translatable("block.valoria." + name + "_heavy_anvil");
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
