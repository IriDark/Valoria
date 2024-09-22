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
    private final Ingredient ingredient;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int time;
    public KegRecipe(Ingredient inputItems, ItemStack output, ResourceLocation id, int time) {
        this.ingredient = inputItems;
        this.output = output;
        this.id = id;
        this.time = time;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return ingredient.test(pContainer.getItem(0));
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
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
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
        private static Ingredient readIngredients(JsonArray ingredientArray) {
            return Ingredient.fromJson(ingredientArray.get(0));
        }

        @Override
        public KegRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
            final Ingredient inputs = readIngredients(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
            return new KegRecipe(inputs, output, pRecipeId, time);
        }

        @Override
        public @Nullable KegRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int time = pBuffer.readInt();
            ItemStack output = pBuffer.readItem();
            return new KegRecipe(Ingredient.fromNetwork(pBuffer), output, pRecipeId, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, KegRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.getTime());
            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}