package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.item.recipe.CrusherRecipe;
import com.idark.valoria.registries.item.recipe.JewelryRecipe;
import com.idark.valoria.registries.item.recipe.KegRecipe;
import com.idark.valoria.registries.item.recipe.ManipulatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipesRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Valoria.ID);

    public static final RegistryObject<RecipeSerializer<KegRecipe>> KEG_SERIALIZER = SERIALIZERS.register("keg_brewery", () -> KegRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<JewelryRecipe>> JEWELRY_SERIALIZER = SERIALIZERS.register("jewelry", () -> JewelryRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER = SERIALIZERS.register("crusher", () -> CrusherRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ManipulatorRecipe>> MANIPULATOR_SERIALIZER = SERIALIZERS.register("manipulator", () -> ManipulatorRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}