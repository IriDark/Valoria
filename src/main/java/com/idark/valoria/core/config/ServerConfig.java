package com.idark.valoria.core.config;

import net.minecraftforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class ServerConfig{ //todo
    public static ForgeConfigSpec.ConfigValue<Double> POT_SPAWN_CHANCE;

    static{
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public ServerConfig(ForgeConfigSpec.Builder builder){
        POT_SPAWN_CHANCE = builder.comment("Pots spawn chance (Default: 5%)").defineInRange("PotSpawnChance", 0.025d, 0.0d, 1.0d);
    }
}