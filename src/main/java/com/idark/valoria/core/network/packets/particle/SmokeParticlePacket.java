package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class SmokeParticlePacket{
    private final double posX, posY, posZ;
    private final float velX, velY, velZ;
    private final int count, colorR, colorG, colorB;

    public SmokeParticlePacket(int count, double posX, double posY, double posZ, float velX, float velY, float velZ, int colorR, int colorG, int colorB){
        this.count = count;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static void packetSmokeParticles(SmokeParticlePacket msg, Level level, ColorParticleData color){
        var random = Tmp.rnd;
        ParticleBuilder.create(ParticleRegistry.SMOKE)
                .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(color)
                .setTransparencyData(GenericParticleData.create().setRandomValue(0, 0.6f, 0, 0).build())
                .setScaleData(GenericParticleData.create(0.92f, 0f).build())
                .setLifetime(95 + random.nextInt(100))
                .randomVelocity(msg.velX, msg.velY, msg.velZ)
                .randomOffset(0.025f)
                .spawn(level, msg.posX, msg.posY, msg.posZ);
    }


    public static SmokeParticlePacket decode(FriendlyByteBuf buf){
        return new SmokeParticlePacket(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(SmokeParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                for(int i = 0; i < msg.count; i++){
                    packetSmokeParticles(msg, level, ColorParticleData.create(color, Pal.darkestGray).build());
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeInt(count);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}