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

public class AlchemyUpgradeRecipe implements Recipe<ContainerRecipeInput> {
    private ResourceLocation id;
    public ResourceLocation getId(){
        return id;
    }

    public AlchemyUpgradeRecipe withId(ResourceLocation id){
        this.id = id;
        return this;
    }

    private final ItemStack result;
    private final String group;
    private final List<Pair<Ingredient, RecipeData>> inputs;

    public AlchemyUpgradeRecipe(ItemStack result, String group, List<Pair<Ingredient, RecipeData>> inputs) {
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
    public String getGroup() {
        return this.group;
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
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Type implements RecipeType<AlchemyUpgradeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy_upgrade";
    }

    public static class Serializer implements RecipeSerializer<AlchemyUpgradeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("alchemy_upgrade");

        private static final MapCodec<AlchemyUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            RecipeCodecs.ITEM_STACK.fieldOf("result").forGetter(r -> r.result),
            Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
            RecipeCodecs.COUNTED_INGREDIENT.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
        ).apply(i, AlchemyUpgradeRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AlchemyUpgradeRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, r -> r.result,
            ByteBufCodecs.STRING_UTF8, r -> r.group,
            RecipeCodecs.COUNTED_INGREDIENT_LIST_STREAM, r -> r.inputs,
            AlchemyUpgradeRecipe::new
        );

        @Override
        public MapCodec<AlchemyUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlchemyUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}