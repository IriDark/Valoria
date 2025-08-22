package com.idark.valoria;

import com.google.common.collect.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.curio.*;
import com.idark.valoria.client.ui.screen.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.command.arguments.*;
import com.idark.valoria.core.compat.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.datagen.*;
import com.idark.valoria.core.loot.conditions.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.proxy.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.registries.item.skins.*;
import com.idark.valoria.registries.item.types.curio.charm.rune.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.util.*;
import com.mojang.logging.*;
import net.mehvahdjukaar.dummmmmmy.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
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
import net.minecraftforge.fml.config.ModConfig.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.fml.loading.*;
import org.slf4j.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import top.theillusivec4.curios.api.client.*;

import java.util.*;

import static com.idark.valoria.registries.EntityStatsRegistry.*;

@Mod(Valoria.ID)
public class Valoria{
    public static final String ID = "valoria";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceLocation FONT = loc("icons");
    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static UUID BASE_ENTITY_REACH_UUID = UUID.fromString("c2e6b27c-fff1-4296-a6b2-7cfff13296cf");
    public static UUID BASE_DASH_DISTANCE_UUID = UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82");
    public static UUID BASE_ATTACK_RADIUS_UUID = UUID.fromString("49438567-6ad2-41bd-8385-676ad2a1bd5e");
    public static UUID BASE_NECROMANCY_LIFETIME_UUID = UUID.fromString("09a12525-61a5-4d57-a125-2561a56d578e");
    public static UUID BASE_NECROMANCY_COUNT_UUID = UUID.fromString("ed80691e-f153-4b5e-8069-1ef153bb5eed");

    public static UUID BASE_NATURE_DAMAGE_UUID = UUID.fromString("15171755-91d0-466e-9717-5591d0b66eba");
    public static UUID BASE_DEPTH_DAMAGE_UUID = UUID.fromString("ff1ed1ea-4a25-462e-9ed1-ea4a25862ef9");
    public static UUID BASE_INFERNAL_DAMAGE_UUID = UUID.fromString("780fa02c-8040-44c7-8fa0-2c804004c776");
    public static UUID BASE_NIHILITY_DAMAGE_UUID = UUID.fromString("8a7e1c44-e461-4692-be1c-44e4618692f6");
    public static UUID BASE_NATURE_RESISTANCE_UUID = UUID.fromString("38289748-ba5b-4ee1-a897-48ba5b3ee15a");
    public static UUID BASE_DEPTH_RESISTANCE_UUID = UUID.fromString("59a7d286-60fc-4a18-a7d2-8660fc9a1803");
    public static UUID BASE_INFERNAL_RESISTANCE_UUID = UUID.fromString("b3198f26-6e76-497b-998f-266e76097b48");
    public static UUID BASE_NIHILITY_RESISTANCE_UUID = UUID.fromString("d54e60fa-ef27-4181-8e60-faef2771814e");
    public static UUID BASE_ELEMENTAL_RESISTANCE_UUID = UUID.fromString("14e99f51-ad2a-4996-a99f-51ad2a0996a0");

    public static final GameRules.Key<GameRules.BooleanValue> DISABLE_BLOCK_BREAKING = GameRules.register("valoria:disableBossDungeonGriefing", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> TRAP_ACTIVATING = GameRules.register("valoria:trapActivating", GameRules.Category.MISC, GameRules.BooleanValue.create(true));

    public Valoria(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EffectsRegistry.register(eventBus);
        EnchantmentsRegistry.register(eventBus);
        MiscRegistry.init(eventBus);
        AttributeReg.register(eventBus);
        PotionBrewery.register(eventBus);
        EntityTypeRegistry.register(eventBus);
        ItemsRegistry.load(eventBus);
        BlockRegistry.load(eventBus);
        LevelGen.init(eventBus);
        LootConditionsRegistry.init(eventBus);

        BlockEntitiesRegistry.register(eventBus);
        RecipesRegistry.register(eventBus);
        MenuRegistry.register(eventBus);
        ParticleRegistry.register(eventBus);
        ModArgumentTypes.register(eventBus);
        SkinRegistryManager.getInstance().registerSkinProvider(new SkinsRegistry());

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(Type.COMMON, CommonConfig.SPEC);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(KeyBindHandler::onInput);
            forgeBus.addListener(ClientEvents::onMouseClick);
            forgeBus.addListener(ClientEvents::onTooltipRender);
            forgeBus.addListener(Events::onTooltip);
            return new Object();
        });

