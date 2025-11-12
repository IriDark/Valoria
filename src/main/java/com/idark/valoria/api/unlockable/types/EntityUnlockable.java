package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class EntityUnlockable extends Unlockable implements OnMobKilledListener{
    public final EntityType<?> type;
    public EntityUnlockable(String id, Item item, EntityType<?> type){
        super(item, id);
        this.type = type;
    }

    public EntityUnlockable(String id, boolean rndObtain, Item item, EntityType<?> type){
        super(item, id, rndObtain);
        this.type = type;
    }

    public void checkCondition(ServerPlayer player, LivingEntity entity){
        if(entity.getType().equals(this.type)) {
            UnlockUtils.add(player, this);
        }
    }
}