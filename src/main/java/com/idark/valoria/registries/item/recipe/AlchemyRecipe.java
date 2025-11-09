package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import com.mojang.datafixers.util.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.*;

public class AlchemyRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack result;
    private final List<Pair<Ingredient, RecipeData>> inputs;
    private final int level;

    public AlchemyRecipe(ResourceLocation id, String group, ItemStack result, int level, List<Pair<Ingredient, RecipeData>> inputs) {
        this.id = id;
        this.group = group;
        this.result = result;
        this.inputs = inputs;
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result.copy();
    }

    public List<Pair<Ingredient, RecipeData>> getInputs(){
        return inputs;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        for (Pair<Ingredient, RecipeData> entry : inputs) {
            for (int i = 0; i < entry.getSecond().count; i++) {
                list.add(entry.getFirst());
            }
        }
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (level.isClientSide) return false;
        List<Pair<Ingredient, RecipeData>> required = new ArrayList<>(inputs);
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty()) {
                for (Pair<Ingredient, RecipeData> req : required) {
                    if (req.getFirst().test(stack)) {
                        int needed = req.getSecond().count;
                        if (stack.getCount() >= needed) {
                            required.remove(req);
                        }
                        break;
                    }
                }
            }
        }

        return required.isEmpty();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Type implements RecipeType<AlchemyRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy";
    }

    public static class Serializer implements RecipeSerializer<AlchemyRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "alchemy");

        @Override
        public AlchemyRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            int level = GsonHelper.getAsInt(json, "level", 1);

            JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
            ItemStack result = ShapedRecipe.itemStackFromJson(resultObj);
            List<Pair<Ingredient, RecipeData>> inputs = new ArrayList<>();
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            for (JsonElement elem : ingredients) {
                JsonObject obj = elem.getAsJsonObject();
                Ingredient ing = Ingredient.fromJson(obj.get("ingredient"));
                int count = GsonHelper.getAsInt(obj, "count", 1);
                inputs.add(Pair.of(ing, new RecipeData(count)));
            }

            return new AlchemyRecipe(recipeId, group, result, level, inputs);
        }

        @Override
        public AlchemyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            int level = buffer.readVarInt();

            ItemStack result = buffer.readItem();
            int size = buffer.readVarInt();
            List<Pair<Ingredient, RecipeData>> inputs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Ingredient ing = Ingredient.fromNetwork(buffer);
                int count = buffer.readVarInt();
                inputs.add(Pair.of(ing, new RecipeData(count)));
            }

            return new AlchemyRecipe(recipeId, group, result, level, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeInt(recipe.level);
            buffer.writeItem(recipe.result);
            buffer.writeVarInt(recipe.inputs.size());
            for (Pair<Ingredient, RecipeData> entry : recipe.inputs) {
                entry.getFirst().toNetwork(buffer);
                buffer.writeVarInt(entry.getSecond().count);
            }
        }
    }
}