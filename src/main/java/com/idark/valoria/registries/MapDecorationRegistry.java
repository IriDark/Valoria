package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.saveddata.maps.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class MapDecorationRegistry{
    public static final DeferredRegister<MapDecorationType> MAP_DECO = DeferredRegister.create(Registries.MAP_DECORATION_TYPE, Valoria.ID);

    public static final Holder<MapDecorationType> CRYPT = register("crypt", "crypt", true, MapColor.COLOR_GRAY.col, false, true);
    public static final Holder<MapDecorationType> BOSS = register("boss", "boss", true, MapColor.COLOR_LIGHT_GRAY.col, false, true);
    public static final Holder<MapDecorationType> FORTRESS = register("fortress", "fortress", true, MapColor.COLOR_PURPLE.col, false, true);

    private static Holder<MapDecorationType> register(String name, String assetId, boolean showOnItemFrame, boolean trackCount) {
        return register(name, assetId, showOnItemFrame, -1, trackCount, false);
    }

    private static Holder<MapDecorationType> register(String name, String assetId, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount) {
        return MAP_DECO.register(name, () -> new MapDecorationType(Valoria.loc(assetId), showOnItemFrame, mapColor, trackCount, explorationMapElement));
    }

    public static void register(IEventBus eventBus){
        MAP_DECO.register(eventBus);
    }
}