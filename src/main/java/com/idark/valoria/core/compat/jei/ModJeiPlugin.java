package com.idark.valoria.core.compat.jei;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.categories.*;
import com.idark.valoria.registries.*;
import mezz.jei.api.*;
import mezz.jei.api.registration.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final ResourceLocation JEI = new ResourceLocation(Valoria.ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return JEI;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new KegRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new JewelryRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ArchaeologyRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ManipulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        //registry.addRecipeCategories(new CrusherRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ModJeiRecipes modRecipes = new ModJeiRecipes();
        registration.addRecipes(ModRecipeTypes.BREWERY, modRecipes.getBreweryRecipes());
        registration.addRecipes(ModRecipeTypes.JEWELRY, modRecipes.getJewelryRecipes());
        registration.addRecipes(ModRecipeTypes.ARCHAEOLOGY, modRecipes.getArchaeologyRecipes());
        registration.addRecipes(ModRecipeTypes.MANIPULATOR, modRecipes.getManipulatorRecipes());
        //registration.addRecipes(ModRecipeTypes.CRUSHER, modRecipes.getCrusherRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.ARCHAEOLOGY_TABLE.get()), ModRecipeTypes.ARCHAEOLOGY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.KEG.get()), ModRecipeTypes.BREWERY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.JEWELER_TABLE.get()), ModRecipeTypes.JEWELRY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.ELEMENTAL_MANIPULATOR.get()), ModRecipeTypes.MANIPULATOR);
        //registration.addRecipeCatalyst(new ItemStack(ModItems.STONE_CRUSHER.get()), ModRecipeTypes.CRUSHER);
    }
}
