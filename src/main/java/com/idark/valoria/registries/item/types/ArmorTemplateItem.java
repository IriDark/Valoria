package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

public class ArmorTemplateItem extends Item{
    public ArmorTemplateItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public String getDescriptionId(){
        return Util.makeDescriptionId("item", new ResourceLocation(Valoria.ID, "armor_template"));
    }
}
