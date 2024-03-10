package com.idark.valoria.registries.world.entity.decoration;

import com.idark.valoria.Valoria;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Valoria.MOD_ID);

    public static final RegistryObject<PaintingVariant> BIG_MOUNTAINS =
            PAINTING_TYPES.register("big_mountains", () -> new PaintingVariant(32, 48));
    public static final RegistryObject<PaintingVariant> FOREST_LONG =
            PAINTING_TYPES.register("forest_long", () -> new PaintingVariant(48, 16));
    public static final RegistryObject<PaintingVariant> HILLS =
            PAINTING_TYPES.register("hills", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> WINTER =
            PAINTING_TYPES.register("winter", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> NETHER =
            PAINTING_TYPES.register("nether", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> MOUNTAINS =
            PAINTING_TYPES.register("mountains", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> END =
            PAINTING_TYPES.register("end", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE0 =
            PAINTING_TYPES.register("cave0", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE1 =
            PAINTING_TYPES.register("cave1", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE2 =
            PAINTING_TYPES.register("cave2", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE3 =
            PAINTING_TYPES.register("cave3", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> SAURON =
            PAINTING_TYPES.register("sauron", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> FOREST =
            PAINTING_TYPES.register("forest", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> VILLAGE =
            PAINTING_TYPES.register("village", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> SAURON2 =
            PAINTING_TYPES.register("sauron2", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> HOUSE =
            PAINTING_TYPES.register("house", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> EMERALD =
            PAINTING_TYPES.register("emerald", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> THE_STARRY_NIGHT =
            PAINTING_TYPES.register("starry_night", () -> new PaintingVariant(32, 32));

    public static void register(IEventBus eventBus) {
        PAINTING_TYPES.register(eventBus);
    }
}