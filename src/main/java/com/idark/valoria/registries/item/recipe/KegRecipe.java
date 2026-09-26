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

public class KegRecipe implements Recipe<ContainerRecipeInput>{
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final int time;

    public KegRecipe(List<Ingredient> inputItems, ItemStack output, int time){
        NonNullList<Ingredient> list = NonNullList.create();
        for(Ingredient ingredient : inputItems){
            if(!ingredient.isEmpty()) list.add(ingredient);
        }

        this.inputs = list;
        this.output = output;
        this.time = time;
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        if(inputs.size() >= 2){
            return inputs.get(0).test(pContainer.getItem(0)) && inputs.get(1).test(pContainer.getItem(1));
        }
        return inputs.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess){
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess){
        return output.copy();
    }

    public boolean isSpecial(){
        return true;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return inputs;
    }

    public int getTime(){
        return time;
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType(){
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<KegRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "keg_brewery";
    }

    public static class Serializer implements RecipeSerializer<KegRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("keg_brewery");

        private static final MapCodec<KegRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.inputs),
            RecipeCodecs.ITEM_STACK.fieldOf("output").forGetter(r -> r.output),
            Codec.INT.fieldOf("time").forGetter(r -> r.time)
        ).apply(i, KegRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, KegRecipe> STREAM_CODEC = StreamCodec.composite(
            RecipeCodecs.INGREDIENT_LIST_STREAM, r -> r.inputs,
            ItemStack.STREAM_CODEC, r -> r.output,
            ByteBufCodecs.VAR_INT, r -> r.time,
            KegRecipe::new
        );

        @Override
        public MapCodec<KegRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KegRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}
