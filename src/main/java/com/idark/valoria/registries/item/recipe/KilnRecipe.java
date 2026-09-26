package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

public class KilnRecipe extends AbstractCookingRecipe{
    private ResourceLocation id;
    public ResourceLocation getId(){
        return id;
    }

    public KilnRecipe withId(ResourceLocation id){
        this.id = id;
        return this;
    }

    public KilnRecipe(String group, CookingBookCategory category, Ingredient pIngredient, ItemStack pResult, float pExperience, int cookingTime){
        super(Type.INSTANCE, group, category, pIngredient, pResult, pExperience, cookingTime);
    }

    public KilnRecipe(Ingredient pIngredient, ItemStack pResult, float pExperience, int cookingTime){
        this("kiln", CookingBookCategory.MISC, pIngredient, pResult, pExperience, cookingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType(){
        return Type.INSTANCE;
    }

    @Override
    public boolean isSpecial(){
        return true;
    }

    public static class Type implements RecipeType<KilnRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "kiln";
    }

    public static class Serializer extends SimpleCookingSerializer<KilnRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("kiln");

        public Serializer(){
            super(KilnRecipe::new, 120);
        }
    }
}
