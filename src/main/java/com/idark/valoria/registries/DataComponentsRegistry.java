package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.mojang.serialization.*;
import net.minecraft.core.component.*;
import net.minecraft.nbt.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class DataComponentsRegistry{
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Valoria.ID);

    /** Souls stored on soul collectors, void crystals, ethereal swords ({@code Souls}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SOULS = intComponent("souls");
    /** Magma sword / ethereal sword charges ({@code charge}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE = intComponent("charge");
    /** Scythe multi-attack usage counter ({@code usageCount}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> USAGE_COUNT = intComponent("usage_count");
    /** Remaining poisoned hits on a weapon ({@code poison_hits}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> POISON_HITS = intComponent("poison_hits");
    /** Food rot progress 0..100 ({@code ValoriaRot}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROT = intComponent("rot");
    /** Eye necklace animation state 0..3 ({@code EyeState}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> EYE_STATE = intComponent("eye_state");
    /** Summon book display colour ({@code DisplayColor.color}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DISPLAY_COLOR = intComponent("display_color");

    /** Celestial necklace night flag ({@code IsNightActive}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> NIGHT_ACTIVE = boolComponent("night_active");
    /** Eye necklace darkness flag ({@code IsDarkActive}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> DARK_ACTIVE = boolComponent("dark_active");
    /** Nihility monitor toggle ({@code ToggleState}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TOGGLE_STATE = boolComponent("toggle_state");
    /** Phantasm bow overcharge bar visibility ({@code isVisible}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> BAR_VISIBLE = boolComponent("bar_visible");
    /** Phantasm bow overcharge sound flag ({@code isPlayed}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SOUND_PLAYED = boolComponent("sound_played");

    /** Item a rot item was made from ({@code OriginalItem}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> ORIGINAL_ITEM = COMPONENTS.registerComponentType("original_item",
        builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    /** Bound target for rift ring ({@code targetPositionX/Y/Z}, {@code targetDimension}). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<net.minecraft.core.GlobalPos>> RIFT_TARGET = COMPONENTS.registerComponentType("rift_target",
        builder -> builder.persistent(net.minecraft.core.GlobalPos.CODEC).networkSynchronized(net.minecraft.core.GlobalPos.STREAM_CODEC));

    private static DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> intComponent(String name){
        return COMPONENTS.registerComponentType(name, builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    }

    private static DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> boolComponent(String name){
        return COMPONENTS.registerComponentType(name, builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    }

    public static void register(IEventBus eventBus){
        COMPONENTS.register(eventBus);
    }

    private static CompoundTag legacyTag(ItemStack stack){
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    /** Int component with fallback to the pre-1.21 root-NBT key stored in {@code minecraft:custom_data}. */
    public static int getInt(ItemStack stack, DataComponentType<Integer> type, String legacyKey){
        Integer value = stack.get(type);
        if(value != null) return value;
        CompoundTag legacy = legacyTag(stack);
        return legacy.contains(legacyKey) ? legacy.getInt(legacyKey) : 0;
    }

    public static boolean has(ItemStack stack, DataComponentType<?> type, String legacyKey){
        return stack.has(type) || legacyTag(stack).contains(legacyKey);
    }

    public static void setInt(ItemStack stack, DataComponentType<Integer> type, int value){
        stack.set(type, value);
    }

    public static boolean getBool(ItemStack stack, DataComponentType<Boolean> type, String legacyKey){
        Boolean value = stack.get(type);
        if(value != null) return value;
        CompoundTag legacy = legacyTag(stack);
        return legacy.contains(legacyKey) && legacy.getBoolean(legacyKey);
    }

    public static void setBool(ItemStack stack, DataComponentType<Boolean> type, boolean value){
        stack.set(type, value);
    }

    /** Removes the component and, if present, the legacy key from {@code minecraft:custom_data}. */
    public static void remove(ItemStack stack, DataComponentType<?> type, String legacyKey){
        stack.remove(type);
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if(data != null && data.contains(legacyKey)){
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(legacyKey));
        }
    }

    /** Copies every Valoria component (and vanilla custom data) from {@code from} onto {@code to}; replaces {@code setTag(copy)}. */
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
        ResourceLocation key = net.minecraft.core.registries.BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type);
        return key == null ? ResourceLocation.withDefaultNamespace("unknown") : key;
    }
}
