package com.idark.valoria.registries.entity.ai.attributes;

import com.idark.valoria.Valoria;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, Valoria.MOD_ID);
    public static final RegistryObject<Attribute> PROJECTILE_DAMAGE = ATTRIBUTES.register("projectile_damage", () -> new RangedAttribute("attribute.valoria.projectile_damage", 2.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> EXCAVATION_SPEED = ATTRIBUTES.register("excavation_speed", () -> new RangedAttribute("attribute.valoria.excavation_speed", 2.0D, 0.0D, 1024.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}