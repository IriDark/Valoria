package com.idark.valoria;

import com.google.common.collect.ImmutableMap;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.client.gui.overlay.CorpsecleaverRender;
import com.idark.valoria.client.gui.overlay.DashOverlayRender;
import com.idark.valoria.client.gui.overlay.MagmaBarRender;
import com.idark.valoria.client.gui.screen.JewelryScreen;
import com.idark.valoria.client.gui.screen.ManipulatorScreen;
import com.idark.valoria.client.gui.screen.book.LexiconChapters;
import com.idark.valoria.client.gui.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.render.curio.BeltRenderer;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.client.render.curio.NecklaceRenderer;
import com.idark.valoria.client.render.model.item.Item2DRenderer;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.core.datagen.BlockStateGen;
import com.idark.valoria.core.datagen.LootTableGen;
import com.idark.valoria.core.datagen.RecipeGen;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.proxy.ClientProxy;
import com.idark.valoria.core.proxy.ISidedProxy;
import com.idark.valoria.core.proxy.ServerProxy;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.ModWoodTypes;
import com.idark.valoria.registries.block.types.SarcophagusBlock;
import com.idark.valoria.registries.command.arguments.ModArgumentTypes;
import com.idark.valoria.registries.entity.decoration.MannequinEntity;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.levelgen.LevelGen;
import com.idark.valoria.registries.recipe.PotionBrewery;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.RenderUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(Valoria.ID)
public class Valoria{
    //TODO: More pyratite content
    //TODO: Mobs for valoria
    //TODO: Misc gen for valoria
    public static final String ID = "valoria";

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Valoria(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SoundsRegistry.SOUNDS.register(eventBus);
        EffectsRegistry.register(eventBus);
        EnchantmentsRegistry.register(eventBus);
        PaintingRegistry.register(eventBus);
        AttributeRegistry.register(eventBus);
        PotionBrewery.register(eventBus);
        ItemsRegistry.register(eventBus);
        BlockRegistry.register(eventBus);
        PoiRegistries.register(eventBus);
        if(QuarkIntegration.isLoaded()){
            QuarkIntegration.init(eventBus);
        }

        BlockEntitiesRegistry.register(eventBus);
        RecipesRegistry.register(eventBus);
        MenuRegistry.register(eventBus);
        EntityTypeRegistry.register(eventBus);
        ParticleRegistry.register(eventBus);
        LootUtil.register(eventBus);
        ModArgumentTypes.register(eventBus);
        LevelGen.FEATURES.register(eventBus);
        LevelGen.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        LevelGen.PLACEMENT_MODIFIERS.register(eventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(RenderUtils::afterLevelRender);
            forgeBus.addListener(DashOverlayRender::tick);
            forgeBus.addListener(DashOverlayRender::onDrawScreenPost);
            forgeBus.addListener(CorpsecleaverRender::tick);
            forgeBus.addListener(CorpsecleaverRender::onDrawScreenPost);
            forgeBus.addListener(MagmaBarRender::onDrawScreenPost);
            //forgeBus.addListener(StaffItem::onDrawScreenPost);
            return new Object();
        });

