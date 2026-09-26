package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.enchantments.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class EnchantmentsRegistry{
    public static final ResourceKey<Enchantment> EXPLOSIVE_FLAME = key("explosive_flame");
    public static final ResourceKey<Enchantment> BLEEDING = key("bleeding");
    public static final ResourceKey<Enchantment> ACCURACY = key("accuracy");
    public static final ResourceKey<Enchantment> SHOCK_ABSORPTION = key("shock_absorption");
    public static final ResourceKey<Enchantment> CONCUSSION = key("concussion");
    public static final ResourceKey<Enchantment> REPULSION = key("repulsion");
    public static final ResourceKey<Enchantment> COLLAPSE = key("collapse");
    public static final ResourceKey<Enchantment> SUNDERING = key("sundering");

    public static final TagKey<Item> BLAZE = TagKey.create(Registries.ITEM, Valoria.loc("enchantable/blaze"));
    public static final TagKey<Item> ACCURACY_CATEGORY = TagKey.create(Registries.ITEM, Valoria.loc("enchantable/accuracy"));
    public static final TagKey<Item> HAMMER_CATEGORY = TagKey.create(Registries.ITEM, Valoria.loc("hammer_category"));

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Valoria.ID);
    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<BleedingEnchantmentEffect>> BLEEDING_EFFECT = ENTITY_EFFECTS.register("bleeding", () -> BleedingEnchantmentEffect.CODEC);

    private static ResourceKey<Enchantment> key(String id){
        return ResourceKey.create(Registries.ENCHANTMENT, Valoria.loc(id));
    }

    /** Level of the given enchantment on the stack (0 when absent), resolved by key without needing registry access. */
    public static int getLevel(ItemStack stack, ResourceKey<Enchantment> key){
        return pro.komaru.tridot.common.registry.EnchantmentsRegistry.getLevel(stack, key);
    }

    public static int getLevel(Level level, ItemStack stack, ResourceKey<Enchantment> key){
        return pro.komaru.tridot.common.registry.EnchantmentsRegistry.getLevel(level, stack, key);
    }

    public static Holder<Enchantment> holder(Level level, ResourceKey<Enchantment> key){
        return pro.komaru.tridot.common.registry.EnchantmentsRegistry.holder(level.registryAccess(), key);
    }

    public static Holder<Enchantment> holder(HolderLookup.Provider registries, ResourceKey<Enchantment> key){
        return pro.komaru.tridot.common.registry.EnchantmentsRegistry.holder(registries, key);
    }

    public static void register(IEventBus eventBus){
        ENTITY_EFFECTS.register(eventBus);
    }
}