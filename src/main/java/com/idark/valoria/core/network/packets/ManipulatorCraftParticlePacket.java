package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManipulatorCraftParticlePacket {

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    public ManipulatorCraftParticlePacket(float posX, float posY, float posZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static ManipulatorCraftParticlePacket decode(FriendlyByteBuf buf) {
        return new ManipulatorCraftParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(ManipulatorCraftParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(-0.05f, 0.025f, -0.05f)
                        .setAlpha(0.35f, 1)
                        .setScale(0.025f, 0.1f)
                        .setColor(231, 76, 60, 0, 0, 0)
                        .setLifetime(8)
                        .spawn(world, msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.85f);

                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(0.05f, 0.025f, 0.05f)
                        .setAlpha(0.35f, 1)
                        .setScale(0.025f, 0.1f)
                        .setColor(46, 204, 113, 0, 0, 0)
                        .setLifetime(8)
                        .spawn(world, msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.15f);

                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(-0.05f, 0.025f, 0.05f)
                        .setAlpha(0.35f, 1)
                        .setScale(0.025f, 0.1f)
                        .setColor(17, 195, 214, 0, 0, 0)
                        .setLifetime(8)
                        .spawn(world, msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.15f);

                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(0.05f, 0.025f, -0.05f)
                        .setAlpha(0.35f, 1)
                        .setScale(0.025f, 0.1f)
                        .setColor(52, 73, 94, 0, 0, 0)
                        .setLifetime(8)
                        .spawn(world, msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.85f);

                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(0, 0.025f, 0)
                        .setAlpha(0.1f, 0.5f)
                        .setScale(0.025f, 0.1f)
                        .setColor(255, 255, 255, 0, 0, 0)
                        .setLifetime(12)
                        .spawn(world, msg.posX + 0.5f, msg.posY + 1, msg.posZ + 0.5f);

                ctx.get().setPacketHandled(true);
            });
        }
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
    }
}