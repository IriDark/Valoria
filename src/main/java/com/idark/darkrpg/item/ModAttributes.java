package com.idark.darkrpg.item;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, DarkRPG.MOD_ID);
    public static final RegistryObject<Attribute> PROJECTILE_DAMAGE = ATTRIBUTES.register("projectile_damage", () -> new RangedAttribute("tooltip.darkrpg.projectile_damage", 2.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final UUID PROJECTILE_DAMAGE_MODIFIER = UUID.fromString("FBB74FC9-47A9-4CF6-B82D-97EA75FFFBCA");

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}