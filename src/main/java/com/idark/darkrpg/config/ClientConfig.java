package com.idark.darkrpg.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig  {	
    public static ForgeConfigSpec.ConfigValue<Boolean> IN_HAND_MODELS_32X, DASH_OVERLAY;

    public ClientConfig (ForgeConfigSpec.Builder builder) {
        IN_HAND_MODELS_32X = builder.comment("When enabled some item models are 32x in-hand (Default: true)")
        .comment("Reload Resourcepacks when joining world (F3+T)")
		.define("32x_Enabled", true);	
        DASH_OVERLAY = builder.comment("When enabled shows a wind like lines on screen corners (Default: true)")
        .comment("Reload Resourcepacks when joining world (F3+T)")
		.define("dash_overlay_Enabled", true);
	}
	
    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}