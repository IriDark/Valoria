package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.common.registry.item.skins.*;

import java.util.function.*;

public class SkinFragmentItem extends SkinTrimItem{
    public ItemSkin skin;
    public Supplier<Item> item;
    public Class<?> itemClass;

    public SkinFragmentItem(ItemSkin skin, Properties properties, Supplier<Item> item){
        super(skin, properties);
        this.item = item;
    }

    public boolean canApply(ItemStack stack){
        return itemClass != null ? this.itemClass.isInstance(stack.getItem()) : stack.is(item.get());
    }

    public String getDescriptionId(){
        return Util.makeDescriptionId("item", new ResourceLocation(Valoria.ID, "skin_fragment"));
    }
}
