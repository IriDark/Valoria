package com.idark.valoria.core.config;

import net.minecraftforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class ClientConfig{
    public static ForgeConfigSpec.ConfigValue<Integer>
    MAGMA_CHARGE_BAR_Y, MAGMA_CHARGE_BAR_X, MAGMA_CHARGE_BAR_TYPE,
    SOUL_BAR_Y, SOUL_BAR_X,
    MISC_UI_X, MISC_UI_Y;
    public static ForgeConfigSpec.ConfigValue<Boolean>
    RENDER_PHANTOM_ACTIVATION, OLD_GOBLIN_MODEL, SHOW_TOASTS, SHOW_UPDATES;

    static{
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public ClientConfig(ForgeConfigSpec.Builder builder){
        builder.comment("Misc").push("misc");
            SHOW_TOASTS = builder.define("showToasts", true);
            SHOW_UPDATES = builder.define("showUpdates", true);

        builder.pop();

        builder.comment("Graphics").push("graphics");
            MISC_UI_Y = builder.comment("(Y) Coordinate for Misc UI").define("miscY", 5);
            MISC_UI_X = builder.comment("(X) Coordinate for Misc UI").define("miscX", 4);
            SOUL_BAR_Y = builder.comment("(Y) Coordinate for Soul Bar").define("soulBarY", 5);
            SOUL_BAR_X = builder.comment("(X) Coordinate for Soul Bar").define("soulBarX", 4);
            MAGMA_CHARGE_BAR_Y = builder.comment("(Y) Coordinate for Magma Bar").define("magmaBarY", 5);
            MAGMA_CHARGE_BAR_X = builder.comment("(X) Coordinate for Magma Bar").define("magmaBarX", 4);
            MAGMA_CHARGE_BAR_TYPE = builder.comment("Type of Magma Bar").defineInRange("magmaBarType", 1, 1, 3);
            RENDER_PHANTOM_ACTIVATION = builder.comment("Item activation on ability use").define("phantomActivationRendering", true);
            OLD_GOBLIN_MODEL = builder.comment("Changes goblin model to old one").comment("You will need to reload your resources to see results").define("goblinModel", false);
        builder.pop();
    }
}