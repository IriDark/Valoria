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

public class JewelryRecipe implements Recipe<ContainerRecipeInput>{
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final int time;

    public JewelryRecipe(ItemStack output, int time, List<Ingredient> inputItems){
        this.output = output;
        this.time = time;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
    }

    public JewelryRecipe(ItemStack output, int time, Ingredient... inputItems){
        this(output, time, Arrays.asList(inputItems));
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        boolean craft = true;
        for(int i = 0; i < 2; i += 1){
            if(!inputs.get(i).test(pContainer.getItem(i))){
                craft = false;
            }
        }

        return craft;
    }

    public boolean isSpecial(){
        return true;
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess){
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess){
        return output.copy();
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

    public static class Type implements RecipeType<JewelryRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "jewelry";
    }

    public static class Serializer implements RecipeSerializer<JewelryRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("jewelry");

        private static final MapCodec<JewelryRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            RecipeCodecs.ITEM_STACK.fieldOf("output").forGetter(r -> r.output),
            Codec.INT.fieldOf("time").forGetter(r -> r.time),
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
        ).apply(i, JewelryRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, JewelryRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, r -> r.output,
            ByteBufCodecs.VAR_INT, r -> r.time,
            RecipeCodecs.INGREDIENT_LIST_STREAM, r -> r.inputs,
            JewelryRecipe::new
        );

        @Override
        public MapCodec<JewelryRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, JewelryRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}
