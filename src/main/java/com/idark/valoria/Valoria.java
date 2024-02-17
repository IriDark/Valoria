package com.idark.valoria;

import com.google.common.collect.ImmutableMap;
import com.idark.valoria.block.ModBlockColors;
import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.block.blockentity.ModBlockEntities;
import com.idark.valoria.block.types.ModWoodTypes;
import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.client.render.CorpsecleaverRender;
import com.idark.valoria.client.render.DashOverlayRender;
import com.idark.valoria.client.render.curio.BeltRenderer;
import com.idark.valoria.client.render.curio.HandsRenderer;
import com.idark.valoria.client.render.curio.NecklaceRenderer;
import com.idark.valoria.client.render.gui.MagmaBarRender;
import com.idark.valoria.client.render.gui.TooltipEventHandler;
import com.idark.valoria.client.screen.book.LexiconChapters;
import com.idark.valoria.client.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.client.screen.ManipulatorScreen;
import com.idark.valoria.command.arguments.ModArgumentTypes;
import com.idark.valoria.config.ClientConfig;
import com.idark.valoria.client.menu.ModMenuTypes;
import com.idark.valoria.datagen.ModRecipeProvider;
import com.idark.valoria.effect.ModEffects;
import com.idark.valoria.enchant.ModEnchantments;
import com.idark.valoria.entity.ModEntityTypes;
import com.idark.valoria.entity.custom.DraugrEntity;
import com.idark.valoria.entity.custom.GoblinEntity;
import com.idark.valoria.entity.custom.MannequinEntity;
import com.idark.valoria.item.ModAttributes;
import com.idark.valoria.item.ModItemGroup;
import com.idark.valoria.item.ModItems;
import com.idark.valoria.item.staffs.StaffItem;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.paintings.ModPaintings;
import com.idark.valoria.effect.potion.ModPotions;
import com.idark.valoria.proxy.ClientProxy;
import com.idark.valoria.proxy.ISidedProxy;
import com.idark.valoria.proxy.ServerProxy;
import com.idark.valoria.recipe.ModRecipes;
import com.idark.valoria.client.screen.JewelryScreen;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.sounds.ModSoundRegistry;
import com.idark.valoria.util.WorldRenderHandler;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.world.ModBlockStateProviderType;
import com.idark.valoria.world.ModTrunkPlacerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
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

import java.util.concurrent.CompletableFuture;

@Mod(Valoria.MOD_ID)
public class Valoria {
    public static final String MOD_ID = "valoria";
    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    //TODO: Create Brewery
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
        ModBlockStateProviderType.register(eventBus);
		ModTrunkPlacerTypes.register(eventBus);

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
            forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
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
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getColor(state, world, pos, tintIndex), ModBlockColors.MODDED_PLANTS);
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
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.createAttributes().build());
            event.put(ModEntityTypes.GOBLIN.get(), GoblinEntity.createAttributes().build());
            event.put(ModEntityTypes.DRAUGR.get(), GoblinEntity.createAttributes().build());
            event.put(ModEntityTypes.NECROMANCER.get(), GoblinEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
            //generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
            //generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
        }
    }
}