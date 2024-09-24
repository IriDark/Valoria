package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;

import javax.annotation.*;

public class KegRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int time;
    public KegRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, int time) {
        this.inputs = inputItems;
        this.output = output;
        this.id = id;
        this.time = time;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return inputs.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<KegRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "keg_brewery";
    }

    public static class Serializer implements RecipeSerializer<KegRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "keg_brewery");
        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public KegRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            final NonNullList<Ingredient> inputs = readIngredients(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new KegRecipe(inputs, output, pRecipeId, time);
        }

        @Override
        public @Nullable KegRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.toArray().length; i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            int time = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new KegRecipe(inputs, output, pRecipeId, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, KegRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputs.size());
            for (Ingredient input : pRecipe.getIngredients()) {
                input.toNetwork(pBuffer);
            }

            pBuffer.writeInt(pRecipe.getTime());
            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}