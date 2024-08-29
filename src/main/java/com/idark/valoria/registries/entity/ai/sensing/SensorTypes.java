package com.idark.valoria.registries.entity.ai.sensing;

import com.idark.valoria.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class SensorTypes{
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, Valoria.ID);
    public static final RegistryObject<SensorType<SuccubusSpecificSensor>> SUCCUBUS_SPECIFIC_SENSOR = SENSOR_TYPES.register("succubus_specific_sensor", () -> new SensorType<>(SuccubusSpecificSensor::new));

    public static void register(IEventBus eventBus){
        SENSOR_TYPES.register(eventBus);
    }
}
