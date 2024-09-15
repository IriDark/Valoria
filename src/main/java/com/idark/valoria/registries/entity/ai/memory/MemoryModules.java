package com.idark.valoria.registries.entity.ai.memory;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.entity.living.Succubus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class MemoryModules {
    public static final DeferredRegister<MemoryModuleType<?>> MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Valoria.ID);
    public static final RegistryObject<MemoryModuleType<List<Succubus>>> NEARBY_ADULT_SUCCUBUS = MODULE_TYPES.register("nearby_adult_succubs", () -> new MemoryModuleType<>(Optional.empty()));

    public static void register(IEventBus eventBus) {
        MODULE_TYPES.register(eventBus);
    }
}