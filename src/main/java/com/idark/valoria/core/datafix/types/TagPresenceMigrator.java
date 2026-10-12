package com.idark.valoria.core.datafix.types;

import com.mojang.datafixers.schemas.*;

import java.util.*;

public class TagPresenceMigrator extends GlobalTagMigrator {
    public TagPresenceMigrator(Schema outputSchema, String modNamespace, String nbtTagName, String componentId) {
        super(outputSchema, modNamespace, Map.of(nbtTagName, componentId));
    }
}