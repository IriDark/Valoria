package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.render.model.entity.*;
import com.idark.valoria.registries.entity.living.AbstractNecromancer.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class NecromancerRenderer extends HumanoidMobRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context){
        super(context, new NecromancerModel<>(NecromancerModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancerEntity pEntity){
        return TEXTURE;
    }

    public void render(NecromancerEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light){
        if(entityIn.getCurrentSpell().id == necromancerSpell.HEAL.id || entityIn.getCurrentSpell().id == necromancerSpell.KNOCKBACK.id){
            float alpha = 1;
            if(entityIn.isCastingSpell()){
                float time = (entityIn.tickCount + partialTicks) / 20f;
                alpha = (float)Math.sin(Math.PI * 0.25f - time + entityIn.getSpellCastingTime());
            }

            if(alpha > 1f) alpha = 1f;
            if(alpha < 0f) alpha = 0f;
            MultiBufferSource bufferDelayed = RenderUtils.getDelayedRender();
            VertexConsumer builder = bufferDelayed.getBuffer(RenderUtils.GLOWING);
            ms.pushPose();

            ms.translate(0, 0.01f, 0);
            ms.mulPose(Axis.YP.rotationDegrees(-ClientTickHandler.ticksInGame * 0.3f));
            AbstractNecromancer.necromancerSpell spell = entityIn.getCurrentSpell();
            int r = spell.spellColor[0];
            int g = spell.spellColor[1];
            int b = spell.spellColor[2];

            RenderUtils.renderAura(ms, builder, 1, 0.75f, 6, new Color(r, g, b), Color.WHITE, 0.15f * alpha, 0, true, true);
            RenderUtils.renderAura(ms, builder, 2.5f, 1.25f, 6, new Color(r, g, b), Color.WHITE, 0.25f * alpha, 0, true, true);
            RenderUtils.renderAura(ms, builder, 0.8f, 0f, 6, new Color(r, g, b), Color.WHITE, 0, alpha, false, true);
            ms.popPose();
        }

        super.render(entityIn, entityYaw, partialTicks, ms, buffers, light);
    }
}