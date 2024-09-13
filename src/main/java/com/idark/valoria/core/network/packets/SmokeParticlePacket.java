package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

import static com.idark.valoria.client.particle.ParticleEffects.ADDITIVE_TRANSPARENT;

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

    public static ParticleEffectSpawner packetSmokeParticles(SmokeParticlePacket msg, Vec3 pos, ColorParticleData colorData){
        return packetSmokeParticles(msg, pos, colorData, new WorldParticleOptions(ParticleRegistry.SMOKE));
    }

    public static ParticleEffectSpawner packetSmokeParticles(SmokeParticlePacket msg, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return packetSmokeParticles(msg, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner packetSmokeParticles(SmokeParticlePacket msg, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return packetSmokeParticles(msg, pos, builder);
    }

    public static ParticleEffectSpawner packetSmokeParticles(SmokeParticlePacket msg, Vec3 pos, WorldParticleBuilder builder){
        Random random = new Random();
        final WorldParticleBuilder particleBuilder = builder
        .setRenderType(ADDITIVE_TRANSPARENT)
        .setTransparencyData(GenericParticleData.create(random.nextFloat(0, 0.6f), 0f).build())
        .setScaleData(GenericParticleData.create(0.92f, 0f).build())
        .setLifetime(95 + random.nextInt(100))
        .setRandomMotion(msg.velX, msg.velY, msg.velZ)
        .setRandomOffset(0.025f);

        return new ParticleEffectSpawner(Valoria.proxy.getLevel(), pos, particleBuilder);
    }


    public static SmokeParticlePacket decode(FriendlyByteBuf buf){
        return new SmokeParticlePacket(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(SmokeParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                Vec3 pos = new Vec3(msg.posX, msg.posY, msg.posZ);
                for(int i = 0; i < msg.count; i++){
                    packetSmokeParticles(msg, pos, ColorParticleData.create(color, Pal.darkestGray).build()).spawnParticles();
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