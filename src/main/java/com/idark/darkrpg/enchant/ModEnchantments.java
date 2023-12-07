package com.idark.darkrpg.enchant;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DarkRPG.MOD_ID);

	public static final EnchantmentCategory BLAZE = EnchantmentCategory.create("blaze", item -> item instanceof BlazeReapItem);
	public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = ENCHANTMENTS.register("explosive_flame", ExplosiveFlameEnchantment::new);
	public static final RegistryObject<Enchantment> BLEEDING = ENCHANTMENTS.register("bleeding", BleedingEnchantment::new);

	private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment) {
		return ENCHANTMENTS.register(id, enchantment);
	}
	
	public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}