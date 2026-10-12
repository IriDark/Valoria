package com.idark.valoria.core.datafix.types;

import com.mojang.datafixers.schemas.*;
import com.mojang.serialization.*;

import java.util.*;

public class ItemNBTMapMigrator extends SimpleItemMigrator{
    private final Map<String, String> migrationMap;

    public ItemNBTMapMigrator(Schema outputSchema, String targetItemId, Map<String, String> migrationMap) {
        super(outputSchema, targetItemId);
        this.migrationMap = migrationMap;
    }

    @Override
    protected <T> Dynamic<T> transformTyped(Dynamic<T> item) {
        return transformTypedMap(item, migrationMap);
    }
}