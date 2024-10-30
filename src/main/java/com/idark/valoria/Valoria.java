package com.idark.valoria;

import com.google.common.collect.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.curio.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.ui.*;
import com.idark.valoria.client.ui.screen.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.command.arguments.*;
import com.idark.valoria.core.conditions.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.datagen.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.proxy.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.entity.ai.sensing.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.registries.item.skins.*;
import com.idark.valoria.registries.item.types.curio.charm.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.util.*;
import com.mojang.logging.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.data.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.Heightmap.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.data.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import org.slf4j.*;
import top.theillusivec4.curios.api.client.*;

import java.util.*;

import static com.idark.valoria.registries.EntityStatsRegistry.*;

@Mod(Valoria.ID)
public class Valoria {
    public static final String ID = "valoria";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static UUID BASE_ENTITY_REACH_UUID = UUID.fromString("c2e6b27c-fff1-4296-a6b2-7cfff13296cf");
    public static UUID BASE_PROJECTILE_DAMAGE_UUID = UUID.fromString("5334b818-69d4-417e-b4b8-1869d4917e29");
    public static UUID BASE_DASH_DISTANCE_UUID = UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82");
    public static UUID BASE_ATTACK_RADIUS_UUID = UUID.fromString("49438567-6ad2-41bd-8385-676ad2a1bd5e");
    public static UUID BASE_NECROMANCY_LIFETIME_UUID = UUID.fromString("09a12525-61a5-4d57-a125-2561a56d578e");
    public static UUID BASE_NECROMANCY_COUNT_UUID = UUID.fromString("ed80691e-f153-4b5e-8069-1ef153bb5eed");

    public Valoria() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EffectsRegistry.register(eventBus);
        EnchantmentsRegistry.register(eventBus);
        MiscRegistry.init(eventBus);
        AttributeRegistry.register(eventBus);
        PotionBrewery.register(eventBus);
        EntityTypeRegistry.register(eventBus);
        ItemsRegistry.load(eventBus);
        BlockRegistry.register(eventBus);
        LevelGen.init(eventBus);

        BlockEntitiesRegistry.register(eventBus);
        RecipesRegistry.register(eventBus);
        MenuRegistry.register(eventBus);
        SensorTypes.register(eventBus);
        ParticleRegistry.register(eventBus);
        LootUtil.register(eventBus);
        ModArgumentTypes.register(eventBus);
        SkinsRegistry.register();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(KeyBindHandler::onInput);
            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(OverlayRender::tick);
            forgeBus.addListener(OverlayRender::onDrawScreenPost);
            forgeBus.addListener(OverlayRenderItem::onDrawScreenPost);
//            forgeBus.addListener(EventPriority.NORMAL, false, CustomizeGuiOverlayEvent.BossEventProgress.class, BossbarRenderer::onBossInfoRender);
            return new Object();
        });

        ItemTabRegistry.register(eventBus);
        eventBus.addListener(ItemTabRegistry::addCreative);
        SoundsRegistry.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    /**
     * To add your items here you'll need to add it in FMLClientSetupEvent event like this one but in your mod class and add an event to client side
     *
     * @see ValoriaClient.RegistryEvents#onModelRegistryEvent(ModelEvent.RegisterAdditional)
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        ValoriaClient.setupMenu();
        ValoriaClient.initializeBosses();
        event.enqueueWork(() -> {
            LexiconChapters.init();
            BlockEntityRenderers.register(BlockEntitiesRegistry.CHEST_BLOCK_ENTITY.get(), ModChestRender::new);
            BlockEntityRenderers.register(BlockEntitiesRegistry.TRAPPED_CHEST_BLOCK_ENTITY.get(), ModTrappedChestRender::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceDiamond.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceEmerald.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceRuby.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceSapphire.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceHealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceWealth.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceDiamond.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceEmerald.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceRuby.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceSapphire.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceHealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenNecklaceWealth.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceDiamond.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceEmerald.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceRuby.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceSapphire.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceHealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteNecklaceWealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.pickNecklace.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.leatherGloves.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironGloves.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.goldenGloves.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.diamondGloves.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.netheriteGloves.get(), HandsRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.leatherBelt.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.jewelryBag.get(), JewelryBagRenderer::new);
            MenuScreens.register(MenuRegistry.KEG_MENU.get(), KegScreen::new);
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
                    ItemsRegistry.woodenRapier.get(),
                    ItemsRegistry.stoneRapier.get(),
                    ItemsRegistry.ironRapier.get(),
                    ItemsRegistry.club.get()
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
                SpawnPlacements.register(EntityTypeRegistry.GOBLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Goblin::checkGoblinSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.DRAUGR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DraugrEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwampWandererEntity::checkDrownedSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShadewoodSpider::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SUCCUBUS.get(), SpawnPlacements.Type.ON_GROUND, Types.WORLD_SURFACE_WG, Succubus::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), SpawnPlacements.Type.ON_GROUND, Types.WORLD_SURFACE_WG, HauntedMerchant::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.TROLL.get(), SpawnPlacements.Type.ON_GROUND, Types.WORLD_SURFACE_WG, Troll::checkMonsterSpawnRules);
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(EntityTypeRegistry.MANNEQUIN.get(), MANNEQUIN);
            event.put(EntityTypeRegistry.GOBLIN.get(), GOBLIN);
            event.put(EntityTypeRegistry.DRAUGR.get(), DRAUGR);
            event.put(EntityTypeRegistry.NECROMANCER.get(), NECROMANCER);
            event.put(EntityTypeRegistry.SWAMP_WANDERER.get(), SWAMP_WANDERER);
            event.put(EntityTypeRegistry.UNDEAD.get(), UNDEAD);
            event.put(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), SHADEWOOD_SPIDER);
            event.put(EntityTypeRegistry.SUCCUBUS.get(), SUCCUBUS);
            event.put(EntityTypeRegistry.TROLL.get(), TROLL);
            event.put(EntityTypeRegistry.CORRUPTED_TROLL.get(), CORRUPTED_TROLL);

            event.put(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HAUNTED_MERCHANT);
            event.put(EntityTypeRegistry.FLESH_SENTINEL.get(), FLESH_SENTINEL);
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
        }
    }
}