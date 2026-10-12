package com.idark.valoria.core.datafix;

import com.idark.valoria.core.datafix.types.*;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.*;
import net.minecraft.util.datafix.schemas.*;

import java.util.*;

public class ValoriaDataFixers {
    public static final int VALORIA_SCHEMA_VERSION = 3955;

    public static void register(DataFixerBuilder builder) {
        Schema schema = builder.addSchema(VALORIA_SCHEMA_VERSION, 1, NamespacedSchema::new);

        builder.addFixer(new GlobalTagMigrator(schema, "valoria", Map.ofEntries(
        Map.entry("Souls", "valoria:souls"),
        Map.entry("charge", "valoria:charge"),
        Map.entry("usageCount", "valoria:usage_count"),
        Map.entry("poison_hits", "valoria:poison_hits"),
        Map.entry("ValoriaRot", "valoria:rot"),
        Map.entry("EyeState", "valoria:eye_state"),
        Map.entry("DisplayColor", "valoria:display_color"),
        Map.entry("IsNightActive", "valoria:night_active"),
        Map.entry("IsDarkActive", "valoria:dark_active"),
        Map.entry("ToggleState", "valoria:toggle_state"),
        Map.entry("isVisible", "valoria:bar_visible"),
        Map.entry("isPlayed", "valoria:sound_played"),
        Map.entry("OriginalItem", "valoria:original_item")
        )));
    }
}