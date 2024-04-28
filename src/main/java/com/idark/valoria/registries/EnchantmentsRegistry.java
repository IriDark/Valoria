package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.item.enchantments.BleedingEnchantment;
import com.idark.valoria.registries.item.enchantments.ExplosiveFlameEnchantment;
import com.idark.valoria.registries.item.enchantments.RadiusEnchantment;
import com.idark.valoria.registries.item.types.BlazeReapItem;
import com.idark.valoria.registries.item.types.IRadiusItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EnchantmentsRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Valoria.ID);

    public static final EnchantmentCategory RADIUS_WEAPON = EnchantmentCategory.create("radius_weapon", item -> item instanceof IRadiusItem);
    public static final EnchantmentCategory BLAZE = EnchantmentCategory.create("blaze", item -> item instanceof BlazeReapItem);

    public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = registerEnchantment("explosive_flame", ExplosiveFlameEnchantment::new);
    public static final RegistryObject<Enchantment> RADIUS = registerEnchantment("radius", RadiusEnchantment::new);
    public static final RegistryObject<Enchantment> BLEEDING = registerEnchantment("bleeding", BleedingEnchantment::new);

    private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment) {
        return ENCHANTMENTS.register(id, enchantment);
    }

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}