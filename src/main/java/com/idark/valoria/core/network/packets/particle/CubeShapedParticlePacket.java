package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

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

                    ParticleBuilder.create(TridotParticles.WISP)
                            .setColorData(ColorParticleData.create(color, Color.black).build())
                            .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                            .setLifetime(6)
                            .addVelocity(0, msg.speedY, 0)
                            .spawn(level, pos0.x, pos0.y, pos0.z);

                    ParticleBuilder.create(TridotParticles.WISP)
                            .setColorData(ColorParticleData.create(color, Color.black).build())
                            .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                            .setLifetime(6)
                            .addVelocity(0, msg.speedY, 0)
                            .spawn(level, pos1.x, pos1.y, pos1.z);

                    ParticleBuilder.create(TridotParticles.WISP)
                            .setColorData(ColorParticleData.create(color, Color.black).build())
                            .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                            .setLifetime(6)
                            .addVelocity(0, msg.speedY, 0)
                            .spawn(level, pos2.x, pos2.y, pos2.z);

                    ParticleBuilder.create(TridotParticles.WISP)
                            .setColorData(ColorParticleData.create(color, Color.black).build())
                            .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                            .setLifetime(6)
                            .addVelocity(0, msg.speedY, 0)
                            .spawn(level, pos3.x, pos3.y, pos3.z);
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