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

public class WorkbenchRecipeCategory extends AbstractRecipeCategory<WorkbenchRecipe>{
    public static final int width = 112;
    public static final int height = 48;

    public WorkbenchRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.WORKBENCH, Component.translatable("jei.valoria.heavy_workbench"), helper.createDrawableItemLike(BlockRegistry.heavyWorkbench.get()), width, height);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WorkbenchRecipe recipe, IFocusGroup focuses){
        ItemStack resultStack = recipe.getResultItem(RegistryAccess.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 48, 14).addItemStack(resultStack);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, WorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY){
        super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);
        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        if(!recipe.getInputs().isEmpty()){
            tooltip.add(result.getHoverName().copy().withStyle(result.getDisplayName().getStyle()));
            tooltip.add(new MaterialListComponent(recipe.getInputs()));
        }
    }

    @Override
    public void draw(WorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ResourceLocation tex = new ResourceLocation(Valoria.ID, "textures/gui/jei/workbench.png");
        guiGraphics.blit(tex, 0, 0, 0, 0, width, height, 256, 256);
    }

    @Override
    public ResourceLocation getRegistryName(WorkbenchRecipe recipe){
        return recipe.getId();
    }
}