package com.idark.valoria.registries.item.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.idark.valoria.Valoria;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ManipulatorRecipe implements Recipe<Container>{
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;
    private final String pCoreId;
    private final int cores;
    private final int time;

    public ManipulatorRecipe(ResourceLocation id, ItemStack output, String pCoreId, int cores, int time, Ingredient... inputItems){
        this.id = id;
        this.output = output;
        this.pCoreId = pCoreId;
        this.cores = cores;
        this.time = time;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems);
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel){
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

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess){
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight){
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess){
        return output.copy();
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
    public ResourceLocation getId(){
        return id;
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
        public static final ResourceLocation ID = new ResourceLocation(Valoria.ID, "manipulator");

        @Override
        public @NotNull ManipulatorRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe){
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            String core = pSerializedRecipe.has("core") ? pSerializedRecipe.get("core").getAsString() : "empty";
            int cores = pSerializedRecipe.has("cores") ? GsonHelper.getAsInt(pSerializedRecipe, "cores") : 0;
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time");
            JsonArray pIngredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for(JsonElement e : pIngredients){
                inputs.add(Ingredient.fromJson(e));
            }

            return new ManipulatorRecipe(pRecipeId, output, core, cores, time, inputs.toArray(new Ingredient[0]));
        }

        @Override
        public @Nullable ManipulatorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer){
            Ingredient[] inputs = new Ingredient[pBuffer.readInt()];
            for(int i = 0; i < inputs.length; i++){
                inputs[i] = Ingredient.fromNetwork(pBuffer);
            }

            ItemStack output = pBuffer.readItem();
            String core = pBuffer.readUtf();
            int cores = pBuffer.readInt();
            int time = pBuffer.readInt();
            return new ManipulatorRecipe(pRecipeId, output, core, cores, time, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ManipulatorRecipe pRecipe){
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for(Ingredient input : pRecipe.getIngredients()){
                input.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
            pBuffer.writeUtf(pRecipe.getCore());
            pBuffer.writeInt(pRecipe.getCoresNeeded());
            pBuffer.writeInt(pRecipe.getTime());
        }
    }
}