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

public class CircleShapedParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<CircleShapedParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("circle_shaped_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CircleShapedParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), CircleShapedParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final double posX, posY, posZ;
    private final float yawRaw;
    private final int colorR, colorG, colorB;

    public CircleShapedParticlePacket(double posX, double posY, double posZ, float yawRaw, int colorR, int colorG, int colorB){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yawRaw = yawRaw;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CircleShapedParticlePacket decode(FriendlyByteBuf buf){
        return new CircleShapedParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(CircleShapedParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                float pRadius = 1;
                double pitch = ((90) * Math.PI) / 180;
                Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                for(int i = 0; i < 360; i += 10){
                    double yaw = ((msg.yawRaw + 90 + i) * Math.PI) / 180;
                    double X = Math.sin(pitch) * Math.cos(yaw) * pRadius * 0.75F;
                    double Y = Math.cos(pitch) * pRadius * 0.75F;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * pRadius * 0.75F;
                    Vec3 pos = new Vec3(msg.posX + X, msg.posY + Y * 0.2F, msg.posZ + Z);
                    ParticleEffects.particles(level, pos, ColorParticleData.create(color, Col.black).build());
                }

            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeFloat(yawRaw);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}