package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import org.joml.*;

import java.awt.*;
import java.lang.Math;
import java.util.function.*;

public class ManipulatorParticlePacket {

    private final double posX, posY, posZ, posToX, posToY, posToZ;
    private final float yawRaw;
    private final int colorR, colorG, colorB;

    public ManipulatorParticlePacket(double posX, double posY, double posZ, double posToX, double posToY, double posToZ, float yawRaw, int colorR, int colorG, int colorB) {
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

    public static ManipulatorParticlePacket decode(FriendlyByteBuf buf) {
        return new ManipulatorParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ManipulatorParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                double pitch = ((90) * Math.PI) / 180;
                double yaw = ((msg.yawRaw + 90) * Math.PI) / 180;

                float pRadius = 0.25f;
                double locYaw = 0;
                double locPitch = 0;
                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
                double Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;
                Vector3d d = new Vector3d(msg.posX - msg.posToX, msg.posY - msg.posToY, msg.posZ - msg.posToZ);
                Vec3 particlePos = new Vec3(msg.posX + X, msg.posY + Y + ((Math.random() - 0.5D) * 0.2F), msg.posZ + Z);
                Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(color, Color.white).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0f).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                .setLifetime(6)
                .setVelocity(d.x, d.y, d.z)
                .spawn(pLevel, particlePos.x, particlePos.y, particlePos.z);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(posToX);
        buf.writeDouble(posToY);
        buf.writeDouble(posToZ);

        buf.writeFloat(yawRaw);
        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}