package com.idark.valoria.core.datafix.types;

import com.mojang.datafixers.schemas.*;
import java.util.*;

public class ItemNBTMigrator extends ItemNBTMapMigrator{
    public ItemNBTMigrator(Schema outputSchema, String targetItemId, String nbtTagName, String componentId) {
        super(outputSchema, targetItemId, Map.of(nbtTagName, componentId));
    }
}