        ItemTabRegistry.register(eventBus);
        eventBus.addListener(ItemTabRegistry::addCreative);
        SoundsRegistry.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        forgeBus.register(this);
        forgeBus.register(new Events());
        forgeBus.register(new StructureEvents());
        if (FMLEnvironment.dist.isClient()) {
            forgeBus.register(ClientEvents.class);
        }
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(ID, path);
    }

    /**
     * To add your items here you'll need to add it in FMLClientSetupEvent event like this one but in your mod class and add an event to client side
     * @see ValoriaClient.RegistryEvents#onModelRegistryEvent(ModelEvent.RegisterAdditional)
     */
    private void clientSetup(final FMLClientSetupEvent event){
        ValoriaClient.setupClient();
        AbstractBossbar.bossbars.put("Wicked Crystal", new BasicBossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/wicked_crystal.png")));
        AbstractBossbar.bossbars.put("Necromancer", new BasicBossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")));
        AbstractBossbar.bossbars.put("Dryador", new BasicBossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/dryador.png")));
        event.enqueueWork(() -> {
            CodexEntries.initChapters();
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceDiamond.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceEmerald.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceRuby.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceSapphire.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceAmber.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceHealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.ironNecklaceWealth.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.respirator.get(), RespiratorRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.gasMask.get(), GasMaskRenderer::new);

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
            CuriosRendererRegistry.register(ItemsRegistry.magmaticGauntlet.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.skeletalVambrace.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.magmaticVambrace.get(), HandsRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.monocle.get(), MonocleRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.brokenMonocle.get(), MonocleRenderer::new);

            CuriosRendererRegistry.register(ItemsRegistry.samuraiBelt.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.leatherBelt.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ItemsRegistry.jewelryBag.get(), JewelryBagRenderer::new);
            MenuScreens.register(MenuRegistry.TINKERING_MENU.get(), TinkeringScreen::new);
            MenuScreens.register(MenuRegistry.KEG_MENU.get(), KegScreen::new);
            MenuScreens.register(MenuRegistry.JEWELRY_MENU.get(), JewelryScreen::new);
            MenuScreens.register(MenuRegistry.MANIPULATOR_MENU.get(), ManipulatorScreen::new);
            MenuScreens.register(MenuRegistry.KILN_MENU.get(), KilnScreen::new);
            MenuScreens.register(MenuRegistry.SOUL_INFUSER_MENU.get(), SoulInfuserScreen::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event){
        Valoria.LOGGER.debug("Item count: {}", ItemsRegistry.ITEMS.getEntries().size());
        Valoria.LOGGER.debug("Block count: {}", BlockRegistry.BLOCK.getEntries().size());
        Valoria.LOGGER.debug("Entity count: {}", EntityTypeRegistry.ENTITY_TYPES.getEntries().size());
        RegisterUnlockables.init();
        ItemsRegistry.setupBook();
        PacketHandler.init();
        PotionBrewery.bootStrap();
        DispenserBehaviours.bootStrap();
        event.enqueueWork(() -> {
            ModCompats.init();
            FireBlock fireblock = (FireBlock)Blocks.FIRE;
            fireblock.setFlammable(BlockRegistry.shadelog.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.shadewood.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.shadewoodLeaves.get(), 30, 60);
            fireblock.setFlammable(BlockRegistry.shadewoodPlanksSlab.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.shadewoodPlanksStairs.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.shadewoodPlanks.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.strippedShadelog.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.strippedShadewood.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.eldritchLog.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.eldritchWood.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.eldritchLeaves.get(), 30, 60);
            fireblock.setFlammable(BlockRegistry.eldritchPlanksSlab.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.eldritchPlanksStairs.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.eldritchPlanks.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.strippedEldritchLog.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.strippedEldritchWood.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.dreadwoodLog.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.dreadWood.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.dreadwoodPlanksSlab.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.dreadwoodPlanksStairs.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.dreadwoodPlanks.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.strippedDreadwoodLog.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.strippedDreadWood.get(), 5, 30);
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
            .put(BlockRegistry.shadelog.get(), BlockRegistry.strippedShadelog.get())
            .put(BlockRegistry.shadewood.get(), BlockRegistry.strippedShadewood.get())
            .put(BlockRegistry.eldritchLog.get(), BlockRegistry.strippedEldritchLog.get())
            .put(BlockRegistry.eldritchWood.get(), BlockRegistry.strippedEldritchWood.get()).build();

            WoodType.register(ModWoodTypes.ELDRITCH);
            WoodType.register(ModWoodTypes.SHADEWOOD);
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{

//        @SubscribeEvent
//        public static void onRegister(RegisterEvent event) {
//            registerVanillaItem(event, "shield", () -> new ValoriaShieldItem(35f, new Item.Properties().durability(336)));
//        }
//
//        private static void registerVanillaItem(RegisterEvent event, String name, Supplier<Item> item) {
//            event.register(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("minecraft", name), item);
//        }

        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event){
            event.register(IUnlockable.class);
            event.register(INihilityLevel.class);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event){
            event.enqueueWork(() -> {
                SpawnPlacements.register(EntityTypeRegistry.GOBLIN.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Goblin::checkGoblinSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.DRAUGR.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, DraugrEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SwampWandererEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SCOURGE.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ScourgeEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ShadewoodSpider::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.DEVIL.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Devil::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, HauntedMerchant::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.TROLL.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Troll::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.CORRUPTED_TROLL.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Troll::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SORCERER.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SorcererEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.ENT.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Ent::checkEntSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.MAGGOT.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MaggotEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.CORRUPTED.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Corrupted::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.KING_CRAB.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, KingCrabEntity::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.WICKED_SCORPION.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, WickedScorpion::checkMonsterSpawnRules);
                SpawnPlacements.register(EntityTypeRegistry.SCAVENGER.get(), SpawnPlacements.Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Scavenger::checkAnimalSpawnRules);
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(EntityTypeRegistry.MANNEQUIN.get(), MANNEQUIN);
            event.put(EntityTypeRegistry.GOBLIN.get(), GOBLIN);
            event.put(EntityTypeRegistry.DRAUGR.get(), DRAUGR);
            event.put(EntityTypeRegistry.NECROMANCER.get(), NECROMANCER);
            event.put(EntityTypeRegistry.SCOURGE.get(), SCOURGE);
            event.put(EntityTypeRegistry.SWAMP_WANDERER.get(), SWAMP_WANDERER);
            event.put(EntityTypeRegistry.UNDEAD.get(), UNDEAD);
            event.put(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), SHADEWOOD_SPIDER);
            event.put(EntityTypeRegistry.DEVIL.get(), DEVIL);
            event.put(EntityTypeRegistry.TROLL.get(), TROLL);
            event.put(EntityTypeRegistry.CORRUPTED_TROLL.get(), CORRUPTED_TROLL);
            event.put(EntityTypeRegistry.SORCERER.get(), SORCERER);
            event.put(EntityTypeRegistry.WICKED_SCORPION.get(), WICKED_SCORPION);
            event.put(EntityTypeRegistry.WICKED_CRYSTAL.get(), WICKED_CRYSTAL);
            event.put(EntityTypeRegistry.WICKED_SHIELD.get(), WICKED_SHIELD);
            event.put(EntityTypeRegistry.CRYSTAL.get(), CRYSTAL);
            event.put(EntityTypeRegistry.ENT.get(), ENT);
            event.put(EntityTypeRegistry.MAGGOT.get(), MAGGOT);
            event.put(EntityTypeRegistry.DRYADOR.get(), DRYADOR);
            event.put(EntityTypeRegistry.PIXIE.get(), PIXIE);
            event.put(EntityTypeRegistry.CORRUPTED.get(), CORRUPTED);
            event.put(EntityTypeRegistry.KING_CRAB.get(), KING_CRAB);
            event.put(EntityTypeRegistry.SCAVENGER.get(), SCAVENGER);

            event.put(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HAUNTED_MERCHANT);
            event.put(EntityTypeRegistry.FLESH_SENTINEL.get(), FLESH_SENTINEL);
        }

        @SubscribeEvent
        public static void attachAttribute(EntityAttributeModificationEvent event){
            event.add(EntityType.PLAYER, AttributeReg.DASH_DISTANCE.get());
            event.add(EntityType.PLAYER, AttributeReg.ATTACK_RADIUS.get());
            event.add(EntityType.PLAYER, AttributeReg.NECROMANCY_LIFETIME.get());
            event.add(EntityType.PLAYER, AttributeReg.NECROMANCY_COUNT.get());
            event.add(EntityType.PLAYER, AttributeReg.MAX_NIHILITY.get());
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_RESILIENCE.get());

            event.add(EntityType.PLAYER, AttributeReg.INFERNAL_DAMAGE.get());
            event.add(EntityType.PLAYER, AttributeReg.DEPTH_DAMAGE.get());
            event.add(EntityType.PLAYER, AttributeReg.NATURE_DAMAGE.get());
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_DAMAGE.get());
            event.add(EntityType.PLAYER, AttributeReg.INFERNAL_RESISTANCE.get());
            event.add(EntityType.PLAYER, AttributeReg.DEPTH_RESISTANCE.get());
            event.add(EntityType.PLAYER, AttributeReg.NATURE_RESISTANCE.get());
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_RESISTANCE.get());
            event.add(EntityType.PLAYER, AttributeReg.ELEMENTAL_RESISTANCE.get());
            if(ModList.get().isLoaded("dummmmmmy")) {
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.INFERNAL_RESISTANCE.get());
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.DEPTH_RESISTANCE.get());
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.NATURE_RESISTANCE.get());
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.NIHILITY_RESISTANCE.get());
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.ELEMENTAL_RESISTANCE.get());
            }

            event.add(EntityTypeRegistry.WICKED_CRYSTAL.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 45);
            event.add(EntityTypeRegistry.WICKED_CRYSTAL.get(), AttributeReg.ELEMENTAL_RESISTANCE.get(), 15);
            event.add(EntityTypeRegistry.DRYADOR.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.DRYADOR.get(), AttributeReg.NATURE_RESISTANCE.get(), 50);
            event.add(EntityTypeRegistry.NECROMANCER.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.NECROMANCER.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 15);
            event.add(EntityTypeRegistry.DEVIL.get(), AttributeReg.DEPTH_RESISTANCE.get(), -15);
            event.add(EntityTypeRegistry.DEVIL.get(), AttributeReg.INFERNAL_RESISTANCE.get(), 25);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NATURE_RESISTANCE.get(), 15);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 25);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NIHILITY_DAMAGE.get(), 1);
            event.add(EntityTypeRegistry.PIXIE.get(), AttributeReg.NATURE_RESISTANCE.get(), 15);
            event.add(EntityTypeRegistry.PIXIE.get(), AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.TROLL.get(), AttributeReg.NATURE_RESISTANCE.get(), 15);
            event.add(EntityTypeRegistry.TROLL.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NATURE_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 35);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NIHILITY_DAMAGE.get(), 3);
            event.add(EntityTypeRegistry.ENT.get(), AttributeReg.NATURE_RESISTANCE.get(), 35);
            event.add(EntityTypeRegistry.ENT.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.SORCERER.get(), AttributeReg.ELEMENTAL_RESISTANCE.get(), 25);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -45);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 35);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.NIHILITY_DAMAGE.get(), 2);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -45);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 65);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.NIHILITY_DAMAGE.get(), 6);
            event.add(EntityTypeRegistry.FLESH_SENTINEL.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -45);
            event.add(EntityTypeRegistry.FLESH_SENTINEL.get(), AttributeReg.NIHILITY_RESISTANCE.get(), 35);
            event.add(EntityTypeRegistry.UNDEAD.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.UNDEAD.get(), AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.SCOURGE.get(), AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.SCOURGE.get(), AttributeReg.DEPTH_RESISTANCE.get(), 25);
            event.add(EntityTypeRegistry.SWAMP_WANDERER.get(), AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.SWAMP_WANDERER.get(), AttributeReg.DEPTH_RESISTANCE.get(), 25);
            event.add(EntityTypeRegistry.DRAUGR.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityTypeRegistry.KING_CRAB.get(), AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityTypeRegistry.KING_CRAB.get(), AttributeReg.DEPTH_RESISTANCE.get(), 50);

            event.add(EntityType.DROWNED, AttributeReg.DEPTH_RESISTANCE.get(), 25);
            event.add(EntityType.DROWNED, AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityType.HUSK, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.ZOMBIE, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.ZOMBIE_HORSE, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.ZOMBIE_VILLAGER, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.ZOMBIFIED_PIGLIN, AttributeReg.INFERNAL_RESISTANCE.get(), 35);
            event.add(EntityType.SKELETON, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.SKELETON_HORSE, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.CREEPER, AttributeReg.NATURE_RESISTANCE.get(), 35); // plant theory
            event.add(EntityType.SPIDER, AttributeReg.NATURE_RESISTANCE.get(), 15);
            event.add(EntityType.CAVE_SPIDER, AttributeReg.NATURE_RESISTANCE.get(), 15);
            event.add(EntityType.SLIME, AttributeReg.DEPTH_RESISTANCE.get(), 35);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.DEPTH_RESISTANCE.get(), -50);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.INFERNAL_RESISTANCE.get(), 25);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityType.WITHER, AttributeReg.INFERNAL_RESISTANCE.get(), 100);
            event.add(EntityType.WITHER_SKELETON, AttributeReg.INFERNAL_RESISTANCE.get(), 50);
            event.add(EntityType.WITHER_SKELETON, AttributeReg.NATURE_RESISTANCE.get(), -15);
            event.add(EntityType.SNOW_GOLEM, AttributeReg.INFERNAL_RESISTANCE.get(), -35);
            event.add(EntityType.SNOW_GOLEM, AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityType.IRON_GOLEM, AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityType.STRIDER, AttributeReg.INFERNAL_RESISTANCE.get(), 50);
            event.add(EntityType.GUARDIAN, AttributeReg.INFERNAL_RESISTANCE.get(), -25);
            event.add(EntityType.ELDER_GUARDIAN, AttributeReg.INFERNAL_RESISTANCE.get(), -50);
            event.add(EntityType.GUARDIAN, AttributeReg.DEPTH_RESISTANCE.get(), 25);
            event.add(EntityType.ELDER_GUARDIAN, AttributeReg.DEPTH_RESISTANCE.get(), 50);
            event.add(EntityType.STRAY, AttributeReg.ELEMENTAL_RESISTANCE.get(), -15);
            event.add(EntityType.STRAY, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.PHANTOM, AttributeReg.NIHILITY_RESISTANCE.get(), -35);
            event.add(EntityType.PHANTOM, AttributeReg.INFERNAL_RESISTANCE.get(), -15);
            event.add(EntityType.GHAST, AttributeReg.INFERNAL_RESISTANCE.get(), 25);
            event.add(EntityType.GHAST, AttributeReg.DEPTH_RESISTANCE.get(), 25);
            event.add(EntityType.GHAST, AttributeReg.NIHILITY_RESISTANCE.get(), -25);
            event.add(EntityType.BLAZE, AttributeReg.INFERNAL_RESISTANCE.get(), 45);
            event.add(EntityType.BLAZE, AttributeReg.DEPTH_RESISTANCE.get(), -25);
            event.add(EntityType.VEX, AttributeReg.ELEMENTAL_RESISTANCE.get(), -25);
            event.add(EntityType.WARDEN, AttributeReg.ELEMENTAL_RESISTANCE.get(), 45);
            event.add(EntityType.WITCH, AttributeReg.ELEMENTAL_RESISTANCE.get(), 25);
            event.add(EntityType.WITCH, AttributeReg.NIHILITY_RESISTANCE.get(), -35);
            event.add(EntityType.ENDERMAN, AttributeReg.ELEMENTAL_RESISTANCE.get(), 15);
            event.add(EntityType.ENDERMAN, AttributeReg.NIHILITY_RESISTANCE.get(), -15);
            event.add(EntityType.SHULKER, AttributeReg.ELEMENTAL_RESISTANCE.get(), 15);
            event.add(EntityType.SHULKER, AttributeReg.NIHILITY_RESISTANCE.get(), -35);
            event.add(EntityType.ALLAY, AttributeReg.ELEMENTAL_RESISTANCE.get(), 15);
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