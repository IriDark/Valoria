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

import javax.annotation.*;
import java.util.*;

public class CrusherRecipe implements Recipe<ContainerRecipeInput>{
    private ResourceLocation id;

    public ResourceLocation getId(){
        return id;
    }

    public CrusherRecipe withId(ResourceLocation id){
        this.id = id;
        return this;
    }

    private final NonNullList<Ingredient> inputs;
    private final ResourceLocation output;

    public CrusherRecipe(ResourceLocation output, List<Ingredient> inputItems){
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
    }

    public CrusherRecipe(ResourceLocation output, Ingredient... inputItems){
        this(output, Arrays.asList(inputItems));
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        if(pLevel.isClientSide()){
            return false;
        }

        return inputs.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess){
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    public boolean isSpecial(){
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess){
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return inputs;
    }

    public ResourceLocation getOutput(){
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return CrusherRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType(){
        return CrusherRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrusherRecipe>{
        public static final CrusherRecipe.Type INSTANCE = new CrusherRecipe.Type();
        public static final String ID = "crusher";
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipe>{
        public static final CrusherRecipe.Serializer INSTANCE = new CrusherRecipe.Serializer();
        public static final ResourceLocation ID = Valoria.loc("crusher");

        private static final MapCodec<CrusherRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceLocation.CODEC.fieldOf("loot_table").forGetter(r -> r.output),
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
        ).apply(i, CrusherRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, r -> r.output,
            RecipeCodecs.INGREDIENT_LIST_STREAM, r -> r.inputs,
            CrusherRecipe::new
        );

        @Override
        public MapCodec<CrusherRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}