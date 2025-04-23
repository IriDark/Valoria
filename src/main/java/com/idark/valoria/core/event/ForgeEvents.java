package com.idark.valoria.core.event;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.jei.jer.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.npc.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.saveddata.maps.MapDecoration.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.village.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import pro.komaru.tridot.api.entity.trades.*;
import pro.komaru.tridot.api.entity.trades.Trade.*;

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
            trade.trades(4).xp(4).createOffer(4, ItemsRegistry.ironRing.get());
            trade.trades(4).xp(4).createOffer(4, ItemsRegistry.ironChain.get());

            trade.tier(TradeTier.APPRENTICE).trades(8).createOffer(8, ItemsRegistry.ironRingRuby.get());
            trade.tier(TradeTier.APPRENTICE).trades(8).createOffer(8, ItemsRegistry.ironGloves.get());
            trade.tier(TradeTier.APPRENTICE).trades(2).xp(16).createOffer(18, ItemsRegistry.diamondGloves.get());

            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingDiamond.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingRuby.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingSapphire.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingAmber.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceAmber.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceRuby.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceSapphire.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(14, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceHealth.get());
            trade.tier(TradeTier.JOURNEYMAN).trades(4).xp(12).createOffer(16, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceWealth.get());
        }

        if(event.getType() == VillagerProfession.FARMER){
            trade.trades(64).createOffer(1, ItemsRegistry.woodenCup.get());
            trade.trades(64).createOffer(1, ItemsRegistry.cup.get());
            trade.trades(16).createOffer(2, ItemsRegistry.greenTeaCup.get());
            trade.trades(16).createOffer(2, ItemsRegistry.coffeeCup.get());
            trade.trades(16).createOffer(2, ItemsRegistry.cacaoCup.get());
            trade.trades(16).createOffer(2, ItemsRegistry.teaCup.get());
            trade.trades(4).createOffer(2, ItemsRegistry.beerCup.get());
            trade.trades(4).createOffer(2, ItemsRegistry.rumCup.get());

            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.kvassBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.wineBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.akvavitBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.sakeBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.liquorBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.meadBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.cognacBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.whiskeyBottle.get());
            trade.trades(8).tier(TradeTier.APPRENTICE).createOffer(6, ItemsRegistry.rumBottle.get());
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
            trade.tier(TradeTier.APPRENTICE).trades(1).xp(4).createListing(new TreasureMapItemListing(trade, 6, TagsRegistry.ON_CRYPT_EXPLORER_MAPS, "filled_map.valoria.crypt", Type.RED_X));
            trade.tier(TradeTier.APPRENTICE).trades(1).xp(6).createListing(new TreasureMapItemListing(trade, 16, TagsRegistry.ON_NECROMANCER_CRYPT_EXPLORER_MAPS, "filled_map.valoria.necromancer_crypt", Type.BANNER_BLACK));
        }

        if(event.getType() == VillagerProfession.LIBRARIAN){
            trade.trades(1).createOffer(1, ItemsRegistry.codex.get());
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event){
        Trade trade = new WanderingTrade(event.getGenericTrades());
        trade.trades(1).xp(4).createOffer(6, ItemsRegistry.ironRing.get());
        trade.trades(1).xp(2).createOffer(6, ItemsRegistry.karusakanRoot.get());
        trade.trades(1).xp(2).createOffer(6, ItemsRegistry.gaibRoot.get());
        trade.trades(6).xp(2).createOffer(4, ItemsRegistry.aloeBandage.get());
        trade.trades(4).xp(2).createOffer(2, ItemsRegistry.aloePiece.get());
    }
}