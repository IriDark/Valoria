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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.*;

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

    public int getSouls(ItemStack itemstack){
        return itemstack.getItem() instanceof ISoulItem soulItem ? soulItem.getMaxSouls() - soulItem.getCurrentSouls(itemstack) : souls;
    }

    public ItemStack assemble(IItemHandler itemHandler){
        ItemStack itemstack = this.output.copy();
        CompoundTag compoundtag = itemHandler.getStackInSlot(0).getTag();
        if (compoundtag != null) {
            compoundtag.remove("Souls");
            compoundtag.putInt("Souls", getSouls(itemHandler.getStackInSlot(0)));
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess){
        ItemStack itemstack = this.output.copy();
        CompoundTag compoundtag = pContainer.getItem(1).getTag();
        if (compoundtag != null) {
            compoundtag.remove("Souls");
            compoundtag.putInt("Souls", getSouls(pContainer.getItem(1)));
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess){
        return output;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients(){
        return NonNullList.of(input);
    }

    @Override
    public ResourceLocation getId(){
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer(){
        return SoulInfuserRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType(){
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
        public @Nullable SoulInfuserRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer){
            Ingredient input = Ingredient.fromNetwork(pBuffer);

            ItemStack output = pBuffer.readItem();
            int souls = pBuffer.readInt();
            int time = pBuffer.readInt();
            return new SoulInfuserRecipe(pRecipeId, output, souls, time, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SoulInfuserRecipe pRecipe){
            for(Ingredient input : pRecipe.getIngredients()){
                input.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeInt(pRecipe.souls);
            pBuffer.writeInt(pRecipe.getTime());
        }
    }
}