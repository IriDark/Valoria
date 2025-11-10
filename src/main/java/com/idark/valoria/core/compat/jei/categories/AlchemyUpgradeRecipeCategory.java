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
import org.jetbrains.annotations.*;

import java.util.*;

public class AlchemyUpgradeRecipeCategory extends AbstractRecipeCategory<AlchemyUpgradeRecipe>{
    public static final int width = 112;
    public static final int height = 48;

    public AlchemyUpgradeRecipeCategory(IGuiHelper helper){
        super(ModRecipeTypes.ALCHEMY_UPGRADE, Component.translatable("jei.valoria.alchemy_upgrade"), helper.createDrawableItemLike(BlockRegistry.alchemyStationTier2.get()), width, height);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlchemyUpgradeRecipe recipe, IFocusGroup focuses){
        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 48, 14).addItemStack(result);

        // Added to look for item Uses by pressing "U"
        for(var input : recipe.getInputs()){
            builder.addSlot(RecipeIngredientRole.INPUT, Integer.MAX_VALUE, Integer.MAX_VALUE).addItemStacks(Arrays.stream(input.getFirst().getItems()).toList());
        }
    }

    private @NotNull ResourceLocation getUpgradeLoc(int tier){
        return Valoria.loc("alchemy/upgrade/alchemy_upgrade_" + tier);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, AlchemyUpgradeRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY){
        super.getTooltip(tooltip, recipe, recipeSlotsView, mouseX, mouseY);
        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        if(!recipe.getInputs().isEmpty()){
            tooltip.add(result.getHoverName().copy().withStyle(result.getDisplayName().getStyle()));
            tooltip.add(new MaterialListComponent(recipe.getInputs()));
        }
    }

    @Override
    public void draw(AlchemyUpgradeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        ResourceLocation tex = new ResourceLocation(Valoria.ID, "textures/gui/jei/workbench.png");
        guiGraphics.blit(tex, 0, 0, 0, 0, width, height, 256, 256);
    }

    @Override
    public ResourceLocation getRegistryName(AlchemyUpgradeRecipe recipe){
        return recipe.getId();
    }
}