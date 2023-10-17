package com.idark.darkrpg.util.particle;

import com.idark.darkrpg.util.RenderUtils;
import com.idark.darkrpg.util.WorldRenderHandler;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class SlashParticle extends GenericParticle {
    public SlashParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void render(VertexConsumer b, Camera info, float pticks) {
        super.render(true ? WorldRenderHandler.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE) : b, info, pticks);
    }
}