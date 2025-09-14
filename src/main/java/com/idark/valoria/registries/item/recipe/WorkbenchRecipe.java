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

public class WorkbenchRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack result;
    private final List<Pair<Ingredient, RecipeData>> inputs;

    public WorkbenchRecipe(ResourceLocation id, String group, ItemStack result, List<Pair<Ingredient, RecipeData>> inputs) {
        this.id = id;
        this.group = group;
        this.result = result;
        this.inputs = inputs;
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

    public static class RecipeData {
        public final int count;
        public int current;
        public boolean isEnough;
        public RecipeData(int count, int current, boolean isEnough) {
            this.count = count;
            this.current = current;
            this.isEnough = isEnough;
        }

        public RecipeData(int count) {
            this.count = count;
            this.current = 0;
            this.isEnough = false;
        }

        public void setCurrent(int value) {
            current = value;
        }

        public void setEnough(boolean value) {
            isEnough = value;
        }
    }

    public static class Type implements RecipeType<WorkbenchRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "heavy_workbench";
    }

    public static class Serializer implements RecipeSerializer<WorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "heavy_workbench");

        @Override
        public WorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
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

            return new WorkbenchRecipe(recipeId, group, result, inputs);
        }

        @Override
        public WorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            ItemStack result = buffer.readItem();

            int size = buffer.readVarInt();
            List<Pair<Ingredient, RecipeData>> inputs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Ingredient ing = Ingredient.fromNetwork(buffer);
                int count = buffer.readVarInt();
                inputs.add(Pair.of(ing, new RecipeData(count)));
            }

            return new WorkbenchRecipe(recipeId, group, result, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WorkbenchRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeItem(recipe.result);

            buffer.writeVarInt(recipe.inputs.size());
            for (Pair<Ingredient, RecipeData> entry : recipe.inputs) {
                entry.getFirst().toNetwork(buffer);
                buffer.writeVarInt(entry.getSecond().count);
            }
        }
    }
}