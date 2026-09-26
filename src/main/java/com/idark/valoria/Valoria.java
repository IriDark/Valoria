package com.idark.valoria;

import com.idark.valoria.client.event.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.core.*;
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
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.components.*;
import com.idark.valoria.registries.item.armor.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.registries.item.skins.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.registries.level.events.*;
import com.mojang.logging.*;
import net.mehvahdjukaar.dummmmmmy.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.GameRules.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.Heightmap.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.fml.loading.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.data.event.*;
import net.neoforged.neoforge.event.entity.*;
import org.slf4j.*;
import pro.komaru.tridot.common.registry.item.skins.*;

import static com.idark.valoria.registries.EntityStatsRegistry.*;

@Mod(Valoria.ID)
public class Valoria{
    public static final String ID = "valoria";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceLocation FONT = loc("icons");
    public static final ISidedProxy proxy = FMLEnvironment.dist.isClient() ? new ClientProxy() : new ServerProxy();
    public static final ResourceLocation BASE_ENTITY_REACH_ID = loc("base_entity_reach");
    public static final ResourceLocation BASE_DASH_DISTANCE_ID = loc("base_dash_distance");
    public static final ResourceLocation BASE_ATTACK_RADIUS_ID = loc("base_attack_radius");
    public static final ResourceLocation BASE_NECROMANCY_COUNT_ID = loc("base_necromancy_count");

    public static final ResourceLocation BASE_NATURE_DAMAGE_ID = loc("base_nature_damage");
    public static final ResourceLocation BASE_DEPTH_DAMAGE_ID = loc("base_depth_damage");
    public static final ResourceLocation BASE_INFERNAL_DAMAGE_ID = loc("base_infernal_damage");
    public static final ResourceLocation BASE_NIHILITY_DAMAGE_ID = loc("base_nihility_damage");
    public static final ResourceLocation BASE_NATURE_RESISTANCE_ID = loc("base_nature_resistance");
    public static final ResourceLocation BASE_DEPTH_RESISTANCE_ID = loc("base_depth_resistance");
    public static final ResourceLocation BASE_INFERNAL_RESISTANCE_ID = loc("base_infernal_resistance");
    public static final ResourceLocation BASE_NIHILITY_RESISTANCE_ID = loc("base_nihility_resistance");
    public static final ResourceLocation BASE_ELEMENTAL_RESISTANCE_ID = loc("base_elemental_resistance");

