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
import net.minecraftforge.client.event.ModelEvent.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.client.model.*;
import pro.komaru.tridot.client.model.item.*;
import pro.komaru.tridot.client.model.render.item.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.common.registry.item.skins.entries.*;
import pro.komaru.tridot.common.registry.item.types.*;
import pro.komaru.tridot.util.*;

import java.util.*;

import static pro.komaru.tridot.common.registry.item.skins.SkinRegistryManager.*;

public class SkinsRegistry implements ISkinProvider{
    public static ItemSkin THE_FALLEN_COLLECTOR, ARCANE_GOLD, CYBERPUNK, MIDNIGHT, MURAMASA, MURASAME, FISH, NERO, STAR_DIVIDER, DEATH_OF_CRABS, ICY, LOTUS;
    public void initializeSkins(){
        THE_FALLEN_COLLECTOR = new SkinBuilder(Valoria.ID, "the_fallen_collector").color(Pal.seaGreen).contributor("Kerdo", Styles.nature)
                .add(new TheFallenCollectorSkinEntry(ArmorItem.class, Valoria.loc("textures/models/armor/skin/the_fallen_collector"))
                        .addArmorSkin(EquipmentSlot.HEAD, Valoria.loc("the_fallen_collector_crown"))
                        .addArmorSkin(EquipmentSlot.CHEST, Valoria.loc("the_fallen_collector_coat")))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.loc("brand")))
                .build();
        ARCANE_GOLD = new SkinBuilder(Valoria.ID, "arcane_gold").color(Pal.arcaneGold)
                .add(new ItemExtendingSkinEntry(ConfigurableBowItem.class, Valoria.loc("arcane_wood_bow")))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.blazeReap.get(), Valoria.loc("arcane_gold_blaze_reap")))
                .build();
        CYBERPUNK = new SkinBuilder(Valoria.ID, "cyberpunk").color(Pal.majestyPurple).contributor("Auriny", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.loc("cyberpunk_quantum_reaper")))
                .build();
        MIDNIGHT = new SkinBuilder(Valoria.ID, "midnight").color(Pal.majestyPurple)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.loc("midnight_quantum_reaper")))
                .build();
        MURAMASA = new SkinBuilder(Valoria.ID, "muramasa").color(Pal.majestyPurple).contributor("Auriny", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.loc("muramasa")))
                .build();
        MURASAME = new SkinBuilder(Valoria.ID, "muramase").color(Pal.flesh)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.loc("murasame")))
                .build();
        FISH = new SkinBuilder(Valoria.ID, "swordfish").color(Pal.crystalBlue).contributor("Skoow", Styles.aquarius)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.murasama.get(), Valoria.loc("swordfish")))
                .build();
        STAR_DIVIDER = new SkinBuilder(Valoria.ID, "star_divider").color((Pal.verySoftPink)).contributor("Rainach", Styles.nihility)
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.quantumReaper.get(), Valoria.loc("star_divider")))
                .build();
        NERO = new SkinBuilder(Valoria.ID, "nero").contributor("NeroWalton", Styles.phantasm).color(Col.fromHex("9A2350"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.phantom.get(), Valoria.loc("nero")))
                .build();
        DEATH_OF_CRABS = new SkinBuilder(Valoria.ID, "death_of_crabs").contributor("TerraPrime", Styles.aquarius).color(Col.fromHex("76bdd1"))
                .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.cobaltSword.get(), Valoria.loc("death_of_crabs")))
                .build();
        ICY = new SkinBuilder(Valoria.ID, "icy").color(Col.fromHex("29dfeb"))
            .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.voidScythe.get(), Valoria.loc("icy_scythe")))
            .build();
        LOTUS = new SkinBuilder(Valoria.ID, "lotus").contributor("KaJiNt", Styles.aquarius).color(Col.fromHex("29dfeb"))
            .add(new ItemSupplierSkinEntry(() -> ItemsRegistry.voidScythe.get(), Valoria.loc("lotus_scythe")))
            .build();
    }

    public void registerModels(){
        ItemSkinModels.addBowSkin(Valoria.loc("arcane_wood_bow"));
        ItemSkinModels.add(Valoria.loc("arcane_gold_blaze_reap"));
        ItemSkinModels.add(Valoria.loc("the_fallen_collector_crown"));
        ItemSkinModels.add(Valoria.loc("the_fallen_collector_coat"));
        ItemSkinModels.add(Valoria.loc("brand"));
        ItemSkinModels.add(Valoria.loc("cyberpunk_quantum_reaper"));
        ItemSkinModels.add(Valoria.loc("midnight_quantum_reaper"));
        ItemSkinModels.add(Valoria.loc("swordfish"));
        ItemSkinModels.add(Valoria.loc("star_divider"));
        ItemSkinModels.add(Valoria.loc("muramasa"));
        ItemSkinModels.add(Valoria.loc("murasame"));
        ItemSkinModels.add(Valoria.loc("death_of_crabs"));
        ItemSkinModels.add(Valoria.loc("icy_scythe"));
        ItemSkinModels.add(Valoria.loc("lotus_scythe"));
        ItemSkinModels.add(Valoria.loc("nero"));
    }

    @Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents{

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onModelRegistry(RegisterAdditional event){
            registerKatana(event);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    TridotModels.addBowItemModel(event, Valoria.ID, item.getId().getPath());
                }

                if(item.get() instanceof ConfigurableCrossbow){
                    TridotModels.addCrossbowItemModel(event, Valoria.ID, item.getId().getPath());
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
        @OnlyIn(Dist.CLIENT)
        public static void onModelBake(ModifyBakingResult event){
            Map<ResourceLocation, BakedModel> map = event.getModels();
            bakeArmor(map);
            bakeKatana(map);
            for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
                if(item.get() instanceof ConfigurableBowItem){
                    TridotModels.addBowItemModel(map, item.getId());
                }

                if(item.get() instanceof ConfigurableCrossbow){
                    TridotModels.addCrossbowItemModel(map, item.getId());
                }
            }

            addSkinModel(map, ItemsRegistry.quantumReaper.getId());
            addLargeModel(map, Valoria.ID, "muramasa");
            addLargeModel(map, Valoria.ID, "murasame");
            addLargeModel(map, Valoria.ID, "icy_scythe");
            addLargeModel(map, Valoria.ID, "lotus_scythe");
            addLargeModel(map, Valoria.ID, "death_of_crabs");
            addLargeModel(map, Valoria.ID, "brand");
            addLargeModel(map, Valoria.ID, "arcane_gold_blaze_reap");
            addLargeModel(map, Valoria.ID, "nero");
            addLargeModel(map, Valoria.ID, "swordfish");
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
                    addSkinModel(map, item.getId());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void bakeArmor(Map<ResourceLocation, BakedModel> map){
        for(RegistryObject<Item> item : ItemsRegistry.ITEMS.getEntries()){
            if(item.get() instanceof SkinableArmorItem){
                addSkinModel(map, item.getId());
            }
        }
    }
}