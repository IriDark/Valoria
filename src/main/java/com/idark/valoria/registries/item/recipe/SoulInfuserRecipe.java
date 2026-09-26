package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.types.*;
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
import org.jetbrains.annotations.*;

import javax.annotation.*;

public class SoulInfuserRecipe implements Recipe<ContainerRecipeInput>{
    private final ItemStack ingredientStack;
    private final Ingredient input;
    private final ItemStack output;
    private final int souls;
    private final int time;

    public SoulInfuserRecipe(ItemStack output, int time, ItemStack ingredientStack){
        this.output = output;
        this.time = time;
        this.ingredientStack = ingredientStack;
        this.input = Ingredient.of(ingredientStack);
        this.souls = ingredientStack.getItem() instanceof ISoulItem soulItem ? soulItem.getMaxSouls() - soulItem.getCurrentSouls(ingredientStack) : 0;
    }

    @Override
    public boolean matches(ContainerRecipeInput pContainer, Level pLevel){
        return input.test(pContainer.getItem(0)) && pContainer.getItem(1).getItem() instanceof SoulCollectorItem;
    }

    public boolean isSpecial(){
        return true;
    }

    public int getTime(){
        return time;
    }

    public int getSouls(ItemStack itemstack) {
        return itemstack.getItem() instanceof ISoulItem soulItem ? soulItem.getMaxSouls() - soulItem.getCurrentSouls(itemstack) : souls;
    }

    public ItemStack assemble(IItemHandler itemHandler, HolderLookup.Provider registryAccess) {
        return assembleFrom(itemHandler.getStackInSlot(0));
    }

    private ItemStack assembleFrom(ItemStack inputStack){
        ItemStack outputStack = this.output.copy();
        outputStack.applyComponents(inputStack.getComponentsPatch());
        if (inputStack.getItem() instanceof ISoulItem soulItem) {
            soulItem.setSouls(soulItem.getMaxSouls(), outputStack);
        }

        return outputStack;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull ContainerRecipeInput pContainer, @NotNull HolderLookup.Provider pRegistryAccess) {
        return assembleFrom(pContainer.getItem(0));
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider pRegistryAccess){
        return output;
    }

    public Ingredient getInput() {
        return input;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return NonNullList.of(input);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer(){
        return SoulInfuserRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType(){
        return SoulInfuserRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<SoulInfuserRecipe>{
        public static final SoulInfuserRecipe.Type INSTANCE = new SoulInfuserRecipe.Type();
        public static final String ID = "soul_infuser";
    }

    public static class Serializer implements RecipeSerializer<SoulInfuserRecipe>{
        public static final SoulInfuserRecipe.Serializer INSTANCE = new SoulInfuserRecipe.Serializer();
        public static final ResourceLocation ID = Valoria.loc("soul_infuser");

        private static final MapCodec<SoulInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            RecipeCodecs.ITEM_STACK.fieldOf("output").forGetter(r -> r.output),
            Codec.INT.fieldOf("time").forGetter(r -> r.time),
            RecipeCodecs.ITEM_STACK.fieldOf("ingredient").forGetter(r -> r.ingredientStack)
        ).apply(i, SoulInfuserRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, SoulInfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, r -> r.output,
            ByteBufCodecs.VAR_INT, r -> r.time,
            ItemStack.STREAM_CODEC, r -> r.ingredientStack,
            SoulInfuserRecipe::new
        );

        @Override
        public MapCodec<SoulInfuserRecipe> codec(){
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SoulInfuserRecipe> streamCodec(){
            return STREAM_CODEC;
        }
    }
}
