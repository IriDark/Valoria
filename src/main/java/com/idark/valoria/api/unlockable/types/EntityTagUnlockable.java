package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class EntityTagUnlockable extends Unlockable implements OnMobKilledListener{
    public final TagKey<EntityType<?>> type;
    public EntityTagUnlockable(String id, Item item, TagKey<EntityType<?>> tag){
        super(item, id, false);
        this.type = tag;
    }

    public EntityTagUnlockable(String id, boolean rndObtain, Item item, TagKey<EntityType<?>> tag){
        super(item, id, rndObtain);
        this.type = tag;
    }

    public void checkCondition(ServerPlayer player, LivingEntity entity){
        if(entity.getType().is(this.type)) {
            UnlockUtils.add(player, this);
        }
    }
}