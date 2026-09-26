package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class TinkeringRecipe implements Recipe<ContainerRecipeInput>{
    protected final Ingredient ingredient;
    protected final int ingredientCount;
    protected final ItemStack result;
    protected final String group;

    public TinkeringRecipe(String pGroup, Ingredient pIngredient, int pIngredientCount, ItemStack pResult){
        this.group = pGroup;
        this.ingredient = pIngredient;
        this.ingredientCount = pIngredientCount;
        this.result = pResult;
    }

    public RecipeType<?> getType(){
        return TinkeringRecipe.Type.INSTANCE;
    }

    public RecipeSerializer<?> getSerializer(){
        return Serializer.INSTANCE;
    }

    public int getIngredientCount(){
        return ingredientCount;
    }

    public String getGroup(){
        return this.group;
    }

    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess){
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    public boolean canCraft(ContainerRecipeInput pContainer){
        ItemStack stackInSlot = pContainer.getItem(0);
        return this.ingredient.test(stackInSlot) && stackInSlot.getCount() >= ingredientCount;
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        ItemStack stackInSlot = pContainer.getItem(0);
        return this.ingredient.test(stackInSlot);
    }

    public boolean isSpecial(){
        return true;
    }

    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess){
        ItemStack stackInSlot = pContainer.getItem(0);
        if(stackInSlot.getCount() >= ingredientCount){
            return this.result.copy();
        }

        return ItemStack.EMPTY;
    }

    public static class Type implements RecipeType<TinkeringRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "tinkering";
    }

    public static class Serializer implements RecipeSerializer<TinkeringRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("tinkering");

        private static final MapCodec<TinkeringRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(r -> r.ingredient),
            Codec.INT.optionalFieldOf("ingredient_count", 1).forGetter(r -> r.ingredientCount),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("result").forGetter(r -> r.result.getItem()),
            Codec.INT.fieldOf("count").forGetter(r -> r.result.getCount())
        ).apply(i, (group, ingredient, ingredientCount, item, count) -> new TinkeringRecipe(group, ingredient, ingredientCount, new ItemStack(item, count))));

        private static final StreamCodec<RegistryFriendlyByteBuf, TinkeringRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, r -> r.group,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredient,
            ByteBufCodecs.VAR_INT, r -> r.ingredientCount,
            ItemStack.STREAM_CODEC, r -> r.result,
            TinkeringRecipe::new
        );

        @Override
        public MapCodec<TinkeringRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TinkeringRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}
