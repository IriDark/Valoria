package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class RecipesRegistry{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Valoria.ID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<KegRecipe>> KEG_SERIALIZER = SERIALIZERS.register("keg_brewery", () -> KegRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<JewelryRecipe>> JEWELRY_SERIALIZER = SERIALIZERS.register("jewelry", () -> JewelryRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER = SERIALIZERS.register("crusher", () -> CrusherRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ManipulatorRecipe>> MANIPULATOR_SERIALIZER = SERIALIZERS.register("manipulator", () -> ManipulatorRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TinkeringRecipe>> TINKERING_SERIALIZER = SERIALIZERS.register("tinkering", () -> TinkeringRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<KilnRecipe>> KILN_SERIALIZER = SERIALIZERS.register("kiln", () -> KilnRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SoulInfuserRecipe>> SOUL_INFUSER_SERIALIZER = SERIALIZERS.register("soul_infuser", () -> SoulInfuserRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PoisonWeaponRecipe>> POISON_RECIPE = SERIALIZERS.register("weapon_poisoning",  () -> new SimpleCraftingRecipeSerializer<>(PoisonWeaponRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PurifyingFoodRecipe>> PURIFY_RECIPE = SERIALIZERS.register("purifying_food",  () -> new SimpleCraftingRecipeSerializer<>(PurifyingFoodRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WorkbenchRecipe>> HEAVY_WORKBENCH = SERIALIZERS.register("heavy_workbench", () -> WorkbenchRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlchemyRecipe>> ALCHEMY = SERIALIZERS.register("alchemy", () -> AlchemyRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlchemyUpgradeRecipe>> ALCHEMY_UPGRADE = SERIALIZERS.register("alchemy_upgrade", () -> AlchemyUpgradeRecipe.Serializer.INSTANCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HeavyAnvilRecipe>> HEAVY_ANVIL = SERIALIZERS.register("heavy_anvil", () -> HeavyAnvilRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}