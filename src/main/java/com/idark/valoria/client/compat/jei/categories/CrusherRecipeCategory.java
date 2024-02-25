package com.idark.valoria.client.compat.jei.categories;

import com.blamejared.crafttweaker.api.loot.table.LootTableManager;
import com.idark.valoria.Valoria;
import com.idark.valoria.client.compat.jei.ModRecipeTypes;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.registries.recipe.CrusherRecipe;
import com.idark.valoria.registries.recipe.KegRecipe;
import com.idark.valoria.registries.world.item.ModItems;
import com.idark.valoria.util.LootUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Arrays;
import java.util.List;

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CrusherRecipeCategory(IGuiHelper helper) {
        title = Component.translatable("jei.valoria.crusher");
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.MOD_ID, "textures/gui/jei/jewelry.png");

        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.STONE_CRUSHER.get()));
    }

    @Override
    public RecipeType<CrusherRecipe> getRecipeType() {
        return ModRecipeTypes.CRUSHER;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrusherRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ResourceLocation loot = recipe.getOutput();

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStack(new ItemStack(Items.IRON_PICKAXE));
        //builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStacks(LootUtil.createWeightedLoot(loot, LootUtil.getGiftParameters2(null, 100), 100));
    }

    @Override
    public void draw(CrusherRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY) {
    }
}