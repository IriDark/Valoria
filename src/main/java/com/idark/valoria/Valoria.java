package com.idark.valoria;

import com.google.common.collect.ImmutableMap;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.client.event.KeyBindHandler;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.render.curio.BeltRenderer;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.client.render.curio.JewelryBagRenderer;
import com.idark.valoria.client.render.curio.NecklaceRenderer;
import com.idark.valoria.client.render.tile.ModChestRender;
import com.idark.valoria.client.render.tile.ModTrappedChestRender;
import com.idark.valoria.client.ui.OverlayRender;
import com.idark.valoria.client.ui.screen.JewelryScreen;
import com.idark.valoria.client.ui.screen.ManipulatorScreen;
import com.idark.valoria.client.ui.screen.book.LexiconChapters;
import com.idark.valoria.client.ui.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.conditions.LootConditionsRegistry;
import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.core.datagen.BlockStateGen;
import com.idark.valoria.core.datagen.LootModifiersProvider;
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
import com.idark.valoria.registries.entity.ai.sensing.SensorTypes;
import com.idark.valoria.registries.entity.decoration.MannequinEntity;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.item.recipe.PotionBrewery;
import com.idark.valoria.registries.item.types.MagmaSwordItem;
import com.idark.valoria.registries.item.types.curio.charm.CurioCurses;
import com.idark.valoria.registries.item.types.ranged.BlazeReapItem;
import com.idark.valoria.registries.levelgen.LevelGen;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.RenderUtils;
import com.idark.valoria.util.ValoriaUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
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
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(Valoria.ID)
public class Valoria {
    public static final String ID = "valoria";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Valoria() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SoundsRegistry.SOUNDS.register(eventBus);
        EffectsRegistry.register(eventBus);
        EnchantmentsRegistry.register(eventBus);
        MiscRegistry.init(eventBus);
        AttributeRegistry.register(eventBus);
        PotionBrewery.register(eventBus);
        ItemsRegistry.register(eventBus);
        BlockRegistry.register(eventBus);
        LevelGen.init(eventBus);

