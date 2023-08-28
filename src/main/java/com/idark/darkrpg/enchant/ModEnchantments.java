package com.idark.darkrpg.enchant;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.*;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DarkRPG.MOD_ID);

	public static final EnchantmentType BLAZE = EnchantmentType.create("blaze", item -> item instanceof BlazeReapItem);
	public static final EnchantmentType KUNAI = EnchantmentType.create("kunai", item -> item instanceof KunaiItem || item instanceof PoisonedKunaiItem);
    public static final RegistryObject<Enchantment> EXPLOSIVE_FLAME = ENCHANTMENTS.register("explosive_flame", ExplosiveFlameEnchantment::new);
    public static final RegistryObject<Enchantment> FLOW_ENCHANT = ENCHANTMENTS.register("flow", FlowEnchantment::new);

	private static RegistryObject<Enchantment> registerEnchantment(String id, Supplier<Enchantment> enchantment) {
		return ENCHANTMENTS.register(id, enchantment);
	}
	
	public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}