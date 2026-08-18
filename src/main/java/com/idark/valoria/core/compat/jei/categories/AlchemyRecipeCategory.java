package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import com.mojang.datafixers.util.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.ingredient.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class AlchemyRecipeCategory extends AbstractRecipeCategory<AlchemyRecipe>{
    public static final int width = 128;
    public static final int height = 128;

    public AlchemyRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.ALCHEMY, Component.translatable("jei.valoria.alchemy"), helper.createDrawableItemLike(BlockRegistry.alchemyStationTier2.get()), width, height);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlchemyRecipe recipe, IFocusGroup focuses){
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT, width / 2 - 9, 14).addItemStack(resultStack);
        int index = 0;
        for (Pair<Ingredient, RecipeData> entry : recipe.getInputs()) {
            Ingredient ingredient = entry.getFirst();
            int count = entry.getSecond().count;

            List<ItemStack> displayStacks = new ArrayList<>();
            for (ItemStack stack : ingredient.getItems()) {
                ItemStack displayStack = stack.copy();
                displayStack.setCount(count);
                displayStacks.add(displayStack);
            }

            int column = index % 7;
            int row = index / 7;

            int xOffset = column * 16;
            int yOffset = 48 + (row * 16);

            builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).setStandardSlotBackground().addItemStacks(displayStacks);
            index++;
        }
    }

    @Override
    public void draw(AlchemyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ResourceLocation tex = Valoria.loc("textures/gui/jei/workbench.png");
        guiGraphics.blit(tex, width / 2 - 56, 0, 0, 0, 112, 48, 256, 256);
    }

    @Override
    public ResourceLocation getRegistryName(AlchemyRecipe recipe){
        return recipe.getId();
    }
}