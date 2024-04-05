package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class NecromancerSummonParticlePacket {
    private static final Random random = new Random();
    private final float posX;
    private final float posY;
    private final float posZ;
    private final float velX;
    private final float velY;
    private final float velZ;
    private final float colorR, colorG, colorB;

    public NecromancerSummonParticlePacket(float posX, float posY, float posZ, float velX, float velY, float velZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static NecromancerSummonParticlePacket decode(FriendlyByteBuf buf) {
        return new NecromancerSummonParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(NecromancerSummonParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                for (int i = 0; i < 26; i++) {
                    Particles.create(ModParticles.SPHERE)
                            .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5), msg.velY + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))), msg.velZ + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5))
                            .setAlpha(0.12f, 0)
                            .setScale(0.36f, 2)
                            .setColor(msg.colorR, msg.colorG, msg.colorB)
                            .setLifetime(125 + random.nextInt(100))
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) / 2)))
                            .spawn(world, msg.posX, msg.posY, msg.posZ);
                }
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}