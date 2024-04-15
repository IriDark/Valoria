package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.function.Supplier;

public class CubeShapedParticlePacket {

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;
    private final float particlesCount;

    private final int colorR, colorG, colorB;

    public CubeShapedParticlePacket(float posX, float posY, float posZ, float particlesCount, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;
        this.particlesCount = particlesCount;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CubeShapedParticlePacket decode(FriendlyByteBuf buf) {
        return new CubeShapedParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(CubeShapedParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                double posX = msg.posX;
                double posY = msg.posY;
                Vector3d[][] edges = getVector3ds(msg, posX, posY);

                Vector3d velocity = new Vector3d(msg.posX - msg.posToX, msg.posY - msg.posToY, msg.posZ - msg.posToZ);
                for (Vector3d[] edge : edges) {
                    double startX = edge[0].x;
                    double startZ = edge[0].z;
                    double endX = edge[1].x;
                    double endZ = edge[1].z;
                    double stepX = (endX - startX) / msg.particlesCount;
                    double stepZ = (endZ - startZ) / msg.particlesCount;
                    for (int i = 0; i <= msg.particlesCount; i++) {
                        double x = startX + stepX * i;
                        double z = startZ + stepZ * i;
                        Particles.create(ModParticles.GLOWING_SPHERE)
                                .addVelocity(velocity.x, velocity.y, velocity.z)
                                .setAlpha(0.65f, 0)
                                .setScale(0.1f, 0)
                                .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                                .setLifetime(12)
                                .spawn(world, x, posY, z);
                    }
                }
                ctx.get().setPacketHandled(true);
            });
        }
    }

    @NotNull
    private static Vector3d[][] getVector3ds(CubeShapedParticlePacket msg, double posX, double posY) {
        double posZ = msg.posZ;
        double size = 1;
        double halfSize = size / 1.85;
        Vector3d[] vertices = {
                new Vector3d(posX - halfSize, posY, posZ - halfSize),
                new Vector3d(posX + halfSize, posY, posZ - halfSize),
                new Vector3d(posX + halfSize, posY, posZ + halfSize),
                new Vector3d(posX - halfSize, posY, posZ + halfSize)
        };

        return new Vector3d[][]{
                { vertices[0], vertices[1] },
                { vertices[1], vertices[2] },
                { vertices[2], vertices[3] },
                { vertices[3], vertices[0] }
        };
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
        buf.writeFloat(particlesCount);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}