package com.idark.valoria.world;

import com.idark.valoria.Valoria;
import com.idark.valoria.world.tree.ShadeWoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.createOptional(Registries.TRUNK_PLACER_TYPE, Valoria.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<ShadeWoodTrunkPlacer>> SHADEWOOD_TRUNK_PLACER_TYPE = TRUNK_PLACER_TYPES.register("shadewood_trunk_placer", () -> new TrunkPlacerType<>(ShadeWoodTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER_TYPES.register(eventBus);
    }
}
