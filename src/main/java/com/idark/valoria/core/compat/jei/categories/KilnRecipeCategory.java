package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.placement.*;
import mezz.jei.api.gui.widgets.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
public class KilnRecipeCategory extends AbstractRecipeCategory<KilnRecipe>{
    protected final int regularCookTime;

    public KilnRecipeCategory(IGuiHelper helper, int regularCookTime){
        super(ModRecipeTypes.KILN, Component.translatable("jei.valoria.kiln"), helper.createDrawableItemLike(BlockRegistry.kiln.get()), 82, 54);
        this.regularCookTime = regularCookTime;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KilnRecipe recipeHolder, IFocusGroup focuses){
        builder.addInputSlot(1, 1)
        .setStandardSlotBackground()
        .addIngredients(recipeHolder.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 1, 37)
        .setStandardSlotBackground();

        builder.addOutputSlot(61, 19)
        .setOutputSlotBackground()
        .addItemStack(recipeHolder.getResultItem(RegistryAccess.EMPTY));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, KilnRecipe recipeHolder, IFocusGroup focuses){
        int cookTime = recipeHolder.getCookingTime();
        if(cookTime <= 0){
            cookTime = regularCookTime;
        }
        builder.addAnimatedRecipeArrow(cookTime)
        .setPosition(26, 17);
        builder.addAnimatedRecipeFlame(300)
        .setPosition(1, 20);

        addExperience(builder, recipeHolder);
        addCookTime(builder, recipeHolder);
    }

    protected void addExperience(IRecipeExtrasBuilder builder, KilnRecipe recipeHolder){
        float experience = recipeHolder.getExperience();
        if(experience > 0){
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            builder.addText(experienceString, getWidth() - 20, 10)
            .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.TOP)
            .setTextAlignment(HorizontalAlignment.RIGHT)
            .setColor(0xFF808080);
        }
    }

    protected void addCookTime(IRecipeExtrasBuilder builder, KilnRecipe recipeHolder){
        int cookTime = recipeHolder.getCookingTime();
        if(cookTime <= 0){
            cookTime = regularCookTime;
        }
        if(cookTime > 0){
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            builder.addText(timeString, getWidth() - 20, 10)
            .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
            .setTextAlignment(HorizontalAlignment.RIGHT)
            .setTextAlignment(VerticalAlignment.BOTTOM)
            .setColor(0xFF808080);
        }
    }

    @Override
    public boolean isHandled(KilnRecipe recipeHolder){
        return !recipeHolder.isSpecial();
    }

    @Override
    public ResourceLocation getRegistryName(KilnRecipe recipe){
        return recipe.getId();
    }
}