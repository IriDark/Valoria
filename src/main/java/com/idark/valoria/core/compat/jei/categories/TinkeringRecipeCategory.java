package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.widgets.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

public class TinkeringRecipeCategory extends AbstractRecipeCategory<TinkeringRecipe>{
    public static final int width = 82;
    public static final int height = 34;

    public TinkeringRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.TINKERING, Component.translatable("jei.valoria.tinkering_workbench"), helper.createDrawableItemLike(BlockRegistry.tinkererWorkbench.get()), width, height);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TinkeringRecipe recipe, IFocusGroup focuses){
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);


        builder.addInputSlot(1, 9).setStandardSlotBackground().addItemStack(new ItemStack(recipeIngredients.get(0).getItems()[0].getItem(), recipe.getIngredientCount())); //cringy currently
        builder.addOutputSlot(61, 9).setOutputSlotBackground().addItemStack(resultStack);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, TinkeringRecipe recipe, IFocusGroup focuses){
        builder.addRecipeArrow().setPosition(26, 9);
    }

    @Override
    public ResourceLocation getRegistryName(TinkeringRecipe recipe){
        return recipe.getId();
    }
}