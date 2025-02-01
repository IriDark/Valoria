package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.drawable.*;
import mezz.jei.api.gui.ingredient.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import pro.komaru.tridot.client.event.*;

import java.util.*;

public class ManipulatorRecipeCategory extends AbstractRecipeCategory<ManipulatorRecipe>{
    private final IDrawable background;

    public ManipulatorRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.MANIPULATOR, Component.translatable("jei.valoria.manipulator"), helper.createDrawableItemLike(BlockRegistry.elementalManipulator.get()), 148, 48);
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.ID, "textures/gui/jei/manipulator.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ManipulatorRecipe recipe, IFocusGroup focusGroup){
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);

        builder.addSlot(RecipeIngredientRole.INPUT, 4, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(1).getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStack(resultStack);
    }

    @Override
    public void draw(ManipulatorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY){
        background.draw(gui);
        Font font_renderer = Minecraft.getInstance().font;
        int ticks = recipe.getTime();
        int seconds = ticks / 20;
        String time = Integer.toString(seconds);
        int stringWidth = font_renderer.width(time);

        ResourceLocation timeIcon = new ResourceLocation(Valoria.ID, "textures/gui/jei/time.png");
        gui.blit(timeIcon, 50 / 2, 29 + font_renderer.lineHeight, 0, 0, 7, 7, 16, 16);

        ResourceLocation cores = new ResourceLocation(Valoria.ID, "textures/gui/container/manipulator.png");
        ResourceLocation arrow = new ResourceLocation(Valoria.ID, "textures/gui/jei/progress_arrow.png");
        int width = 22;
        if(ClientTickHandler.ticksInGame % recipe.getTime() > 0){
            width /= ((double)recipe.getTime() / (double)(ClientTickHandler.ticksInGame % recipe.getTime()));
            gui.blit(arrow, 90, 16, 0, 0, width, 16, 32, 32);
        }

        if(recipe.getCore().equals("infernal_core")){
            gui.blit(cores, 0, 0, 181 * 2, 27 * 2, 10, 10, 512, 512);
        }

        if(recipe.getCore().equals("nature_core")){
            gui.blit(cores, 0, 0, 186 * 2, 27 * 2, 10, 10, 512, 512);
        }

        if(recipe.getCore().equals("aquarius_core")){
            gui.blit(cores, 0, 0, 176 * 2, 27 * 2, 10, 10, 512, 512);
        }

        if(recipe.getCore().equals("void_core")){
            gui.blit(cores, 0, 0, 191 * 2, 27 * 2, 10, 10, 512, 512);
        }

        if(mouseX >= (double)0 && mouseX < 8 && mouseY >= (double)0 && mouseY < 8){
            gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("tooltip.valoria.core_charges").append(": " + recipe.getCoresNeeded() + "/8"), -34, -2);
        }

        gui.drawString(font_renderer, time + "s", (95 - stringWidth) / 2, 28 + font_renderer.lineHeight, 0xffffff);
    }
}