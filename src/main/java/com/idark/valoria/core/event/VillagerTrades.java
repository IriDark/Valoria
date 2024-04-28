package com.idark.valoria.core.event;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.ItemsRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class VillagerTrades {

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void addTrades(VillagerTradesEvent event) {
            Int2ObjectMap<List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing>> trades = event.getTrades();
            if (event.getType() == VillagerProfession.TOOLSMITH) {
                trades.get(1).add(itemPurchase(6, ItemsRegistry.IRON_RING.get(), 1, 4, 2));
                trades.get(2).add(itemPurchase(16, ItemsRegistry.IRON_GLOVES.get(), 1, 1, 12));
                trades.get(1).add(itemPurchase(4, ItemsRegistry.IRON_CHAIN.get(), 1, 2, 6));
                trades.get(3).add(itemPurchase(18, ItemsRegistry.IRON_RING_EMERALD.get(), 1, 1, 12));
            }

            if (event.getType() == VillagerProfession.WEAPONSMITH) {
                trades.get(2).add(itemPurchase(14, ItemsRegistry.IRON_KATANA.get(), 1, 1, 12));
                trades.get(1).add(itemPurchase(6, ItemsRegistry.IRON_RAPIER.get(), 1, 1, 6));
                trades.get(1).add(itemPurchase(2, ItemsRegistry.CLUB.get(), 1, 2, 6));
                trades.get(2).add(itemPurchase(22, ItemsRegistry.IRON_SCYTHE.get(), 1, 1, 16));
            }

            if (event.getType() == VillagerProfession.LIBRARIAN) {
                trades.get(1).add(itemPurchase(4, ItemsRegistry.LEXICON.get(), 1, 12, 5));
            }
        }

        @SubscribeEvent
        public static void addWanderingTrades(WandererTradesEvent event) {
            List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing> trades = event.getGenericTrades();
            System.out.print(trades.get(1));
            trades.add(itemSell(4, ItemsRegistry.IRON_RING.get(), 1, 12));
            trades.add(itemPurchase(6, ItemsRegistry.KARUSAKAN_ROOT.get(), 1, 2, 12));
            trades.add(itemPurchase(6, ItemsRegistry.GAIB_ROOT.get(), 1, 2, 12));
            trades.add(itemPurchase(4, ItemsRegistry.ALOE_BANDAGE.get(), 2, 4, 6));
            trades.add(itemPurchase(4, ItemsRegistry.ALOE_PIECE.get(), 1, 2, 12));
        }

        public static BasicItemListing itemPurchase(int pEmeralds, ItemLike item, int count, int maxTrades, int xp) {
            return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD, pEmeralds), maxTrades, xp, 0.05F);
        }

        public static BasicItemListing itemSell(int pEmeralds, ItemLike item, int maxTrades, int xp) {
            return new BasicItemListing(pEmeralds, new ItemStack(item), maxTrades, xp, 0.05F);
        }
    }
}