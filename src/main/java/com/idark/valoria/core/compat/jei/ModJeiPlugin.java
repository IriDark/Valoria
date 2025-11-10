package com.idark.valoria.core.compat.jei;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.client.ui.screen.*;
import com.idark.valoria.core.compat.jei.categories.*;
import com.idark.valoria.registries.*;
import mezz.jei.api.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.registration.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin{
    private static final ResourceLocation JEI = new ResourceLocation(Valoria.ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid(){
        return JEI;
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(JewelryScreen.class, 102, 48, 22, 15, ModRecipeTypes.JEWELRY);
        registration.addRecipeClickArea(ManipulatorScreen.class, 102, 48, 22, 15, ModRecipeTypes.MANIPULATOR);
        registration.addRecipeClickArea(KilnScreen.class, 78, 32, 28, 23, ModRecipeTypes.KILN, RecipeTypes.FUELING);
        registration.addRecipeClickArea(KegScreen.class, 78, 32, 22, 22, ModRecipeTypes.BREWERY);
        registration.addRecipeClickArea(SoulInfuserScreen.class, 102, 28, 22, 22, ModRecipeTypes.SOUL_INFUSER);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(KilnMenu.class, MenuRegistry.KILN_MENU.get(), RecipeTypes.FUELING, 1, 1, 3, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        registry.addRecipeCategories(new KilnRecipeCategory(registry.getJeiHelpers().getGuiHelper(), 120));
        registry.addRecipeCategories(new KegRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new JewelryRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new WorkbenchRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ManipulatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        //registry.addRecipeCategories(new CrusherRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new InfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new AlchemyRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new AlchemyUpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration){
        ModJeiRecipes modRecipes = new ModJeiRecipes();
        registration.addIngredientInfo(BlockRegistry.stoneCrusher.get(), Component.translatable("codex.valoria.stone_crusher.description"));
        registration.addIngredientInfo(ItemsRegistry.runicDust.get(), Component.translatable("jei.valoria.runic_dust"));
        registration.addIngredientInfo(BlockRegistry.crystalStone.get(), Component.translatable("jei.valoria.crystal_stone"));
        registration.addRecipes(ModRecipeTypes.BREWERY, modRecipes.getBreweryRecipes());
        registration.addRecipes(ModRecipeTypes.JEWELRY, modRecipes.getJewelryRecipes());
        registration.addRecipes(ModRecipeTypes.KILN, modRecipes.getKilnRecipes());
        registration.addRecipes(ModRecipeTypes.ALCHEMY, modRecipes.getAlchemyRecipes());
        registration.addRecipes(ModRecipeTypes.ALCHEMY_UPGRADE, modRecipes.getAlchemyUpgradeRecipes());
        registration.addRecipes(ModRecipeTypes.WORKBENCH, modRecipes.getWorkbenchRecipes());
        registration.addRecipes(ModRecipeTypes.MANIPULATOR, modRecipes.getManipulatorRecipes());
        registration.addRecipes(ModRecipeTypes.SOUL_INFUSER, modRecipes.getInfuserRecipes());
        //registration.addRecipes(ModRecipeTypes.CRUSHER, modRecipes.getCrusherRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.heavyWorkbench.get()), ModRecipeTypes.WORKBENCH);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.keg.get()), ModRecipeTypes.BREWERY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.kiln.get()), ModRecipeTypes.KILN);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.jewelerTable.get()), ModRecipeTypes.JEWELRY);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.alchemyStationTier1.get()), ModRecipeTypes.ALCHEMY, ModRecipeTypes.ALCHEMY_UPGRADE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.alchemyStationTier2.get()), ModRecipeTypes.ALCHEMY, ModRecipeTypes.ALCHEMY_UPGRADE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.alchemyStationTier3.get()), ModRecipeTypes.ALCHEMY, ModRecipeTypes.ALCHEMY_UPGRADE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.alchemyStationTier4.get()), ModRecipeTypes.ALCHEMY, ModRecipeTypes.ALCHEMY_UPGRADE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.elementalManipulator.get()), ModRecipeTypes.MANIPULATOR);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.soulInfuser.get()), ModRecipeTypes.SOUL_INFUSER);
        //registration.addRecipeCatalyst(new ItemStack(BlockRegistry.stoneCrusher.get()), ModRecipeTypes.CRUSHER);
    }
}
