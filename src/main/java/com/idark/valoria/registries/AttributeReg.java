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

    public static final RegistryObject<Attribute> ELEMENTAL_RESISTANCE = ATTRIBUTES.register("elemental_resistance", () -> new RangedAttribute("attribute.valoria.elemental_resistance", 0.0D, -100.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> NATURE_DAMAGE = ATTRIBUTES.register("nature_damage", () -> new RangedAttribute("attribute.valoria.nature_damage", 0.0D, 0.0D, 1024.0D));
    public static final RegistryObject<Attribute> NATURE_RESISTANCE = ATTRIBUTES.register("nature_resistance", () -> new RangedAttribute("attribute.valoria.nature_resistance", 0.0D, -100.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DEPTH_DAMAGE = ATTRIBUTES.register("depth_damage", () -> new RangedAttribute("attribute.valoria.depth_damage", 0.0D, 0.0D, 1024.0D));
    public static final RegistryObject<Attribute> DEPTH_RESISTANCE = ATTRIBUTES.register("depth_resistance", () -> new RangedAttribute("attribute.valoria.depth_resistance", 0.0D, -100.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> INFERNAL_DAMAGE = ATTRIBUTES.register("infernal_damage", () -> new RangedAttribute("attribute.valoria.infernal_damage", 0.0D, 0.0D, 1024.0D));
    public static final RegistryObject<Attribute> INFERNAL_RESISTANCE = ATTRIBUTES.register("infernal_resistance", () -> new RangedAttribute("attribute.valoria.infernal_resistance", 0.0D, -100.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> NIHILITY_DAMAGE = ATTRIBUTES.register("nihility_damage", () -> new RangedAttribute("attribute.valoria.nihility_damage", 0.0D, 0.0D, 1024.0D));
    public static final RegistryObject<Attribute> NIHILITY_RESISTANCE = ATTRIBUTES.register("nihility_resistance", () -> new RangedAttribute("attribute.valoria.nihility_resistance", 0.0D, -100.0D, 100.0D).setSyncable(true));

    public static final RegistryObject<Attribute> CRIT_DAMAGE = register("crit_damage", 2, 0, 2048.0D);
    public static final RegistryObject<Attribute> RANGED_CRIT_DAMAGE = register("ranged_crit_damage", 2, 0, 2048.0D);
    public static final RegistryObject<Attribute> CRIT_CHANCE = registerSync("crit_chance", 0, 0, 100);
    public static final RegistryObject<Attribute> RANGED_CRIT_CHANCE = registerSync("ranged_crit_chance", 0, 0, 100);
    public static final RegistryObject<Attribute> DODGE_CHANCE = registerSync("dodge_chance", 0.0D, 0, 100.0D);
    public static final RegistryObject<Attribute> MISS_CHANCE = registerSync("miss_chance", 0.0D, 0, 100.0D);

    public static final RegistryObject<Attribute> MAX_NIHILITY = ATTRIBUTES.register("max_nihility", () -> new RangedAttribute("attribute.valoria.max_nihility", 100.0D, 0.0D, 10000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> NIHILITY_RESILIENCE = ATTRIBUTES.register("nihility_resilience", () -> new RangedAttribute("attribute.valoria.nihility_resilience", 5.0D, 1, 100.0D).setSyncable(true));

    public record ElementalType(RegistryObject<Attribute> damageAttr, RegistryObject<Attribute> resistAttr) {}

    private static RegistryObject<Attribute> register(String name, double pDefaultValue, double pMin, double pMax) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute("attribute.valoria." + name, pDefaultValue, pMin, pMax));
    }

    private static RegistryObject<Attribute> registerSync(String name, double pDefaultValue, double pMin, double pMax) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute("attribute.valoria." + name, pDefaultValue, pMin, pMax).setSyncable(true));
    }

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }
}