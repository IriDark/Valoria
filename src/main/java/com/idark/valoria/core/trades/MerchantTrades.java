package com.idark.valoria.core.trades;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.VillagerTrades.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.*;
import net.minecraft.world.level.block.*;

public class MerchantTrades {
    public static final Int2ObjectMap<ItemListing[]> HAUNTED_MERCHANT_TRADES = toIntMap(ImmutableMap.of(1, getHauntedTrader()));

    private static Int2ObjectMap<ItemListing[]> toIntMap(ImmutableMap<Integer, ItemListing[]> pMap) {
        return new Int2ObjectOpenHashMap<>(pMap);
    }

    public static ItemListing[] getHauntedTrader() {
        return new ItemListing[]{
                new ItemsForItem(ItemsRegistry.wraithKatana.get(), ItemsRegistry.candyCorn.get(), 36, 1, 4),
                new ItemsForItem(ItemsRegistry.dreadAxe.get(), ItemsRegistry.candyCorn.get(), 26, 1, 2),
                new ItemsForItem(ItemsRegistry.reaperScythe.get(), ItemsRegistry.candyCorn.get(), 52, 1, 6),
                new ItemsForItem(ItemsRegistry.soulReaver.get(), ItemsRegistry.candyCorn.get(), 38, 1, 2),
                new ItemsForItem(ItemsRegistry.pumpkinBomb.get(), ItemsRegistry.candyCorn.get(), 8, 16, 1)
        };
    }

    static class ItemsForItem implements ItemListing {
        private final ItemStack soldStack;
        private final ItemStack currencyStack;
        private final int currencyCount;
        private final int numberOfItems;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public ItemsForItem(Block pBlock, ItemStack pCurrency, int pCost, int pNumberOfItems, int pMaxUses, int pExp) {
            this(new ItemStack(pBlock), pCurrency, pCost, pNumberOfItems, pMaxUses, pExp);
        }

        public ItemsForItem(Item pItem, Item pCurrency, int pCost, int pNumberOfItems, int pExp) {
            this(new ItemStack(pItem), new ItemStack(pCurrency), pCost, pNumberOfItems, 12, pExp);
        }

        public ItemsForItem(Item pItem, Item pCurrency, int pCost, int pNumberOfItems, int pMaxUses, int pExp) {
            this(new ItemStack(pItem), new ItemStack(pCurrency), pCost, pNumberOfItems, pMaxUses, pExp);
        }

        public ItemsForItem(ItemStack pSold, ItemStack pCurrency, int pCost, int pNumberOfItems, int pMaxUses, int pExp) {
            this(pSold, pCurrency, pCost, pNumberOfItems, pMaxUses, pExp, 0.05F);
        }

        public ItemsForItem(ItemStack pSold, ItemStack pCurrency, int pCost, int pNumberOfItems, int pMaxUses, int pExp, float pPriceMultiplier) {
            this.soldStack = pSold;
            this.currencyStack = pCurrency;
            this.currencyCount = pCost;
            this.numberOfItems = pNumberOfItems;
            this.maxUses = pMaxUses;
            this.experience = pExp;
            this.priceMultiplier = pPriceMultiplier;
        }

        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            return new MerchantOffer(new ItemStack(this.currencyStack.getItem(), this.currencyCount), new ItemStack(this.soldStack.getItem(), this.numberOfItems), this.maxUses, this.experience, this.priceMultiplier);
        }
    }
}
