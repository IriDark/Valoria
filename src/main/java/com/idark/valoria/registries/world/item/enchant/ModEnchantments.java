package com.idark.valoria.registries.world.item.enchant;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.item.enchant.types.BleedingEnchantment;
import com.idark.valoria.registries.world.item.enchant.types.ExplosiveFlameEnchantment;
import com.idark.valoria.registries.world.item.enchant.types.RadiusEnchantment;
import com.idark.valoria.registries.world.item.types.BlazeReapItem;
import com.idark.valoria.registries.world.item.types.MagmaSwordItem;
import com.idark.valoria.registries.world.item.types.PhantomItem;
import com.idark.valoria.registries.world.item.types.ScytheItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Valoria.MOD_ID);

    public static final EnchantmentCategory RADIUS_WEAPON = EnchantmentCategory.create("radius_weapon", item -> item instanceof ScytheItem || item instanceof PhantomItem || item instanceof MagmaSwordItem);

    public static final EnchantmentCategory BLAZE = EnchantmentCategory.create("blaze", item -> item instanceof BlazeReapItem);
    public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = ENCHANTMENTS.register("explosive_flame", ExplosiveFlameEnchantment::new);
    public static final RegistryObject<Enchantment> RADIUS = ENCHANTMENTS.register("radius", RadiusEnchantment::new);
    public static final RegistryObject<Enchantment> BLEEDING = ENCHANTMENTS.register("bleeding", BleedingEnchantment::new);

    private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment) {
        return ENCHANTMENTS.register(id, enchantment);
    }

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}