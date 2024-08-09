package com.idark.valoria.registries.recipe;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class PotionBrewery extends PotionBrewing{
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Valoria.ID);
    public static final RegistryObject<Potion> ALOE_POTION = POTIONS.register("aloe_potion",
    () -> new Potion(new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 3600, 0)));

    // Uses the Access Transformer to process recipes
    public static void bootStrap(){
        addMix(Potions.WATER, ItemsRegistry.ALOE_PIECE.get(), PotionBrewery.ALOE_POTION.get());
    }

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}