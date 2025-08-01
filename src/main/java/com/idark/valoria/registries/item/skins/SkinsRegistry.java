package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.entries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.client.model.*;
import pro.komaru.tridot.client.model.item.*;
import pro.komaru.tridot.client.model.render.item.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.common.registry.item.types.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class SkinsRegistry{
    public static ItemSkin THE_FALLEN_COLLECTOR, ARCANE_GOLD, CYBERPUNK, MIDNIGHT, MURAMASA, MURASAME, FISH, NERO, STAR_DIVIDER, DEATH_OF_CRABS, ICY, LOTUS;

    public static void init(){
        THE_FALLEN_COLLECTOR = new SkinBuilder(Valoria.ID, "the_fallen_collector").color(Pal.seaGreen).contributor("Kerdo", Styles.nature)
                .add(new TheFallenCollectorSkinEntry(ArmorItem.class, Valoria.ID + ":textures/models/armor/skin/the_fallen_collector")
                        .addArmorSkin(EquipmentSlot.HEAD, Valoria.ID + ":the_fallen_collector_crown")
                        .addArmorSkin(EquipmentSlot.CHEST, Valoria.ID + ":the_fallen_collector_coat"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":brand"))
                .build();
        ARCANE_GOLD = new SkinBuilder(Valoria.ID, "arcane_gold").color(Pal.arcaneGold)
                .add(new ItemExtendingSkinEntry(ConfigurableBowItem.class, Valoria.ID + ":arcane_wood_bow"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.blazeReap.get(), Valoria.ID + ":arcane_gold_blaze_reap"))
                .build();
        CYBERPUNK = new SkinBuilder(Valoria.ID, "cyberpunk").color(Pal.majestyPurple).contributor("Auriny", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":cyberpunk_quantum_reaper"))
                .build();
        MIDNIGHT = new SkinBuilder(Valoria.ID, "midnight").color(Pal.majestyPurple)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":midnight_quantum_reaper"))
                .build();
        MURAMASA = new SkinBuilder(Valoria.ID, "muramasa").color(Pal.majestyPurple).contributor("Auriny", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":muramasa"))
                .build();
        MURASAME = new SkinBuilder(Valoria.ID, "muramase").color(Pal.flesh)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":murasame"))
                .build();
        FISH = new SkinBuilder(Valoria.ID, "swordfish").color(Pal.crystalBlue).contributor("Skoow", Styles.aquarius)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.ID + ":swordfish"))
                .build();
        STAR_DIVIDER = new SkinBuilder(Valoria.ID, "star_divider").color((Pal.verySoftPink)).contributor("Rainach", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.ID + ":star_divider"))
                .build();
        NERO = new SkinBuilder(Valoria.ID, "nero").contributor("NeroWalton", Styles.phantasm).color(Col.fromHex("9A2350"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.phantom.get(), Valoria.ID + ":nero"))
                .build();
        DEATH_OF_CRABS = new SkinBuilder(Valoria.ID, "death_of_crabs").contributor("TerraPrime", Styles.aquarius).color(Col.fromHex("76bdd1"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.cobaltSword.get(), Valoria.ID + ":death_of_crabs"))
                .build();
        ICY = new SkinBuilder(Valoria.ID, "icy").color(Col.fromHex("29dfeb"))
            .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.voidScythe.get(), Valoria.ID + ":icy_scythe"))
            .build();
        LOTUS = new SkinBuilder(Valoria.ID, "lotus").contributor("KaJiNt", Styles.aquarius).color(Col.fromHex("29dfeb"))
            .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.voidScythe.get(), Valoria.ID + ":lotus_scythe"))
            .build();
    }

    public static void register(){
        init();
        ItemSkinHandler.register(ARCANE_GOLD);
        ItemSkinHandler.register(THE_FALLEN_COLLECTOR);
        ItemSkinHandler.register(CYBERPUNK);
        ItemSkinHandler.register(MIDNIGHT);
        ItemSkinHandler.register(MURASAME);
        ItemSkinHandler.register(MURAMASA);
        ItemSkinHandler.register(FISH);
        ItemSkinHandler.register(NERO);
        ItemSkinHandler.register(STAR_DIVIDER);
        ItemSkinHandler.register(DEATH_OF_CRABS);
        ItemSkinHandler.register(ICY);
        ItemSkinHandler.register(LOTUS);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(){
        ItemSkinModels.addBowSkin(Valoria.ID + ":arcane_wood_bow");
        ItemSkinModels.addSkin(Valoria.ID + ":arcane_gold_blaze_reap");
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_crown");
        ItemSkinModels.addSkin(Valoria.ID + ":the_fallen_collector_coat");
        ItemSkinModels.addSkin(Valoria.ID + ":brand");
        ItemSkinModels.addSkin(Valoria.ID + ":cyberpunk_quantum_reaper");
        ItemSkinModels.addSkin(Valoria.ID + ":midnight_quantum_reaper");
        ItemSkinModels.addSkin(Valoria.ID + ":swordfish");
        ItemSkinModels.addSkin(Valoria.ID + ":star_divider");
        ItemSkinModels.addSkin(Valoria.ID + ":muramasa");
        ItemSkinModels.addSkin(Valoria.ID + ":murasame");
        ItemSkinModels.addSkin(Valoria.ID + ":death_of_crabs");
        ItemSkinModels.addSkin(Valoria.ID + ":icy_scythe");
        ItemSkinModels.addSkin(Valoria.ID + ":lotus_scythe");
        ItemSkinModels.addSkin(Valoria.ID + ":nero");
    }

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents{
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event){
            registerKatana(event);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    TridotModels.addBowItemModel(event, Valoria.ID, item.getId().getPath());
                }
            }

            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "void_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "cobalt_sword"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "phantom"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "blaze_reap"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/lotus_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/icy_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/death_of_crabs"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/arcane_gold_blaze_reap"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/muramasa"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/murasame"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/brand"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/nero"));
            event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, "skin/swordfish"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event){
            Map<ResourceLocation, BakedModel> map = event.getModels();
            bakeArmor(map);
            bakeKatana(map);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    TridotModels.addBowItemModel(map, item.getId());
                }
            }

            TridotItemSkins.addSkinModel(map, ItemsRegistry.quantumReaper.getId());
            TridotItemSkins.addLargeModel(map, Valoria.ID, "muramasa");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "murasame");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "icy_scythe");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "lotus_scythe");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "death_of_crabs");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "brand");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "nero");
            TridotItemSkins.addLargeModel(map, Valoria.ID, "swordfish");
            LargeItemRenderer.bakeModel(map, Valoria.ID, "void_scythe", new ItemSkinItemOverrides());
            LargeItemRenderer.bakeModel(map, Valoria.ID, "cobalt_sword", new ItemSkinItemOverrides());
            LargeItemRenderer.bakeModel(map, Valoria.ID, "phantom", new ItemSkinItemOverrides());
            LargeItemRenderer.bakeModel(map, Valoria.ID, "blaze_reap", new ItemSkinItemOverrides());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerKatana(ModelEvent.RegisterAdditional event){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem && ((KatanaItem)item.get()).builder.hasLargeModel){
                event.register(LargeItemRenderer.getModelResourceLocation(Valoria.ID, item.getId().getPath()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeKatana(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof KatanaItem katana){
                if(katana.builder.hasLargeModel){
                    LargeItemRenderer.bakeModel(map, Valoria.ID, item.getId().getPath(), new ItemSkinItemOverrides());
                }else{
                    TridotItemSkins.addSkinModel(map, item.getId());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeArmor(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof SkinableArmorItem){
                TridotItemSkins.addSkinModel(map, item.getId());
            }
        }
    }
}