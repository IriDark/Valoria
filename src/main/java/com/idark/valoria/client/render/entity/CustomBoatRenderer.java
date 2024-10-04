package com.idark.valoria.client.render.entity;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.*;
import com.mojang.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import org.joml.*;

import java.lang.Math;
import java.util.*;
import java.util.stream.*;

public class CustomBoatRenderer extends EntityRenderer<CustomBoatEntity> {

    private final Map<CustomBoatEntity.Type, Pair<ResourceLocation, BoatModel>> boatResources;

    public CustomBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(CustomBoatEntity.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(new ResourceLocation(Valoria.ID, getTextureLocation(type, chest)), this.createBoatModel(context, type, chest))));
    }

    private static ModelLayerLocation createLocation(String path) {
        return new ModelLayerLocation(new ResourceLocation(Valoria.ID, path), "main");
    }

    public static ModelLayerLocation createBoatModelName(CustomBoatEntity.Type type) {
        return createLocation("boat/" + type.getName());
    }

    public static ModelLayerLocation createChestBoatModelName(CustomBoatEntity.Type type) {
        return createLocation("chest_boat/" + type.getName());
    }

    private static String getTextureLocation(CustomBoatEntity.Type type, boolean chest) {
        return chest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
    }

    private BoatModel createBoatModel(EntityRendererProvider.Context context, CustomBoatEntity.Type type, boolean chest) {
        ModelLayerLocation modellayerlocation = chest ? createChestBoatModelName(type) : createBoatModelName(type);
        ModelPart modelpart = context.bakeLayer(modellayerlocation);
        return chest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
    }

    @Override
    public void render(CustomBoatEntity boat, float boatYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.translate(0.0F, 0.375F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(180.0F - boatYaw));
        float f = (float) boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            stack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float) boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            stack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float) Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, BoatModel> pair = this.getModelWithLocation(boat);
        ResourceLocation resourcelocation = pair.getFirst();
        BoatModel model = pair.getSecond();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.mulPose(Axis.YP.rotationDegrees(90.0F));
        model.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(model.renderType(resourcelocation));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertex = buffer.getBuffer(RenderType.waterMask());
            model.waterPatch().render(stack, vertex, light, OverlayTexture.NO_OVERLAY);
        }

        stack.popPose();
        super.render(boat, boatYaw, partialTicks, stack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomBoatEntity boat) {
        return this.getModelWithLocation(boat).getFirst();
    }

    public Pair<ResourceLocation, BoatModel> getModelWithLocation(CustomBoatEntity boat) {
        return this.boatResources.get(boat.getCustomBoatEntityType());
    }
}