package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class RecipesRegistry{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Valoria.ID);
    public static final RegistryObject<RecipeSerializer<KegRecipe>> KEG_SERIALIZER = SERIALIZERS.register("keg_brewery", () -> KegRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<JewelryRecipe>> JEWELRY_SERIALIZER = SERIALIZERS.register("jewelry", () -> JewelryRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER = SERIALIZERS.register("crusher", () -> CrusherRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ManipulatorRecipe>> MANIPULATOR_SERIALIZER = SERIALIZERS.register("manipulator", () -> ManipulatorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<TinkeringRecipe>> TINKERING_SERIALIZER = SERIALIZERS.register("tinkering", () -> TinkeringRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<KilnRecipe>> KILN_SERIALIZER = SERIALIZERS.register("kiln", () -> KilnRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SoulInfuserRecipe>> SOUL_INFUSER_SERIALIZER = SERIALIZERS.register("soul_infuser", () -> SoulInfuserRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<PoisonWeaponRecipe>> POISON_RECIPE = SERIALIZERS.register("weapon_poisoning",  () -> new SimpleCraftingRecipeSerializer<>(PoisonWeaponRecipe::new));
    public static final RegistryObject<RecipeSerializer<PurifyingFoodRecipe>> PURIFY_RECIPE = SERIALIZERS.register("purifying_food",  () -> new SimpleCraftingRecipeSerializer<>(PurifyingFoodRecipe::new));
    public static final RegistryObject<RecipeSerializer<WorkbenchRecipe>> HEAVY_WORKBENCH = SERIALIZERS.register("heavy_workbench", () -> WorkbenchRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}