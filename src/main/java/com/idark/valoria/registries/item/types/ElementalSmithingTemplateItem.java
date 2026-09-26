package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

public class ElementalSmithingTemplateItem extends Item{
    public ElementalSmithingTemplateItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public String getDescriptionId(){
        return Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template"));
    }
}
