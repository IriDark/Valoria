package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;

import java.util.function.*;

public class CircleShapedParticlePacket{

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;
    private final float yawRaw;

    private final int colorR, colorG, colorB;

    public CircleShapedParticlePacket(float posX, float posY, float posZ, float yawRaw, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB){
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

    public static CircleShapedParticlePacket decode(FriendlyByteBuf buf){
        return new CircleShapedParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(CircleShapedParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                float pRadius = 1;
                double pitch = ((90) * Math.PI) / 180;
                for(int i = 0; i < 360; i += 10){
                    double yaw = ((msg.yawRaw + 90 + i) * Math.PI) / 180;
                    double X = Math.sin(pitch) * Math.cos(yaw) * pRadius * 0.75F;
                    double Y = Math.cos(pitch) * pRadius * 0.75F;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * pRadius * 0.75F;
                    Particles.create(ParticleRegistry.GLOWING_SPHERE)
                    .addVelocity(msg.posToX, msg.posToY, msg.posToZ)
                    .setAlpha(0.25f, 0)
                    .setScale(0.2f, 0)
                    .setColor(msg.colorR, msg.colorG, msg.colorB, 0, 0, 0)
                    .setLifetime(6)
                    .spawn(world, msg.posX + X, msg.posY + Y * 0.2F, msg.posZ + Z);
                }

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