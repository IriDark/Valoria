package com.idark.valoria.registries.world.item;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.registries.world.entity.decoration.ModPaintings;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Comparator;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Valoria.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class ModItemGroup {

    private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(Holder::value, Comparator.<PaintingVariant>comparingInt((p_270004_) -> p_270004_.getHeight() * p_270004_.getWidth()).thenComparing(PaintingVariant::getWidth));
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Valoria.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VALORIA_TAB = CREATIVE_MODE_TABS.register("valoriamodtab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NATURE_PICKAXE.get()))
                    .hideTitle()
                    .title(Component.translatable("itemGroup.valoriaModTab"))
                    .withTabsImage(getTabsImage())
                    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_BLOCKS_TAB = CREATIVE_MODE_TABS.register("valoriablocksmodtab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOID_STONE.get()))
                    .hideTitle()
                    .title(Component.translatable("itemGroup.valoriaBlocksModTab"))
                    .withTabsImage(getTabsImage())
                    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static ResourceLocation getBackgroundImage() {
        return new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/tab_valoria_item.png");
    }

    public static ResourceLocation getTabsImage() {
        return new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/tabs_valoria.png");
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModItemGroup.VALORIA_TAB.getKey()) {
            event.accept(ModItems.COBALT_HELMET);
            event.accept(ModItems.COBALT_CHESTPLATE);
            event.accept(ModItems.COBALT_LEGGINGS);
            event.accept(ModItems.COBALT_BOOTS);
            event.accept(ModItems.SAMURAI_KABUTO);
            event.accept(ModItems.SAMURAI_CHESTPLATE);
            event.accept(ModItems.SAMURAI_LEGGINGS);
            event.accept(ModItems.SAMURAI_BOOTS);
            event.accept(ModItems.NATURE_HELMET);
            event.accept(ModItems.NATURE_CHESTPLATE);
            event.accept(ModItems.NATURE_LEGGINGS);
            event.accept(ModItems.NATURE_BOOTS);
            event.accept(ModItems.DEPTH_HELMET);
            event.accept(ModItems.DEPTH_CHESTPLATE);
            event.accept(ModItems.DEPTH_LEGGINGS);
            event.accept(ModItems.DEPTH_BOOTS);
            event.accept(ModItems.INFERNAL_HELMET);
            event.accept(ModItems.INFERNAL_CHESTPLATE);
            event.accept(ModItems.INFERNAL_LEGGINGS);
            event.accept(ModItems.INFERNAL_BOOTS);
            event.accept(ModItems.AWAKENED_VOID_HELMET);
            event.accept(ModItems.AWAKENED_VOID_CHESTPLATE);
            event.accept(ModItems.AWAKENED_VOID_LEGGINGS);
            event.accept(ModItems.AWAKENED_VOID_BOOTS);
            event.accept(ModItems.PHANTASM_HELMET);
            event.accept(ModItems.PHANTASM_CHESTPLATE);
            event.accept(ModItems.PHANTASM_LEGGINGS);
            event.accept(ModItems.PHANTASM_BOOTS);
            event.accept(ModItems.DIRT_GEODE);
            event.accept(ModItems.STONE_GEODE);
            event.accept(ModItems.MINERS_BAG);
            event.accept(ModItems.GEM_BAG);
            event.accept(ModItems.RAW_COBALT);
            event.accept(ModItems.AMBER_GEM);
            event.accept(ModItems.AMETHYST_GEM);
            event.accept(ModItems.RUBY_GEM);
            event.accept(ModItems.SAPPHIRE_GEM);
            event.accept(ModItems.AMETHYST);
            event.accept(ModItems.SOUL_SHARD);
            event.accept(ModItems.UNCHARGED_SHARD);
            event.accept(ModItems.TOXINS_BOTTLE);
            event.accept(ModItems.NATURE_GIFT);
            event.accept(ModItems.OCEANIC_SHELL);
            event.accept(ModItems.INFERNAL_STONE);
            event.accept(ModItems.BONE_FRAGMENT);
            event.accept(ModItems.PAIN_CRYSTAL);
            event.accept(ModItems.ILLUSION_STONE);
            event.accept(ModItems.NATURE_CORE);
            event.accept(ModItems.AQUARIUS_CORE);
            event.accept(ModItems.INFERNAL_CORE);
            event.accept(ModItems.VOID_CORE);
            event.accept(ModItems.SOUL_COLLECTOR_EMPTY);
            event.accept(ModItems.SOUL_COLLECTOR);
            event.accept(ModItems.LEXICON);
            event.accept(ModItems.CRYPT);
            event.accept(ModItems.VOID_KEY);
            event.accept(ModItems.GAIB_ROOT);
            event.accept(ModItems.KARUSAKAN_ROOT);
            event.accept(ModItems.WOODEN_CUP);
            event.accept(ModItems.CUP);
            event.accept(ModItems.BOTTLE);
            event.accept(ModItems.TAINTED_BERRIES);
            event.accept(ModItems.EYE_CHUNK);
            event.accept(ModItems.COOKED_ABYSSAL_GLOWFERN);
            event.accept(ModItems.COOKED_GLOW_VIOLET_SPROUT);
            event.accept(ModItems.ALOE_PIECE);
            event.accept(ModItems.ALOE_BANDAGE);
            event.accept(ModItems.ALOE_BANDAGE_UPGRADED);
            event.accept(ModItems.CACAO_CUP);
            event.accept(ModItems.COFFE_CUP);
            event.accept(ModItems.TEA_CUP);
            event.accept(ModItems.GREEN_TEA_CUP);
            event.accept(ModItems.BEER_CUP);
            event.accept(ModItems.RUM_CUP);
            event.accept(ModItems.KVASS_BOTTLE);
            event.accept(ModItems.WINE_BOTTLE);
            event.accept(ModItems.AKVAVIT_BOTTLE);
            event.accept(ModItems.LIQUOR_BOTTLE);
            event.accept(ModItems.MEAD_BOTTLE);
            event.accept(ModItems.RUM_BOTTLE);
            event.accept(ModItems.COGNAC_BOTTLE);
            event.accept(ModItems.WHISKEY_BOTTLE);
            event.accept(ModItems.COKE_BOTTLE);
            event.accept(ModItems.APPLE_PIE);
            event.accept(ModItems.HOLIDAY_CANDY);
            event.accept(ModItems.DUNESTONE_BRICK);
            event.accept(ModItems.TOMBSTONE_BRICK);
            event.accept(ModItems.AMBANE_STONE_BRICK);
            event.accept(ModItems.LIMESTONE_BRICK);
            event.accept(ModItems.CRYSTAL_STONE_BRICK);
            event.accept(ModItems.VOID_STONE_BRICK);
            event.accept(ModItems.BRONZE_INGOT);
            event.accept(ModItems.ANCIENT_INGOT);
            event.accept(ModItems.PEARLIUM_INGOT);
            event.accept(ModItems.COBALT_INGOT);
            event.accept(ModItems.NATURE_INGOT);
            event.accept(ModItems.AQUARIUS_INGOT);
            event.accept(ModItems.INFERNAL_INGOT);
            event.accept(ModItems.VOID_INGOT);
            event.accept(ModItems.CLUB);
            event.accept(ModItems.BRONZE_SWORD);
            event.accept(ModItems.QUANTUM_REAPER);
            event.accept(ModItems.VOID_EDGE);
            event.accept(ModItems.BLOODHOUND);
            event.accept(ModItems.BLAZE_REAP);
            event.accept(ModItems.PHANTOM);
            event.accept(ModItems.MURASAMA);
            event.accept(ModItems.SAMURAI_KUNAI);
            event.accept(ModItems.SAMURAI_POISONED_KUNAI);
            event.accept(ModItems.SPECTRAL_BLADE);
            event.accept(ModItems.WOODEN_SPEAR);
            event.accept(ModItems.STONE_SPEAR);
            event.accept(ModItems.IRON_SPEAR);
            event.accept(ModItems.GOLDEN_SPEAR);
            event.accept(ModItems.DIAMOND_SPEAR);
            event.accept(ModItems.NETHERITE_SPEAR);
            event.accept(ModItems.GLAIVE);
            event.accept(ModItems.WOODEN_RAPIER);
            event.accept(ModItems.STONE_RAPIER);
            event.accept(ModItems.IRON_RAPIER);
            event.accept(ModItems.GOLDEN_RAPIER);
            event.accept(ModItems.DIAMOND_RAPIER);
            event.accept(ModItems.NETHERITE_RAPIER);
            event.accept(ModItems.IRON_SCYTHE);
            event.accept(ModItems.GOLDEN_SCYTHE);
            event.accept(ModItems.DIAMOND_SCYTHE);
            event.accept(ModItems.NETHERITE_SCYTHE);
            event.accept(ModItems.BEAST);
            event.accept(ModItems.CORPSECLEAVER);
            event.accept(ModItems.HOLIDAY_KATANA);
            event.accept(ModItems.IRON_KATANA);
            event.accept(ModItems.GOLDEN_KATANA);
            event.accept(ModItems.DIAMOND_KATANA);
            event.accept(ModItems.NETHERITE_KATANA);
            event.accept(ModItems.SAMURAI_KATANA);
            event.accept(ModItems.PEARLIUM_SWORD);
            event.accept(ModItems.PEARLIUM_PICKAXE);
            event.accept(ModItems.PEARLIUM_AXE);
            event.accept(ModItems.HOLIDAY_PICKAXE);
            event.accept(ModItems.HOLIDAY_AXE);
            event.accept(ModItems.XMAS_SWORD);
            event.accept(ModItems.COBALT_SWORD);
            event.accept(ModItems.COBALT_PICKAXE);
            event.accept(ModItems.COBALT_SHOVEL);
            event.accept(ModItems.COBALT_AXE);
            event.accept(ModItems.COBALT_HOE);
            event.accept(ModItems.ENT);
            event.accept(ModItems.NATURE_SCYTHE);
            event.accept(ModItems.NATURE_PICKAXE);
            event.accept(ModItems.NATURE_SHOVEL);
            event.accept(ModItems.NATURE_AXE);
            event.accept(ModItems.NATURE_HOE);
            event.accept(ModItems.CORAL_REEF);
            event.accept(ModItems.AQUARIUS_SCYTHE);
            event.accept(ModItems.AQUARIUS_PICKAXE);
            event.accept(ModItems.AQUARIUS_SHOVEL);
            event.accept(ModItems.AQUARIUS_AXE);
            event.accept(ModItems.AQUARIUS_HOE);
            event.accept(ModItems.INFERNAL_SWORD);
            event.accept(ModItems.INFERNAL_SCYTHE);
            event.accept(ModItems.INFERNAL_PICKAXE);
            event.accept(ModItems.INFERNAL_SHOVEL);
            event.accept(ModItems.INFERNAL_AXE);
            event.accept(ModItems.INFERNAL_HOE);
            event.accept(ModItems.SAMURAI_LONG_BOW);
            event.accept(ModItems.NATURE_BOW);
            event.accept(ModItems.AQUARIUS_BOW);
            event.accept(ModItems.BOW_OF_DARKNESS);
            event.accept(ModItems.PHANTASM_BOW);
            event.accept(ModItems.PICK_NECKLACE);
            event.accept(ModItems.IRON_CHAIN);
            event.accept(ModItems.IRON_NECKLACE_AMBER);
            event.accept(ModItems.IRON_NECKLACE_DIAMOND);
            event.accept(ModItems.IRON_NECKLACE_EMERALD);
            event.accept(ModItems.IRON_NECKLACE_RUBY);
            event.accept(ModItems.IRON_NECKLACE_SAPPHIRE);
            event.accept(ModItems.IRON_NECKLACE_HEALTH);
            event.accept(ModItems.IRON_NECKLACE_ARMOR);
            event.accept(ModItems.IRON_NECKLACE_WEALTH);
            event.accept(ModItems.GOLDEN_CHAIN);
            event.accept(ModItems.GOLDEN_NECKLACE_AMBER);
            event.accept(ModItems.GOLDEN_NECKLACE_DIAMOND);
            event.accept(ModItems.GOLDEN_NECKLACE_EMERALD);
            event.accept(ModItems.GOLDEN_NECKLACE_RUBY);
            event.accept(ModItems.GOLDEN_NECKLACE_SAPPHIRE);
            event.accept(ModItems.GOLDEN_NECKLACE_HEALTH);
            event.accept(ModItems.GOLDEN_NECKLACE_ARMOR);
            event.accept(ModItems.GOLDEN_NECKLACE_WEALTH);
            event.accept(ModItems.NETHERITE_CHAIN);
            event.accept(ModItems.NETHERITE_NECKLACE_AMBER);
            event.accept(ModItems.NETHERITE_NECKLACE_DIAMOND);
            event.accept(ModItems.NETHERITE_NECKLACE_EMERALD);
            event.accept(ModItems.NETHERITE_NECKLACE_RUBY);
            event.accept(ModItems.NETHERITE_NECKLACE_SAPPHIRE);
            event.accept(ModItems.NETHERITE_NECKLACE_HEALTH);
            event.accept(ModItems.NETHERITE_NECKLACE_ARMOR);
            event.accept(ModItems.NETHERITE_NECKLACE_WEALTH);
            event.accept(ModItems.LEATHER_BELT);
            event.accept(ModItems.IRON_RING);
            event.accept(ModItems.IRON_RING_AMBER);
            event.accept(ModItems.IRON_RING_DIAMOND);
            event.accept(ModItems.IRON_RING_EMERALD);
            event.accept(ModItems.IRON_RING_RUBY);
            event.accept(ModItems.IRON_RING_SAPPHIRE);
            event.accept(ModItems.GOLDEN_RING);
            event.accept(ModItems.GOLDEN_RING_AMBER);
            event.accept(ModItems.GOLDEN_RING_DIAMOND);
            event.accept(ModItems.GOLDEN_RING_EMERALD);
            event.accept(ModItems.GOLDEN_RING_RUBY);
            event.accept(ModItems.GOLDEN_RING_SAPPHIRE);
            event.accept(ModItems.NETHERITE_RING);
            event.accept(ModItems.NETHERITE_RING_AMBER);
            event.accept(ModItems.NETHERITE_RING_DIAMOND);
            event.accept(ModItems.NETHERITE_RING_EMERALD);
            event.accept(ModItems.NETHERITE_RING_RUBY);
            event.accept(ModItems.NETHERITE_RING_SAPPHIRE);
            event.accept(ModItems.LEATHER_GLOVES);
            event.accept(ModItems.IRON_GLOVES);
            event.accept(ModItems.GOLDEN_GLOVES);
            event.accept(ModItems.DIAMOND_GLOVES);
            event.accept(ModItems.NETHERITE_GLOVES);
            event.accept(ModItems.AMBER_TOTEM);
            event.accept(ModItems.AMBER_WINGLET);
            event.accept(ModItems.AMBER_GAZER);
            event.accept(ModItems.EMERALD_TOTEM);
            event.accept(ModItems.EMERALD_WINGLET);
            event.accept(ModItems.EMERALD_GAZER);
            event.accept(ModItems.AMETHYST_TOTEM);
            event.accept(ModItems.AMETHYST_WINGLET);
            event.accept(ModItems.AMETHYST_GAZER);
            event.accept(ModItems.RUBY_TOTEM);
            event.accept(ModItems.RUBY_WINGLET);
            event.accept(ModItems.RUBY_GAZER);
            event.accept(ModItems.BROKEN_BLOODSIGHT_MONOCLE);
            event.accept(ModItems.BLOODSIGHT_MONOCLE);
            event.accept(ModItems.PICK);
            event.accept(ModItems.RUNE);
            event.accept(ModItems.RUNE_OF_VISION);
            event.accept(ModItems.RUNE_OF_WEALTH);
            event.accept(ModItems.RUNE_OF_CURSES);
            event.accept(ModItems.RUNE_OF_STRENGTH);
            event.accept(ModItems.RUNE_OF_ACCURACY);
            event.accept(ModItems.RUNE_OF_DEEP);
            event.accept(ModItems.RUNE_OF_PYRO);
            event.accept(ModItems.RUNE_OF_COLD);
            event.accept(ModItems.MANNEQUIN_SPAWN_EGG);
            event.accept(ModItems.SWAMP_WANDERER_SPAWN_EGG);
            event.accept(ModItems.DRAUGR_SPAWN_EGG);
            event.accept(ModItems.NECROMANCER_SPAWN_EGG);
            event.accept(ModItems.UNDEAD_SPAWN_EGG);
            event.accept(ModItems.GOBLIN_SPAWN_EGG);
        }

        if (event.getTabKey() == ModItemGroup.VALORIA_BLOCKS_TAB.getKey()) {
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
            event.accept(ModItems.VIOLET_SPROUT);
            event.accept(ModItems.GLOW_VIOLET_SPROUT);
            event.accept(ModItems.ABYSSAL_GLOWFERN);
            event.accept(ModItems.SOULFLOWER);
            event.accept(ModItems.VOID_ROOTS);
            event.accept(ModItems.VOIDVINE);
            event.accept(ModItems.VOID_SERPENTS);
            event.accept(ModItems.GEODITE_DIRT);
            event.accept(ModItems.GEODITE_STONE);
            event.accept(ModItems.AMBER_ORE);
            event.accept(ModItems.AMETHYST_ORE);
            event.accept(ModItems.RUBY_ORE);
            event.accept(ModItems.SAPPHIRE_ORE);
            event.accept(ModItems.COBALT_ORE);
            event.accept(ModItems.DEEPSLATE_AMBER_ORE);
            event.accept(ModItems.DEEPSLATE_AMETHYST_ORE);
            event.accept(ModItems.DEEPSLATE_RUBY_ORE);
            event.accept(ModItems.DEEPSLATE_SAPPHIRE_ORE);
            event.accept(ModItems.DEEPSLATE_COBALT_ORE);
            event.accept(ModItems.RAW_COBALT_ORE_BLOCK);
            event.accept(ModItems.PEARLIUM_ORE);
            event.accept(ModItems.WICKED_AMETHYST_ORE);
            event.accept(ModItems.DORMANT_CRYSTALS);
            event.accept(ModItems.AMBER_BLOCK);
            event.accept(ModItems.AMETHYST_BLOCK);
            event.accept(ModItems.RUBY_BLOCK);
            event.accept(ModItems.SAPPHIRE_BLOCK);
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
            event.accept(ModItems.BRONZE_BLOCK);
            event.accept(ModBlocks.BRONZE_BLOCK_STAIRS);
            event.accept(ModBlocks.BRONZE_BLOCK_SLAB);
            event.accept(ModBlocks.BRONZE_DOOR);
            event.accept(ModBlocks.BRONZE_TRAPDOOR);
            event.accept(ModBlocks.BRONZE_TRAPDOOR_GLASS);
            event.accept(ModItems.BRONZE_VENT);
            event.accept(ModItems.BRONZE_GLASS);
            event.accept(ModItems.CUT_BRONZE);
            event.accept(ModBlocks.CUT_BRONZE_STAIRS);
            event.accept(ModBlocks.CUT_BRONZE_SLAB);
            event.accept(ModItems.BRONZE_LAMP);
            event.accept(ModItems.DECORATED_BRONZE_LAMP);
            event.accept(ModItems.BRONZE_LAMP_BLOCK);
            event.accept(ModItems.PEARLIUM);
            event.accept(ModItems.EPHEMARITE);
            event.accept(ModItems.POLISHED_EPHEMARITE);
            event.accept(ModItems.EPHEMARITE_LOW);
            event.accept(ModItems.POLISHED_EPHEMARITE_LOW);
            event.accept(ModItems.AMBANE_STONE);
            event.accept(ModBlocks.AMBANE_STONE_STAIRS);
            event.accept(ModBlocks.AMBANE_STONE_SLAB);
            event.accept(ModItems.AMBANE_STONE_WALL);
            event.accept(ModItems.AMBANE_STONE_BRICKS);
            event.accept(ModBlocks.AMBANE_STONE_BRICKS_STAIRS);
            event.accept(ModBlocks.AMBANE_STONE_BRICKS_SLAB);
            event.accept(ModItems.AMBANE_STONE_BRICKS_WALL);
            event.accept(ModItems.CHISELED_AMBANE_STONE_BRICKS);
            event.accept(ModItems.CUT_AMBANE_STONE);
            event.accept(ModItems.POLISHED_AMBANE_STONE);
            event.accept(ModBlocks.POLISHED_AMBANE_STONE_STAIRS);
            event.accept(ModBlocks.POLISHED_AMBANE_STONE_SLAB);
            event.accept(ModItems.QUICKSAND);
            event.accept(ModItems.DUNESTONE);
            event.accept(ModBlocks.DUNESTONE_STAIRS);
            event.accept(ModBlocks.DUNESTONE_SLAB);
            event.accept(ModItems.DUNESTONE_WALL);
            event.accept(ModItems.DUNESTONE_BRICKS);
            event.accept(ModBlocks.DUNESTONE_BRICKS_STAIRS);
            event.accept(ModBlocks.DUNESTONE_BRICKS_SLAB);
            event.accept(ModItems.DUNESTONE_BRICKS_WALL);
            event.accept(ModItems.CUT_DUNESTONE);
            event.accept(ModItems.POLISHED_DUNESTONE);
            event.accept(ModItems.LIMESTONE);
            event.accept(ModBlocks.LIMESTONE_STAIRS);
            event.accept(ModBlocks.LIMESTONE_SLAB);
            event.accept(ModItems.LIMESTONE_WALL);
            event.accept(ModItems.LIMESTONE_BRICKS);
            event.accept(ModBlocks.LIMESTONE_BRICKS_STAIRS);
            event.accept(ModBlocks.LIMESTONE_BRICKS_SLAB);
            event.accept(ModItems.CRACKED_LIMESTONE_BRICKS);
            event.accept(ModBlocks.CRACKED_LIMESTONE_BRICKS_STAIRS);
            event.accept(ModBlocks.CRACKED_LIMESTONE_BRICKS_SLAB);
            event.accept(ModItems.LIMESTONE_BRICKS_WALL);
            event.accept(ModItems.CUT_LIMESTONE);
            event.accept(ModBlocks.CUT_LIMESTONE_STAIRS);
            event.accept(ModBlocks.CUT_LIMESTONE_SLAB);
            event.accept(ModItems.CRACKED_LIMESTONE_BRICKS_WALL);
            event.accept(ModItems.POLISHED_LIMESTONE);
            event.accept(ModBlocks.POLISHED_LIMESTONE_STAIRS);
            event.accept(ModBlocks.POLISHED_LIMESTONE_SLAB);
            event.accept(ModItems.CRYSTAL_STONE);
            event.accept(ModBlocks.CRYSTAL_STONE_STAIRS);
            event.accept(ModBlocks.CRYSTAL_STONE_SLAB);
            event.accept(ModItems.CRYSTAL_STONE_WALL);
            event.accept(ModItems.CRYSTAL_STONE_BRICKS);
            event.accept(ModBlocks.CRYSTAL_STONE_BRICKS_STAIRS);
            event.accept(ModBlocks.CRYSTAL_STONE_BRICKS_SLAB);
            event.accept(ModItems.CRYSTAL_STONE_BRICKS_WALL);
            event.accept(ModItems.CUT_CRYSTAL_STONE);
            event.accept(ModItems.CUT_POLISHED_CRYSTAL_STONE);
            event.accept(ModItems.POLISHED_CRYSTAL_STONE);
            event.accept(ModItems.CRYSTAL_STONE_PILLAR);
            event.accept(ModItems.TOMBSTONE);
            event.accept(ModBlocks.TOMBSTONE_STAIRS);
            event.accept(ModBlocks.TOMBSTONE_SLAB);
            event.accept(ModItems.TOMBSTONE_WALL);
            event.accept(ModItems.TOMBSTONE_BRICKS);
            event.accept(ModBlocks.TOMBSTONE_BRICKS_STAIRS);
            event.accept(ModBlocks.TOMBSTONE_BRICKS_SLAB);
            event.accept(ModItems.TOMBSTONE_BRICKS_WALL);
            event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS);
            event.accept(ModItems.CRACKED_TOMBSTONE_BRICKS_WALL);
            event.accept(ModItems.POLISHED_TOMBSTONE);
            event.accept(ModItems.TOMBSTONE_PILLAR);
            event.accept(ModItems.CUT_TOMBSTONE_PILLAR);
            event.accept(ModItems.WICKED_TOMBSTONE_PILLAR);
            event.accept(ModItems.CUT_TOMBSTONE);
            event.accept(ModItems.TOMBSTONE_FIRECHARGE_TRAP);
            event.accept(ModItems.TOMBSTONE_SPIKES_TRAP);
            event.accept(ModItems.SUSPICIOUS_TOMBSTONE);
            event.accept(ModItems.SUSPICIOUS_ICE);
            event.accept(ModItems.SPIKES);
            event.accept(ModItems.EYE_STONE);
            event.accept(ModItems.DEEP_MARBLE);
            event.accept(ModItems.POLISHED_DEEP_MARBLE);
            event.accept(ModBlocks.DEEP_MARBLE_STAIRS);
            event.accept(ModBlocks.DEEP_MARBLE_SLAB);
            event.accept(ModBlocks.POLISHED_DEEP_MARBLE_STAIRS);
            event.accept(ModBlocks.POLISHED_DEEP_MARBLE_SLAB);
            event.accept(ModItems.DEEP_MARBLE_WALL);
            event.accept(ModItems.POLISHED_DEEP_MARBLE_WALL);
            event.accept(ModItems.VOID_TAINT_LANTERN);
            event.accept(ModItems.ABYSSAL_LANTERN);
            event.accept(ModItems.VOID_GRASS);
            event.accept(ModItems.VOID_TAINT);
            event.accept(ModItems.VOID_STONE);
            event.accept(ModBlocks.VOID_STONE_STAIRS);
            event.accept(ModBlocks.VOID_STONE_SLAB);
            event.accept(ModItems.VOID_STONE_WALL);
            event.accept(ModItems.VOID_BRICK);
            event.accept(ModBlocks.VOID_BRICK_STAIRS);
            event.accept(ModBlocks.VOID_BRICK_SLAB);
            event.accept(ModItems.VOID_BRICK_WALL);
            event.accept(ModItems.VOID_CRACKED_BRICK);
            event.accept(ModBlocks.VOID_CRACKED_BRICK_STAIRS);
            event.accept(ModBlocks.VOID_CRACKED_BRICK_SLAB);
            event.accept(ModItems.VOID_CRACKED_BRICK_WALL);
            event.accept(ModItems.VOID_CHISELED_BRICK);
            event.accept(ModItems.VOID_CHISELED_BRICKS);
            event.accept(ModBlocks.VOID_CHISELED_BRICKS_STAIRS);
            event.accept(ModBlocks.VOID_CHISELED_BRICKS_SLAB);
            event.accept(ModItems.POLISHED_VOID_STONE);
            event.accept(ModItems.VOID_PILLAR);
            event.accept(ModItems.VOID_PILLAR_AMETHYST);
            event.accept(ModItems.CHARGED_VOID_PILLAR);
            event.accept(ModItems.SHADELOG);
            event.accept(ModItems.SHADEWOOD);
            event.accept(ModItems.STRIPPED_SHADELOG);
            event.accept(ModItems.STRIPPED_SHADEWOOD);
            event.accept(ModItems.SHADEWOOD_PLANKS);
            event.accept(ModBlocks.SHADEWOOD_PLANKS_STAIRS);
            event.accept(ModBlocks.SHADEWOOD_PLANKS_SLAB);
            event.accept(ModItems.SHADEWOOD_PLANKS_BUTTON);
            event.accept(ModBlocks.SHADEWOOD_TRAPDOOR);
            event.accept(ModBlocks.SHADEWOOD_DOOR);
            event.accept(ModItems.SHADEWOOD_PLANKS_PRESSURE_PLATE);
            event.accept(ModItems.SHADEWOOD_SIGN);
            event.accept(ModItems.SHADEWOOD_HANGING_SIGN);
            event.accept(ModItems.SHADEWOOD_BOAT_ITEM);
            event.accept(ModItems.SHADEWOOD_CHEST_BOAT_ITEM);
            event.accept(ModItems.SHADEWOOD_LEAVES);
            event.accept(ModItems.SHADEWOOD_BRANCH);
            event.accept(ModItems.SHADEWOOD_SAPLING);
            event.accept(ModItems.STONE_CRUSHER);
            event.accept(ModItems.JEWELER_TABLE);
            event.accept(ModItems.KEG);
            event.accept(ModItems.VALORIA_PORTAL_FRAME);
            event.accept(ModItems.VALORIA_PORTAL);
            event.accept(ModItems.UMBRAL_KEYPAD);
            event.accept(ModItems.CUT_UMBRAL_BLOCK);
            event.accept(ModItems.UMBRAL_BRICKS);
            event.accept(ModItems.UMBRAL_BLOCK);
            event.accept(ModItems.TILE);
            event.accept(ModItems.ELEMENTAL_MANIPULATOR);
            event.accept(ModItems.ELEGANT_PEDESTAL);
            event.accept(ModItems.TOMB);
            event.accept(ModItems.POT_SMALL);
            event.accept(ModItems.POT_SMALL_HANDLESS);
            event.accept(ModItems.POT_LONG);
            event.accept(ModItems.POT_LONG_HANDLES);
            event.accept(ModItems.POT_LONG_MOSSY);
            event.accept(ModItems.POT_LONG_MOSSY_HANDLES);
            event.accept(ModItems.SPIDER_EGG);
            event.accept(ModItems.MEAT_BLOCK);
            event.accept(ModItems.EYE_MEAT);
            event.accept(ModItems.SARCOPHAGUS);
            event.getParameters().holders().lookup(ModPaintings.PAINTING_TYPES.getRegistryKey()).ifPresent((p_270026_) -> generatePresetPaintings(event, p_270026_, (p_270037_) -> p_270037_.is(TagsRegistry.MODDED), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
        }
    }

    private static void generatePresetPaintings(CreativeModeTab.Output pOutput, HolderLookup.RegistryLookup<PaintingVariant> pPaintingVariants, Predicate<Holder<PaintingVariant>> pPredicate, CreativeModeTab.TabVisibility pTabVisibility) {
        pPaintingVariants.listElements().filter(pPredicate).sorted(PAINTING_COMPARATOR).forEach((p_269979_) -> {
            ItemStack itemstack = new ItemStack(Items.PAINTING);
            CompoundTag compoundtag = itemstack.getOrCreateTagElement("EntityTag");
            Painting.storeVariant(compoundtag, p_269979_);
            pOutput.accept(itemstack, pTabVisibility);
        });
    }
}