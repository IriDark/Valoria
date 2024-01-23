package com.idark.valoria.client.compat.jei;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.compat.jei.categories.KegRecipeCategory;
import com.idark.valoria.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final ResourceLocation JEI = new ResourceLocation(Valoria.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return JEI;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new KegRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ModJeiRecipes modRecipes = new ModJeiRecipes();
        registration.addRecipes(ModRecipeTypes.BREWERY, modRecipes.getBreweryRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.KEG.get()), ModRecipeTypes.BREWERY);
    }
}
