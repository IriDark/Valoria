package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class HeavyAnvilRecipe implements Recipe<ContainerRecipeInput> {
    private ResourceLocation id;
    private final Ingredient input;
    private final ItemStack result;
    private final int requiredHits;
    private final int maxInstability;
    private final float cursorSpeed;

    public HeavyAnvilRecipe(Ingredient input, ItemStack result, int requiredHits, int maxInstability, float cursorSpeed) {
        this.input = input;
        this.result = result;
        this.requiredHits = requiredHits;
        this.maxInstability = maxInstability;
        this.cursorSpeed = cursorSpeed;
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        if (pLevel.isClientSide()) {
            return false;
        }
        return input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input);
    }

    public HeavyAnvilRecipe withId(ResourceLocation id){
        this.id = id;
        return this;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int getRequiredHits() {
        return this.requiredHits;
    }

    public int getMaxInstability() {
        return this.maxInstability;
    }
    
    public float getCursorSpeed() {
        return this.cursorSpeed;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<HeavyAnvilRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "heavy_anvil";
    }

    public static class Serializer implements RecipeSerializer<HeavyAnvilRecipe>{
        public static final HeavyAnvilRecipe.Serializer INSTANCE = new HeavyAnvilRecipe.Serializer();
        public static final ResourceLocation ID = Valoria.loc("heavy_anvil");

        private static final MapCodec<HeavyAnvilRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.input),
        ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
        Codec.INT.optionalFieldOf("required_hits", 10).forGetter(r -> r.requiredHits),
        Codec.INT.optionalFieldOf("max_instability", 3).forGetter(r -> r.maxInstability),
        Codec.FLOAT.optionalFieldOf("cursor_speed", 0.05f).forGetter(r -> r.cursorSpeed)
        ).apply(i, HeavyAnvilRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, HeavyAnvilRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
        ItemStack.STREAM_CODEC, r -> r.result,
        ByteBufCodecs.VAR_INT, r -> r.requiredHits,
        ByteBufCodecs.VAR_INT, r -> r.maxInstability,
        ByteBufCodecs.FLOAT, r -> r.cursorSpeed,
        HeavyAnvilRecipe::new
        );

        @Override
        public MapCodec<HeavyAnvilRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HeavyAnvilRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}