package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class RecipesRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Valoria.ID);

    public static final RegistryObject<RecipeSerializer<KegRecipe>> KEG_SERIALIZER = SERIALIZERS.register("keg_brewery", () -> KegRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<JewelryRecipe>> JEWELRY_SERIALIZER = SERIALIZERS.register("jewelry", () -> JewelryRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER = SERIALIZERS.register("crusher", () -> CrusherRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ManipulatorRecipe>> MANIPULATOR_SERIALIZER = SERIALIZERS.register("manipulator", () -> ManipulatorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ArchaeologyRecipe>> ARCHAEOLOGY_SERIALIZER = SERIALIZERS.register("archaeology", () -> ArchaeologyRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}