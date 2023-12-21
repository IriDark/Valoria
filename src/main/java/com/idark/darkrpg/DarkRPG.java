package com.idark.darkrpg;

import com.google.common.collect.ImmutableMap;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.block.types.ModWoodTypes;
import com.idark.darkrpg.capability.IPage;
import com.idark.darkrpg.client.event.ClientTickHandler;
import com.idark.darkrpg.client.render.CorpsecleaverRender;
import com.idark.darkrpg.client.render.DashOverlayRender;
import com.idark.darkrpg.client.render.gui.MagmaBarRender;
import com.idark.darkrpg.client.render.gui.TooltipEventHandler;
import com.idark.darkrpg.config.ClientConfig;
import com.idark.darkrpg.datagen.ModGlobalLootModifiersProvider;
import com.idark.darkrpg.datagen.ModWorldGenProvider;
import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.enchant.ModEnchantments;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.entity.custom.DraugrEntity;
import com.idark.darkrpg.entity.custom.GoblinEntity;
import com.idark.darkrpg.entity.custom.MannequinEntity;
import com.idark.darkrpg.item.ModAttributes;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.paintings.ModPaintings;
import com.idark.darkrpg.potion.ModPotions;
import com.idark.darkrpg.tileentity.ModTileEntities;
import com.idark.darkrpg.util.LootUtil;
import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.util.WorldRenderHandler;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.world.WorldGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.concurrent.CompletableFuture;

@Mod(DarkRPG.MOD_ID)
public class DarkRPG {
	public static final String MOD_ID = "darkrpg";

	public DarkRPG() {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModSoundRegistry.SOUNDS.register(eventBus);
		ModEffects.register(eventBus);
		ModEnchantments.register(eventBus);
		ModPaintings.register(eventBus);
		ModAttributes.register(eventBus);
		ModPotions.register(eventBus);
		ModItems.register(eventBus);
		ModBlocks.register(eventBus);
		ModTileEntities.register(eventBus);
		ModEntityTypes.register(eventBus);
		ModParticles.register(eventBus);
		LootUtil.register(eventBus);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
		forgeBus.register(new WorldGen());
		DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
			forgeBus.addListener(ClientTickHandler::clientTickEnd);
			forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
			forgeBus.addListener(DashOverlayRender::tick);
			forgeBus.addListener(DashOverlayRender::onDrawScreenPost);
			forgeBus.addListener(CorpsecleaverRender::tick);
			forgeBus.addListener(CorpsecleaverRender::onDrawScreenPost);
			forgeBus.addListener(MagmaBarRender::onDrawScreenPost);
			forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
			return new Object();
		});

		ModItemGroup.register(eventBus);
		eventBus.addListener(ModItemGroup::addCreative);

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new Events());
	}

	private void setup(final FMLCommonSetupEvent event) {
		ModPotions.bootStrap();
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

	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void registerCaps(RegisterCapabilitiesEvent event) {
			event.register(IPage.class);
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
		}

		@SubscribeEvent
		public static void gatherData(GatherDataEvent event) {
			DataGenerator generator = event.getGenerator();
			PackOutput packOutput = generator.getPackOutput();
			CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

			generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
			generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
		}
	}
}