        BlockEntitiesRegistry.register(eventBus);
        RecipesRegistry.register(eventBus);
        MenuRegistry.register(eventBus);
        SensorTypes.register(eventBus);
        EntityTypeRegistry.register(eventBus);
        ParticleRegistry.register(eventBus);
        LootUtil.register(eventBus);
        ModArgumentTypes.register(eventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(KeyBindHandler::onInput);
            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(RenderUtils::afterLevelRender);
            forgeBus.addListener(OverlayRender::tick);
            forgeBus.addListener(OverlayRender::onDrawScreenPost);
            forgeBus.addListener(MagmaSwordItem::onDrawScreenPost);
            forgeBus.addListener(BlazeReapItem::onDrawScreenPost);
            return new Object();
        });

        ItemTabRegistry.register(eventBus);
        eventBus.addListener(ItemTabRegistry::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    /**
     * To add your items here you'll need to add it in FMLClientSetupEvent event like this one but in your mod class and add an event to client side
     *
     * @see ValoriaClient.RegistryEvents#onModelRegistryEvent(ModelEvent.RegisterAdditional)
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            LexiconChapters.init();
            BlockEntityRenderers.register(BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get(), ModChestRender::new);
            BlockEntityRenderers.register(BlockEntitiesRegistry.TRAPPED_CHEST_BLOCK_ENTITY.get(), ModTrappedChestRender::new);
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
            CuriosRendererRegistry.register(ItemsRegistry.JEWELRY_BAG.get(), JewelryBagRenderer::new);

            MenuScreens.register(MenuRegistry.JEWELRY_MENU.get(), JewelryScreen::new);
            MenuScreens.register(MenuRegistry.MANIPULATOR_MENU.get(), ManipulatorScreen::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        PotionBrewery.bootStrap();
        LootConditionsRegistry.register();
        RegisterUnlockables.init();
        event.enqueueWork(() -> {
            FireBlock fireblock = (FireBlock) Blocks.FIRE;
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
            DraugrEntity.spawnable(
                    Items.BOW,
                    Items.WOODEN_AXE,
                    Items.STONE_SWORD,
                    Items.IRON_SWORD,
                    Items.GOLDEN_AXE,
                    Items.IRON_PICKAXE
            );

            Goblin.spawnable(
                    ItemsRegistry.WOODEN_RAPIER.get(),
                    ItemsRegistry.STONE_RAPIER.get(),
                    ItemsRegistry.IRON_RAPIER.get(),
                    ItemsRegistry.CLUB.get()
            );

            ValoriaUtils.addList(SarcophagusBlock.spawnableWith,
                    Items.BOW,
                    Items.WOODEN_AXE,
                    Items.STONE_SWORD,
                    Items.IRON_SWORD,
                    Items.GOLDEN_AXE,
                    Items.IRON_PICKAXE
            );

            ValoriaUtils.addList(SarcophagusBlock.halloweenSpawnableWith,
                    Items.PUMPKIN,
                    Items.JACK_O_LANTERN,
                    Items.CARVED_PUMPKIN
            );

            CurioCurses.effects(
                    MobEffects.DARKNESS,
                    MobEffects.WEAKNESS,
                    MobEffects.WITHER,
                    MobEffects.POISON,
                    MobEffects.MOVEMENT_SLOWDOWN,
                    MobEffects.DIG_SLOWDOWN
            );

            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
                    .put(BlockRegistry.SHADELOG.get(), BlockRegistry.STRIPPED_SHADELOG.get())
                    .put(BlockRegistry.SHADEWOOD.get(), BlockRegistry.STRIPPED_SHADEWOOD.get())
                    .put(BlockRegistry.ELDRITCH_LOG.get(), BlockRegistry.STRIPPED_ELDRITCH_LOG.get())
                    .put(BlockRegistry.ELDRITCH_WOOD.get(), BlockRegistry.STRIPPED_ELDRITCH_WOOD.get()).build();

            WoodType.register(ModWoodTypes.ELDRITCH);
            WoodType.register(ModWoodTypes.SHADEWOOD);
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(IUnlockable.class);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                SpawnPlacements.register(EntityTypeRegistry.GOBLIN.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        Goblin::checkGoblinSpawnRules);

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

                SpawnPlacements.register(EntityTypeRegistry.SUCCUBUS.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Types.WORLD_SURFACE_WG,
                        Succubus::checkMonsterSpawnRules);

                SpawnPlacements.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Types.WORLD_SURFACE_WG,
                        HauntedMerchant::checkMonsterSpawnRules);

                SpawnPlacements.register(EntityTypeRegistry.TROLL.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Types.WORLD_SURFACE_WG,
                        Troll::checkMonsterSpawnRules);
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityTypeRegistry.MANNEQUIN.get(), MannequinEntity.createAttributes().build());
            event.put(EntityTypeRegistry.GOBLIN.get(), Goblin.createAttributes().build());
            event.put(EntityTypeRegistry.DRAUGR.get(), DraugrEntity.createAttributes().build());
            event.put(EntityTypeRegistry.NECROMANCER.get(), NecromancerEntity.createAttributes().build());
            event.put(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererEntity.createAttributes().build());
            event.put(EntityTypeRegistry.UNDEAD.get(), UndeadEntity.createAttributes().build());
            event.put(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpider.createAttributes().build());
            event.put(EntityTypeRegistry.SUCCUBUS.get(), Succubus.createAttributes().build());
            event.put(EntityTypeRegistry.TROLL.get(), Troll.createAttributes().build());
            event.put(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HauntedMerchant.createAttributes().build());
        }

        @SubscribeEvent
        public static void attachAttribute(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, AttributeRegistry.DASH_DISTANCE.get());
            event.add(EntityType.PLAYER, AttributeRegistry.ATTACK_RADIUS.get());
            event.add(EntityType.PLAYER, AttributeRegistry.PROJECTILE_DAMAGE.get());
            event.add(EntityType.PLAYER, AttributeRegistry.NECROMANCY_LIFETIME.get());
            event.add(EntityType.PLAYER, AttributeRegistry.NECROMANCY_COUNT.get());
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            generator.addProvider(event.includeServer(), LootTableGen.create(packOutput));
            generator.addProvider(event.includeServer(), new RecipeGen(packOutput));
            generator.addProvider(event.includeClient(), new BlockStateGen(packOutput, existingFileHelper));
            generator.addProvider(event.includeServer(), new LootModifiersProvider(packOutput));
        }
    }
}