    public static final GameRules.Key<GameRules.BooleanValue> DISABLE_BLOCK_BREAKING = GameRules.register("valoria:disableBossDungeonGriefing", Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> TRAP_ACTIVATING = GameRules.register("valoria:trapActivating", Category.MISC, GameRules.BooleanValue.create(true));

    public Valoria(IEventBus eventBus, ModContainer container){
        ConfigMigrator.migrate();
        DataComponentsRegistry.register(eventBus);
        EffectsRegistry.register(eventBus);
        EnchantmentsRegistry.register(eventBus);
        MiscRegistry.init(eventBus);
        AttributeReg.register(eventBus);
        PotionBrewery.register(eventBus);
        EntityTypeRegistry.register(eventBus);
        ArmorRegistry.register(eventBus);
        ItemsRegistry.load(eventBus);
        BlockRegistry.load(eventBus);
        LevelGen.init(eventBus);
        LootConditionsRegistry.init(eventBus);

        BlockEntitiesRegistry.register(eventBus);
        eventBus.addListener(BlockCapabilities::register);
        RecipesRegistry.register(eventBus);
        MenuRegistry.register(eventBus);
        ParticleRegistry.register(eventBus);
        ModArgumentTypes.register(eventBus);
        ValoriaAttachments.register(eventBus);
        SkinRegistryManager.getInstance().registerSkinProvider(new SkinsRegistry());
        ItemTabRegistry.register(eventBus);
        SoundsRegistry.register(eventBus);

        IEventBus forgeBus = NeoForge.EVENT_BUS;
        container.registerConfig(Type.SERVER, ServerConfig.SPEC, "valoria/server.toml");
        container.registerConfig(Type.CLIENT, ClientConfig.SPEC, "valoria/client.toml");
        container.registerConfig(Type.COMMON, CommonConfig.SPEC, "valoria/common.toml");
        if(FMLEnvironment.dist.isClient()){
            KeyBindHandler.register(forgeBus);
            forgeBus.addListener(Events::onTooltip);
            forgeBus.addListener(AbilityOverlayHandler::onDrawScreenPost);
        }

        eventBus.addListener(ItemTabRegistry::addCreative);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(PacketHandler::register);

        forgeBus.register(new Events());
        forgeBus.register(new CapabilityEvents());
        forgeBus.register(new StructureEvents());
        if (FMLEnvironment.dist.isClient()) {
            forgeBus.register(ClientEvents.class);
            forgeBus.register(AbilityClientEvents.class);
        }
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        ValoriaClient.setupClient(event);
    }

    private void setup(final FMLCommonSetupEvent event){
        PatreonManager.fetchPatrons();
        AbilityRegistry.register(DescriptionAbility.TYPE);
        AbilityRegistry.register(DashAbility.TYPE);
        AbilityRegistry.register(ScytheAbility.TYPE);
        Valoria.LOGGER.debug("Item count: {}", ItemsRegistry.ITEMS.getEntries().size());
        Valoria.LOGGER.debug("Block count: {}", BlockRegistry.BLOCK.getEntries().size());
        Valoria.LOGGER.debug("Entity count: {}", EntityTypeRegistry.ENTITY_TYPES.getEntries().size());
        RegisterUnlockables.init();
        ItemsRegistry.setupBook();
        PotionBrewery.bootStrap();
        DispenserBehaviours.bootStrap();
        event.enqueueWork(() -> {
            ModCompats.init();
            FireBlock fireblock = (FireBlock)Blocks.FIRE;
            fireblock.setFlammable(BlockRegistry.shadeLog.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.shadeWood.get(), 5, 20);
            fireblock.setFlammable(BlockRegistry.shadeLeaves.get(), 30, 60);
            fireblock.setFlammable(BlockRegistry.shadePlanksSlab.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.shadePlanksStairs.get(), 5, 40);
            fireblock.setFlammable(BlockRegistry.shadePlanks.get(), 5, 25);
            fireblock.setFlammable(BlockRegistry.strippedShadeLog.get(), 5, 30);
            fireblock.setFlammable(BlockRegistry.strippedShadeWood.get(), 5, 30);
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
            WoodType.register(ModWoodTypes.ELDRITCH);
            WoodType.register(ModWoodTypes.SHADEWOOD);
            WoodType.register(ModWoodTypes.DREADWOOD);
        });
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void onPlacementRegistry(RegisterSpawnPlacementsEvent event){
            event.register(EntityTypeRegistry.GOBLIN.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Goblin::checkGoblinSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.DRAUGR.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, DraugrEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SwampWandererEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.SCOURGE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ScourgeEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, ShadewoodSpider::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.DEVIL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Devil::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, HauntedMerchant::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.TROLL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Troll::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.CORRUPTED_TROLL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Troll::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.SORCERER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SorcererEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.ENT.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Ent::checkEntSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.NATURE_GOLEM.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, NatureGolem::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.RIVER_GOLEM.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, RiverGolem::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.MAGMATIC_GOLEM.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MagmaticGolem::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.MAGGOT.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MaggotEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.CORRUPTED.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Corrupted::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.KING_CRAB.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, KingCrabEntity::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.WICKED_SCORPION.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, WickedScorpion::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.SCAVENGER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Scavenger::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(EntityTypeRegistry.FLESH_SENTINEL.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Types.MOTION_BLOCKING_NO_LEAVES, (type, level, spawnType, pos, random) -> true, RegisterSpawnPlacementsEvent.Operation.OR);
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
            event.put(EntityTypeRegistry.NATURE_GOLEM.get(), NATURE_GOLEM);
            event.put(EntityTypeRegistry.RIVER_GOLEM.get(), RIVER_GOLEM);
            event.put(EntityTypeRegistry.MAGMATIC_GOLEM.get(), MAGMATIC_GOLEM);
            event.put(EntityTypeRegistry.MAGGOT.get(), MAGGOT);
            event.put(EntityTypeRegistry.DRYADOR.get(), DRYADOR);
            event.put(EntityTypeRegistry.PIXIE.get(), PIXIE);
            event.put(EntityTypeRegistry.CORRUPTED.get(), CORRUPTED);
            event.put(EntityTypeRegistry.KING_CRAB.get(), KING_CRAB);
            event.put(EntityTypeRegistry.SCAVENGER.get(), SCAVENGER);
            event.put(EntityTypeRegistry.FIRRON.get(), FIRRON);

            event.put(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HAUNTED_MERCHANT);
            event.put(EntityTypeRegistry.FLESH_SENTINEL.get(), FLESH_SENTINEL);
        }

        @SubscribeEvent
        public static void attachAttribute(EntityAttributeModificationEvent event){
            event.add(EntityType.PLAYER, AttributeReg.DASH_DISTANCE);
            event.add(EntityType.PLAYER, AttributeReg.ATTACK_RADIUS);
            event.add(EntityType.PLAYER, AttributeReg.SUMMON_DAMAGE);
            event.add(EntityType.PLAYER, AttributeReg.NECROMANCY_COUNT);
            event.add(EntityType.PLAYER, AttributeReg.MAX_NIHILITY);
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_RESILIENCE);
            event.add(EntityType.PLAYER, AttributeReg.MISS_CHANCE);
            event.add(EntityType.PLAYER, AttributeReg.DODGE_CHANCE);
            event.add(EntityType.PLAYER, AttributeReg.EXCAVATION_SPEED);

            event.add(EntityType.PLAYER, AttributeReg.INFERNAL_DAMAGE);
            event.add(EntityType.PLAYER, AttributeReg.DEPTH_DAMAGE);
            event.add(EntityType.PLAYER, AttributeReg.NATURE_DAMAGE);
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_DAMAGE);
            event.add(EntityType.PLAYER, AttributeReg.INFERNAL_RESISTANCE);
            event.add(EntityType.PLAYER, AttributeReg.DEPTH_RESISTANCE);
            event.add(EntityType.PLAYER, AttributeReg.NATURE_RESISTANCE);
            event.add(EntityType.PLAYER, AttributeReg.NIHILITY_RESISTANCE);
            event.add(EntityType.PLAYER, AttributeReg.ELEMENTAL_RESISTANCE);
            if(ModList.get().isLoaded("dummmmmmy")) {
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.INFERNAL_RESISTANCE);
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.DEPTH_RESISTANCE);
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.NATURE_RESISTANCE);
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.NIHILITY_RESISTANCE);
                event.add(Dummmmmmy.TARGET_DUMMY.get(), AttributeReg.ELEMENTAL_RESISTANCE);
            }

            event.add(EntityTypeRegistry.WICKED_CRYSTAL.get(), AttributeReg.NIHILITY_RESISTANCE, 45);
            event.add(EntityTypeRegistry.WICKED_CRYSTAL.get(), AttributeReg.ELEMENTAL_RESISTANCE, 15);
            event.add(EntityTypeRegistry.DRYADOR.get(), AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityTypeRegistry.DRYADOR.get(), AttributeReg.NATURE_RESISTANCE, 50);
            event.add(EntityTypeRegistry.DRYADOR.get(), AttributeReg.NATURE_DAMAGE, 2);
            event.add(EntityTypeRegistry.NECROMANCER.get(), AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityTypeRegistry.NECROMANCER.get(), AttributeReg.NIHILITY_RESISTANCE, 15);
            event.add(EntityTypeRegistry.DEVIL.get(), AttributeReg.DEPTH_RESISTANCE, -15);
            event.add(EntityTypeRegistry.DEVIL.get(), AttributeReg.INFERNAL_RESISTANCE, 25);
            event.add(EntityTypeRegistry.DEVIL.get(), AttributeReg.INFERNAL_DAMAGE, 2);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NIHILITY_RESISTANCE, 25);
            event.add(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), AttributeReg.NIHILITY_DAMAGE, 1);
            event.add(EntityTypeRegistry.PIXIE.get(), AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityTypeRegistry.PIXIE.get(), AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityTypeRegistry.TROLL.get(), AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityTypeRegistry.TROLL.get(), AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NATURE_RESISTANCE, -25);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NIHILITY_RESISTANCE, 35);
            event.add(EntityTypeRegistry.CORRUPTED_TROLL.get(), AttributeReg.NIHILITY_DAMAGE, 3);
            event.add(EntityTypeRegistry.ENT.get(), AttributeReg.NATURE_RESISTANCE, 35);
            event.add(EntityTypeRegistry.ENT.get(), AttributeReg.NATURE_DAMAGE, 2);
            event.add(EntityTypeRegistry.ENT.get(), AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityTypeRegistry.NATURE_GOLEM.get(), AttributeReg.NATURE_RESISTANCE, 50);
            event.add(EntityTypeRegistry.NATURE_GOLEM.get(), AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityTypeRegistry.RIVER_GOLEM.get(), AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityTypeRegistry.RIVER_GOLEM.get(), AttributeReg.DEPTH_RESISTANCE, 50);
            event.add(EntityTypeRegistry.RIVER_GOLEM.get(), AttributeReg.DEPTH_DAMAGE, 2);
            event.add(EntityTypeRegistry.RIVER_GOLEM.get(), AttributeReg.INFERNAL_RESISTANCE, -65);
            event.add(EntityTypeRegistry.SORCERER.get(), AttributeReg.ELEMENTAL_RESISTANCE, 25);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.INFERNAL_RESISTANCE, -45);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.NIHILITY_RESISTANCE, 35);
            event.add(EntityTypeRegistry.CORRUPTED.get(), AttributeReg.NIHILITY_DAMAGE, 2);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.INFERNAL_RESISTANCE, -45);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.NIHILITY_RESISTANCE, 65);
            event.add(EntityTypeRegistry.WICKED_SCORPION.get(), AttributeReg.NIHILITY_DAMAGE, 6);
            event.add(EntityTypeRegistry.FLESH_SENTINEL.get(), AttributeReg.INFERNAL_RESISTANCE, -45);
            event.add(EntityTypeRegistry.FLESH_SENTINEL.get(), AttributeReg.NIHILITY_RESISTANCE, 35);
            event.add(EntityTypeRegistry.UNDEAD.get(), AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityTypeRegistry.UNDEAD.get(), AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityTypeRegistry.SCOURGE.get(), AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityTypeRegistry.SCOURGE.get(), AttributeReg.DEPTH_RESISTANCE, 25);
            event.add(EntityTypeRegistry.SCOURGE.get(), AttributeReg.DEPTH_DAMAGE, 2);
            event.add(EntityTypeRegistry.SWAMP_WANDERER.get(), AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityTypeRegistry.SWAMP_WANDERER.get(), AttributeReg.DEPTH_RESISTANCE, 25);
            event.add(EntityTypeRegistry.SWAMP_WANDERER.get(), AttributeReg.DEPTH_DAMAGE, 2);
            event.add(EntityTypeRegistry.DRAUGR.get(), AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityTypeRegistry.KING_CRAB.get(), AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityTypeRegistry.KING_CRAB.get(), AttributeReg.DEPTH_RESISTANCE, 50);
            event.add(EntityTypeRegistry.KING_CRAB.get(), AttributeReg.DEPTH_DAMAGE, 2);

            event.add(EntityType.DROWNED, AttributeReg.DEPTH_RESISTANCE, 25);
            event.add(EntityType.DROWNED, AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityType.HUSK, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.ZOMBIE, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.ZOMBIE_HORSE, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.ZOMBIE_VILLAGER, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.ZOMBIFIED_PIGLIN, AttributeReg.INFERNAL_RESISTANCE, 35);
            event.add(EntityType.SKELETON, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.SKELETON_HORSE, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.CREEPER, AttributeReg.NATURE_RESISTANCE, 35); // plant theory
            event.add(EntityType.SPIDER, AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityType.CAVE_SPIDER, AttributeReg.NATURE_RESISTANCE, 15);
            event.add(EntityType.SLIME, AttributeReg.DEPTH_RESISTANCE, 35);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.DEPTH_RESISTANCE, -50);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.INFERNAL_RESISTANCE, 25);
            event.add(EntityType.MAGMA_CUBE, AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityType.WITHER, AttributeReg.INFERNAL_RESISTANCE, 100);
            event.add(EntityType.WITHER_SKELETON, AttributeReg.INFERNAL_RESISTANCE, 50);
            event.add(EntityType.WITHER_SKELETON, AttributeReg.NATURE_RESISTANCE, -15);
            event.add(EntityType.SNOW_GOLEM, AttributeReg.INFERNAL_RESISTANCE, -35);
            event.add(EntityType.SNOW_GOLEM, AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityType.IRON_GOLEM, AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityType.STRIDER, AttributeReg.INFERNAL_RESISTANCE, 50);
            event.add(EntityType.GUARDIAN, AttributeReg.INFERNAL_RESISTANCE, -25);
            event.add(EntityType.ELDER_GUARDIAN, AttributeReg.INFERNAL_RESISTANCE, -50);
            event.add(EntityType.GUARDIAN, AttributeReg.DEPTH_RESISTANCE, 25);
            event.add(EntityType.ELDER_GUARDIAN, AttributeReg.DEPTH_RESISTANCE, 50);
            event.add(EntityType.STRAY, AttributeReg.ELEMENTAL_RESISTANCE, -15);
            event.add(EntityType.STRAY, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.PHANTOM, AttributeReg.NIHILITY_RESISTANCE, -35);
            event.add(EntityType.PHANTOM, AttributeReg.INFERNAL_RESISTANCE, -15);
            event.add(EntityType.GHAST, AttributeReg.INFERNAL_RESISTANCE, 25);
            event.add(EntityType.GHAST, AttributeReg.DEPTH_RESISTANCE, 25);
            event.add(EntityType.GHAST, AttributeReg.NIHILITY_RESISTANCE, -25);
            event.add(EntityType.BLAZE, AttributeReg.INFERNAL_RESISTANCE, 45);
            event.add(EntityType.BLAZE, AttributeReg.DEPTH_RESISTANCE, -25);
            event.add(EntityType.VEX, AttributeReg.ELEMENTAL_RESISTANCE, -25);
            event.add(EntityType.WARDEN, AttributeReg.ELEMENTAL_RESISTANCE, 45);
            event.add(EntityType.WITCH, AttributeReg.ELEMENTAL_RESISTANCE, 25);
            event.add(EntityType.WITCH, AttributeReg.NIHILITY_RESISTANCE, -35);
            event.add(EntityType.ENDERMAN, AttributeReg.ELEMENTAL_RESISTANCE, 15);
            event.add(EntityType.ENDERMAN, AttributeReg.NIHILITY_RESISTANCE, -15);
            event.add(EntityType.SHULKER, AttributeReg.ELEMENTAL_RESISTANCE, 15);
            event.add(EntityType.SHULKER, AttributeReg.NIHILITY_RESISTANCE, -35);
            event.add(EntityType.ALLAY, AttributeReg.ELEMENTAL_RESISTANCE, 15);
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event){
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            java.util.concurrent.CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
            generator.addProvider(event.includeServer(), blockTagsProvider);
            generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
            generator.addProvider(event.includeServer(), LootTableGen.create(packOutput, lookupProvider));
            generator.addProvider(event.includeServer(), new RecipeGen(packOutput, lookupProvider));

            generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
            generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, lookupProvider, existingFileHelper, java.util.List.of(new ModAdvancements())));

            generator.addProvider(event.includeClient(), new BlockStateGen(packOutput, existingFileHelper));
            generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
            generator.addProvider(event.includeClient(), new ModSoundProvider(packOutput, existingFileHelper));
        }
    }
}