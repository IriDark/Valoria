package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.levelgen.structure.*;

public class DungeonUnlockable extends Unlockable implements OnDungeonVisitListener{
    public ResourceKey<Structure> structureKey;
    public DungeonUnlockable(String id, Item item, ResourceKey<Structure> structureKey){
        super(item, id);
        this.structureKey = structureKey;
    }

    public DungeonUnlockable(String id, boolean rndObtain, Item item, ResourceKey<Structure> structureKey){
        super(item, id, rndObtain);
        this.structureKey = structureKey;
    }

    public void checkCondition(ServerPlayer player, ServerLevel serverLevel){
        if(isPlayerInStructure(player, serverLevel, structureKey)) {
            UnlockUtils.add(player, this);
        }
    }
}