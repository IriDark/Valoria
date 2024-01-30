package com.idark.valoria.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.idark.valoria.Valoria;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class JewelryRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;

    public JewelryRecipe(ResourceLocation id, ItemStack output, Ingredient... inputItems) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems);
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        boolean craft = true;
        for (int i = 0; i < 2; i += 1) {
            if (!inputs.get(i).test(pContainer.getItem(i))) {
                craft = false;
            }
        }

        return craft;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output;
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

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<JewelryRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "jewelry";
    }

    public static class Serializer implements RecipeSerializer<JewelryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.MOD_ID, "jewelry");

        @Override
        public JewelryRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray pIngredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : pIngredients) {
                inputs.add(Ingredient.fromJson(e));
            }

            return new JewelryRecipe(pRecipeId, output, inputs.toArray(new Ingredient[0]));
        }

        @Override
        public @Nullable JewelryRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient[] inputs = new Ingredient[pBuffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(pBuffer);
            }

            ItemStack output = pBuffer.readItem();
            return new JewelryRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, JewelryRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient input : pRecipe.getIngredients()) {
                input.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}