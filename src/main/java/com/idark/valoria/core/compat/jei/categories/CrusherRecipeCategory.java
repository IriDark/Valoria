package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.compat.jei.ModRecipeTypes;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.item.recipe.CrusherRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

@Deprecated
@SuppressWarnings("removal")
public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipe>{
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CrusherRecipeCategory(IGuiHelper helper){
        title = Component.translatable("jei.valoria.crusher");
        ResourceLocation backgroundImage = new ResourceLocation(Valoria.ID, "textures/gui/jei/jewelry.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 148, 48);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.stoneCrusher.get()));
    }

    @Override
    public RecipeType<CrusherRecipe> getRecipeType(){
        return ModRecipeTypes.CRUSHER;
    }

    @Override
    public Component getTitle(){
        return this.title;
    }

    @Override
    public IDrawable getBackground(){
        return this.background;
    }

    @Override
    public IDrawable getIcon(){
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrusherRecipe recipe, IFocusGroup focusGroup){
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ResourceLocation loot = recipe.getOutput();

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStacks(Arrays.asList(recipeIngredients.get(0).getItems()));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 16).addItemStack(new ItemStack(Items.IRON_PICKAXE));
        //builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStacks(LootUtil.createWeightedLoot(loot, LootUtil.getGiftParameters2(null, 100), 100));
    }
}