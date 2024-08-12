package com.idark.valoria;

import com.idark.valoria.client.color.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.render.entity.*;
import com.idark.valoria.client.render.model.curio.*;
import com.idark.valoria.client.render.model.item.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.entity.decoration.*;
import com.idark.valoria.registries.sounds.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import org.lwjgl.glfw.*;

import java.io.*;

public class ValoriaClient{

    private static final String CATEGORY_KEY = "key.category.valoria.general";
    public static final KeyMapping BAG_MENU_KEY = new KeyMapping("key.valoria.bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final KeyMapping JEWELRY_BONUSES_KEY = new KeyMapping("key.valoria.jewelry", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);

    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "belt"), "main");
    public static ModelLayerLocation BAG_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "belt"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.ID, "elemental_sphere", "");

    public static CooldownSoundInstance COOLDOWN_SOUND = new CooldownSoundInstance(null);

    public static ShaderInstance GLOWING_SHADER, GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;

    public static ShaderInstance getGlowingParticleShader(){
        return GLOWING_PARTICLE_SHADER;
    }

    public static ShaderInstance getSpriteParticleShader(){
        return SPRITE_PARTICLE_SHADER;
    }

    public static ShaderInstance getGlowingShader(){
        return GLOWING_SHADER;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event){
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getGrassColor(state, world, pos, tintIndex), ModBlockColors.MODDED_GRASS);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.MODDED_FOLIAGE);
        }

        @SubscribeEvent
        public static void ColorMappingItems(RegisterColorHandlersEvent.Item event){
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 12487423, BlockRegistry.ELDRITCH_SAPLING.get(), BlockRegistry.ELDRITCH_LEAVES.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 9100543, BlockRegistry.SHADEWOOD_BRANCH.get(), BlockRegistry.SHADEWOOD_SAPLING.get(), BlockRegistry.SHADEWOOD_LEAVES.get());
            event.register((stack, tintIndex) -> 11301619, BlockRegistry.VOID_GRASS.get(), BlockRegistry.VOID_TAINT.get(), BlockRegistry.VOID_ROOTS.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.LEATHER_GLOVES.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.JEWELRY_BAG.get());
        }

        @SubscribeEvent
        public static void doClientStuff(FMLClientSetupEvent event){
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (trd) -> new ManipulatorBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (trd) -> new JewelryBlockEntityRender());
                BlockEntityRenderers.register(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
                Sheets.addWoodType(ModWoodTypes.ELDRITCH);
                Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
            });

            EntityRenderers.register(EntityTypeRegistry.BOAT.get(), m -> new CustomBoatRenderer(m, false));
            EntityRenderers.register(EntityTypeRegistry.CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, true));

            EntityRenderers.register(EntityTypeRegistry.NECROMANCER.get(), NecromancerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DRAUGR.get(), DraugrRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.GOBLIN.get(), GoblinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MANNEQUIN.get(), MannequinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.KUNAI.get(), KunaiRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWABLE_BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MEAT.get(), MeatBlockRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.NECROMANCER_FANGS.get(), NecromancerFangsRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.UNDEAD.get(), UndeadRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PHANTOM_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SOUL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.INFERNAL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpiderRenderer::new);

            ModItemModelProperties.makeBow(ItemsRegistry.INFERNAL_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.SAMURAI_LONG_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.NATURE_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.AQUARIUS_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.BOW_OF_DARKNESS.get());
            ModItemModelProperties.makeBow(ItemsRegistry.PHANTASM_BOW.get());
            ModItemModelProperties.makeSize(ItemsRegistry.SOUL_COLLECTOR.get());
            ModItemModelProperties.makeCooldown(ItemsRegistry.SPECTRAL_BLADE.get());
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event){
            event.register(KEG_MODEL);
            event.register(SPHERE);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ValoriaClient.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BAG_LAYER, JewelryBagModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER, HandsModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER_SLIM, HandsModelSlim::createBodyLayer);
            for(CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()){
                event.registerLayerDefinition(CustomBoatRenderer.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(CustomBoatRenderer.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }
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
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:glowing"), DefaultVertexFormat.POSITION_COLOR), shader -> GLOWING_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:glowing_particle"), DefaultVertexFormat.PARTICLE), shader -> GLOWING_PARTICLE_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:sprite_particle"), DefaultVertexFormat.PARTICLE), shader -> SPRITE_PARTICLE_SHADER = shader);
        }
    }
}