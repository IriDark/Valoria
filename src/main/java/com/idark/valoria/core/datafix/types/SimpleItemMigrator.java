package com.idark.valoria.core.datafix.types;

import com.idark.valoria.core.datafix.*;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.*;
import com.mojang.datafixers.types.*;
import com.mojang.serialization.*;
import net.minecraft.util.datafix.fixes.*;
import net.minecraft.util.datafix.schemas.*;

public abstract class SimpleItemMigrator extends SimplifiedDataFix{
    private final String targetItemId;

    public SimpleItemMigrator(Schema outputSchema, String targetItemId) {
        super(outputSchema, false);
        this.targetItemId = NamespacedSchema.ensureNamespaced(targetItemId);
    }

    @Override
    protected TypeRewriteRule makeRule(){
        Type<?> itemType = this.getInputSchema().getType(References.ITEM_STACK);
        Type<?> newItemType = this.getOutputSchema().getType(References.ITEM_STACK);
        return this.writeFixAndRead("Fix_" + this.targetItemId, itemType, newItemType, dynamic -> {
            String id = dynamic.get("id").asString().result().orElse("");
            if(!id.equals(this.targetItemId)){
                return dynamic;
            }

            return transformItemNbt(dynamic);
        });
    }

    protected Dynamic<?> transformItemNbt(Dynamic<?> item) {
        return this.applyTransform(item);
    }

    private <T> Dynamic<T> applyTransform(Dynamic<T> item) {
        return this.transformTyped(item);
    }

    protected abstract <T> Dynamic<T> transformTyped(Dynamic<T> item);
}