package com.idark.valoria.core.config;

import net.neoforged.neoforge.common.*;
import org.apache.commons.lang3.tuple.*;

public class ClientConfig{
    public static ModConfigSpec.ConfigValue<Integer>
    MAGMA_CHARGE_BAR_Y, MAGMA_CHARGE_BAR_X, MAGMA_CHARGE_BAR_TYPE,
    SOUL_BAR_Y, SOUL_BAR_X,
    NIHILITY_METER_X, NIHILITY_METER_Y,
    MISC_UI_X, MISC_UI_Y;
    public static ModConfigSpec.ConfigValue<Float> CODEX_SENSITIVITY;

    public static ModConfigSpec.ConfigValue<Boolean>
    RENDER_PHANTOM_ACTIVATION, OLD_GOBLIN_MODEL, SHOW_TOASTS, SHOW_UPDATES, NIHILITY_METER_ALWAYS_VISIBLE, NIHILITY_METER_ANIMATE, DAMAGE_INDICATOR;

    static{
        final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final ClientConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    public ClientConfig(ModConfigSpec.Builder builder){
        builder.comment("Misc").push("misc");
            SHOW_TOASTS = builder.define("showToasts", true);
            SHOW_UPDATES = builder.define("showUpdates", true);
            DAMAGE_INDICATOR = builder.define("damageIndicator", true);
        builder.pop();

        builder.comment("Graphics").push("graphics");
            MISC_UI_Y = builder.comment("(Y) Coordinate for Misc UI").define("miscY", 5);
            MISC_UI_X = builder.comment("(X) Coordinate for Misc UI").define("miscX", 4);
            SOUL_BAR_Y = builder.comment("(Y) Coordinate for Soul Bar").define("soulBarY", 5);
            SOUL_BAR_X = builder.comment("(X) Coordinate for Soul Bar").define("soulBarX", 4);
            NIHILITY_METER_X = builder.comment("(X) Coordinate for Nihility Meter").define("nihilityMeterX", 35);
            NIHILITY_METER_Y = builder.comment("(Y) Coordinate for Nihility Meter").define("nihilityMeterY", 35);
            NIHILITY_METER_ALWAYS_VISIBLE = builder.comment("Bar will be always visible, (Default: False, seen only on updating)").define("nihilityMeterAlwaysVisible", false);
            NIHILITY_METER_ANIMATE = builder.comment("Squish animation for Nihiity Meter").define("nihilityMeterAnimate", true);
            MAGMA_CHARGE_BAR_Y = builder.comment("(Y) Coordinate for Magma Bar").define("magmaBarY", 5);
            MAGMA_CHARGE_BAR_X = builder.comment("(X) Coordinate for Magma Bar").define("magmaBarX", 4);
            MAGMA_CHARGE_BAR_TYPE = builder.comment("Type of Magma Bar").defineInRange("magmaBarType", 1, 1, 3);
            RENDER_PHANTOM_ACTIVATION = builder.comment("Item activation on ability use").define("phantomActivationRendering", true);
            OLD_GOBLIN_MODEL = builder.comment("Changes goblin model to old one").comment("You will need to reload your resources to see results").define("goblinModel", false);
            CODEX_SENSITIVITY = builder.comment("Changes the drag sensitivity of Codex").define("dragSensitivity", 1.25f);
        builder.pop();
    }
}