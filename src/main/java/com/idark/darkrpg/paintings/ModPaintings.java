package com.idark.darkrpg.paintings;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

	public class ModPaintings {
	public static final DeferredRegister<PaintingType> PAINTING_TYPES =
	DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, DarkRPG.MOD_ID);
	
	public static final RegistryObject<PaintingType> BIG_MOUNTAINS =
	PAINTING_TYPES.register("big_mountains", () -> new PaintingType(32, 48));
	public static final RegistryObject<PaintingType> FOREST_LONG =
	PAINTING_TYPES.register("forest_long", () -> new PaintingType(48, 16));
	public static final RegistryObject<PaintingType> HILLS =
	PAINTING_TYPES.register("hills", () -> new PaintingType(16, 32));
	public static final RegistryObject<PaintingType> WINTER =
	PAINTING_TYPES.register("winter", () -> new PaintingType(16, 32));
	public static final RegistryObject<PaintingType> NETHER =
	PAINTING_TYPES.register("nether", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> MOUNTAINS =
	PAINTING_TYPES.register("mountains", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> END =
	PAINTING_TYPES.register("end", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> CAVE0 =
	PAINTING_TYPES.register("cave0", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> CAVE1 =
	PAINTING_TYPES.register("cave1", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> CAVE2 =
	PAINTING_TYPES.register("cave2", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> CAVE3 =
	PAINTING_TYPES.register("cave3", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> SAURON =
	PAINTING_TYPES.register("sauron", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> FOREST =
	PAINTING_TYPES.register("forest", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> VILLAGE =
	PAINTING_TYPES.register("village", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> SAURON2 =
	PAINTING_TYPES.register("sauron2", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> HOUSE =
	PAINTING_TYPES.register("house", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> EMERALD =
	PAINTING_TYPES.register("emerald", () -> new PaintingType(16, 16));
	
	public static void register(IEventBus eventBus) {
	PAINTING_TYPES.register(eventBus);
	}
}