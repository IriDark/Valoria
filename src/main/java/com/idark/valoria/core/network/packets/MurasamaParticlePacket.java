package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class MurasamaParticlePacket{

    private final float distance;
    private final float posX;
    private final float posY;
    private final float posZ;

    private final int colorR, colorG, colorB;

    public MurasamaParticlePacket(float distance, float posX, float posY, float posZ, int colorR, int colorG, int colorB){
        this.distance = distance;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static MurasamaParticlePacket decode(FriendlyByteBuf buf){
        return new MurasamaParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(MurasamaParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                Random rand = new Random();
                RandomSource source = RandomSource.create();
                double X = ((rand.nextDouble() - 0.5D) * msg.distance);
                double Y = ((rand.nextDouble() - 0.5D) * msg.distance);
                double Z = ((rand.nextDouble() - 0.5D) * msg.distance);

                double dX = -X;
                double dY = -Y;
                double dZ = -Z;
                int count = 1 + Mth.nextInt(source, 0, 2);

                for(int ii = 0; ii < count; ii += 1){
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                    double XX = Math.sin(pitch) * Math.cos(yaw) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    double YY = Math.sin(pitch) * Math.sin(yaw) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    double ZZ = Math.cos(pitch) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    Particles.create(ParticleRegistry.GLOWING_SPHERE)
                    .addVelocity(XX, YY, ZZ)
                    .setAlpha(0.50f, 1)
                    .setScale(0.12f, 0)
                    .setColor(msg.colorR, msg.colorG, msg.colorB, 255, 67, 231)
                    .setLifetime(6)
                    .spawn(world, msg.posX + X, msg.posY + Y, msg.posZ + Z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeFloat(distance);

        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}