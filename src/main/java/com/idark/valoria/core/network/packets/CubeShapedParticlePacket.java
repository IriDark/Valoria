package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.function.*;

public class CubeShapedParticlePacket{

    private final double posX, posY, posZ;
    private final int colorR, colorG, colorB;
    private final float speedY, size;

    public CubeShapedParticlePacket(double posX, double posY, double posZ, float size, float speedY, int colorR, int colorG, int colorB){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.speedY = speedY;
        this.size = size;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CubeShapedParticlePacket decode(FriendlyByteBuf buf){
        return new CubeShapedParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(CubeShapedParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                float size = msg.size;

                for(int i = 0; i < 25 * size; i++){
                    double pOffset = Math.sin(i) * size;

                    Vec3 pos0 = new Vec3(msg.posX + size, msg.posY, msg.posZ + pOffset);
                    Vec3 pos1 = new Vec3(msg.posX - size, msg.posY, msg.posZ + pOffset);
                    Vec3 pos2 = new Vec3(msg.posX + pOffset, msg.posY, msg.posZ - size);
                    Vec3 pos3 = new Vec3(msg.posX - pOffset, msg.posY, msg.posZ + size);

                    ParticleEffects.particles(level, pos0, ColorParticleData.create(color, Color.black).build()).getBuilder().setMotion(0, msg.speedY, 0).spawnLine(level, pos0, pos0);
                    ParticleEffects.particles(level, pos0, ColorParticleData.create(color, Color.black).build()).getBuilder().setMotion(0, msg.speedY, 0).spawnLine(level, pos1, pos1);
                    ParticleEffects.particles(level, pos0, ColorParticleData.create(color, Color.black).build()).getBuilder().setMotion(0, msg.speedY, 0).spawnLine(level, pos2, pos2);
                    ParticleEffects.particles(level, pos0, ColorParticleData.create(color, Color.black).build()).getBuilder().setMotion(0, msg.speedY, 0).spawnLine(level, pos3, pos3);

                }

                ctx.get().setPacketHandled(true);
            });
        }
    }


    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeFloat(size);
        buf.writeFloat(speedY);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}