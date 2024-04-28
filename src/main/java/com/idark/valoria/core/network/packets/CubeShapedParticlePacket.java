package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CubeShapedParticlePacket {

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float speedY;
    private final float size;

    private final int colorR, colorG, colorB;

    public CubeShapedParticlePacket(float posX, float posY, float posZ, float size, float speedY, int colorR, int colorG, int colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.speedY = speedY;
        this.size = size;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CubeShapedParticlePacket decode(FriendlyByteBuf buf) {
        return new CubeShapedParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(CubeShapedParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                for (int i = 0; i < 15; i++) {
                    float size = msg.size;
                    double Offset = Math.sin(i - 5) * size;
                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity(0, msg.speedY, 0)
                            .setAlpha(0.25f, 0)
                            .setScale(0.1f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(10)
                            .spawn(world, msg.posX + size, msg.posY, msg.posZ + Offset);

                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity(0, msg.speedY, 0)
                            .setAlpha(0.25f, 0)
                            .setScale(0.1f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(10)
                            .spawn(world, msg.posX - size, msg.posY, msg.posZ - Offset);

                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity(0, msg.speedY, 0)
                            .setAlpha(0.25f, 0)
                            .setScale(0.1f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(10)
                            .spawn(world, msg.posX + Offset, msg.posY, msg.posZ + size);

                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity(0, msg.speedY, 0)
                            .setAlpha(0.25f, 0)
                            .setScale(0.1f, 0)
                            .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                            .setLifetime(10)
                            .spawn(world, msg.posX + Offset, msg.posY, msg.posZ - size);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }


    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(size);
        buf.writeFloat(speedY);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}