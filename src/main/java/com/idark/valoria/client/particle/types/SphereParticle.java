package com.idark.valoria.client.particle.types;

import com.idark.valoria.util.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;

public class SphereParticle extends GenericParticle{

    public SphereParticle(ClientLevel pLevel, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz){
        super(pLevel, data, x, y, z, vx, vy, vz);
    }

    @Override
    public void render(VertexConsumer b, Camera info, float pticks){
        super.render(Minecraft.getInstance().options.graphicsMode().get().getId() == 3 ? RenderUtils.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE) : b, info, pticks);
    }
}