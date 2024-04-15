package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class KeypadParticlePacket {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final double targetPosX;
    private final double targetPosY;
    private final double targetPosZ;

    public KeypadParticlePacket(double posX, double posY, double posZ, double targetPosX, double targetPosY, double targetPosZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.targetPosX = targetPosX;
        this.targetPosY = targetPosY;
        this.targetPosZ = targetPosZ;
    }

    public static KeypadParticlePacket decode(FriendlyByteBuf buf) {
        return new KeypadParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(KeypadParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getWorld();
                Random rand = new Random();
                for (int a = 0; a < 3; a++) {
                    Particles.create(ModParticles.TRANSFORM_PARTICLE)
                            .addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
                            .setAlpha(1.0f, 0)
                            .setScale(0.3f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(36)
                            .setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
                            .spawn(pLevel, msg.posX + (rand.nextDouble() * 1.25), msg.posY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.posZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25))
                            .spawn(pLevel, msg.targetPosX + (rand.nextDouble() * 1.25), msg.targetPosY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.targetPosZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeDouble(targetPosX);
        buf.writeDouble(targetPosY);
        buf.writeDouble(targetPosZ);
    }
}