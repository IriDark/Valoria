package com.idark.valoria.core.event;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.jer.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.npc.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.saveddata.maps.MapDecoration.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.village.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import pro.komaru.tridot.core.entity.trades.*;
import pro.komaru.tridot.core.entity.trades.Trade.*;

import java.util.*;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents{

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event){
        if(ModList.get().isLoaded("jeresources")){
            JerCompat.onClientPlayerLogin(event);
        }
    }

    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event){
        Trade trade = new Trade(event.getTrades());
        if(event.getType() == VillagerProfessionRegistry.JEWELER.get()){
            trade.trades(16).createSale(2, ItemsRegistry.amberGem.get());
            trade.trades(16).createOffer(2, Items.AMETHYST_SHARD);
            trade.trades(16).createOffer(3, ItemsRegistry.sapphireGem.get());
            trade.trades(16).createOffer(4, ItemsRegistry.rubyGem.get());
            trade.trades(4).xp(2).createOffer(4, ItemsRegistry.ironRing.get());
            trade.trades(4).xp(2).createOffer(4, ItemsRegistry.ironChain.get());
            trade.tier(TradeTier.APPRENTICE).trades(2).createOffer(8, ItemsRegistry.ironRingRuby.get());
            trade.tier(TradeTier.APPRENTICE).trades(2).createOffer(8, ItemsRegistry.ironGloves.get());
            trade.tier(TradeTier.EXPERT).trades(1).xp(2).createOffer(10, ItemsRegistry.goldenRingAmber.get());
            trade.tier(TradeTier.EXPERT).trades(1).xp(4).createOffer(16, ItemsRegistry.goldenNecklaceHealth.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(6).createOffer(18, ItemsRegistry.diamondGloves.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingDiamond.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingRuby.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingSapphire.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingAmber.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenNecklaceAmber.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenNecklaceRuby.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenNecklaceSapphire.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(14, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenNecklaceHealth.get());
            trade.tier(TradeTier.MASTER).trades(1).xp(12).createOffer(16, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenNecklaceWealth.get());
        }

        if(event.getType() == VillagerProfession.TOOLSMITH){
            trade.trades(1).createOffer(6, ItemsRegistry.ironRing.get());
            trade.trades(1).xp(2).createOffer(12, ItemsRegistry.ironGloves.get());
            trade.trades(1).createOffer(4, ItemsRegistry.ironChain.get());
            trade.trades(1).xp(2).createOffer(16, ItemsRegistry.ironRingEmerald.get());
        }

        if(event.getType() == VillagerProfession.WEAPONSMITH){
            trade.tier(TradeTier.APPRENTICE).trades(1).createOffer(12, ItemsRegistry.ironKatana.get());
            trade.trades(1).createOffer(6, ItemsRegistry.ironRapier.get());
            trade.trades(1).createOffer(2, ItemsRegistry.club.get());
            trade.tier(TradeTier.APPRENTICE).trades(1).createOffer(16, ItemsRegistry.ironScythe.get());
        }

        if(event.getType() == VillagerProfession.CARTOGRAPHER){
            trade.tier(TradeTier.APPRENTICE).trades(1).xp(4).createListing(new TreasureMapItemListing(trade, 8, TagsRegistry.ON_CRYPT_EXPLORER_MAPS, "filled_map.valoria.crypt", Type.RED_X));
            trade.tier(TradeTier.APPRENTICE).trades(1).xp(6).createListing(new TreasureMapItemListing(trade, 16, TagsRegistry.ON_NECROMANCER_CRYPT_EXPLORER_MAPS, "filled_map.valoria.necromancer_crypt", Type.BANNER_BLACK));
        }

        if(event.getType() == VillagerProfession.LIBRARIAN){
            trade.trades(1).createOffer(1, ItemsRegistry.lexicon.get());
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event){
        List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing> trades = event.getGenericTrades();
        trades.add(itemSell(4, ItemsRegistry.ironRing.get(), 1, 12));
        trades.add(itemSell(6, ItemsRegistry.karusakanRoot.get(), 1, 2));
        trades.add(itemSell(6, ItemsRegistry.gaibRoot.get(), 1, 2));
        trades.add(itemSell(4, ItemsRegistry.aloeBandage.get(), 2, 4));
        trades.add(itemPurchase(4, ItemsRegistry.aloePiece.get(), 1, 2, 2));
    }

    public static BasicItemListing itemPurchase(int pEmeralds, ItemLike item, int count, int maxTrades, int xp){
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD, pEmeralds), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemSell(int pEmeralds, ItemLike additional, ItemLike item, int maxTrades, int xp){
        return new BasicItemListing(new ItemStack(Items.EMERALD, pEmeralds), new ItemStack(additional), new ItemStack(item), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemSell(int pEmeralds, ItemLike item, int maxTrades, int xp){
        return new BasicItemListing(pEmeralds, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}