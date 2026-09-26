package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.*;

public class AlchemyRecipe implements Recipe<ContainerRecipeInput> {
    private ResourceLocation id;

    public ResourceLocation getId(){
        return id;
    }

    public AlchemyRecipe withId(ResourceLocation id){
        this.id = id;
        return this;
    }

    private final String group;
    private final ItemStack result;
    private final List<Pair<Ingredient, RecipeData>> inputs;
    private final int level;

    public AlchemyRecipe(String group, ItemStack result, int level, List<Pair<Ingredient, RecipeData>> inputs) {
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

    public String getCategory() {
        return this.group;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider access) {
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
    public boolean matches(ContainerRecipeInput container, Level level) {
        if (level.isClientSide) return false;
        List<Pair<Ingredient, RecipeData>> required = new ArrayList<>(inputs);
        for (int slot = 0; slot < container.size(); slot++) {
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
    public ItemStack assemble(ContainerRecipeInput container, HolderLookup.Provider access) {
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
        public static final ResourceLocation ID = Valoria.loc("alchemy");

        private static final MapCodec<AlchemyRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
            RecipeCodecs.ITEM_STACK.fieldOf("result").forGetter(r -> r.result),
            Codec.INT.optionalFieldOf("level", 1).forGetter(r -> r.level),
            RecipeCodecs.COUNTED_INGREDIENT.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
        ).apply(i, AlchemyRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AlchemyRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, r -> r.group,
            ItemStack.STREAM_CODEC, r -> r.result,
            ByteBufCodecs.VAR_INT, r -> r.level,
            RecipeCodecs.COUNTED_INGREDIENT_LIST_STREAM, r -> r.inputs,
            AlchemyRecipe::new
        );

        @Override
        public MapCodec<AlchemyRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlchemyRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}