package com.idark.valoria;

import com.idark.valoria.client.*;
import com.idark.valoria.client.color.*;
import com.idark.valoria.client.model.*;
import com.idark.valoria.client.model.armor.*;
import com.idark.valoria.client.model.curio.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.*;
import com.idark.valoria.client.render.entity.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.shaders.*;
import com.idark.valoria.client.sounds.*;
import com.idark.valoria.client.sounds.LoopedSoundInstance;
import com.idark.valoria.client.ui.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.component.client.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.platform.*;
import net.minecraft.client.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.client.model.render.entity.*;
import pro.komaru.tridot.client.render.gui.*;
import pro.komaru.tridot.client.sound.*;
import pro.komaru.tridot.client.tooltip.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import java.io.*;

import static com.idark.valoria.Valoria.*;

public class ValoriaClient{
    private static final String CATEGORY_KEY = "key.category.valoria.general";
    public static final KeyMapping BAG_MENU_KEY = new KeyMapping("key.valoria.bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final KeyMapping JEWELRY_BONUSES_KEY = new KeyMapping("key.valoria.jewelry", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);

    public static LoopedSoundInstance BOSS_MUSIC;
    public static ElementalManipulatorSoundInstance MANIPULATOR_LOOP;

    public static void setupClient(){
        SplashHandler.add("Also try Starbound!");
        SplashHandler.add("Also try Mindustry!");
        SplashHandler.add("Valoria was known as DarkRPG");
        SplashHandler.add("Valoria, animated by Kerdo!");
        SplashHandler.add("Valoria music by DuUaader!");

        TooltipModifierHandler.add(BASE_ENTITY_REACH_UUID);
        TooltipModifierHandler.add(BASE_DASH_DISTANCE_UUID);
        TooltipModifierHandler.add(BASE_ATTACK_RADIUS_UUID);
        TooltipModifierHandler.add(BASE_NECROMANCY_COUNT_UUID);
        TooltipModifierHandler.add(BASE_NECROMANCY_LIFETIME_UUID);
        TooltipModifierHandler.add(BASE_NATURE_DAMAGE_UUID);
        TooltipModifierHandler.add(BASE_NATURE_RESISTANCE_UUID);
        TooltipModifierHandler.add(BASE_DEPTH_DAMAGE_UUID);
        TooltipModifierHandler.add(BASE_DEPTH_RESISTANCE_UUID);
        TooltipModifierHandler.add(BASE_INFERNAL_DAMAGE_UUID);
        TooltipModifierHandler.add(BASE_INFERNAL_RESISTANCE_UUID);
        TooltipModifierHandler.add(BASE_NIHILITY_DAMAGE_UUID);
        TooltipModifierHandler.add(BASE_NIHILITY_RESISTANCE_UUID);
        TooltipModifierHandler.add(BASE_ELEMENTAL_RESISTANCE_UUID);
        MusicHandler.register(new MusicModifier.DungeonMusic(SoundsRegistry.MUSIC_NECROMANCER_DUNGEON.get(), LevelGen.NECROMANCER_CRYPT));
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("nihility", NihilityHudOverlay.instance);
        }

        @SubscribeEvent
        public static void registerComponents(RegisterClientTooltipComponentFactoriesEvent e) {
            e.register(MaterialListComponent.class, c -> MaterialListClientComponent.create(c.list()));
        }

        @SubscribeEvent
        public static void RegisterDimensionEffects(RegisterDimensionSpecialEffectsEvent e){
            e.register(new ResourceLocation(Valoria.ID, "valoria_sky"), new ValoriaEffects());
        }

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event){
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getGrassColor(state, world, pos, tintIndex), ModBlockColors.MODDED_GRASS);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.MODDED_FOLIAGE);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getAloeColor(state, world, pos, tintIndex), BlockRegistry.aloe.get(), BlockRegistry.aloeSmall.get());
        }

        @SubscribeEvent
        public static void ColorMappingItems(RegisterColorHandlersEvent.Item event){
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : SummonBook.getColor(stack), ItemsRegistry.summonBook.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 12487423, BlockRegistry.eldritchSapling.get(), BlockRegistry.eldritchLeaves.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 6740479, BlockRegistry.shadeSapling.get(), BlockRegistry.shadeLeaves.get(), BlockRegistry.shadeBranchVine.get(), BlockRegistry.shadeBranch.get());
            event.register((stack, tintIndex) -> 11301619, BlockRegistry.voidGrass.get(), BlockRegistry.voidTaint.get(), BlockRegistry.voidRoots.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.leatherGloves.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.jewelryBag.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : Col.fromHex("dfff30").pack(), BlockRegistry.aloe.get(), BlockRegistry.aloeSmall.get());
        }

        @SubscribeEvent
        public static void doClientStuff(FMLClientSetupEvent event){
            ClientBossbarRegistry.register(Valoria.loc("basic"), BasicBossbar.class);
            MinecraftForge.EVENT_BUS.register(new NihilityMeterRender());
            AbstractMinionEntity.minionColors.put(EntityTypeRegistry.UNDEAD.get(), Pal.darkishGray.toJava());
            AbstractMinionEntity.minionColors.put(EntityTypeRegistry.FLESH_SENTINEL.get(), Pal.flesh.toJava());
            AbstractMinionEntity.minionColors.put(EntityTypeRegistry.PIXIE.get(), Pal.vividGreen.toJava());
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntitiesRegistry.WICKED_ALTAR.get(), (trd) -> new AltarBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRYPTIC_ALTAR.get(), (trd) -> new AltarBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.FLESH_CYST.get(), (trd) -> new FleshCystBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (trd) -> new ManipulatorBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (trd) -> new JewelryBlockEntityRender());
                BlockEntityRenderers.register(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.VALORIA_PORTAL_BLOCK_ENTITY.get(), ValoriaPortalRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.BOSS_TROPHY_ENTITIES.get(), BossTrophyBlockEntityRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.SOUL_INFUSER_BLOCK_ENTITY.get(), (trd) -> new SoulInfuserBlockEntityRenderer());

                Sheets.addWoodType(ModWoodTypes.ELDRITCH);
                Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
                Sheets.addWoodType(ModWoodTypes.DREADWOOD);
            });

            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "shade", false, false));
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "shade", true, false));
            EntityRenderers.register(EntityTypeRegistry.ELDRITCH_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "eldritch", false, false));
            EntityRenderers.register(EntityTypeRegistry.ELDRITCH_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "eldritch", true, false));
            EntityRenderers.register(EntityTypeRegistry.DREADWOOD_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "dread", false, false));
            EntityRenderers.register(EntityTypeRegistry.DREADWOOD_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "dread", true, false));

            EntityRenderers.register(EntityTypeRegistry.LOCATOR.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.CLAW.get(), ClawRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SCAVENGER.get(), ScavengerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_SCORPION.get(), WickedScorpionRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ACID_SPIT.get(), AcidSpitRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.CORRUPTED.get(), CorruptedRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SHURIKEN.get(), ShurikenRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HauntedMerchantRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.NECROMANCER.get(), NecromancerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DRAUGR.get(), DraugrRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.GOBLIN.get(), GoblinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.FLESH_SENTINEL.get(), FleshSentinelRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SCOURGE.get(), ScourgeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MANNEQUIN.get(), MannequinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.KUNAI.get(), KunaiRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWABLE_BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MEAT.get(), MeatBlockRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DEVOURER.get(), DevourerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.UNDEAD.get(), UndeadRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.NATURE_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.AQUARIUS_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.INFERNAL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PYRATITE_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PHANTOM_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SOUL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpiderRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DEVIL.get(), DevilRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.TROLL.get(), m -> new TrollRenderer(m, false));
            EntityRenderers.register(EntityTypeRegistry.CORRUPTED_TROLL.get(), m -> new TrollRenderer(m, true));
            EntityRenderers.register(EntityTypeRegistry.SORCERER.get(), SorcererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPELL.get(), SpellProjectileRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_CRYSTAL.get(), WickedCrystalRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_SHIELD.get(), WickedShieldRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.CRYSTAL.get(), CrystalRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.CRYSTAL_SPIKES.get(), CrystalSpikesRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.CRYSTAL_SHARD.get(), CrystalShardRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PYRATITE_SHARD.get(), PyratiteShardRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ENT.get(), EntRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.LASER.get(), LaserRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MAGGOT.get(), MaggotRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DRYADOR.get(), DryadorRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ACORN.get(), AcornRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PIXIE.get(), PixieRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.KING_CRAB.get(), KingCrabRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WATER_BUBBLE.get(), WaterBubbleRenderer::new);

            ModItemModelProperties.makeShield(ItemsRegistry.crabBuckler.get());
            ModItemModelProperties.makeShield(ItemsRegistry.wickedShield.get());
            ModItemModelProperties.makeShield(ItemsRegistry.draugrShield.get());
            ModItemModelProperties.makeCooldown(ItemsRegistry.spectralBlade.get());
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event){
            event.register(ValoriaLayers.KEG_MODEL);
            event.register(ValoriaLayers.SPHERE);
            event.register(ValoriaLayers.CYST);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ValoriaLayers.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.BAG_LAYER, JewelryBagModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.HANDS_LAYER, HandsModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.HANDS_LAYER_SLIM, HandsModelSlim::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.MONOCLE_LAYER, MonocleModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.RESPIRATOR_LAYER, RespiratorModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.CROWN_LAYER, CrownModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaLayers.GAS_MASK_LAYER, GasMaskModel::createBodyLayer);

            event.registerLayerDefinition(ValoriaLayers.INFERNAL_ARMOR_INNER, () -> LayerDefinition.create(InfernalArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
            event.registerLayerDefinition(ValoriaLayers.INFERNAL_ARMOR_OUTER, () -> LayerDefinition.create(InfernalArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32));
            event.registerLayerDefinition(ValoriaLayers.VOID_ARMOR_INNER, () -> LayerDefinition.create(VoidArmorModel.addPieces(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
            event.registerLayerDefinition(ValoriaLayers.VOID_ARMOR_OUTER, () -> LayerDefinition.create(VoidArmorModel.addPieces(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32));
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event){
            event.register(ValoriaClient.BAG_MENU_KEY);
            event.register(ValoriaClient.JEWELRY_BONUSES_KEY);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event){
            ParticleRegistry.registerParticleFactory(event);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerRenderTypes(FMLClientSetupEvent event){
            ShaderRegistry.registerRenderTypes(event);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
            ShaderRegistry.shaderRegistry(event);
        }
    }
}