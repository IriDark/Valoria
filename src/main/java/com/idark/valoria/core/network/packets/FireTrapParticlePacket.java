package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class FireTrapParticlePacket {

    private final double posX;
    private final double posY;
    private final double posZ;

    private final int colorR, colorG, colorB;
    private final int colorToR, colorToG, colorToB;

    public FireTrapParticlePacket(double posX, double posY, double posZ, int colorR, int colorG, int colorB, int colorToR, int colorToG, int colorToB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        this.colorToR = colorToR;
        this.colorToG = colorToG;
        this.colorToB = colorToB;
    }

    public static FireTrapParticlePacket decode(FriendlyByteBuf buf) {
        return new FireTrapParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(FireTrapParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getWorld();
                for (int i = 0; i < 20; i++) {
                    Particles.create(ParticleRegistry.GLOWING_SPHERE)
                            .addVelocity((new Random().nextDouble() - 0.5D) / 30, (new Random().nextDouble() + 0.5D) / 6, (new Random().nextDouble() - 0.5D) / 30)
                            .setAlpha(0.15f, 0)
                            .setScale(0.32f + RandomUtil.randomValueUpTo(0.2f), RandomUtil.randomValueUpTo(0.2f))
                            .setColor(msg.colorR, msg.colorG, msg.colorB, msg.colorToR, msg.colorToG, msg.colorToB)
                            .setLifetime(21)
                            .setSpin(0.5f)
                            .spawn(level, msg.posX, msg.posY + 1.2f, msg.posZ);
                    Particles.create(ParticleRegistry.SPHERE)
                            .addVelocity((new Random().nextDouble() - 0.2D) / 30, (new Random().nextDouble() + 0.2D) / 6, (new Random().nextDouble() - 0.2D) / 30)
                            .setAlpha(0.25f, 0)
                            .setScale(0.25f, RandomUtil.randomValueUpTo(0.55f))
                            .setColor(0, 0, 0, 0, 0, 0)
                            .setLifetime(32)
                            .setSpin(0.5f)
                            .spawn(level, msg.posX, msg.posY + 1.2f, msg.posZ);

                    level.addParticle(ParticleTypes.LAVA, msg.posX + 0.5, msg.posY + 1.5, msg.posZ + 0.5, 0.0D, 0.0D, 0.0D);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);

        buf.writeInt(colorToR);
        buf.writeInt(colorToG);
        buf.writeInt(colorToB);
    }
}
