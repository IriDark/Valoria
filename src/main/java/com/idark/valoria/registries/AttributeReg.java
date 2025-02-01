package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class AttributeReg{
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, Valoria.ID);
    public static final RegistryObject<Attribute> DASH_DISTANCE = ATTRIBUTES.register("dash_distance", () -> new RangedAttribute("attribute.valoria.dash_distance", 0.0D, 0.0D, 128.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ATTACK_RADIUS = ATTRIBUTES.register("attack_radius", () -> new RangedAttribute("attribute.valoria.attack_radius", 0.0D, 0.0D, 32.0D).setSyncable(true));
    public static final RegistryObject<Attribute> EXCAVATION_SPEED = ATTRIBUTES.register("excavation_speed", () -> new RangedAttribute("attribute.valoria.excavation_speed", 2.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> NECROMANCY_LIFETIME = ATTRIBUTES.register("necromancy_lifetime", () -> new RangedAttribute("attribute.valoria.necromancy_lifetime", 0.0D, 0.0D, 4028.0D).setSyncable(true));
    public static final RegistryObject<Attribute> NECROMANCY_COUNT = ATTRIBUTES.register("necromancy_count", () -> new RangedAttribute("attribute.valoria.necromancy_count", 0.0D, 0.0D, 15.0D).setSyncable(true));

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }
}