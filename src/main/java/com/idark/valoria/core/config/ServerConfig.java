package com.idark.valoria.core.config;

import com.idark.valoria.core.*;
import net.minecraftforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class ServerConfig{
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_CODEX_PROGRESSION, ENABLE_FOOD_ROT, ENABLE_NIHILITY, CRITICAL_NIHILITY_BLINDNESS;

    public static ForgeConfigSpec.ConfigValue<Double> POT_SPAWN_CHANCE,
    FOOD_ROT_INTERVAL,
    NIHILITY_DAMAGE_MULTIPLIER, NIHILITY_ACCUMULATION_INTERVAL, NIHILITY_DECAY_INTERVAL,
    CODEX_UPDATE_INTERVAL;

    public static ForgeConfigSpec.ConfigValue<MaxNihilityAction> MAX_NIHILITY_ACTION;


    static{
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public ServerConfig(ForgeConfigSpec.Builder builder){
        builder.comment("Gameplay Settings").push("gameplay");
            ENABLE_CODEX_PROGRESSION = builder.comment("Enables the progression system for the Codex.",
            "If true, players must unlock entries via gameplay.",
            "If false, all entries are visible by default.")
            .define("enableCodexProgression", true);
            CODEX_UPDATE_INTERVAL = builder.comment("Codex updating rate, specified in seconds").defineInRange("codexUpdateInterval", 3, 1d, 10000);
            ENABLE_FOOD_ROT = builder.comment("Food spoiling on entering Valoria dimension").define("enableFoodRot", true);
            FOOD_ROT_INTERVAL = builder.comment("Food spoiling time, specified in seconds").defineInRange("foodRotInterval", 3, 0.1, 10000);
            ENABLE_NIHILITY = builder.comment("Nihility System in Valoria").define("enableNihility", true);
            NIHILITY_DAMAGE_MULTIPLIER = builder.comment("Damage multiplier per 10% of excess Nihility", "Formula: 1.0 + (segments * multiplier)").defineInRange("nihilityDamageMultiplier", 2d, 0, 100d);
            NIHILITY_ACCUMULATION_INTERVAL = builder.comment("Nihility applying time, specified in seconds").defineInRange("nihilityAccumulationInterval", 3, 0.1, 10000);
            NIHILITY_DECAY_INTERVAL = builder.comment("Nihility decreasing time, specified in seconds").defineInRange("nihilityDecayInterval", 6, 0.1, 10000);
            MAX_NIHILITY_ACTION = builder.comment("Action on maximum Nihility amount reached").worldRestart().defineEnum("maxNihilityAction", MaxNihilityAction.KILL);
            CRITICAL_NIHILITY_BLINDNESS = builder.comment("Blindness on critical Nihility amount reached").define("criticalNihilityBlindness", true);
        builder.pop();

        builder.comment("Level Generation").push("levelgen");
            POT_SPAWN_CHANCE = builder.comment("Pots spawn chance").defineInRange("PotSpawnChance", 0.025d, 0.0d, 1.0d);
        builder.pop();
    }
}