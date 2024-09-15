package com.idark.valoria.registries.entity.ai.sensing;

import com.idark.valoria.Valoria;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SensorTypes {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, Valoria.ID);
    public static final RegistryObject<SensorType<SuccubusSpecificSensor>> SUCCUBUS_SPECIFIC_SENSOR = SENSOR_TYPES.register("succubus_specific_sensor", () -> new SensorType<>(SuccubusSpecificSensor::new));

    public static void register(IEventBus eventBus) {
        SENSOR_TYPES.register(eventBus);
    }
}
