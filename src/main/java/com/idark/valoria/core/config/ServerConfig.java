package com.idark.valoria.core.config;

import net.minecraftforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class ServerConfig { //todo
    public static ForgeConfigSpec.ConfigValue<Boolean>
    PERCENT_ARMOR;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        PERCENT_ARMOR = builder.comment("When enabled armor is defined as percent (Default: true)").comment("keep in mind that Minecraft attributes are limited, so you'll need to download some mod that removes the limits, otherwise high tier armor will be a nonsense thanks to Mojang... that's the reason why percentage armor is implemented").define("PercentArmor", true);
    }
}