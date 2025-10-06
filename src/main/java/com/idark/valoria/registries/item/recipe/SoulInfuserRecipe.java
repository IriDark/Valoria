package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.items.*;
import org.jetbrains.annotations.*;
import org.jetbrains.annotations.Nullable;

import javax.annotation.*;

public class SoulInfuserRecipe implements Recipe<Container>{
    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int souls;
    private final int time;

    public SoulInfuserRecipe(ResourceLocation id, ItemStack output, int souls, int time, Ingredient input){
        this.id = id;
        this.output = output;
        this.souls = souls;
        this.time = time;
        this.input = input;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel){
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

    public ItemStack assemble(IItemHandler itemHandler, RegistryAccess registryAccess) {
        ItemStack outputStack = this.output.copy();
        ItemStack inputStack = itemHandler.getStackInSlot(0);
        Item inputItem = inputStack.getItem();

        CompoundTag compoundtag = inputStack.getTag();
        if (compoundtag != null) {
            outputStack.setTag(compoundtag.copy());
        }

        if (inputItem instanceof ISoulItem soulItem) {
            CompoundTag outputTag = outputStack.getOrCreateTag();
            outputTag.putInt("Souls", soulItem.getMaxSouls());
        }

        return outputStack;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        IItemHandler handler = new IItemHandler() {
            @Override public int getSlots() { return pContainer.getContainerSize(); }
            @Nonnull @Override public ItemStack getStackInSlot(int slot) { return pContainer.getItem(slot); }
            @Nonnull @Override public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) { return stack; }
            @Nonnull @Override public ItemStack extractItem(int slot, int amount, boolean simulate) { return ItemStack.EMPTY; }
            @Override public int getSlotLimit(int slot) { return 64; }
            @Override public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return true; }
        };
        return assemble(handler, pRegistryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess){
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
    public @NotNull ResourceLocation getId(){
        return id;
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
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "soul_infuser");

        @Override
        public @NotNull SoulInfuserRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe){
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));

            int souls = 0;
            if(input.getItem() instanceof ISoulItem soulItem){
                souls = soulItem.getMaxSouls() - soulItem.getCurrentSouls(input);
            }

            return new SoulInfuserRecipe(pRecipeId, output, souls, time, Ingredient.of(input));
        }

        @Override
        public @Nullable SoulInfuserRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer){
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            int souls = pBuffer.readInt();
            int time = pBuffer.readInt();
            return new SoulInfuserRecipe(pRecipeId, output, souls, time, input);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, SoulInfuserRecipe pRecipe){
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeInt(pRecipe.souls);
            pBuffer.writeInt(pRecipe.getTime());
        }
    }
}