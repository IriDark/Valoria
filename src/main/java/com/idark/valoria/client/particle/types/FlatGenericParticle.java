package com.idark.valoria.client.particle.types;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;

import java.lang.Math;

public class FlatGenericParticle extends GenericParticle {

    public FlatGenericParticle(ClientLevel level, GenericParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, spriteSet, x, y, z, vx, vy, vz);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        if(shouldRenderTraits) updateRenderTraits(partialTicks);
        spriteData.renderTick(this, partialTicks);
        renderActors.forEach(actor -> actor.accept(this));

        VertexConsumer consumer = renderType != null ? TridotRenderTypes.getDelayedRender().getBuffer(renderType) : vertexConsumer;
        if(behavior != null) {
            behavior.render(this, consumer, camera, partialTicks);
            return;
        }

        Vec3 cameraPos = camera.getPosition();
        float px = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPos.x());
        float py = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPos.y());
        float pz = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPos.z());

        // Поворот на 90 градусов (укладываем на землю) и затем крутим по оси Z (spin/roll)
        Quaternionf rotation = new Quaternionf()
            .rotationX((float) (Math.PI / 2.0))
            .rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));

        Vector3f[] vertices = new Vector3f[]{
            new Vector3f(-1.0F, -1.0F, 0.0F),
            new Vector3f(-1.0F, 1.0F, 0.0F),
            new Vector3f(1.0F, 1.0F, 0.0F),
            new Vector3f(1.0F, -1.0F, 0.0F)
        };

        float size = this.getQuadSize(partialTicks);
        for (int i = 0; i < 4; ++i) {
            Vector3f vertex = vertices[i];
            vertex.rotate(rotation);
            vertex.mul(size);
            vertex.add(px, py, pz);
        }

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTicks);

        // Вверх
        consumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();

        // Вниз
        consumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        consumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }
}
