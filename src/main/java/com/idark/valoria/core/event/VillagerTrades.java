package com.idark.valoria.core.event;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.village.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

import java.util.*;

public class VillagerTrades {

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void addTrades(VillagerTradesEvent event) {
            Int2ObjectMap<List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing>> trades = event.getTrades();
            if (event.getType() == VillagerProfession.TOOLSMITH) {
                trades.get(1).add(itemSell(6, ItemsRegistry.ironRing.get(), 1, 4));
                trades.get(2).add(itemSell(16, ItemsRegistry.ironGloves.get(), 1, 1));
                trades.get(1).add(itemSell(4, ItemsRegistry.ironChain.get(), 1, 2));
                trades.get(3).add(itemSell(18, ItemsRegistry.ironRingEmerald.get(), 1, 1));
            }

            if (event.getType() == VillagerProfession.WEAPONSMITH) {
                trades.get(2).add(itemSell(14, ItemsRegistry.ironKatana.get(), 1, 1));
                trades.get(1).add(itemSell(6, ItemsRegistry.ironRapier.get(), 1, 1));
                trades.get(1).add(itemSell(2, ItemsRegistry.club.get(), 1, 2));
                trades.get(2).add(itemSell(22, ItemsRegistry.ironScythe.get(), 1, 1));
            }

            if (event.getType() == VillagerProfession.LIBRARIAN) {
                trades.get(1).add(itemSell(1, ItemsRegistry.lexicon.get(), 1, 2));
            }
        }

        @SubscribeEvent
        public static void addWanderingTrades(WandererTradesEvent event) {
            List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing> trades = event.getGenericTrades();
            System.out.print(trades.get(1));
            trades.add(itemSell(4, ItemsRegistry.ironRing.get(), 1, 12));
            trades.add(itemSell(6, ItemsRegistry.karusakanRoot.get(), 1, 2));
            trades.add(itemSell(6, ItemsRegistry.gaibRoot.get(), 1, 2));
            trades.add(itemSell(4, ItemsRegistry.aloeBandage.get(), 2, 4));
            trades.add(itemPurchase(4, ItemsRegistry.aloePiece.get(), 1, 2, 2));
        }

        /**
         * an Item that villager purchases
         */
        public static BasicItemListing itemPurchase(int pEmeralds, ItemLike item, int count, int maxTrades, int xp) {
            return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD, pEmeralds), maxTrades, xp, 0.05F);
        }

        /**
         * an Item that villager sells
         */
        public static BasicItemListing itemSell(int pEmeralds, ItemLike item, int maxTrades, int xp) {
            return new BasicItemListing(pEmeralds, new ItemStack(item), maxTrades, xp, 0.05F);
        }
    }
}