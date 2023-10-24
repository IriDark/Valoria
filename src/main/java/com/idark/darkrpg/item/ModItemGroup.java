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

	public static final RegistryObject<CreativeModeTab> DARKRPG_GROUP = CREATIVE_MODE_TABS.register("darkrpgmodtab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NATURE_PICKAXE.get()))
					.hideTitle()
					.title(Component.translatable("itemGroup.DarkRPGModTab"))
					.withTabsImage(getTabsImage())
					.backgroundSuffix("darkrpg_item_search.png").withBackgroundLocation(getBackgroundImage()).build());

	public static final RegistryObject<CreativeModeTab> DARKRPG_BLOCKS_GROUP = CREATIVE_MODE_TABS.register("darkrpgblocksmodtab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOID_STONE.get()))
					.hideTitle()
					.title(Component.translatable("itemGroup.DarkRPGBlocksModTab"))
					.withTabsImage(getTabsImage())
					.backgroundSuffix("darkrpg_item_search.png").withBackgroundLocation(getBackgroundImage()).build());

	public static ResourceLocation getTabsImage() {
		return new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/tabs_darkrpg.png");
	}

	public static ResourceLocation getBackgroundImage() {
		return new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/tab_darkrpg_item_search.png");
	}

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}

	public static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ModItemGroup.DARKRPG_GROUP.getKey()) {
			event.accept(ModItems.ALOE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.ALOE);
			event.accept(ModItems.ALOE_SMALL);
			event.accept(ModItems.CATTAIL);
			event.accept(ModItems.KARUSAKAN_ROOTS);
			event.accept(ModItems.GAIB_ROOTS);
			event.accept(ModItems.DRIED_PLANT);
			event.accept(ModItems.DRIED_ROOTS);
			event.accept(ModItems.CRIMSON_SOULROOT);
			event.accept(ModItems.DOUBLE_SOULROOT);
			event.accept(ModItems.MAGMAROOT);
			event.accept(ModItems.DOUBLE_MAGMAROOT);
			event.accept(ModItems.GOLDY);
			event.accept(ModItems.DOUBLE_GOLDY);
			event.accept(ModItems.RAJUSH);
			event.accept(ModItems.SOULROOT);
			event.accept(ModItems.BLOODROOT);
			event.accept(ModItems.FALSEFLOWER_SMALL);
			event.accept(ModItems.FALSEFLOWER);
			event.accept(ModItems.SOULFLOWER);
			event.accept(ModItems.VOID_ROOTS);
			event.accept(ModItems.VOID_STONE);
			event.accept(ModItems.GEODITE_DIRT);
			event.accept(ModItems.GEODITE_STONE);
			event.accept(ModItems.AMBER_ORE);
			event.accept(ModItems.SAPPHIRE_ORE);
			event.accept(ModItems.COBALT_ORE);
			event.accept(ModItems.PEARLIUM_ORE);
			event.accept(ModItems.WICKED_AMETHYST_ORE);
			event.accept(ModItems.AMBER_CRYSTAL);
			event.accept(ModItems.AMETHYST_CRYSTAL);
			event.accept(ModItems.RUBY_CRYSTAL);
			event.accept(ModItems.SAPPHIRE_CRYSTAL);
			event.accept(ModItems.VOID_CRYSTAL);
			event.accept(ModItems.NATURE_BLOCK);
			event.accept(ModItems.AQUARIUS_BLOCK);
			event.accept(ModItems.INFERNAL_BLOCK);
			event.accept(ModItems.AWAKENED_VOID_BLOCK);
			event.accept(ModItems.COBALT_BLOCK);
			event.accept(ModItems.PEARLIUM);
			event.accept(ModItems.AMBANE_STONE);
			event.accept(ModItems.AMBANE_STONE_BRICKS);
			event.accept(ModItems.AMBANE_STONE_WALL);
			event.accept(ModItems.AMBANE_STONE_BRICKS_WALL);
			event.accept(ModItems.CHISELED_AMBANE_STONE_BRICKS);
			event.accept(ModItems.CUT_AMBANE_STONE);
			event.accept(ModItems.POLISHED_AMBANE_STONE);
			event.accept(ModItems.DUNESTONE);
			event.accept(ModItems.DUNESTONE_BRICKS);
			event.accept(ModItems.DUNESTONE_WALL);
			event.accept(ModItems.DUNESTONE_BRICKS_WALL);
			event.accept(ModItems.CUT_DUNESTONE);
			event.accept(ModItems.POLISHED_DUNESTONE);
			event.accept(ModItems.LIMESTONE);
			event.accept(ModItems.LIMESTONE_BRICKS);
			event.accept(ModItems.CRACKED_LIMESTONE_BRICKS);
			event.accept(ModItems.LIMESTONE_WALL);
			event.accept(ModItems.LIMESTONE_BRICKS_WALL);
			event.accept(ModItems.CUT_LIMESTONE);
			event.accept(ModItems.CRACKED_LIMESTONE_BRICKS_WALL);
			event.accept(ModItems.POLISHED_LIMESTONE);
			event.accept(ModItems.CRYSTAL_STONE);
			event.accept(ModItems.CRYSTAL_STONE_WALL);
			event.accept(ModItems.CRYSTAL_STONE_PILLAR);
			event.accept(ModItems.CRYSTAL_STONE_BRICKS);
			event.accept(ModItems.CRYSTAL_STONE_BRICKS_WALL);
			event.accept(ModItems.CUT_CRYSTAL_STONE);
			event.accept(ModItems.CUT_POLISHED_CRYSTAL_STONE);
			event.accept(ModItems.TOMBSTONE);
			event.accept(ModItems.CUT_TOMBSTONE);
			event.accept(ModItems.TOMBSTONE_FIRECHARGE_TRAP);
			event.accept(ModItems.TOMBSTONE_SPIKES_TRAP);
			event.accept(ModItems.TOMBSTONE_BRICKS);
			event.accept(ModItems.TOMBSTONE_WALL);
			event.accept(ModItems.TOMBSTONE_BRICKS_WALL);
			event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS);
			event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS_WALL);
			event.accept(ModItems.POLISHED_TOMBSTONE);
			event.accept(ModItems.TOMBSTONE_PILLAR);
			event.accept(ModItems.WICKED_TOMBSTONE_PILLAR);
			event.accept(ModItems.CUT_TOMBSTONE_PILLAR);
			event.accept(ModItems.SPIKES);
			event.accept(ModItems.VOID_GRASS);
			event.accept(ModItems.VOID_STONE);
			event.accept(ModItems.VOID_STONE_WALL);
			event.accept(ModItems.VOID_BRICK);
			event.accept(ModItems.VOID_CRACKED_BRICK);
			event.accept(ModItems.VOID_BRICK_WALL);
			event.accept(ModItems.VOID_CRACKED_BRICK_WALL);
			event.accept(ModItems.VOID_CHISELED_BRICK);
			event.accept(ModItems.VOID_CHISELED_BRICKS);
			event.accept(ModItems.POLISHED_VOID_STONE);
			event.accept(ModItems.VOID_PILLAR);
			event.accept(ModItems.VOID_PILLAR_AMETHYST);
			event.accept(ModItems.CHARGED_VOID_PILLAR);
		}
	}
}