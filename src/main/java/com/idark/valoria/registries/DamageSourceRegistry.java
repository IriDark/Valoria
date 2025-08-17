package com.idark.valoria.registries;


import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.level.*;

public class DamageSourceRegistry{
    public static final ResourceKey<DamageType> BLEEDING = register("bleeding");
    public static final ResourceKey<DamageType> QUICKSAND_SUFFOCATING = register("quicksand_suffocating");
    public static final ResourceKey<DamageType> VOIDTHORN = register("voidthorn");
    public static final ResourceKey<DamageType> VOID = register("void");

    public static DamageSource voidHarm(Level level) {
        return new DamageSource(source(level, DamageSourceRegistry.VOID).typeHolder());
    }

    public static DamageSource bleeding(Level level) {
        return new DamageSource(source(level, DamageSourceRegistry.BLEEDING).typeHolder());
    }

    public static DamageSource quicksand(Level level) {
        return new DamageSource(source(level, DamageSourceRegistry.QUICKSAND_SUFFOCATING).typeHolder());
    }

    public static DamageSource voidthorn(Level level) {
        return new DamageSource(source(level, DamageSourceRegistry.VOIDTHORN).typeHolder());
    }

    private static ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Valoria.ID, name));
    }

    public static DamageSource source(Level level, ResourceKey<DamageType> key){
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}