package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class ItemTierRegistry{
    //WOOD(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    //STONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    //IRON(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    //DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(Items.DIAMOND)),
    //GOLD(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
    //NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static Tier NONE = registerTier(new ForgeTier(4, 1561, 10f, 4.0F, 15, TagsRegistry.NEEDS_NONE_TOOL, Ingredient::of), Valoria.loc("none"));
    public static Tier BRONZE = TierSortingRegistry.registerTier(new ForgeTier(2, 1048, 4f, 0.0F, 8, TagsRegistry.NEEDS_BRONZE_TOOL, () -> Ingredient.of(ItemsRegistry.bronzeIngot.get())), Valoria.loc("bronze"), List.of(Tiers.STONE), List.of(Tiers.IRON));
    public static Tier PEARLIUM = registerTier(new ForgeTier(3, 425, 5f, 2.0F, 6, TagsRegistry.NEEDS_PEARLIUM_TOOL, () -> Ingredient.of(ItemsRegistry.pearliumIngot.get())), Valoria.loc("pearlium"));
    public static Tier HOLIDAY = registerTier(new ForgeTier(2, 740, 5f, 2.0F, 8, TagsRegistry.NEEDS_HOLIDAY_TOOL, () -> Ingredient.of(ItemsRegistry.holidayCandy.get())), Valoria.loc("holiday"));
    public static Tier HALLOWEEN = registerTier(new ForgeTier(3, 1150, 6f, 3.0F, 8, TagsRegistry.NEEDS_HALLOWEEN_TOOL, () -> Ingredient.of(ItemsRegistry.candyCorn.get())), Valoria.loc("halloween"));
    public static Tier SAMURAI = registerTier(new ForgeTier(3, 1250, 10f, 5.0F, 7, TagsRegistry.NEEDS_SAMURAI_TOOL, () -> Ingredient.of(ItemsRegistry.ancientIngot.get())), Valoria.loc("samurai"));
    public static Tier COBALT = TierSortingRegistry.registerTier(new ForgeTier(4, 1750, 12f, 4f, 12, TagsRegistry.NEEDS_COBALT_TOOL, () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())), Valoria.loc("cobalt"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
    public static Tier ETHEREAL = TierSortingRegistry.registerTier(new ForgeTier(4, 2025, 15f, 5f, 15, TagsRegistry.NEEDS_ETHEREAL_TOOL, () -> Ingredient.of(ItemsRegistry.etherealShard.get())), Valoria.loc("ethereal"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
    public static Tier NATURE = TierSortingRegistry.registerTier(new ForgeTier(5, 2651, 17f, 8.0F, 17, TagsRegistry.NEEDS_NATURE_TOOL, () -> Ingredient.of(ItemsRegistry.natureIngot.get())), Valoria.loc("nature"), List.of(Tiers.NETHERITE), List.of(Valoria.loc("depth")));
    public static Tier AQUARIUS = TierSortingRegistry.registerTier(new ForgeTier(5, 3256, 18f, 9f, 18, TagsRegistry.NEEDS_DEPTH_TOOL, () -> Ingredient.of(ItemsRegistry.aquariusIngot.get())), Valoria.loc("depth"), List.of(Valoria.loc("nature")), List.of(Valoria.loc("infernal")));
    public static Tier INFERNAL= TierSortingRegistry.registerTier(new ForgeTier(5, 4256, 20f, 10.0F, 19, TagsRegistry.NEEDS_INFERNAL_TOOL, () -> Ingredient.of(ItemsRegistry.infernalIngot.get())), Valoria.loc("infernal"), List.of(Valoria.loc("depth")), List.of(Valoria.loc("void")));
    public static Tier JADE = TierSortingRegistry.registerTier(new ForgeTier(5, 4112, 22f, 11F, 20, TagsRegistry.NEEDS_JADE_TOOL, () -> Ingredient.of(ItemsRegistry.jade.get())), Valoria.loc("jade"), List.of(Tiers.NETHERITE), List.of(Valoria.loc("depth")));
    public static Tier SPIDER = TierSortingRegistry.registerTier(new ForgeTier(5, 2831, 22f, 11F, 15, TagsRegistry.NEEDS_SPIDER_TOOL, () -> Ingredient.of(ItemsRegistry.spiderFang.get())), Valoria.loc("spider"), List.of(Tiers.NETHERITE), List.of(Valoria.loc("depth")));
    public static Tier PYRATITE = TierSortingRegistry.registerTier(new ForgeTier(6, 3112, 24f, 13F, 15, TagsRegistry.NEEDS_PYRATITE_TOOL, () -> Ingredient.of(ItemsRegistry.pyratite.get())), Valoria.loc("pyratite"), List.of(Tiers.NETHERITE), List.of(Valoria.loc("depth")));
    public static Tier BLOOD = TierSortingRegistry.registerTier(new ForgeTier(5, 2431, 24.0F, 15.0F, 15, TagsRegistry.NEEDS_MEAT_TOOL, () -> Ingredient.of(ItemsRegistry.painCrystal.get())), Valoria.loc("meat"), List.of(Tiers.NETHERITE), List.of(Valoria.loc("depth")));
    public static Tier NIHILITY = TierSortingRegistry.registerTier(new ForgeTier(5, 5248, 30F, 17.0F, 20, TagsRegistry.NEEDS_VOID_TOOL, () -> Ingredient.of(ItemsRegistry.nihilityShard.get())), Valoria.loc("void"), List.of(Valoria.loc("infernal")), List.of(Valoria.loc("phantom")));
    public static Tier PHANTOM = TierSortingRegistry.registerTier(new ForgeTier(5, 6428, 35F, 20F, 20, TagsRegistry.NEEDS_PHANTOM_TOOL, () -> Ingredient.of(ItemsRegistry.illusionStone.get())), Valoria.loc("phantom"), List.of(Valoria.loc("void")), List.of());

    public static List<ItemStack> getTieredItems(Tier tier) {
        List<ItemStack> list = new ArrayList<>();
        for (var entry : ForgeRegistries.ITEMS.getEntries()) {
            Item item = entry.getValue();
            if (item instanceof TieredItem tieredItem && tieredItem.getTier() == tier) {
                list.add(item.getDefaultInstance());
            }
        }

        return list;
    }

    public static Tier registerTier(Tier tier, ResourceLocation loc){
        return TierSortingRegistry.registerTier(tier, loc, List.of(Tiers.STONE), List.of(Tiers.DIAMOND));
    }
}