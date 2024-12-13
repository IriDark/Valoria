package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class ArchaeologyRecipe implements Recipe<Container> {
    protected final Ingredient ingredient;
    protected final int ingredientCount;
    protected final ItemStack result;
    protected final ResourceLocation id;
    protected final String group;

    public ArchaeologyRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, int pIngredientCount, ItemStack pResult) {
        this.id = pId;
        this.group = pGroup;
        this.ingredient = pIngredient;
        this.ingredientCount = pIngredientCount;
        this.result = pResult;
    }

    public RecipeType<?> getType() {
        return ArchaeologyRecipe.Type.INSTANCE;
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getGroup() {
        return this.group;
    }

    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public boolean canCraft(Container pContainer) {
        ItemStack stackInSlot = pContainer.getItem(0);
        return this.ingredient.test(stackInSlot) && stackInSlot.getCount() >= ingredientCount;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack stackInSlot = pContainer.getItem(0);
        return this.ingredient.test(stackInSlot);
    }

    public boolean isSpecial() {
        return true;
    }

    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        ItemStack stackInSlot = pContainer.getItem(0);
        if(stackInSlot.getCount() >= ingredientCount){
            return this.result.copy();
        }

        return ItemStack.EMPTY;
    }

    public static class Type implements RecipeType<ArchaeologyRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "archaeology";
    }

    public static class Serializer implements RecipeSerializer<ArchaeologyRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "archaeology");
        public ArchaeologyRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pJson, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pJson, "ingredient"), false);
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"), false);
            }

            int ingredientCount = GsonHelper.getAsInt(pJson, "ingredient_count", 1);
            String s1 = GsonHelper.getAsString(pJson, "result");
            int i = GsonHelper.getAsInt(pJson, "count");
            ItemStack itemstack = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(s1)), i);
            return new ArchaeologyRecipe(pRecipeId, s, ingredient, ingredientCount, itemstack);
        }

        public ArchaeologyRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int ingredientCount = pBuffer.readVarInt();
            ItemStack itemstack = pBuffer.readItem();
            return new ArchaeologyRecipe(pRecipeId, s, ingredient, ingredientCount, itemstack);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ArchaeologyRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeVarInt(pRecipe.ingredientCount);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
