package com.idark.darkrpg.damage;


import com.idark.darkrpg.DarkRPG;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModDamageSources {
    public static final DeferredRegister<DamageType> DAMAGE_TYPE_REGISTER = DeferredRegister.create(Registries.DAMAGE_TYPE, DarkRPG.MOD_ID);

    public static final ResourceKey<DamageType> BLEEDING = register("bleeding");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DarkRPG.MOD_ID, name));
    }

    public static void register(IEventBus eventBus) {
        DAMAGE_TYPE_REGISTER.register(eventBus);
    }
}