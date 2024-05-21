package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class SarcophagusSummonPacket{
    private static final Random random = new Random();
    private final float posX;
    private final float posY;
    private final float posZ;
    private final float velX;
    private final float velY;
    private final float velZ;
    private final float colorR, colorG, colorB;

    public SarcophagusSummonPacket(float posX, float posY, float posZ, float velX, float velY, float velZ, float colorR, float colorG, float colorB){
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

    public static SarcophagusSummonPacket decode(FriendlyByteBuf buf){
        return new SarcophagusSummonPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(SarcophagusSummonPacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level world = Valoria.proxy.getWorld();
                for(int i = 0; i < 26; i++){
                    Particles.create(ParticleRegistry.SPHERE)
                    .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5), msg.velY + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))), msg.velZ + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5))
                    .setAlpha(0.22f, 0)
                    .setScale(0.36f, 2)
                    .setColor(msg.colorR, msg.colorG, msg.colorB)
                    .setLifetime(95 + random.nextInt(100))
                    .setSpin((0.5f * (float)((random.nextDouble() - 0.5D) / 2)))
                    .spawn(world, msg.posX, msg.posY, msg.posZ);
                }
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}