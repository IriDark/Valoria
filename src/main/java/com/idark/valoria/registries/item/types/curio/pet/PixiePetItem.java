package com.idark.valoria.registries.item.types.curio.pet;

import com.idark.valoria.*;
import net.minecraft.resources.*;

public class PixiePetItem extends PetItem{
    public PixiePetItem(Properties properties){
        super(properties);
    }

    @Override
    public ResourceLocation modelPath(){
        return Valoria.loc("curio/pixie_pet");
    }
}
