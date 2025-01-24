package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.entity.projectile.SpellProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SpellProjectileRenderer<T extends SpellProjectile> extends EntityRenderer<T>{
    public SpellProjectileRenderer(Context pContext){
        super(pContext);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity){
        return null;
    }
}