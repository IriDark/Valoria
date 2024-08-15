package com.idark.valoria.registries.entity.ai.memory;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class MemoryModules{
    public static final DeferredRegister<MemoryModuleType<?>> MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Valoria.ID);
    public static final RegistryObject<MemoryModuleType<List<Succubus>>> NEARBY_ADULT_SUCCUBUS = MODULE_TYPES.register("nearby_adult_succubs", () -> new MemoryModuleType<>(Optional.empty()));

    public static void register(IEventBus eventBus){
        MODULE_TYPES.register(eventBus);
    }
}