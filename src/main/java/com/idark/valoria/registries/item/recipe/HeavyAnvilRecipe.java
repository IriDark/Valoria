package com.idark.valoria.registries.item.recipe;

import com.google.gson.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

public class HeavyAnvilRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack result;
    private final int requiredHits;
    private final int maxInstability;
    private final float cursorSpeed;
    private final HeavyAnvilTier minTier;
    private final boolean acceptsTierBonus;

    public HeavyAnvilRecipe(ResourceLocation id, Ingredient input, ItemStack result, int requiredHits, int maxInstability, float cursorSpeed) {
        this(id, input, result, requiredHits, maxInstability, cursorSpeed, HeavyAnvilTier.BRONZE, true);
    }

    public HeavyAnvilRecipe(ResourceLocation id, Ingredient input, ItemStack result, int requiredHits, int maxInstability, float cursorSpeed, HeavyAnvilTier minTier, boolean acceptsTierBonus) {
        this.id = id;
        this.input = input;
        this.result = result;
        this.requiredHits = requiredHits;
        this.maxInstability = maxInstability;
        this.cursorSpeed = cursorSpeed;
        this.minTier = minTier != null ? minTier : HeavyAnvilTier.BRONZE;
        this.acceptsTierBonus = acceptsTierBonus;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return input.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input);
    }

    @Override
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

    public HeavyAnvilTier getMinTier() {
        return this.minTier;
    }

    public boolean acceptsTierBonus() {
        return this.acceptsTierBonus;
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

    public static class Serializer implements RecipeSerializer<HeavyAnvilRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Valoria.loc("heavy_anvil");

        @Override
        public HeavyAnvilRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(pSerializedRecipe.get("ingredient"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            int requiredHits = GsonHelper.getAsInt(pSerializedRecipe, "required_hits", 10);
            int maxInstability = GsonHelper.getAsInt(pSerializedRecipe, "max_instability", 3);
            float cursorSpeed = GsonHelper.getAsFloat(pSerializedRecipe, "cursor_speed", 0.05f);
            HeavyAnvilTier minTier = HeavyAnvilTier.fromString(GsonHelper.getAsString(pSerializedRecipe, "min_tier", "bronze"));
            boolean acceptsTierBonus = GsonHelper.getAsBoolean(pSerializedRecipe, "accepts_tier_bonus", true);

            return new HeavyAnvilRecipe(pRecipeId, input, result, requiredHits, maxInstability, cursorSpeed, minTier, acceptsTierBonus);
        }

        @Override
        public @Nullable HeavyAnvilRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            int requiredHits = pBuffer.readInt();
            int maxInstability = pBuffer.readInt();
            float cursorSpeed = pBuffer.readFloat();
            HeavyAnvilTier minTier = HeavyAnvilTier.fromString(pBuffer.readUtf());
            boolean acceptsTierBonus = pBuffer.readBoolean();

            return new HeavyAnvilRecipe(pRecipeId, input, result, requiredHits, maxInstability, cursorSpeed, minTier, acceptsTierBonus);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HeavyAnvilRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeInt(pRecipe.requiredHits);
            pBuffer.writeInt(pRecipe.maxInstability);
            pBuffer.writeFloat(pRecipe.cursorSpeed);
            pBuffer.writeUtf(pRecipe.minTier.getSerializedName());
            pBuffer.writeBoolean(pRecipe.acceptsTierBonus);
        }
    }
}
