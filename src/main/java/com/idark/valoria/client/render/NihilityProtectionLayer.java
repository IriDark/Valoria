package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class NihilityProtectionLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>{
    private static final ResourceLocation AURA_TEXTURE = Valoria.loc("textures/entity/aura.png");

    public NihilityProtectionLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        if(!player.hasEffect(EffectsRegistry.NIHILITY_PROTECTION.get())) return;

        poseStack.pushPose();
        float scale = 1.05F;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0D, -0.03D, 0D);

        PlayerModel<AbstractClientPlayer> model = this.getParentModel();
        model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        this.getParentModel().copyPropertiesTo(model);
        float f = (float)player.tickCount + partialTicks;
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.energySwirl(AURA_TEXTURE, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));

        float alpha = 0.45F;
        model.renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY, alpha, alpha, alpha, alpha);
        poseStack.popPose();
    }

    protected float xOffset(float padding) {
        return padding * 0.001F;
    }
}
