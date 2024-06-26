package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;
import org.joml.*;

import java.lang.Math;
import java.util.function.*;

public class ManipulatorParticlePacket{

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;
    private final float yawRaw;

    private final float colorR, colorG, colorB;

    public ManipulatorParticlePacket(float posX, float posY, float posZ, float yawRaw, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB){
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

    public static ManipulatorParticlePacket decode(FriendlyByteBuf buf){
        return new ManipulatorParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(ManipulatorParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                double pitch = ((90) * Math.PI) / 180;
                double yaw = ((msg.yawRaw + 90) * Math.PI) / 180;

                float pRadius = 0.25f;
                double locYaw = 0;
                double locPitch = 0;
                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * pRadius * 0.75F;
                double Y = Math.cos(locPitch + pitch) * pRadius * 0.75F;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * pRadius * 0.75F;
                Vector3d d = new Vector3d(msg.posX - msg.posToX, msg.posY - msg.posToY, msg.posZ - msg.posToZ);
                Particles.create(ParticleRegistry.GLOWING_SPHERE)
                .addVelocity(d.x, d.y, d.z)
                .setAlpha(0.52f, 0)
                .setScale(0.1f, 0)
                .setColor(msg.colorR / 255, msg.colorG / 255, msg.colorB / 255, 0, 0, 0)
                .setLifetime(6)
                .spawn(world, msg.posX + X, msg.posY + Y + ((Math.random() - 0.5D) * 0.2F), msg.posZ + Z);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
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