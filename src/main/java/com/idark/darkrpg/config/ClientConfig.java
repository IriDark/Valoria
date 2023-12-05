package com.idark.darkrpg.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig  {	
    public static ForgeConfigSpec.ConfigValue<Integer>
		MAGMA_CHARGE_BAR_Y, MAGMA_CHARGE_BAR_X, MAGMA_CHARGE_BAR_TYPE, DEBUG_X, DEBUG_Y;
    public static ForgeConfigSpec.ConfigValue<Boolean>
		IN_HAND_MODELS_32X, DASH_OVERLAY, BLOOD_OVERLAY;

    public ClientConfig (ForgeConfigSpec.Builder builder) {
        IN_HAND_MODELS_32X = builder.comment("When enabled some item models are 32x in-hand (Default: true)")
        .comment("Reload Resourcepacks when joining world (F3+T)")
		.define("32x", true);
        DASH_OVERLAY = builder.comment("When enabled shows a wind like lines on screen corners (Default: true)")
        .comment("Reload Resourcepacks when joining world (F3+T)")
		.define("DashOverlay", true);
        BLOOD_OVERLAY = builder.comment("When enabled shows a red vignette on screen corners (Default: true)")
        .comment("Reload Resourcepacks when joining world (F3+T)")
        .define("DashOverlay", true);
        MAGMA_CHARGE_BAR_Y = builder.comment("(Y) Coordinate for Magma Bar")
        .comment("Can be edited in-game without reloading packs (If nothing changed reload packs with [F3+T] keybind)")		
		.define("MagmaBarY", 5);
        MAGMA_CHARGE_BAR_X = builder.comment("(X) Coordinate for Magma Bar")
        .comment("Can be edited in-game without reloading packs (If nothing changed reload packs with [F3+T] keybind)")				
		.define("MagmaBarX", 4);
        MAGMA_CHARGE_BAR_TYPE = builder.comment("Type of Magma Bar")
        .comment("Can be edited in-game without reloading packs (If nothing changed reload packs with [F3+T] keybind)")		
		.defineInRange("MagmaBarType", 1, 1, 3);
        DEBUG_Y = builder.comment("(Y) Coordinate for Debuging UI`s")
        .comment("Can be edited in-game without reloading packs (If nothing changed reload packs with [F3+T] keybind)")		
		.define("DebugY", 0);
        DEBUG_X = builder.comment("(X) Coordinate for Debuging UI`s")
        .comment("Can be edited in-game without reloading packs (If nothing changed reload packs with [F3+T] keybind)")				
		.define("DebugX", 0);		
	}
	
    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}