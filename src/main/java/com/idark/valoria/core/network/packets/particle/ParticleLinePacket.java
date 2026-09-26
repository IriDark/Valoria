package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

public class ParticleLinePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<ParticleLinePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("particle_line_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ParticleLinePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), ParticleLinePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final double posX, posY, posZ;
    private final float posToX, posToY, posToZ;
    private final int colorR, colorG, colorB, colorToR, colorToG, colorToB;

    public ParticleLinePacket(double posX, double posY, double posZ, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB, int colorToR, int colorToG, int colorToB){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorToR = colorToR;
        this.colorToG = colorToG;
        this.colorToB = colorToB;
    }

    public static ParticleLinePacket decode(FriendlyByteBuf buf){
        return new ParticleLinePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ParticleLinePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                Vec3 pos = new Vec3(msg.posX, msg.posY, msg.posZ);
                Vec3 pTo = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                double distance = pos.distanceTo(pTo);
                double distanceInBlocks = Math.floor(distance);
                for(int i = 0; i < distanceInBlocks; i++){
                    double dX = msg.posX - pTo.x;
                    double dY = msg.posY - pTo.y;
                    double dZ = msg.posZ - pTo.z;

                    float x = (float)(dX / distanceInBlocks);
                    float y = (float)(dY / distanceInBlocks);
                    float z = (float)(dZ / distanceInBlocks);
                    Vec3 particlePos = new Vec3(pTo.x + (x * i), pTo.y + 0.2f + (y * i), pTo.z + (z * i));
                    Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                    ParticleEffects.particles(pLevel, particlePos, ColorParticleData.create(color, Col.white).build());
                }
            });
        }

    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
        buf.writeInt(colorToR);
        buf.writeInt(colorToG);
        buf.writeInt(colorToB);
    }
}