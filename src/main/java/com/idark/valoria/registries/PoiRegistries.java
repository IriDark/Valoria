package com.idark.valoria.registries;

import com.google.common.collect.*;
import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class PoiRegistries{

    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Valoria.ID);

    public static final RegistryObject<PoiType> VALORIA_PORTAL = POI.register("arcana_portal", () -> new PoiType(ImmutableSet.copyOf(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Valoria.ID, "valoria_portal")).getStateDefinition().getPossibleStates()), 0, 1));

    public static void register(IEventBus eventBus){
        POI.register(eventBus);
    }
}
