package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.client.model.entity.NecromancerModel;
import com.idark.valoria.registries.entity.living.boss.AbstractNecromancer.NecromancerSpells;
import com.idark.valoria.registries.entity.living.boss.NecromancerEntity;
import com.idark.valoria.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    @Deprecated
    public void render(NecromancerEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light){
        if(entityIn.getCurrentSpell().hasAura){
            float alpha = 1;
            if(entityIn.isCastingSpell()){
                float time = (entityIn.tickCount + partialTicks) / 20f;
                alpha = (float)Math.sin(Math.PI * 0.25f - time + entityIn.getSpellCastingTime());
            }

            if(alpha > 1f) alpha = 1f;
            if(alpha < 0f) alpha = 0f;
            ms.pushPose();

            ms.translate(0, 0.01f, 0);
            ms.mulPose(Axis.YP.rotationDegrees(-ClientTickHandler.ticksInGame * 0.3f));
            NecromancerSpells spell = entityIn.getCurrentSpell();
            int r = spell.spellColor[0];
            int g = spell.spellColor[1];
            int b = spell.spellColor[2];
            Color color = new Color(r, g, b);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(0.15f * alpha).setSecondAlpha(0), ms, 1, 0.75f, 6, true);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(0.25f * alpha).setSecondAlpha(0), ms, 2.5f, 1.25f, 6, true);
            RenderUtils.renderAura(RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).enableSided().setFirstColor(color).setSecondColor(Color.WHITE).setFirstAlpha(alpha).setSecondAlpha(0), ms, 0.8f, 0f, 6, false);
            ms.popPose();
        }

        float spawnProgress = entityIn.getSpawnProgress(partialTicks);
        float scale = 0.5f + 0.5f * spawnProgress;
        ms.translate(0, 0.01f, 0);
        ms.scale(scale, scale, scale);
        super.render(entityIn, entityYaw, partialTicks, ms, buffers, light);
    }
}