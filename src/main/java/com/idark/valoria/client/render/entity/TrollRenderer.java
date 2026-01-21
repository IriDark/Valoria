package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.model.render.entity.*;

@OnlyIn(Dist.CLIENT)
public class TrollRenderer extends MobRenderer<Troll, TrollModel<Troll>>{
    protected ResourceLocation texture;
    private final boolean corrupted;

    public TrollRenderer(EntityRendererProvider.Context context, boolean corrupted){
        super(context, new TrollModel<>(TrollModel.createBodyLayer().bakeRoot()), 0.35F);
        this.corrupted = corrupted;
        this.texture = new ResourceLocation(Valoria.ID, corrupted ? "textures/entity/corrupted_troll.png" : "textures/entity/troll.png");
        this.addLayer(new LuminescentLayer.Builder<>(this)
                .setTexture(new ResourceLocation(Valoria.ID, corrupted ? "textures/entity/corrupted_troll_eyes.png" : "textures/entity/troll_eyes_layer.png"))
                .build()
        );
    }

    public void render(Troll pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
        if(corrupted){
            Vec3[] avec3 = pEntity.getOffsets();
            float f = this.getBob(pEntity, pPartialTicks);
            for(int i = 0; i < avec3.length; ++i){
                pPoseStack.pushPose();
                pPoseStack.translate(avec3[i].x + (double)Mth.cos((float)i + f * 0.5F) * 0.015D, avec3[i].y + (double)Mth.cos((float)i + f * 0.5F) * 0.0125D, avec3[i].z + (double)Mth.cos((float)i + f * 0.7F) * 0.015D);
                super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
                pPoseStack.popPose();
            }
        }else{
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Troll pEntity){
        return texture;
    }
}