        ItemTabRegistry.register(eventBus);
        eventBus.addListener(ItemTabRegistry::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    /**
     * To add your items here you'll need to add it in FMLClientSetupEvent event like this one but in your mod class and add an event to client side
     * @see ValoriaClient.RegistryEvents#onModelRegistryEvent(ModelEvent.RegisterAdditional)
     */

    private void clientSetup(final FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            LexiconChapters.init();
            Item2DRenderer.handModelItems.add("valoria:phantom");
            Item2DRenderer.handModelItems.add("valoria:bloodhound");
            Item2DRenderer.handModelItems.add("valoria:cobalt_sword");
            Item2DRenderer.handModelItems.add("valoria:netherite_scythe");
            Item2DRenderer.handModelItems.add("valoria:diamond_scythe");
            Item2DRenderer.handModelItems.add("valoria:golden_scythe");
            Item2DRenderer.handModelItems.add("valoria:iron_scythe");
            Item2DRenderer.handModelItems.add("valoria:pyratite_spear");
            Item2DRenderer.handModelItems.add("valoria:netherite_spear");
            Item2DRenderer.handModelItems.add("valoria:diamond_spear");
            Item2DRenderer.handModelItems.add("valoria:golden_spear");
            Item2DRenderer.handModelItems.add("valoria:iron_spear");
            Item2DRenderer.handModelItems.add("valoria:stone_spear");
            Item2DRenderer.handModelItems.add("valoria:wooden_spear");
            Item2DRenderer.handModelItems.add("valoria:ent");
            Item2DRenderer.handModelItems.add("valoria:nature_scythe");
            Item2DRenderer.handModelItems.add("valoria:infernal_sword");
            Item2DRenderer.handModelItems.add("valoria:infernal_scythe");
            Item2DRenderer.handModelItems.add("valoria:void_edge");
            Item2DRenderer.handModelItems.add("valoria:bronze_sword");
            Item2DRenderer.handModelItems.add("valoria:glaive");
            Item2DRenderer.handModelItems.add("valoria:coral_reef");
            Item2DRenderer.handModelItems.add("valoria:beast"); // MrBeast???? Omg
            Item2DRenderer.handModelItems.add("valoria:aquarius_scythe");
            Item2DRenderer.handModelItems.add("valoria:blaze_reap");
            Item2DRenderer.handModelItems.add("valoria:murasama");

            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_NECKLACE_WEALTH.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_NECKLACE_WEALTH.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_NECKLACE_WEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.PICK_NECKLACE.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.LEATHER_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.IRON_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.GOLDEN_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.DIAMOND_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.NETHERITE_GLOVES.get(), HandsRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.LEATHER_BELT.get(), BeltRenderer::new);

            MenuScreens.register(MenuRegistry.JEWELRY_MENU.get(), JewelryScreen::new);
            MenuScreens.register(MenuRegistry.MANIPULATOR_MENU.get(), ManipulatorScreen::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event){
        PacketHandler.init();
        PotionBrewery.bootStrap();
        RegisterUnlockables.init();
        event.enqueueWork(() -> {
            FireBlock fireblock = (FireBlock)Blocks.FIRE;
            fireblock.setFlammable(BlockRegistry.SHADELOG.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.SHADEWOOD.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.SHADEWOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(BlockRegistry.SHADEWOOD_PLANKS_SLAB.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.SHADEWOOD_PLANKS_STAIRS.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.SHADEWOOD_PLANKS.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.STRIPPED_SHADELOG.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.STRIPPED_SHADEWOOD.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_LOG.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_WOOD.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_LEAVES.get(), 30, 60);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_PLANKS_SLAB.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_PLANKS_STAIRS.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.ELDRITCH_PLANKS.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.STRIPPED_ELDRITCH_LOG.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.STRIPPED_ELDRITCH_WOOD.get(), 5, 30);

            DraugrEntity.draugrCanSpawnWith.add(Items.BOW);
            DraugrEntity.draugrCanSpawnWith.add(Items.WOODEN_AXE);
            DraugrEntity.draugrCanSpawnWith.add(Items.STONE_SWORD);
            DraugrEntity.draugrCanSpawnWith.add(Items.IRON_SWORD);
            DraugrEntity.draugrCanSpawnWith.add(Items.GOLDEN_AXE);
            DraugrEntity.draugrCanSpawnWith.add(Items.IRON_PICKAXE);
            GoblinEntity.goblinCanSpawnWith.add(ItemsRegistry.WOODEN_RAPIER.get());
            GoblinEntity.goblinCanSpawnWith.add(ItemsRegistry.STONE_RAPIER.get());
            GoblinEntity.goblinCanSpawnWith.add(ItemsRegistry.IRON_RAPIER.get());
            GoblinEntity.goblinCanSpawnWith.add(ItemsRegistry.CLUB.get());
            SarcophagusBlock.spawnableWith.add(Items.BOW);
            SarcophagusBlock.spawnableWith.add(Items.WOODEN_AXE);
            SarcophagusBlock.spawnableWith.add(Items.STONE_SWORD);
            SarcophagusBlock.spawnableWith.add(Items.IRON_SWORD);
            SarcophagusBlock.spawnableWith.add(Items.GOLDEN_AXE);
            SarcophagusBlock.spawnableWith.add(Items.IRON_PICKAXE);
            SarcophagusBlock.halloweenSpawnableWith.add(Items.PUMPKIN);
            SarcophagusBlock.halloweenSpawnableWith.add(Items.JACK_O_LANTERN);
            SarcophagusBlock.halloweenSpawnableWith.add(Items.CARVED_PUMPKIN);

            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
            .put(BlockRegistry.SHADELOG.get(), BlockRegistry.STRIPPED_SHADELOG.get())
            .put(BlockRegistry.SHADEWOOD.get(), BlockRegistry.STRIPPED_SHADEWOOD.get())
            .put(BlockRegistry.ELDRITCH_LOG.get(), BlockRegistry.STRIPPED_ELDRITCH_LOG.get())
            .put(BlockRegistry.ELDRITCH_WOOD.get(), BlockRegistry.STRIPPED_ELDRITCH_WOOD.get()).build();

            WoodType.register(ModWoodTypes.ELDRITCH);
            WoodType.register(ModWoodTypes.SHADEWOOD);
        });
    }

    private void processIMC(final InterModProcessEvent event){
        // some example code to receive and process InterModComms from other mods
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event){
            event.register(IUnlockable.class);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event){
            event.enqueueWork(() -> {
                SpawnPlacements.register(EntityTypeRegistry.GOBLIN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GoblinEntity::checkGoblinSpawnRules);

                SpawnPlacements.register(EntityTypeRegistry.DRAUGR.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DraugrEntity::checkMonsterSpawnRules);

                SpawnPlacements.register(EntityTypeRegistry.SWAMP_WANDERER.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SwampWandererEntity::checkDrownedSpawnRules);

                SpawnPlacements.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ShadewoodSpider::checkMonsterSpawnRules);
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(EntityTypeRegistry.MANNEQUIN.get(), MannequinEntity.createAttributes().build());
            event.put(EntityTypeRegistry.GOBLIN.get(), GoblinEntity.createAttributes().build());
            event.put(EntityTypeRegistry.DRAUGR.get(), DraugrEntity.createAttributes().build());
            event.put(EntityTypeRegistry.NECROMANCER.get(), NecromancerEntity.createAttributes().build());
            event.put(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererEntity.createAttributes().build());
            event.put(EntityTypeRegistry.UNDEAD.get(), UndeadEntity.createAttributes().build());
            event.put(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpider.createAttributes().build());
        }

        @SubscribeEvent
        public static void attachAttribute(EntityAttributeModificationEvent event){
            event.add(EntityType.PLAYER, AttributeRegistry.DASH_DISTANCE.get());
            event.add(EntityType.PLAYER, AttributeRegistry.ATTACK_RADIUS.get());
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event){
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            generator.addProvider(event.includeServer(), LootTableGen.create(packOutput));
            generator.addProvider(event.includeServer(), new RecipeGen(packOutput));
            generator.addProvider(event.includeClient(), new BlockStateGen(packOutput, existingFileHelper));
        }
    }
}