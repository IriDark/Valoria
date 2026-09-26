package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.*;

import java.util.*;
import java.util.function.*;

public class ItemTierRegistry{
    private static final Map<Tier, Integer> LEVELS = new IdentityHashMap<>();
    private static final Map<Tier, ResourceLocation> NAMES = new IdentityHashMap<>();

    public static Tier BRONZE = tier("bronze", 2, TagsRegistry.INCORRECT_FOR_BRONZE_TOOL, 600, 5f, 0.0F, 8, () -> Ingredient.of(ItemsRegistry.bronzeIngot.get()));
    public static Tier PEARLIUM = tier("pearlium", 2, TagsRegistry.INCORRECT_FOR_PEARLIUM_TOOL, 425, 7f, 2.0F, 6, () -> Ingredient.of(ItemsRegistry.pearliumIngot.get()));
    public static Tier HOLIDAY = tier("holiday", 2, TagsRegistry.INCORRECT_FOR_HOLIDAY_TOOL, 740, 6f, 3.0F, 8, () -> Ingredient.of(ItemsRegistry.holidayCandy.get()));
    public static Tier HALLOWEEN = tier("halloween", 2, TagsRegistry.INCORRECT_FOR_HALLOWEEN_TOOL, 1150, 6f, 3.0F, 8, () -> Ingredient.of(ItemsRegistry.candyCorn.get()));
    public static Tier LUNAR = tier("lunar", 2, TagsRegistry.INCORRECT_FOR_LUNAR_TOOL, 1450, 6f, 3.0F, 8, Ingredient::of);
    public static Tier SAMURAI = tier("samurai", 2, TagsRegistry.INCORRECT_FOR_SAMURAI_TOOL, 1250, 8f, 5.0F, 7, () -> Ingredient.of(ItemsRegistry.ancientIngot.get()));
    public static Tier COBALT = tier("cobalt", 3, TagsRegistry.INCORRECT_FOR_COBALT_TOOL, 1750, 12f, 4f, 12, () -> Ingredient.of(ItemsRegistry.cobaltIngot.get()));
    public static Tier BLACK_GOLD = tier("black_gold", 3, TagsRegistry.INCORRECT_FOR_GOLD_TOOL, 1500, 10f, 0.0F, 15, () -> Ingredient.of(ItemsRegistry.blackGold.get()));
    public static Tier ETHEREAL = tier("ethereal", 3, TagsRegistry.INCORRECT_FOR_ETHEREAL_TOOL, 2025, 14f, 5f, 15, () -> Ingredient.of(ItemsRegistry.etherealShard.get()));
    public static Tier NONE = tier("none", 4, TagsRegistry.INCORRECT_FOR_NONE_TOOL, 1561, 10f, 4.0F, 15, Ingredient::of);
    public static Tier BLAZE_REAP = tier("blazereap", 4, TagsRegistry.INCORRECT_FOR_BLAZEREAP_TOOL, 1561, 14f, 4.0F, 15, Ingredient::of);
    public static Tier NATURE = tier("nature", 4, TagsRegistry.INCORRECT_FOR_NATURE_TOOL, 2651, 16f, 8.0F, 17, () -> Ingredient.of(ItemsRegistry.natureIngot.get()));
    public static Tier AQUARIUS = tier("depth", 5, TagsRegistry.INCORRECT_FOR_DEPTH_TOOL, 3256, 18f, 9f, 18, () -> Ingredient.of(ItemsRegistry.aquariusIngot.get()));
    public static Tier INFERNAL = tier("infernal", 5, TagsRegistry.INCORRECT_FOR_INFERNAL_TOOL, 4256, 20f, 10.0F, 19, () -> Ingredient.of(ItemsRegistry.infernalIngot.get()));
    public static Tier JADE = tier("jade", 5, TagsRegistry.INCORRECT_FOR_JADE_TOOL, 4112, 22f, 11F, 20, () -> Ingredient.of(ItemsRegistry.jade.get()));
    public static Tier SPIDER = tier("spider", 5, TagsRegistry.INCORRECT_FOR_SPIDER_TOOL, 2831, 22f, 11F, 15, () -> Ingredient.of(ItemsRegistry.spiderFang.get()));
    public static Tier PYRATITE = tier("pyratite", 6, TagsRegistry.INCORRECT_FOR_PYRATITE_TOOL, 3112, 24f, 13F, 15, () -> Ingredient.of(ItemsRegistry.pyratite.get()));
    public static Tier BLOOD = tier("meat", 5, TagsRegistry.INCORRECT_FOR_MEAT_TOOL, 2431, 24.0F, 15.0F, 15, () -> Ingredient.of(ItemsRegistry.painCrystal.get()));
    public static Tier NIHILITY = tier("void", 5, TagsRegistry.INCORRECT_FOR_VOID_TOOL, 5248, 30F, 17.0F, 20, () -> Ingredient.of(ItemsRegistry.nihilityShard.get()));
    public static Tier PHANTOM = tier("phantom", 5, TagsRegistry.INCORRECT_FOR_PHANTOM_TOOL, 6428, 35F, 20F, 20, () -> Ingredient.of(ItemsRegistry.illusionStone.get()));

    public static List<ItemStack> getTieredItems(Tier tier) {
        List<ItemStack> list = new ArrayList<>();
        for (var entry : BuiltInRegistries.ITEM.entrySet()) {
            Item item = entry.getValue();
            if (item instanceof TieredItem tieredItem && tieredItem.getTier() == tier) {
                list.add(item.getDefaultInstance());
            }
        }

        return list;
    }

    public static int levelOf(Tier tier){
        Integer level = LEVELS.get(tier);
        if(level != null) return level;
        if(tier instanceof Tiers vanilla){
            return switch(vanilla){
                case WOOD, GOLD -> 0;
                case STONE -> 1;
                case IRON -> 2;
                case DIAMOND -> 3;
                case NETHERITE -> 4;
            };
        }
        return 0;
    }

    public static ResourceLocation nameOf(Tier tier){
        return NAMES.get(tier);
    }

    public static Tier tier(String name, int level, TagKey<Block> incorrectBlocks, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repair){
        SimpleTier tier = new SimpleTier(incorrectBlocks, uses, speed, damage, enchantmentValue, repair);
        LEVELS.put(tier, level);
        NAMES.put(tier, Valoria.loc(name));
        return tier;
    }
}
