package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.recipe.*;
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

import java.util.*;

public class AlchemyRecipeCategory extends AbstractRecipeCategory<AlchemyRecipe>{
    public static final int width = 112;
    public static final int height = 48;

    public AlchemyRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.ALCHEMY, Component.translatable("jei.valoria.alchemy"), helper.createDrawableItemLike(BlockRegistry.alchemyStationTier2.get()), width, height);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlchemyRecipe recipe, IFocusGroup focuses){
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 48, 14).addItemStack(resultStack);
        for(var input : recipe.getInputs()){
            builder.addSlot(RecipeIngredientRole.INPUT, Integer.MAX_VALUE, Integer.MAX_VALUE).addItemStacks(Arrays.stream(input.getFirst().getItems()).toList());
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, AlchemyRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY){
        super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);
        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        if(!recipe.getInputs().isEmpty()){
            tooltip.add(result.getHoverName().copy().withStyle(result.getDisplayName().getStyle()));
            tooltip.add(new MaterialListComponent(recipe.getInputs()));
        }
    }

    @Override
    public void draw(AlchemyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ResourceLocation tex = new ResourceLocation(Valoria.ID, "textures/gui/jei/workbench.png");
        guiGraphics.blit(tex, 0, 0, 0, 0, width, height, 256, 256);
    }

    @Override
    public ResourceLocation getRegistryName(AlchemyRecipe recipe){
        return recipe.getId();
    }
}