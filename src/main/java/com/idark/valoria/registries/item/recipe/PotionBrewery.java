package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.alchemy.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.brewing.*;
import net.neoforged.neoforge.registries.*;

public class PotionBrewery{
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Valoria.ID);
    public static final DeferredHolder<Potion, Potion> ALOE_POTION = POTIONS.register("aloe_potion",
            () -> new Potion("aloe_potion", new MobEffectInstance(EffectsRegistry.ALOEREGEN, 3600, 0)));

    public static void bootStrap(){
        NeoForge.EVENT_BUS.addListener(PotionBrewery::onRegisterBrewingRecipes);
    }

    private static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event){
        event.getBuilder().addMix(Potions.WATER, ItemsRegistry.aloePiece.get(), PotionBrewery.ALOE_POTION);
    }

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
