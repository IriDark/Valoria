package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.levelgen.structure.*;

public class DungeonTagUnlockable extends Unlockable implements OnDungeonVisitListener{
    public TagKey<Structure> structureKey;
    public DungeonTagUnlockable(String id, Item item, TagKey<Structure> structureKey){
        super(item, id);
        this.structureKey = structureKey;
    }

    public DungeonTagUnlockable(String id, boolean rndObtain, Item item, TagKey<Structure> structureKey){
        super(item, id, rndObtain);
        this.structureKey = structureKey;
    }

    public void checkCondition(ServerPlayer player, ServerLevel serverLevel){
        if(isPlayerInStructure(player, serverLevel, structureKey)) {
            UnlockUtils.add(player, this);
        }
    }
}