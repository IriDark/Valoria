package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.drawable.*;
import mezz.jei.api.gui.ingredient.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipe>{
    public static final Map<ResourceLocation, List<ItemStack>> DROPS = new HashMap<>();
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CrusherRecipeCategory(IGuiHelper helper){
        title = Component.translatable("jei.valoria.crusher");
        ResourceLocation backgroundImage = Valoria.loc("textures/gui/jei/stone_crusher.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 116, 130);
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
    public void draw(CrusherRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY){
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrusherRecipe recipe, IFocusGroup focusGroup){
        List<ItemStack> drops = DROPS.getOrDefault(recipe.getId(), List.of());
        List<ItemStack> pickaxes = new ArrayList<>(List.of());
        for(var item : ForgeRegistries.ITEMS) {
            var stack = new ItemStack(item);
            if(stack.is(ItemTags.PICKAXES)) pickaxes.add(stack);
        }

        builder.addSlot(RecipeIngredientRole.CATALYST, 41, 24).addItemStacks(pickaxes);
        builder.addSlot(RecipeIngredientRole.INPUT, 49, 6).addIngredients(recipe.getIngredients().get(0));
        int maxItems = 7;
        for (int i = 0; i < drops.size(); i++) {
            int column = i % maxItems;
            int row = i / maxItems;

            int xOffset = 2 + column * 16;
            int yOffset = 71 + (row * 16);

            builder.addSlot(RecipeIngredientRole.OUTPUT, xOffset, yOffset).addItemStack(drops.get(i));
        }
    }
}