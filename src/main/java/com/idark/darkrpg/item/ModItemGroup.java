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
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.ALOE_SMALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CATTAIL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.KARUSAKAN_ROOTS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.GAIB_ROOTS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DRIED_PLANT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DRIED_ROOTS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRIMSON_SOULROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DOUBLE_SOULROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.MAGMAROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DOUBLE_MAGMAROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.GOLDY);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DOUBLE_GOLDY);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.RAJUSH);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.SOULROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.BLOODROOT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.FALSEFLOWER_SMALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.FALSEFLOWER);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.SOULFLOWER);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_ROOTS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.GEODITE_DIRT);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.GEODITE_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBER_ORE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.SAPPHIRE_ORE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.COBALT_ORE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.PEARLIUM_ORE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.WICKED_AMETHYST_ORE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBER_CRYSTAL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMETHYST_CRYSTAL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.RUBY_CRYSTAL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.SAPPHIRE_CRYSTAL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_CRYSTAL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.NATURE_BLOCK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AQUARIUS_BLOCK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.INFERNAL_BLOCK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AWAKENED_VOID_BLOCK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.COBALT_BLOCK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.PEARLIUM);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBANE_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBANE_STONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBANE_STONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.AMBANE_STONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CHISELED_AMBANE_STONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_AMBANE_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.POLISHED_AMBANE_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DUNESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DUNESTONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DUNESTONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.DUNESTONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_DUNESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.POLISHED_DUNESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.LIMESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.LIMESTONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRACKED_LIMESTONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.LIMESTONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.LIMESTONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_LIMESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRACKED_LIMESTONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.POLISHED_LIMESTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRYSTAL_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRYSTAL_STONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRYSTAL_STONE_PILLAR);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRYSTAL_STONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRYSTAL_STONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_CRYSTAL_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_POLISHED_CRYSTAL_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_TOMBSTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_FIRECHARGE_TRAP);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_SPIKES_TRAP);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.POLISHED_TOMBSTONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.TOMBSTONE_PILLAR);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.WICKED_TOMBSTONE_PILLAR);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CUT_TOMBSTONE_PILLAR);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.SPIKES);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_GRASS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_STONE_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_BRICK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_CRACKED_BRICK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_BRICK_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_CRACKED_BRICK_WALL);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_CHISELED_BRICK);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_CHISELED_BRICKS);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.POLISHED_VOID_STONE);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_PILLAR);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.VOID_PILLAR_AMETHYST);
		}

		if (event.getTabKey() == ModItemGroup.DARKRPG_BLOCKS_GROUP.getKey()) {
			event.accept(ModItems.CHARGED_VOID_PILLAR);
		}
	}
}