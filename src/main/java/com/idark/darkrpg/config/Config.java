package com.idark.darkrpg.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static ForgeConfigSpec.ConfigValue<Integer>
	COBALT_MAX_Y, COBALT_VEIN_SIZE, COBALT_VEIN_COUNT,
	AMBER_MAX_Y, AMBER_VEIN_SIZE, AMBER_VEIN_COUNT,
	AMETHYST_MAX_Y, AMETHYST_VEIN_SIZE, AMETHYST_VEIN_COUNT,
	RUBY_MAX_Y, RUBY_VEIN_SIZE, RUBY_VEIN_COUNT,
	SAPPHIRE_MAX_Y, SAPPHIRE_VEIN_SIZE, SAPPHIRE_VEIN_COUNT,
	GEODITE_MAX_Y, GEODITE_VEIN_SIZE, GEODITE_VEIN_COUNT,
	GEODITE_DIRT_MAX_Y, GEODITE_DIRT_VEIN_SIZE, GEODITE_DIRT_VEIN_COUNT,
	LIMESTONE_MAX_Y, LIMESTONE_VEIN_SIZE, LIMESTONE_VEIN_COUNT,
	QUICKSAND_MAX_Y, QUICKSAND_VEIN_SIZE, QUICKSAND_VEIN_COUNT;
	
    public static ForgeConfigSpec.ConfigValue<Boolean> COBALT_ENABLED, AMBER_ENABLED, AMETHYST_ENABLED, RUBY_ENABLED, SAPPHIRE_ENABLED, GEODITE_ENABLED, GEODITE_DIRT_ENABLED, LIMESTONE_ENABLED, QUICKSAND_ENABLED;

    public Config(ForgeConfigSpec.Builder builder) {
        
        builder.comment("World generation settings").push("world");
        COBALT_ENABLED = builder.comment("Whether cobalt ore is enabled. Set to false to disable spawning.")
		.define("cobaltEnabled", true);
        COBALT_MAX_Y = builder.comment("Maximum Y value for cobalt ore veins")
		.defineInRange("cobaltOreMaxY", 60, 16, 255);
        COBALT_VEIN_SIZE = builder.comment("Maximum number of blocks per cobalt ore vein")
		.defineInRange("cobaltOreVeinSize", 8, 3, 255);
        COBALT_VEIN_COUNT = builder.comment("Number of cobalt ore veins per chunk")
		.defineInRange("cobaltOreVeinCount", 3, 1, 255);
        AMBER_ENABLED = builder.comment("Whether amber ore is enabled. Set to false to disable spawning.")
		.define("amberEnabled", true);
        AMBER_MAX_Y = builder.comment("Maximum Y value for amber ore veins")
		.defineInRange("amberOreMaxY", 48, 3, 255);
        AMBER_VEIN_SIZE = builder.comment("Maximum number of blocks per amber ore vein")
		.defineInRange("amberOreVeinSize", 6, 2, 255);
        AMBER_VEIN_COUNT = builder.comment("Number of amber ore veins per chunk")
		.defineInRange("amberOreVeinCount", 3, 1, 255);
        AMETHYST_ENABLED = builder.comment("Whether amethyst ore is enabled. Set to false to disable spawning.")
		.define("amethystEnabled", true);
        AMETHYST_MAX_Y = builder.comment("Maximum Y value for amethyst ore veins")
		.defineInRange("amethystOreMaxY", 36, 3, 255);
        AMETHYST_VEIN_SIZE = builder.comment("Maximum number of blocks per amethyst ore vein")
		.defineInRange("amethystOreVeinSize", 4, 2, 255);
        AMETHYST_VEIN_COUNT = builder.comment("Number of amethyst ore veins per chunk")
		.defineInRange("amethystOreVeinCount", 2, 1, 255);
        RUBY_ENABLED = builder.comment("Whether ruby ore is enabled. Set to false to disable spawning.")
		.define("rubyEnabled", true);
        RUBY_MAX_Y = builder.comment("Maximum Y value for ruby ore veins")
		.defineInRange("rubyOreMaxY", 38, 6, 255);
        RUBY_VEIN_SIZE = builder.comment("Maximum number of blocks per ruby ore vein")
		.defineInRange("rubyOreVeinSize", 4, 1, 255);
        RUBY_VEIN_COUNT = builder.comment("Number of ruby ore veins per chunk")
		.defineInRange("rubyOreVeinCount", 2, 1, 255);
        SAPPHIRE_ENABLED = builder.comment("Whether sapphire ore is enabled. Set to false to disable spawning.")
		.define("sapphireEnabled", true);
        SAPPHIRE_MAX_Y = builder.comment("Maximum Y value for sapphire ore veins")
		.defineInRange("sapphireOreMaxY", 62, 6, 255);
        SAPPHIRE_VEIN_SIZE = builder.comment("Maximum number of blocks per sapphire ore vein")
		.defineInRange("sapphireOreVeinSize", 4, 1, 255);
        SAPPHIRE_VEIN_COUNT = builder.comment("Number of sapphire ore veins per chunk")
		.defineInRange("sapphireOreVeinCount", 3, 1, 255);
		GEODITE_ENABLED = builder.comment("Whether geodites is enabled. Set to false to disable spawning.")
		.define("geoditesEnabled", true);
        GEODITE_MAX_Y = builder.comment("Maximum Y value for geodites veins")
		.defineInRange("geoditesMaxY", 65, 6, 255);
        GEODITE_VEIN_SIZE = builder.comment("Maximum number of blocks per geodites vein")
		.defineInRange("geoditesVeinSize", 4, 1, 255);
        GEODITE_VEIN_COUNT = builder.comment("Number of geodites veins per chunk")
		.defineInRange("geoditesVeinCount", 3, 1, 255);
		GEODITE_DIRT_ENABLED = builder.comment("Whether geodites is enabled. Set to false to disable spawning.")
		.define("geoditeDirtEnabled", true);
        GEODITE_DIRT_MAX_Y = builder.comment("Maximum Y value for geodites veins")
		.defineInRange("geoditeDirtMaxY", 67, 6, 255);
        GEODITE_DIRT_VEIN_SIZE = builder.comment("Maximum number of blocks per geodites vein")
		.defineInRange("geoditeDirtVeinSize", 3, 1, 255);
        GEODITE_DIRT_VEIN_COUNT = builder.comment("Number of geodites veins per chunk")
		.defineInRange("geoditeDirtVeinCount", 2, 1, 255);
		LIMESTONE_ENABLED = builder.comment("Whether limestone is enabled. Set to false to disable spawning.")
		.define("limestoneEnabled", true);
        LIMESTONE_MAX_Y = builder.comment("Maximum Y value for limestone veins")
		.defineInRange("limestoneMaxY", 70, 6, 255);
        LIMESTONE_VEIN_SIZE = builder.comment("Maximum number of blocks per geodites vein")
		.defineInRange("limestoneVeinSize", 14, 6, 255);
        LIMESTONE_VEIN_COUNT = builder.comment("Number of limestone veins per chunk")
		.defineInRange("limestoneVeinCount", 9, 3, 255);
		QUICKSAND_ENABLED = builder.comment("Whether quicksand is enabled. Set to false to disable spawning.")
		.define("quicksandEnabled", true);
        QUICKSAND_MAX_Y = builder.comment("Maximum Y value for quicksand veins")
		.defineInRange("quicksandMaxY", 80, 62, 255);
        QUICKSAND_VEIN_SIZE = builder.comment("Maximum number of blocks per quicksand vein")
		.defineInRange("quicksandVeinSize", 9, 4, 255);
        QUICKSAND_VEIN_COUNT = builder.comment("Number of quicksand veins per chunk")
		.defineInRange("quicksandVeinCount", 7, 3, 255);

    }

    public static final Config INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}