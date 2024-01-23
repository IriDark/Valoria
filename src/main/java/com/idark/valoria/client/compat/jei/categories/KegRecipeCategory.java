package com.idark.valoria.client.compat.jei.categories;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.compat.jei.ModRecipeTypes;
import com.idark.valoria.item.ModItems;
import com.idark.valoria.recipe.KegRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public class KegRecipeCategory implements IRecipeCategory<KegRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public KegRecipeCategory(IGuiHelper helper) {
        title = Component.translatable(Valoria.MOD_ID + "jei.valoria.brewery");
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.MOD_ID, "textures/gui/jei/keg_brewery.png");

        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.KEG.get()));
    }

    @Override
    public RecipeType<KegRecipe> getRecipeType() {
        return ModRecipeTypes.BREWERY;
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
    public void setRecipe(IRecipeLayoutBuilder builder, KegRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);

        int borderSlotSize = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                int inputIndex = row * 3 + column;
                if (inputIndex < recipeIngredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, (column * borderSlotSize) + 1, (row * borderSlotSize) + 1).addItemStacks(Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 63, 16).addItemStack(new ItemStack(ModItems.KEG.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 17).addItemStack(resultStack);
    }

    @Override
    public void draw(KegRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String time = Integer.toString(recipe.getTime());
        int stringWidth = font_renderer.width(time);

        gui.drawString(Minecraft.getInstance().font, time, (85 - stringWidth) / 2, 26 + font_renderer.lineHeight, 0xffffff);
    }
}