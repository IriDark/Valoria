package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;

public class KilnRecipe extends AbstractCookingRecipe{
    public KilnRecipe(Ingredient pIngredient, ItemStack pResult, ResourceLocation id, float pExperience, int cookingTime){
        super(Type.INSTANCE, id, "kiln", CookingBookCategory.MISC, pIngredient, pResult, pExperience, cookingTime);
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

    public static class Serializer implements RecipeSerializer<KilnRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "kiln");

        @Override
        public KilnRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe){
            JsonElement jsonelement = GsonHelper.isArrayNode(pSerializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonelement, false);
            if (!pSerializedRecipe.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack itemstack;
            if (pSerializedRecipe.get("result").isJsonObject()) itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            else {
                String s1 = GsonHelper.getAsString(pSerializedRecipe, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                itemstack = new ItemStack(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
            }

            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0.0F);
            int time = GsonHelper.getAsInt(pSerializedRecipe, "cookingtime", 120);
            return new KilnRecipe(ingredient, itemstack, pRecipeId, experience, time);
        }

        @Override
        public @Nullable KilnRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer){
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            float experience = pBuffer.readFloat();
            int time = pBuffer.readVarInt();
            return new KilnRecipe(ingredient, itemstack, pRecipeId, experience, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, KilnRecipe pRecipe){
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeFloat(pRecipe.experience);
            pBuffer.writeVarInt(pRecipe.cookingTime);
        }
    }
}