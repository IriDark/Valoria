package com.idark.darkrpg.item;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class ModItemGroup {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkRPG.MOD_ID);

	public static final RegistryObject<CreativeModeTab> DARKRPG_GROUP = CREATIVE_MODE_TABS.register("DarkRPGModTab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NATURE_PICKAXE.get()))
					.title(Component.translatable("itemGroup.DarkRPGModTab"))
					.backgroundSuffix("darkrpg_item_search.png").withBackgroundLocation(getTabsImage()).build());

	public static final RegistryObject<CreativeModeTab> DARKRPG_BLOCKS_GROUP = CREATIVE_MODE_TABS.register("DarkRPGBlocksModTab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOID_STONE.get()))
					.title(Component.translatable("itemGroup.DarkRPGBlocksModTab"))
					.backgroundSuffix("darkrpg_item_search.png").withBackgroundLocation(getTabsImage()).build());

	public static ResourceLocation getTabsImage() {
		return new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/tabs_darkrpg.png");
	}

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}

	public static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ModItemGroup.DARKRPG_GROUP.getKey()) {
			event.accept(ModItems.NATURE_PICKAXE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_STONE);
		}
	}
}