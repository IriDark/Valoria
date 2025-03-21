package com.idark.valoria.registries;

import com.google.common.collect.ImmutableSet;
import com.idark.valoria.Valoria;
import com.idark.valoria.registries.entity.npc.VillagerProfessionRegistry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class MiscRegistry{
    public static final DeferredRegister<PaintingVariant> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Valoria.ID);
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Valoria.ID);

    public static final RegistryObject<PaintingVariant> BIG_MOUNTAINS = PAINTING_TYPES.register("big_mountains", () -> new PaintingVariant(32, 48));
    public static final RegistryObject<PaintingVariant> FOREST_LONG = PAINTING_TYPES.register("forest_long", () -> new PaintingVariant(48, 16));
    public static final RegistryObject<PaintingVariant> HILLS = PAINTING_TYPES.register("hills", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> WINTER = PAINTING_TYPES.register("winter", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> NETHER = PAINTING_TYPES.register("nether", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> MOUNTAINS = PAINTING_TYPES.register("mountains", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> END = PAINTING_TYPES.register("end", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE0 = PAINTING_TYPES.register("cave0", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE1 = PAINTING_TYPES.register("cave1", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE2 = PAINTING_TYPES.register("cave2", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CAVE3 = PAINTING_TYPES.register("cave3", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> SAURON = PAINTING_TYPES.register("sauron", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> FOREST = PAINTING_TYPES.register("forest", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> VILLAGE = PAINTING_TYPES.register("village", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> SAURON2 = PAINTING_TYPES.register("sauron2", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> HOUSE = PAINTING_TYPES.register("house", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> EMERALD = PAINTING_TYPES.register("emerald", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> THE_STARRY_NIGHT = PAINTING_TYPES.register("starry_night", () -> new PaintingVariant(32, 32));

    public static final RegistryObject<PoiType> VALORIA_PORTAL = POI.register("valoria_portal", () -> register(getBlockStates(BlockRegistry.valoriaPortal.get()), 0, 1));
    public static final RegistryObject<PoiType> JEWELER = POI.register("jeweler", () -> register(getBlockStates(BlockRegistry.jewelerTable.get()), 1, 1));

    private static Set<BlockState> getBlockStates(Block pBlock){
        return ImmutableSet.copyOf(pBlock.getStateDefinition().getPossibleStates());
    }

    private static PoiType register(Set<BlockState> pMatchingStates, int pMaxTickets, int pValidRange){
        return new PoiType(pMatchingStates, pMaxTickets, pValidRange);
    }

    public static void init(IEventBus eventBus){
        PAINTING_TYPES.register(eventBus);
        POI.register(eventBus);
        VillagerProfessionRegistry.register(eventBus);
    }
}
