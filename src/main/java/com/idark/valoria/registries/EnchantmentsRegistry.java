package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.interfaces.CooldownReductionItem;
import com.idark.valoria.core.interfaces.DashItem;
import com.idark.valoria.core.interfaces.RadiusItem;
import com.idark.valoria.registries.item.enchantments.*;
import com.idark.valoria.registries.item.types.ranged.BlazeReapItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EnchantmentsRegistry{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Valoria.ID);
    public static final EnchantmentCategory DASH_WEAPON = EnchantmentCategory.create("radius_weapon", item -> item instanceof DashItem);
    public static final EnchantmentCategory RADIUS_WEAPON = EnchantmentCategory.create("radius_weapon", item -> item instanceof RadiusItem);
    public static final EnchantmentCategory BLAZE = EnchantmentCategory.create("blaze", item -> item instanceof BlazeReapItem);
    public static final EnchantmentCategory OVERDRIVE_CATEGORY = EnchantmentCategory.create("overdrive", item -> item instanceof CooldownReductionItem);

    public static final RegistryObject<Enchantment> DASH = registerEnchantment("dash", DashEnchantment::new);
    public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = registerEnchantment("explosive_flame", ExplosiveFlameEnchantment::new);
    public static final RegistryObject<Enchantment> RADIUS = registerEnchantment("radius", RadiusEnchantment::new);
    public static final RegistryObject<Enchantment> BLEEDING = registerEnchantment("bleeding", BleedingEnchantment::new);
    public static final RegistryObject<Enchantment> OVERDRIVE = registerEnchantment("overdrive", OverdriveEnchantment::new);

    private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment){
        return ENCHANTMENTS.register(id, enchantment);
    }

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }
}