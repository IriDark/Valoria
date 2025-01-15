package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.AbstractNecromancer.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import mod.maxbogomol.fluffy_fur.client.render.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class NecromancerRenderer extends HumanoidMobRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new NecromancerModel<>(NecromancerModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancerEntity pEntity) {
        return TEXTURE;
    }

    @Deprecated
    public void render(NecromancerEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        if (entityIn.getCurrentSpell().hasAura) {
            float alpha = 1;
            if (entityIn.isCastingSpell()) {
                float time = (entityIn.tickCount + partialTicks) / 20f;
                alpha = (float) Math.sin(Math.PI * 0.25f - time + entityIn.getSpellCastingTime());
            }

            if (alpha > 1f) alpha = 1f;
            if (alpha < 0f) alpha = 0f;
            ms.pushPose();

            ms.translate(0, 0.01f, 0);
            ms.mulPose(Axis.YP.rotationDegrees(-ClientTickHandler.ticksInGame * 0.3f));
            NecromancerSpells spell = entityIn.getCurrentSpell();
            int r = spell.spellColor[0];
            int g = spell.spellColor[1];
            int b = spell.spellColor[2];
            Color color = new Color(r,g,b);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(0.15f * alpha).setSecondAlpha(0), ms, 1, 0.75f, 6, true);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(0.25f * alpha).setSecondAlpha(0), ms, 2.5f, 1.25f, 6, true);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(alpha).setSecondAlpha(0), ms, 0.8f, 0f, 6, false);
            ms.popPose();
        }

        if(entityIn.playSpawnAnimation()){
            float spawnProgress = entityIn.getSpawnProgress(partialTicks);
            float scale = 0.5f + 0.5f * spawnProgress;
            ms.translate(0, 0.01f, 0);
            ms.scale(scale, scale, scale);
        }

        super.render(entityIn, entityYaw, partialTicks, ms, buffers, light);
    }
}