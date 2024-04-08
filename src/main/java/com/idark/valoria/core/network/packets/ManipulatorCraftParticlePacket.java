package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

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
                Vector3d d = new Vector3d(msg.posX - msg.posToX, msg.posY - msg.posToY + 0.05f, msg.posZ - msg.posToZ);
                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(d.x, d.y, d.z)
                        .setAlpha(0.35f, 1)
                        .setScale(0.055f, 0.15f)
                        .setColor(msg.colorR / 255, msg.colorG / 255, msg.colorB / 255, 0, 0, 0)
                        .setLifetime(2)
                        .spawn(world, msg.posX, msg.posY, msg.posZ);

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