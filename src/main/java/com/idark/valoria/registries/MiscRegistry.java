package com.idark.valoria.registries;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.entity.npc.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;

public class MiscRegistry{
    public static final ResourceKey<PaintingVariant> BIG_MOUNTAINS = painting("big_mountains");
    public static final ResourceKey<PaintingVariant> FOREST_LONG = painting("forest_long");
    public static final ResourceKey<PaintingVariant> HILLS = painting("hills");
    public static final ResourceKey<PaintingVariant> WINTER = painting("winter");
    public static final ResourceKey<PaintingVariant> NETHER = painting("nether");
    public static final ResourceKey<PaintingVariant> MOUNTAINS = painting("mountains");
    public static final ResourceKey<PaintingVariant> END = painting("end");
    public static final ResourceKey<PaintingVariant> CAVE0 = painting("cave0");
    public static final ResourceKey<PaintingVariant> CAVE1 = painting("cave1");
    public static final ResourceKey<PaintingVariant> CAVE2 = painting("cave2");
    public static final ResourceKey<PaintingVariant> CAVE3 = painting("cave3");
    public static final ResourceKey<PaintingVariant> SAURON = painting("sauron");
    public static final ResourceKey<PaintingVariant> FOREST = painting("forest");
    public static final ResourceKey<PaintingVariant> VILLAGE = painting("village");
    public static final ResourceKey<PaintingVariant> SAURON2 = painting("sauron2");
    public static final ResourceKey<PaintingVariant> HOUSE = painting("house");
    public static final ResourceKey<PaintingVariant> EMERALD = painting("emerald");
    public static final ResourceKey<PaintingVariant> THE_STARRY_NIGHT = painting("starry_night");
    public static final ResourceKey<PaintingVariant> MOUNTAIN_LANDSCAPE = painting("mountain_landscape");
    public static final List<ResourceKey<PaintingVariant>> PAINTINGS = List.of(BIG_MOUNTAINS, FOREST_LONG, HILLS, WINTER, NETHER, MOUNTAINS, END, CAVE0, CAVE1, CAVE2, CAVE3, SAURON, FOREST, VILLAGE, SAURON2, HOUSE, EMERALD, THE_STARRY_NIGHT, MOUNTAIN_LANDSCAPE);

    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Valoria.ID);
    public static final DeferredHolder<PoiType, PoiType> VALORIA_PORTAL = POI.register("valoria_portal", () -> register(getBlockStates(BlockRegistry.valoriaPortal.get()), 0, 1));
    public static final DeferredHolder<PoiType, PoiType> JEWELER = POI.register("jeweler", () -> register(getBlockStates(BlockRegistry.jewelerTable.get()), 1, 1));

    private static ResourceKey<PaintingVariant> painting(String name){
        return ResourceKey.create(Registries.PAINTING_VARIANT, Valoria.loc(name));
    }

    private static Set<BlockState> getBlockStates(Block pBlock){
        return ImmutableSet.copyOf(pBlock.getStateDefinition().getPossibleStates());
    }

    private static PoiType register(Set<BlockState> pMatchingStates, int pMaxTickets, int pValidRange){
        return new PoiType(pMatchingStates, pMaxTickets, pValidRange);
    }

    public static void init(IEventBus eventBus){
        POI.register(eventBus);
        VillagerProfessionRegistry.register(eventBus);
    }
}
