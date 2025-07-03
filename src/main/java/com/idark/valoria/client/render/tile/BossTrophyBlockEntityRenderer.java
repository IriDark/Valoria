package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.stats.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import pro.komaru.tridot.client.*;

import java.lang.Math;

public class BossTrophyBlockEntityRenderer implements BlockEntityRenderer<BossTrophyBlockEntity>{
    private final EntityRenderDispatcher entityRenderer;
    public BossTrophyBlockEntityRenderer(BlockEntityRendererProvider.Context pContext){
        this.entityRenderer = pContext.getEntityRenderer();
    }

    @Override
    public void render(BossTrophyBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        Minecraft mc = Minecraft.getInstance();
        Entity entity = be.instance;
        int lightAbove = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().above());
        if(entity != null){
            renderDisplayedEntity(be, partialTicks, ms, buffers, entity, lightAbove);
            HitResult hit = mc.hitResult;
            if (hit instanceof BlockHitResult bhr && bhr.getBlockPos().equals(be.getBlockPos())) {
                ms.pushPose();
                mc.getConnection().send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
                drawText(ms, buffers, entity, lightAbove);
                ms.popPose();
            }
        }
    }

    public void renderDisplayedEntity(BossTrophyBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffers, Entity entity, int lightAbove){
        ms.pushPose();
        ms.translate(0.5, 1.0, 0.5);
        double ticks = (ClientTick.ticksInGame + partialTicks) * 0.5f;
        double ticksUp = (ClientTick.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float)ticks));

        float scale = 0.75F;
        float maxScale = Math.max(entity.getBbWidth(), be.instance.getBbHeight());
        if(maxScale > 1.0D) scale /= maxScale;

        ms.translate(0, -0.2F, 0);
        ms.scale(scale, scale, scale);
        entityRenderer.render(entity, 0, 0, 0, 0, partialTicks, ms, buffers, lightAbove);
        ms.popPose();
    }

    public void drawText(PoseStack ms, MultiBufferSource buffers, Entity entity, int lightAbove){
        Stat<?> stat = Stats.ENTITY_KILLED.get(entity.getType());
        int value = Minecraft.getInstance().player.getStats().getValue(stat);
        Component component = Component.literal(String.valueOf(value));

        Font font = Minecraft.getInstance().font;
        ms.translate(0.5f, 2.35f, 0.5f);
        ms.mulPose(entityRenderer.cameraOrientation());
        Matrix4f matrix4f = ms.last().pose();
        ms.scale(-0.05f, -0.05f, -0.05f);


        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;
        font.drawInBatch(component, (float)(-font.width(component) / 2), 0, -1, false, matrix4f, buffers, Font.DisplayMode.NORMAL, 0, lightAbove);
        font.drawInBatch(component, (float)(-font.width(component) / 2), 0, -1, false, matrix4f, buffers, Font.DisplayMode.SEE_THROUGH, j, lightAbove);
    }
}