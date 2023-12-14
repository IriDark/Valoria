package com.idark.darkrpg.potion;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions extends PotionBrewing {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, DarkRPG.MOD_ID);

    public static final RegistryObject<Potion> ALOE_POTION = POTIONS.register("aloe_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ALOEREGEN.get(), 3600, 0)));

    // Uses the Access Transformer to process recipes
    public static void bootStrap() {
        addMix(Potions.WATER, ModItems.ALOE_PIECE.get(), ModPotions.ALOE_POTION.get());
    }

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}