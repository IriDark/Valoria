package com.idark.valoria.api.unlockable.types;

import com.idark.valoria.api.unlockable.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class DimensionUnlockable extends Unlockable implements OnDimensionChangeListener{
    public final ResourceKey<Level> dim;
    public DimensionUnlockable(String id, Item item, ResourceKey<Level> dim){
        super(item, id);
        this.dim = dim;
    }

    public DimensionUnlockable(String id, boolean rndObtain, Item item, ResourceKey<Level> dim){
        super(item, id, rndObtain);
        this.dim = dim;
    }

    public void checkCondition(ServerPlayer player, ResourceKey<Level> dim){
        if(dim.equals(this.dim)) {
            UnlockUtils.add(player, this);
        }
    }
}