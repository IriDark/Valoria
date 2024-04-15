package com.idark.valoria.registries;

import com.google.common.collect.ImmutableSet;
import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PoiRegistries {

    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Valoria.MOD_ID);

    public static final RegistryObject<PoiType> VALORIA_PORTAL = POI.register("arcana_portal", () -> new PoiType(ImmutableSet.copyOf(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Valoria.MOD_ID, "valoria_portal")).getStateDefinition().getPossibleStates()), 0, 1));

    public static void register(IEventBus eventBus) {
        POI.register(eventBus);
    }
}
