package com.idark.darkrpg.item;

import com.idark.darkrpg.item.curio.ring.Iron_ring;
import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.entity.ModEntityTypes;
import net.minecraft.item.*;
import net.minecraft.item.Rarity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DarkRPG.MOD_ID);

    private final static String MODID = DarkRPG.MOD_ID;
	//BLOCKITEMS
	//bronze
	public static final RegistryObject<Item> BRONZE_PLATES = ITEMS.register("bronze_plates", () -> new BlockItem(ModBlocks.BRONZE_PLATES.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> BRONZE_PLATE_1 = ITEMS.register("bronze_plate_1", () -> new BlockItem(ModBlocks.BRONZE_PLATE_1.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> BRONZE_PLATE_2 = ITEMS.register("bronze_plate_2", () -> new BlockItem(ModBlocks.BRONZE_PLATE_2.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> BRONZE_VENT = ITEMS.register("bronze_vent", () -> new BlockItem(ModBlocks.BRONZE_VENT.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> BRONZE_GLASS = ITEMS.register("bronze_glass", () -> new BlockItem(ModBlocks.BRONZE_GLASS.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> BRONZE_LAMP_3 = ITEMS.register("bronze_lamp_3", () -> new BlockItem(ModBlocks.BRONZE_LAMP_3.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	//ore
	public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new BlockItem(ModBlocks.VOID_CRYSTAL.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> COBALT_ORE = ITEMS.register("cobalt_ore", () -> new BlockItem(ModBlocks.COBALT_ORE.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	//void
	public static final RegistryObject<Item> VOID_STONE = ITEMS.register("void_stone", () -> new BlockItem(ModBlocks.VOID_STONE.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_COBBLESTONE = ITEMS.register("void_cobblestone", () -> new BlockItem(ModBlocks.VOID_COBBLESTONE.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));	public static final RegistryObject<Item> VOID_ORE = ITEMS.register("void_ore", () -> new BlockItem(ModBlocks.VOID_ORE.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_BRICK = ITEMS.register("void_brick", () -> new BlockItem(ModBlocks.VOID_BRICK.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_CRACKED_BRICK = ITEMS.register("void_cracked_brick", () -> new BlockItem(ModBlocks.VOID_CRACKED_BRICK.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_CHISELED_BRICK = ITEMS.register("void_chiseled_brick", () -> new BlockItem(ModBlocks.VOID_CHISELED_BRICK.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_CHISELED_BRICKS = ITEMS.register("void_chiseled_bricks", () -> new BlockItem(ModBlocks.VOID_CHISELED_BRICKS.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_CHISELED_BRICKS1 = ITEMS.register("void_chiseled_bricks1", () -> new BlockItem(ModBlocks.VOID_CHISELED_BRICKS1.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_CHISELED_BLOCK = ITEMS.register("void_chiseled_block", () -> new BlockItem(ModBlocks.VOID_CHISELED_BLOCK.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_SMOOTH_BLOCK = ITEMS.register("void_smooth_block", () -> new BlockItem(ModBlocks.VOID_SMOOTH_BLOCK.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VOID_PILLAR = ITEMS.register("void_pillar", () -> new BlockItem(ModBlocks.VOID_PILLAR.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> CHARGED_VOID_PILLAR = ITEMS.register("charged_void_pillar", () -> new BlockItem(ModBlocks.CHARGED_VOID_PILLAR.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	//other
 	public static final RegistryObject<Item> ELEMENTAL_MANIPULATOR = ITEMS.register("elemental_manipulator", () -> new BlockItem(ModBlocks.ELEMENTAL_MANIPULATOR.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg", () -> new BlockItem(ModBlocks.SPIDER_EGG.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> PEDESTAL = ITEMS.register("pedestal", () -> new BlockItem(ModBlocks.PEDESTAL.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	public static final RegistryObject<Item> VASE_SMALL = ITEMS.register("vase_small", () -> new BlockItem(ModBlocks.VASE_SMALL.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
  	public static final RegistryObject<Item> VASE_SMALL_1 = ITEMS.register("vase_small_1", () -> new BlockItem(ModBlocks.VASE_SMALL_1.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
    	public static final RegistryObject<Item> VASE_BIG = ITEMS.register("vase_big", () -> new BlockItem(ModBlocks.VASE_BIG.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	public static final RegistryObject<Item> VASE_BIG_1 = ITEMS.register("vase_big_1", () -> new BlockItem(ModBlocks.VASE_BIG_1.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	//plants
	public static final RegistryObject<Item> CATTAIL = ITEMS.register("cattail", () -> new BlockItem(ModBlocks.CATTAIL.get(), new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
   	//ARMOR
   	//awakened void
	public static final RegistryObject<Item> AWAKENED_VOID_HELMET = ITEMS.register("awakened_void_helmet", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, EquipmentSlotType.HEAD, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> AWAKENED_VOID_CHESTPLATE = ITEMS.register("awakened_void_chestplate", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, EquipmentSlotType.CHEST, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> AWAKENED_VOID_LEGGINGS = ITEMS.register("awakened_void_leggings", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, EquipmentSlotType.LEGS, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> AWAKENED_VOID_BOOTS = ITEMS.register("awakened_void_boots", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, EquipmentSlotType.FEET, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
   	//nature
   	public static final RegistryObject<Item> NATURE_HELMET = ITEMS.register("nature_helmet", () -> new ArmorItem(ModArmorMaterial.NATURE, EquipmentSlotType.HEAD, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> NATURE_CHESTPLATE = ITEMS.register("nature_chestplate", () -> new ArmorItem(ModArmorMaterial.NATURE, EquipmentSlotType.CHEST, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> NATURE_LEGGINGS = ITEMS.register("nature_leggings", () -> new ArmorItem(ModArmorMaterial.NATURE, EquipmentSlotType.LEGS, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> NATURE_BOOTS = ITEMS.register("nature_boots", () -> new ArmorItem(ModArmorMaterial.NATURE, EquipmentSlotType.FEET, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));		
	//infernal
   	public static final RegistryObject<Item> INFERNAL_HELMET = ITEMS.register("infernal_helmet", () -> new ArmorItem(ModArmorMaterial.INFERNAL, EquipmentSlotType.HEAD, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> INFERNAL_CHESTPLATE = ITEMS.register("infernal_chestplate", () -> new ArmorItem(ModArmorMaterial.INFERNAL, EquipmentSlotType.CHEST, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> INFERNAL_LEGGINGS = ITEMS.register("infernal_leggings", () -> new ArmorItem(ModArmorMaterial.INFERNAL, EquipmentSlotType.LEGS, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));
	public static final RegistryObject<Item> INFERNAL_BOOTS = ITEMS.register("infernal_boots", () -> new ArmorItem(ModArmorMaterial.INFERNAL, EquipmentSlotType.FEET, (new Item.Properties().group(ModItemGroup.DARKRPG_ARMOR_GROUP))));	
	//ITEMS
	//ingots
	 public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> NATURE_INGOT = ITEMS.register("nature_ingot",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> INFERNAL_INGOT = ITEMS.register("infernal_ingot",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	//TOOLS
	//vanilla
	public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
        	() -> new SwordItem(ItemTier.IRON, 8, -3.5f,
               	 	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
        	() -> new SwordItem(ItemTier.GOLD, 8, -3.5f,
               	 	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
        	() -> new SwordItem(ItemTier.DIAMOND, 7, -3.4f,
               		 new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
        	() -> new SwordItem(ItemTier.NETHERITE, 7, -3.4f,
               	 	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	
	public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana",
        	() -> new SwordItem(ItemTier.IRON, 2, -2.2f,
               		 new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> GOLDEN_KATANA = ITEMS.register("golden_katana",
      		() -> new SwordItem(ItemTier.GOLD, 2, -2.1f,
                	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> DIAMOND_KATANA = ITEMS.register("diamond_katana",
        	() -> new SwordItem(ItemTier.DIAMOND, 1, -1.9f,
                	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> NETHERITE_KATANA = ITEMS.register("netherite_katana",
        	() -> new SwordItem(ItemTier.NETHERITE, 1, -1.8f,
                	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	//cobalt
	public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
   		() -> new SwordItem(ModItemTier.COBALT, 4, -2.3f,
   			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
		() -> new PickaxeItem(ModItemTier.COBALT, 1, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
		() -> new ShovelItem(ModItemTier.COBALT, 1, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
		() -> new AxeItem(ModItemTier.COBALT, 6, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
		() -> new HoeItem(ModItemTier.COBALT, -2, 0f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	//nature
	public static final RegistryObject<Item> ENT = ITEMS.register("ent",
        	() -> new SwordItem(ModItemTier.NATURE, 3, -2f,
                	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> NATURE_SCYTHE = ITEMS.register("nature_scythe",
       		() -> new SwordItem(ModItemTier.NATURE, 7, -3.4f,
             	   	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> NATURE_PICKAXE = ITEMS.register("nature_pickaxe",
		() -> new PickaxeItem(ModItemTier.NATURE, 1, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> NATURE_SHOVEL = ITEMS.register("nature_shovel",
		() -> new ShovelItem(ModItemTier.NATURE, 1, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> NATURE_AXE = ITEMS.register("nature_axe",
		() -> new AxeItem(ModItemTier.NATURE, 5, -3f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> NATURE_HOE = ITEMS.register("nature_hoe",
		() -> new HoeItem(ModItemTier.NATURE, -2, 0f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	//infernal
   	public static final RegistryObject<Item> INFERNAL_SWORD = ITEMS.register("infernal_sword",
     	   	() -> new SwordItem(ModItemTier.INFERNAL, 2, -2f,
            		new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));	
	public static final RegistryObject<Item> INFERNAL_SCYTHE = ITEMS.register("infernal_scythe",
       		() -> new SwordItem(ModItemTier.INFERNAL, 4, -3.5f,
               		new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> INFERNAL_PICKAXE = ITEMS.register("infernal_pickaxe",
		() -> new PickaxeItem(ModItemTier.INFERNAL, -2, -2.8f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	public static final RegistryObject<Item> INFERNAL_SHOVEL = ITEMS.register("infernal_shovel",
		() -> new ShovelItem(ModItemTier.INFERNAL, -1, -2.9f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
		() -> new AxeItem(ModItemTier.INFERNAL, 3, -2.9f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<Item> INFERNAL_HOE = ITEMS.register("infernal_hoe",
		() -> new HoeItem(ModItemTier.INFERNAL, -3, 0f,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
   	//accessories
	public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring",
    		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_ACCESSORIES_GROUP).maxStackSize(1).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> RUNE = ITEMS.register("rune",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> RUNE_OF_VISION = ITEMS.register("rune_of_vision",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_WEALTH = ITEMS.register("rune_of_wealth",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_CURSES = ITEMS.register("rune_of_curses",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_SHARPNESS = ITEMS.register("rune_of_sharpness",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_ACCURACY = ITEMS.register("rune_of_accuracy",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_DEEP = ITEMS.register("rune_of_deep",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_PYRO = ITEMS.register("rune_of_pyro",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_COLD = ITEMS.register("rune_of_cold",
		() -> new Item(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(16).maxDamage(8).rarity(Rarity.EPIC)));
	//BOWS
   	public static final RegistryObject<Item> NATURE_BOW = ITEMS.register("nature_bow",
        () -> new BowItem(new Item.Properties().group(ModItemGroup.DARKRPG_GROUP).maxStackSize(1)));
   	//SPAWN EGGS
   	public static final RegistryObject<ModSpawnEggItem> SWAMP_WANDERER_SPAWN_EGG = ITEMS.register("swamp_wanderer_spawn_egg",
     	 	() -> new ModSpawnEggItem(ModEntityTypes.SWAMP_WANDERER, 0x618244, 0x4f5c3d,
			new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));
	public static final RegistryObject<ModSpawnEggItem> MANNEQUIN_SPAWN_EGG = ITEMS.register("mannequin_spawn_egg",
      	  	() -> new ModSpawnEggItem(ModEntityTypes.MANNEQUIN, 0x948b4a, 0xc7bc67,
		  	new Item.Properties().group(ModItemGroup.DARKRPG_GROUP)));

    public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
    }
}
