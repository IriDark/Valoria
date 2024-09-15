package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.GoblinModel;
import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.registries.entity.living.Goblin;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class GoblinRenderer extends MobRenderer<Goblin, GoblinModel<Goblin>> {
    protected static final ResourceLocation NEW = new ResourceLocation(Valoria.ID, "textures/entity/goblin.png");
    protected static final ResourceLocation OLD = new ResourceLocation(Valoria.ID, "textures/entity/goblin_old.png");

    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinModel<>(ClientConfig.OLD_GOBLIN_MODEL.get() ? GoblinModel.createOldBodyLayer().bakeRoot() : GoblinModel.createBodyLayer().bakeRoot()), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin entity) {
        return ClientConfig.OLD_GOBLIN_MODEL.get() ? OLD : NEW;
    }

    @Override
    public void render(Goblin pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}