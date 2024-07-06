package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class SmokeParticlePacket{
    private static final Random random = new Random();
    private final double posX, posY, posZ, velX, velY, velZ;
    private final int colorR, colorG, colorB;

    public SmokeParticlePacket(double posX, double posY, double posZ, double velX, double velY, double velZ, int colorR, int colorG, int colorB){
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

    public static SmokeParticlePacket decode(FriendlyByteBuf buf){
        return new SmokeParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(SmokeParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getWorld();
                Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                Vec3 particlePos = new Vec3(msg.posX, msg.posY, msg.posZ);
                for(int i = 0; i < 26; i++){
                    ParticleEffects.particles(pLevel, particlePos, ColorParticleData.create(color, Pal.darkestGray).build()).getBuilder().setScaleData(GenericParticleData.create(0.92f, 0f).build()).setLifetime(95 + random.nextInt(100)).setGravityStrength(0.005f).setTransparencyData(GenericParticleData.create(0.25f).build()).setNoClip(true).setMotion(msg.velX + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5), msg.velY + ((random.nextDouble() - 0.5D) / (20 - (5 * random.nextDouble()))), msg.velZ + ((random.nextDouble() - 0.5D) / (2 * random.nextDouble()) / 5)).spawn(pLevel, msg.posX, msg.posY, msg.posZ);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}