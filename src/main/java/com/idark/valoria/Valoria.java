package com.idark.valoria;

import com.google.common.collect.ImmutableMap;
import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.client.gui.menu.ModMenuTypes;
import com.idark.valoria.client.gui.overlay.CorpsecleaverRender;
import com.idark.valoria.client.gui.overlay.DashOverlayRender;
import com.idark.valoria.client.gui.overlay.MagmaBarRender;
import com.idark.valoria.client.gui.screen.JewelryScreen;
import com.idark.valoria.client.gui.screen.ManipulatorScreen;
import com.idark.valoria.client.gui.screen.book.LexiconChapters;
import com.idark.valoria.client.gui.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.render.curio.BeltRenderer;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.client.render.curio.NecklaceRenderer;
import com.idark.valoria.config.ClientConfig;
import com.idark.valoria.datagen.ModBlockStateProvider;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.proxy.ClientProxy;
import com.idark.valoria.proxy.ISidedProxy;
import com.idark.valoria.proxy.ServerProxy;
import com.idark.valoria.registries.command.arguments.ModArgumentTypes;
import com.idark.valoria.registries.recipe.ModRecipes;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.registries.world.block.entity.ModBlockEntities;
import com.idark.valoria.registries.world.block.types.ModWoodTypes;
import com.idark.valoria.registries.world.effect.ModEffects;
import com.idark.valoria.registries.world.effect.potion.ModPotions;
import com.idark.valoria.registries.world.entity.ModEntityTypes;
import com.idark.valoria.registries.world.entity.ai.attributes.ModAttributes;
import com.idark.valoria.registries.world.entity.decoration.ModPaintings;
import com.idark.valoria.registries.world.entity.living.*;
import com.idark.valoria.registries.world.item.ModItemGroup;
import com.idark.valoria.registries.world.item.ModItems;
import com.idark.valoria.registries.world.item.enchant.ModEnchantments;
import com.idark.valoria.registries.world.item.types.ScytheItem;
import com.idark.valoria.registries.world.item.types.mana.staffs.StaffItem;
import com.idark.valoria.registries.world.levelgen.LevelGen;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.WorldRenderHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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

@Mod(Valoria.MOD_ID)
public class Valoria {
    public static final String MOD_ID = "valoria";
    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Valoria() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModSoundRegistry.SOUNDS.register(eventBus);
        ModEffects.register(eventBus);
        ModEnchantments.register(eventBus);
        ModPaintings.register(eventBus);
        ModAttributes.register(eventBus);
        ModPotions.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModRecipes.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModParticles.register(eventBus);
        LootUtil.register(eventBus);
        ModArgumentTypes.register(eventBus);
        LevelGen.FEATURES.register(eventBus);
        LevelGen.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        LevelGen.PLACEMENT_MODIFIERS.register(eventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
            forgeBus.addListener(DashOverlayRender::tick);
            forgeBus.addListener(DashOverlayRender::onDrawScreenPost);
            forgeBus.addListener(CorpsecleaverRender::tick);
            forgeBus.addListener(CorpsecleaverRender::onDrawScreenPost);
            forgeBus.addListener(MagmaBarRender::onDrawScreenPost);
            forgeBus.addListener(StaffItem::onDrawScreenPost);
            return new Object();
        });

        ModItemGroup.register(eventBus);
        eventBus.addListener(ModItemGroup::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            LexiconChapters.init();

            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_NECKLACE_WEALTH.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_NECKLACE_WEALTH.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_AMBER.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_DIAMOND.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_EMERALD.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_RUBY.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_SAPPHIRE.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_ARMOR.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_HEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_NECKLACE_WEALTH.get(), NecklaceRenderer::new);
            CuriosRendererRegistry.register(ModItems.PICK_NECKLACE.get(), NecklaceRenderer::new);

            CuriosRendererRegistry.register(ModItems.LEATHER_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRON_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ModItems.GOLDEN_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ModItems.DIAMOND_GLOVES.get(), HandsRenderer::new);
            CuriosRendererRegistry.register(ModItems.NETHERITE_GLOVES.get(), HandsRenderer::new);

            CuriosRendererRegistry.register(ModItems.LEATHER_BELT.get(), BeltRenderer::new);

            MenuScreens.register(ModMenuTypes.JEWELRY_MENU.get(), JewelryScreen::new);
            MenuScreens.register(ModMenuTypes.MANIPULATOR_MENU.get(), ManipulatorScreen::new);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        ModPotions.bootStrap();
        RegisterUnlockables.init();
        event.enqueueWork(() -> {
            FireBlock fireblock = (FireBlock) Blocks.FIRE;
            fireblock.setFlammable(ModBlocks.SHADELOG.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SHADEWOOD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SHADEWOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.SHADEWOOD_PLANKS_SLAB.get(), 5, 40);
            fireblock.setFlammable(ModBlocks.SHADEWOOD_PLANKS_STAIRS.get(), 5, 40);
            fireblock.setFlammable(ModBlocks.SHADEWOOD_PLANKS.get(), 5, 25);
            fireblock.setFlammable(ModBlocks.STRIPPED_SHADELOG.get(), 5, 30);
            fireblock.setFlammable(ModBlocks.STRIPPED_SHADEWOOD.get(), 5, 30);

            ScytheItem.scytheItems.add(ModItems.IRON_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.GOLDEN_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.DIAMOND_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.NETHERITE_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.BEAST.get());
            ScytheItem.scytheItems.add(ModItems.NATURE_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.AQUARIUS_SCYTHE.get());
            ScytheItem.scytheItems.add(ModItems.INFERNAL_SCYTHE.get());

            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
                    .put(ModBlocks.SHADELOG.get(), ModBlocks.STRIPPED_SHADELOG.get())
                    .put(ModBlocks.SHADEWOOD.get(), ModBlocks.STRIPPED_SHADEWOOD.get()).build();

            WoodType.register(ModWoodTypes.SHADEWOOD);
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
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
                SpawnPlacements.register(ModEntityTypes.GOBLIN.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        GoblinEntity::checkGoblinSpawnRules);

                SpawnPlacements.register(ModEntityTypes.DRAUGR.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        DraugrEntity::checkMonsterSpawnRules);

                SpawnPlacements.register(ModEntityTypes.SWAMP_WANDERER.get(),
                        SpawnPlacements.Type.IN_WATER,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        SwampWandererEntity::checkDrownedSpawnRules);
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.createAttributes().build());
            event.put(ModEntityTypes.GOBLIN.get(), GoblinEntity.createAttributes().build());
            event.put(ModEntityTypes.DRAUGR.get(), DraugrEntity.createAttributes().build());
            event.put(ModEntityTypes.NECROMANCER.get(), NecromancerEntity.createAttributes().build());
            event.put(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererEntity.createAttributes().build());
            event.put(ModEntityTypes.UNDEAD.get(), UndeadEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            //generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
            generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        }
    }
}