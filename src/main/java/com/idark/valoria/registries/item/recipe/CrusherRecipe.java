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
import java.util.*;

public class CrusherRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> inputs;
    private final ResourceLocation output;
    private final ResourceLocation id;

    public CrusherRecipe(ResourceLocation id, ResourceLocation output, Ingredient... inputItems) {
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems);
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputs.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
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

    public ResourceLocation getOutput() {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrusherRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CrusherRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrusherRecipe> {
        public static final CrusherRecipe.Type INSTANCE = new CrusherRecipe.Type();
        public static final String ID = "crusher";
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipe> {
        public static final CrusherRecipe.Serializer INSTANCE = new CrusherRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "crusher");

        @Override
        public CrusherRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation output = new ResourceLocation(pSerializedRecipe.get("loot_table").getAsString());

            JsonArray pIngredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : pIngredients) {
                inputs.add(Ingredient.fromJson(e));
            }

            return new CrusherRecipe(pRecipeId, output, inputs.toArray(new Ingredient[0]));
        }

        @Override
        public @Nullable CrusherRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient[] inputs = new Ingredient[pBuffer.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(pBuffer);
            }

            ResourceLocation output = pBuffer.readResourceLocation();
            return new CrusherRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrusherRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient input : pRecipe.getIngredients()) {
                input.toNetwork(pBuffer);
            }

            pBuffer.writeResourceLocation(pRecipe.output);
        }
    }
}