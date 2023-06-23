package com.idark.darkrpg.client.render.model.item;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class Item2DRenderer {
    public static final String[] HAND_MODEL_ITEMS = new String[]{"cobalt_sword", "netherite_scythe", "diamond_scythe", "golden_scythe", "iron_scythe",
            "netherite_spear", "diamond_spear", "golden_spear", "iron_spear",
            "ent", "nature_scythe", "infernal_sword", "infernal_scythe",
            "bloodhound", "void_edge", "bronze_sword", "double_spear", "infernal_sword", "coral_reef", "beast", "aquarius_scythe",
            "blaze_reap", "murasama", "phantom"};

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        Map<ResourceLocation, IBakedModel> map = event.getModelRegistry();
        for (String item : HAND_MODEL_ITEMS) {
            ResourceLocation modelInventory = new ModelResourceLocation(DarkRPG.MOD_ID+":" + item, "inventory");
            ResourceLocation modelHand = new ModelResourceLocation(DarkRPG.MOD_ID+":" + item + "_in_hand", "inventory");

            IBakedModel bakedModelDefault = map.get(modelInventory);
            IBakedModel bakedModelHand = map.get(modelHand);
            IBakedModel modelWrapper = new IBakedModel() {
                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
                    return bakedModelDefault.getQuads(state, side, rand);
                }

                @Override
                public boolean isAmbientOcclusion() {
                    return bakedModelDefault.isAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean isSideLit() {
                    return bakedModelDefault.isSideLit();
                }

                @Override
                public boolean isBuiltInRenderer() {
                    return bakedModelDefault.isBuiltInRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleTexture() {
                    return bakedModelDefault.getParticleTexture();
                }

                @Override
                public ItemOverrideList getOverrides() {
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
                    IBakedModel modelToUse = bakedModelDefault;
                    if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                            || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        modelToUse = bakedModelHand;
                    }
                    return ForgeHooksClient.handlePerspective(modelToUse, cameraTransformType, mat);
                }
            };
            map.put(modelInventory, modelWrapper);
        }
    }
}