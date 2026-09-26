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
import net.neoforged.neoforge.items.*;

import javax.annotation.*;
import java.util.*;

public class ManipulatorRecipe implements Recipe<ContainerRecipeInput>{
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final String pCoreId;
    private final int cores;
    private final int time;

    public ManipulatorRecipe(ItemStack output, String pCoreId, int cores, int time, List<Ingredient> inputItems){
        this.output = output;
        this.pCoreId = pCoreId;
        this.cores = cores;
        this.time = time;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems.toArray(new Ingredient[0]));
    }

    public ManipulatorRecipe(ItemStack output, String pCoreId, int cores, int time, Ingredient... inputItems){
        this(output, pCoreId, cores, time, Arrays.asList(inputItems));
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

    public int getTime(){
        return time;
    }

    public int getCoresNeeded(){
        return cores;
    }

    public ItemStack assemble(IItemHandler itemHandler){
        ItemStack itemstack = this.output.copy();
        itemstack.applyComponents(itemHandler.getStackInSlot(0).getComponentsPatch());
        return itemstack;
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput pContainer, HolderLookup.Provider pRegistryAccess){
        ItemStack itemstack = this.output.copy();
        itemstack.applyComponents(pContainer.getItem(1).getComponentsPatch());
        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess){
        return output;
    }

    public String getCore(){
        return pCoreId;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return inputs;
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return ManipulatorRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType(){
        return ManipulatorRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<ManipulatorRecipe>{
        public static final ManipulatorRecipe.Type INSTANCE = new ManipulatorRecipe.Type();
        public static final String ID = "manipulator";
    }

    public static class Serializer implements RecipeSerializer<ManipulatorRecipe>{
        public static final ManipulatorRecipe.Serializer INSTANCE = new ManipulatorRecipe.Serializer();
        public static final ResourceLocation ID = Valoria.loc("manipulator");

        private static final MapCodec<ManipulatorRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            RecipeCodecs.ITEM_STACK.fieldOf("output").forGetter(r -> r.output),
            Codec.STRING.optionalFieldOf("core", "empty").forGetter(r -> r.pCoreId),
            Codec.INT.optionalFieldOf("cores", 0).forGetter(r -> r.cores),
            Codec.INT.fieldOf("time").forGetter(r -> r.time),
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
        ).apply(i, ManipulatorRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, ManipulatorRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, r -> r.output,
            ByteBufCodecs.STRING_UTF8, r -> r.pCoreId,
            ByteBufCodecs.VAR_INT, r -> r.cores,
            ByteBufCodecs.VAR_INT, r -> r.time,
            RecipeCodecs.INGREDIENT_LIST_STREAM, r -> r.inputs,
            ManipulatorRecipe::new
        );

        @Override
        public MapCodec<ManipulatorRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ManipulatorRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}
