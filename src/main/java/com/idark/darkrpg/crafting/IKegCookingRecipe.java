package com.idark.darkrpg.crafting;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IKegCookingRecipe extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(DarkRPG.MOD_ID, "keg_cooking");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic(){
        return true;
    }
}