package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.type.capability.*;

public class DyeableGlovesItem extends GlovesItem implements ICurioItem, ICurioTexture, DyeableItem{
    public DyeableGlovesItem(DyeableBuilder builder){
        super(builder);
    }

    public static class DyeableBuilder extends GlovesBuilder{

        public DyeableBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        @Override
        public DyeableGlovesItem build(){
            return new DyeableGlovesItem(this);
        }
    }
}