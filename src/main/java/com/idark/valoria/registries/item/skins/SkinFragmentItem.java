package com.idark.valoria.registries.item.skins;

import com.idark.valoria.Valoria;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class SkinFragmentItem extends SkinTrimItem{
    public ItemSkin skin;
    public Supplier<Item> item;
    public Class<?> itemClass;

    public SkinFragmentItem(ItemSkin skin, Properties properties, Supplier<Item> item){
        super(skin, properties);
        this.item = item;
    }

    public SkinFragmentItem(ItemSkin skin, Properties properties, Class<?> item){
        super(skin, properties);
        this.itemClass = item;
    }

    public boolean canApply(ItemStack stack){
        return itemClass != null ? this.itemClass.isInstance(stack.getItem()) : stack.is(item.get());
    }

    public String getDescriptionId(){
        return Util.makeDescriptionId("item", new ResourceLocation(Valoria.ID, "skin_fragment"));
    }
}
