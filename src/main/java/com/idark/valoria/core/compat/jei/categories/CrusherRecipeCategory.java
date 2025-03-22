package com.idark.valoria.core.compat.jei.categories;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.gui.builder.*;
import mezz.jei.api.gui.drawable.*;
import mezz.jei.api.helpers.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

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
        ResourceLocation loot = recipe.getOutput();
//        var serv = Valoria.proxy.getLevel().getServer();
//        if (serv == null) return;
//
//        var loottable = serv.getLootData().getLootTable(loot);
//        LootParams.Builder params = new LootParams.Builder(serv.overworld()).withParameter(LootContextParams.ORIGIN, new Vec3(0, 0, 0));
//        List<ItemStack> possibleDrops = loottable.getRandomItems(params.create(LootContextParamSets.ALL_PARAMS));
//        Valoria.LOGGER.info("Loot Table: {}", loot);
//        Valoria.LOGGER.info("Generated Drops: {}", possibleDrops);
//        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 16).addItemStacks(possibleDrops);
    }
}