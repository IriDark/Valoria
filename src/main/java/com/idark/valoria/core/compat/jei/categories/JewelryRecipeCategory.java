package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.core.compat.jei.ModRecipeTypes;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.item.recipe.JewelryRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
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

public class JewelryRecipeCategory extends AbstractRecipeCategory<JewelryRecipe>{
    private final IDrawable background;

    public JewelryRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.JEWELRY, Component.translatable("jei.valoria.jewelry"), helper.createDrawableItemLike(BlockRegistry.jewelerTable.get()), 148, 48);
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.ID, "textures/gui/jei/jewelry.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JewelryRecipe recipe, IFocusGroup focusGroup){
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);

        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(1).getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(resultStack);
    }

    @Override
    public void draw(JewelryRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY){
        background.draw(gui, 0, 1);
        Font font_renderer = Minecraft.getInstance().font;
        int ticks = recipe.getTime();
        int seconds = ticks / 20;
        String time = Integer.toString(seconds);
        int stringWidth = font_renderer.width(time);

        ResourceLocation timeIcon = new ResourceLocation(Valoria.ID, "textures/gui/jei/time.png");
        gui.blit(timeIcon, 50 / 2, 29 + font_renderer.lineHeight, 0, 0, 7, 7, 16, 16);

        ResourceLocation arrow = new ResourceLocation(Valoria.ID, "textures/gui/jei/progress_arrow.png");
        int width = 22;
        if(ClientTickHandler.ticksInGame % recipe.getTime() > 0){
            width /= ((double)recipe.getTime() / (double)(ClientTickHandler.ticksInGame % recipe.getTime()));
            gui.blit(arrow, 90, 16, 0, 0, width, 16, 32, 32);
        }

        gui.drawString(font_renderer, time + "s", (95 - stringWidth) / 2, 28 + font_renderer.lineHeight, 0xffffff);
    }
}