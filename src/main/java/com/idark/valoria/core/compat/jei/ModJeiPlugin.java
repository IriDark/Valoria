package com.idark.valoria.core.compat.jei;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.compat.jei.categories.ArchaeologyRecipeCategory;
import com.idark.valoria.core.compat.jei.categories.JewelryRecipeCategory;
import com.idark.valoria.core.compat.jei.categories.KegRecipeCategory;
import com.idark.valoria.core.compat.jei.categories.ManipulatorRecipeCategory;
import com.idark.valoria.registries.BlockRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin{
    private static final ResourceLocation JEI = new ResourceLocation(Valoria.ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid(){
        return JEI;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        registry.addRecipeCategories(new KegRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new JewelryRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ArchaeologyRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ManipulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        //registry.addRecipeCategories(new CrusherRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration){
        ModJeiRecipes modRecipes = new ModJeiRecipes();
        registration.addRecipes(ModRecipeTypes.BREWERY, modRecipes.getBreweryRecipes());
        registration.addRecipes(ModRecipeTypes.JEWELRY, modRecipes.getJewelryRecipes());
        registration.addRecipes(ModRecipeTypes.ARCHAEOLOGY, modRecipes.getArchaeologyRecipes());
        registration.addRecipes(ModRecipeTypes.MANIPULATOR, modRecipes.getManipulatorRecipes());
        //registration.addRecipes(ModRecipeTypes.CRUSHER, modRecipes.getCrusherRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.archaeologyTable.get()), ModRecipeTypes.ARCHAEOLOGY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.keg.get()), ModRecipeTypes.BREWERY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.jewelerTable.get()), ModRecipeTypes.JEWELRY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.elementalManipulator.get()), ModRecipeTypes.MANIPULATOR);
        //registration.addRecipeCatalyst(new ItemStack(ModItems.STONE_CRUSHER.get()), ModRecipeTypes.CRUSHER);
    }
}
