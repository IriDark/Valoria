package com.idark.valoria.registries.recipe;

import com.idark.valoria.Valoria;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Valoria.MOD_ID);

    public static final RegistryObject<RecipeSerializer<KegRecipe>> KEG_SERIALIZER = SERIALIZERS
            .register("keg_brewery", () -> KegRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<JewelryRecipe>> JEWELRY_SERIALIZER = SERIALIZERS
            .register("jewelry", () -> JewelryRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER = SERIALIZERS
            .register("crusher", () -> CrusherRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}