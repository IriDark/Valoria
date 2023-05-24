package com.idark.darkrpg.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static ForgeConfigSpec.ConfigValue<Integer>
	COBALT_MAX_Y, COBALT_VEIN_SIZE, COBALT_VEIN_COUNT,
	AMBER_MAX_Y, AMBER_VEIN_SIZE, AMBER_VEIN_COUNT,
	AMETHYST_MAX_Y, AMETHYST_VEIN_SIZE, AMETHYST_VEIN_COUNT,
	RUBY_MAX_Y, RUBY_VEIN_SIZE, RUBY_VEIN_COUNT,
	SAPPHIRE_MAX_Y, SAPPHIRE_VEIN_SIZE, SAPPHIRE_VEIN_COUNT;
	
    public static ForgeConfigSpec.ConfigValue<Boolean> COBALT_ENABLED, AMBER_ENABLED, AMETHYST_ENABLED, RUBY_ENABLED, SAPPHIRE_ENABLED;

    public Config(ForgeConfigSpec.Builder builder) {
        
        builder.comment("World generation settings").push("world");
        COBALT_ENABLED = builder.comment("Whether cobalt ore is enabled. Set to false to disable spawning.")
		.define("cobaltEnabled", true);
        COBALT_MAX_Y = builder.comment("Maximum Y value for cobalt ore veins")
		.defineInRange("cobaltOreMaxY", 48, 16, 255);
        COBALT_VEIN_SIZE = builder.comment("Maximum number of blocks per cobalt ore vein")
		.defineInRange("cobaltOreVeinSize", 8, 2, 255);
        COBALT_VEIN_COUNT = builder.comment("Number of cobalt ore veins per chunk")
		.defineInRange("cobaltOreVeinCount", 3, 1, 255);
        AMBER_ENABLED = builder.comment("Whether amber ore is enabled. Set to false to disable spawning.")
		.define("amberEnabled", true);
        AMBER_MAX_Y = builder.comment("Maximum Y value for amber ore veins")
		.defineInRange("amberOreMaxY", 36, 3, 255);
        AMBER_VEIN_SIZE = builder.comment("Maximum number of blocks per amber ore vein")
		.defineInRange("amberOreVeinSize", 8, 2, 255);
        AMBER_VEIN_COUNT = builder.comment("Number of amber ore veins per chunk")
		.defineInRange("amberOreVeinCount", 4, 1, 255);
        AMETHYST_ENABLED = builder.comment("Whether amethyst ore is enabled. Set to false to disable spawning.")
		.define("amethystEnabled", true);
        AMETHYST_MAX_Y = builder.comment("Maximum Y value for amethyst ore veins")
		.defineInRange("amethystOreMaxY", 24, 3, 255);
        AMETHYST_VEIN_SIZE = builder.comment("Maximum number of blocks per amethyst ore vein")
		.defineInRange("amethystOreVeinSize", 6, 3, 255);
        AMETHYST_VEIN_COUNT = builder.comment("Number of amethyst ore veins per chunk")
		.defineInRange("amethystOreVeinCount", 3, 2, 255);
        RUBY_ENABLED = builder.comment("Whether ruby ore is enabled. Set to false to disable spawning.")
		.define("rubyEnabled", true);
        RUBY_MAX_Y = builder.comment("Maximum Y value for ruby ore veins")
		.defineInRange("rubyOreMaxY", 30, 6, 255);
        RUBY_VEIN_SIZE = builder.comment("Maximum number of blocks per ruby ore vein")
		.defineInRange("rubyOreVeinSize", 6, 2, 255);
        RUBY_VEIN_COUNT = builder.comment("Number of ruby ore veins per chunk")
		.defineInRange("rubyOreVeinCount", 6, 2, 255);
        SAPPHIRE_ENABLED = builder.comment("Whether sapphire ore is enabled. Set to false to disable spawning.")
		.define("sapphireEnabled", true);
        SAPPHIRE_MAX_Y = builder.comment("Maximum Y value for sapphire ore veins")
		.defineInRange("sapphireOreMaxY", 46, 6, 255);
        SAPPHIRE_VEIN_SIZE = builder.comment("Maximum number of blocks per sapphire ore vein")
		.defineInRange("sapphiretOreVeinSize", 8, 3, 255);
        SAPPHIRE_VEIN_COUNT = builder.comment("Number of sapphire ore veins per chunk")
		.defineInRange("sapphireOreVeinCount", 5, 1, 255);

    }

    public static final Config INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}