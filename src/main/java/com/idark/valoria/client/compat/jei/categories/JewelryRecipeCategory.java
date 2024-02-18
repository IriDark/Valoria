package com.idark.valoria.client.compat.jei.categories;

import com.idark.valoria.Valoria;
import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.client.compat.jei.ModRecipeTypes;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.recipe.JewelryRecipe;
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public class JewelryRecipeCategory implements IRecipeCategory<JewelryRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public JewelryRecipeCategory(IGuiHelper helper) {
        title = Component.translatable("jei.valoria.jewelry");
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.MOD_ID, "textures/gui/jei/jewelry.png");

        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.JEWELER_TABLE.get()));
    }

    @Override
    public RecipeType<JewelryRecipe> getRecipeType() {
        return ModRecipeTypes.JEWELRY;
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
    public void setRecipe(IRecipeLayoutBuilder builder, JewelryRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);

        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(1).getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 17).addItemStack(resultStack);
    }

    @Override
    public void draw(JewelryRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY) {
        Font font_renderer = Minecraft.getInstance().font;
        String time = Integer.toString(recipe.getTime());
        int stringWidth = font_renderer.width(time);

        ResourceLocation timeIcon = new ResourceLocation(Valoria.MOD_ID, "textures/gui/jei/time.png");
        gui.blit(timeIcon, 50 / 2, 29 + font_renderer.lineHeight, 0, 0, 7, 7, 16, 16);

        ResourceLocation arrow = new ResourceLocation(Valoria.MOD_ID, "textures/gui/jei/progress_arrow.png");
        int width = 22;
        if (ClientTickHandler.ticksInGame % recipe.getTime() > 0) {
            width /= ((double) recipe.getTime() / (double) (ClientTickHandler.ticksInGame % recipe.getTime()));
            gui.blit(arrow, 90, 16, 0, 0, width, 16, 32, 32);
        }
        
        gui.drawString(font_renderer, time, (95 - stringWidth) / 2, 28 + font_renderer.lineHeight, 0xffffff);
    }
}