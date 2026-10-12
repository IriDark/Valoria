package com.idark.valoria.core.datafix;

import com.idark.valoria.*;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.*;
import com.mojang.serialization.*;
import net.minecraft.util.datafix.schemas.*;

import java.util.*;

public abstract class SimplifiedDataFix extends DataFix{
    public SimplifiedDataFix(Schema outputSchema, boolean changesType){
        super(outputSchema, changesType);
    }

    protected <T> Dynamic<T> transformTypedMap(Dynamic<T> item, Map<String, String> migrationMap){
        Dynamic<T> components = item.get("components").orElseEmptyMap();
        Optional<Dynamic<T>> customDataOpt = components.get("minecraft:custom_data").result();
        if(customDataOpt.isEmpty()) return item;

        Dynamic<T> currentSource = customDataOpt.get();
        boolean changed = false;
        for (Map.Entry<String, String> entry : migrationMap.entrySet()) {
            String oldKey = entry.getKey();
            String compId = NamespacedSchema.ensureNamespaced(entry.getValue());
            var valOpt = currentSource.get(oldKey).result();
            if (valOpt.isPresent()) {
                components = components.set(compId, valOpt.get());
                currentSource = currentSource.remove(oldKey);
                changed = true;
            }
        }

        if(!changed){
            return item;
        }

        return migrateIfExists(item, components, currentSource);
    }


    public <T> Dynamic<T> migrateIfExists(Dynamic<T> item, Dynamic<T> components, Dynamic<T> updatedSource){
        boolean isEmpty = updatedSource.asMapOpt().result().map(stream -> stream.findAny().isEmpty()).orElse(true);
        Dynamic<T> finalComponents = isEmpty ? components.remove("minecraft:custom_data") : components.set("minecraft:custom_data", updatedSource);

        Dynamic<T> result = item.set("components", finalComponents);
        Valoria.LOGGER.debug("Applied data fixer to {}", result);
        return result;
    }
}