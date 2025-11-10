package com.idark.valoria.core.compat.jei;

import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class ModJeiRecipes{
    private final RecipeManager recipeManager;

    public ModJeiRecipes(){
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if(level != null){
            this.recipeManager = level.getRecipeManager();
        }else{
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public List<KilnRecipe> getKilnRecipes(){
        return recipeManager.getAllRecipesFor(KilnRecipe.Type.INSTANCE).stream().sorted(Comparator.comparing(KilnRecipe::getCookingTime)).toList();
    }

    public List<KegRecipe> getBreweryRecipes(){
        return recipeManager.getAllRecipesFor(KegRecipe.Type.INSTANCE).stream().sorted(Comparator.comparing(KegRecipe::getTime)).toList();
    }

    public List<JewelryRecipe> getJewelryRecipes(){
        return recipeManager.getAllRecipesFor(JewelryRecipe.Type.INSTANCE).stream().sorted(Comparator.comparing(JewelryRecipe::getTime)).toList();
    }

    public List<WorkbenchRecipe> getWorkbenchRecipes(){
        return recipeManager.getAllRecipesFor(WorkbenchRecipe.Type.INSTANCE);
    }

    public List<SoulInfuserRecipe> getInfuserRecipes(){
        return recipeManager.getAllRecipesFor(SoulInfuserRecipe.Type.INSTANCE);
    }

    public List<CrusherRecipe> getCrusherRecipes(){
        return recipeManager.getAllRecipesFor(CrusherRecipe.Type.INSTANCE).stream().toList();
    }

    public List<AlchemyRecipe> getAlchemyRecipes(){
        return recipeManager.getAllRecipesFor(AlchemyRecipe.Type.INSTANCE).stream().sorted(Comparator.comparing(AlchemyRecipe::getLevel)).toList();
    }

    public List<AlchemyUpgradeRecipe> getAlchemyUpgradeRecipes(){
        return recipeManager.getAllRecipesFor(AlchemyUpgradeRecipe.Type.INSTANCE).stream().toList();
    }

    public List<ManipulatorRecipe> getManipulatorRecipes(){
        return recipeManager.getAllRecipesFor(ManipulatorRecipe.Type.INSTANCE).stream().sorted(Comparator.comparing(ManipulatorRecipe::getCore)).sorted(Comparator.comparing(ManipulatorRecipe::getTime)).toList();
    }
}