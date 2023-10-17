package com.idark.darkrpg.util.particle;

import com.idark.darkrpg.util.RenderUtils;
import com.idark.darkrpg.util.WorldRenderHandler;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class SlashParticle extends GenericParticle {
    public SlashParticle(ClientWorld world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
	}

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void render(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.render(true ? WorldRenderHandler.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE) : b, info, pticks);
    }
}