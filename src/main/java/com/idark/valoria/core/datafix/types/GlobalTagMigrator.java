package com.idark.valoria.core.datafix.types;

import com.idark.valoria.core.datafix.*;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.*;
import com.mojang.datafixers.types.*;
import net.minecraft.util.datafix.fixes.*;
import net.minecraft.util.datafix.schemas.*;

import java.util.*;

public class GlobalTagMigrator extends SimplifiedDataFix{
    private final String modNamespace;
    private final Map<String, String> migrationMap;

    public GlobalTagMigrator(Schema outputSchema, String modNamespace, Map<String, String> migrationMap) {
        super(outputSchema, false);
        this.modNamespace = modNamespace + ":";
        this.migrationMap = migrationMap;
    }

    @Override
    protected TypeRewriteRule makeRule(){
        Type<?> itemType = this.getInputSchema().getType(References.ITEM_STACK);
        Type<?> newItemType = this.getOutputSchema().getType(References.ITEM_STACK);
        return this.writeFixAndRead("GlobalTagMigrator_" + this.modNamespace, itemType, newItemType, dynamic -> {
            String id = dynamic.get("id").asString().result()
            .map(NamespacedSchema::ensureNamespaced)
            .orElse("");

            if(!id.startsWith(this.modNamespace)){
                return dynamic;
            }

            return this.transformTypedMap(dynamic, this.migrationMap);
        });
    }
}