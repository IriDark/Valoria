package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticleLinePacket {
    private final float posX, posY, posZ;
    private final float posToX, posToY, posToZ;
    private final int colorR, colorG, colorB;
    private final int colorToR, colorToG, colorToB;

    public ParticleLinePacket(float posX, float posY, float posZ, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB, int colorToR, int colorToG, int colorToB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorToR = colorToR;
        this.colorToG = colorToG;
        this.colorToB = colorToB;
    }

    public static ParticleLinePacket decode(FriendlyByteBuf buf) {
        return new ParticleLinePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ParticleLinePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getWorld();
                Vec3 pos = new Vec3(msg.posX, msg.posY, msg.posZ);
                Vec3 pTo = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                double distance = pos.distanceTo(pTo);
                double distanceInBlocks = Math.floor(distance);
                for (int i = 0; i < distanceInBlocks; i++) {
                    double dX = msg.posX - pTo.x;
                    double dY = msg.posY - pTo.y;
                    double dZ = msg.posZ - pTo.z;

                    float x = (float) (dX / distanceInBlocks);
                    float y = (float) (dY / distanceInBlocks);
                    float z = (float) (dZ / distanceInBlocks);
                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity((x / i) / 2, (y * i), (z / i) / 2)
                            .setAlpha(1, 0)
                            .setScale(0.2f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(6)
                            .spawn(pLevel, pTo.x + (x * i), pTo.y + 0.2f + (y * i), pTo.z + (z * i));
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
        buf.writeFloat(colorToR);
        buf.writeFloat(colorToG);
        buf.writeFloat(colorToB);
    }
}