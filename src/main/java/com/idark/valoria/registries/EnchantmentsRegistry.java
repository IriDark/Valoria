package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.enchantments.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

public class EnchantmentsRegistry{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Valoria.ID);
    public static final EnchantmentCategory BLAZE = EnchantmentCategory.create("blaze", item -> item instanceof BlazeReapItem);

    public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = registerEnchantment("explosive_flame", ExplosiveFlameEnchantment::new);
    public static final RegistryObject<Enchantment> BLEEDING = registerEnchantment("bleeding", BleedingEnchantment::new);

    private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment){
        return ENCHANTMENTS.register(id, enchantment);
    }

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }
}