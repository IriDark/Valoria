package com.idark.valoria.client.particle.types;

import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;

public class SparkleParticle extends GenericParticle{
    public SparkleParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz){
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float partialTicks){
        return 0xF000F0;
    }

    @Override
    public void render(VertexConsumer b, Camera info, float pticks){
        super.render(RenderUtils.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE), info, pticks);
    }
}