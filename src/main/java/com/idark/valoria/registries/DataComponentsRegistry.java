package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.mojang.serialization.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class DataComponentsRegistry{
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Valoria.ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SOULS = intComponent("souls");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE = intComponent("charge");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> USAGE_COUNT = intComponent("usage_count");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> POISON_HITS = intComponent("poison_hits");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROT = intComponent("rot");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> EYE_STATE = intComponent("eye_state");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DISPLAY_COLOR = intComponent("display_color");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> NIGHT_ACTIVE = boolComponent("night_active");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> DARK_ACTIVE = boolComponent("dark_active");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TOGGLE_STATE = boolComponent("toggle_state");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> BAR_VISIBLE = boolComponent("bar_visible");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SOUND_PLAYED = boolComponent("sound_played");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> ORIGINAL_ITEM = COMPONENTS.registerComponentType("original_item", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<net.minecraft.core.GlobalPos>> RIFT_TARGET = COMPONENTS.registerComponentType("rift_target", builder -> builder.persistent(net.minecraft.core.GlobalPos.CODEC).networkSynchronized(net.minecraft.core.GlobalPos.STREAM_CODEC));

    private static DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> intComponent(String name){
        return COMPONENTS.registerComponentType(name, builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    }

    private static DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> boolComponent(String name){
        return COMPONENTS.registerComponentType(name, builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    }

    public static void register(IEventBus eventBus){
        COMPONENTS.register(eventBus);
    }

    public static int getInt(ItemStack stack, DataComponentType<Integer> type){
        return stack.getOrDefault(type, 0);
    }

    public static boolean has(ItemStack stack, DataComponentType<?> type){
        return stack.has(type);
    }

    public static boolean getBool(ItemStack stack, DataComponentType<Boolean> type){
        return stack.getOrDefault(type, false);
    }

    public static void copyState(ItemStack from, ItemStack to){
        for(var entry : from.getComponents()){
            if(entry.type().equals(DataComponents.CUSTOM_DATA) || Valoria.ID.equals(keyOf(entry.type()).getNamespace())){
                copyEntry(entry, to);
            }
        }
    }

    private static <T> void copyEntry(TypedDataComponent<T> entry, ItemStack to){
        to.set(entry.type(), entry.value());
    }

    private static ResourceLocation keyOf(DataComponentType<?> type){
        ResourceLocation key = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type);
        return key == null ? ResourceLocation.withDefaultNamespace("unknown") : key;
    }
}
