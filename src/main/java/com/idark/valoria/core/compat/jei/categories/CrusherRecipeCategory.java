package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.drawable.*;
import mezz.jei.api.gui.ingredient.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CrusherRecipeCategory(IGuiHelper helper) {
        title = Component.translatable("jei.valoria.crusher");
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.ID, "textures/gui/jei/jewelry.png");

        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.STONE_CRUSHER.get()));
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

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStack(new ItemStack(Items.IRON_PICKAXE));
        //builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStacks(LootUtil.createWeightedLoot(loot, LootUtil.getGiftParameters2(null, 100), 100));
    }

    @Override
    public void draw(CrusherRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY) {
    }
}