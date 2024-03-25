package com.idark.valoria.network;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

import java.util.function.Supplier;

public class NecromancerTransformParticlePacket {

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;
    private final float yawRaw;

    private final float colorR, colorG, colorB;

    public NecromancerTransformParticlePacket(float posX, float posY, float posZ, float yawRaw, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;
        this.yawRaw = yawRaw;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static NecromancerTransformParticlePacket decode(FriendlyByteBuf buf) {
        return new NecromancerTransformParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(NecromancerTransformParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                double pitch = ((90) * Math.PI) / 180;
                double yaw = ((msg.yawRaw + 90) * Math.PI) / 180;

                float pRadius = 1;
                double locYaw = 0;
                double locPitch = 0;
                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
                double Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;
                Vector3d d = new Vector3d(msg.posX - msg.posToX, msg.posY - msg.posToY, msg.posZ - msg.posToZ);
                for (int i = 0; i < 6; i++) {
                    Particles.create(ModParticles.SPHERE)
                            .addVelocity(d.x, d.y, d.z)
                            .setAlpha(0.65f, 0)
                            .setScale(0.2f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(6)
                            .spawn(world, msg.posX + X, msg.posY + Y + ((Math.random() - 0.5D) * 0.2F), msg.posZ + Z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
        buf.writeFloat(yawRaw);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}