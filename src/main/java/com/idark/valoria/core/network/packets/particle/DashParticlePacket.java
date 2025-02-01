package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class DashParticlePacket{
    private final UUID id;
    private final float velX, velY, velZ;
    private final int count, colorR, colorG, colorB;

    public DashParticlePacket(UUID id, int count, float velX, float velY, float velZ, int colorR, int colorG, int colorB){
        this.id = id;
        this.count = count;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public DashParticlePacket(UUID id, int count, float velX, float velY, float velZ, Color clr){
        this.id = id;
        this.count = count;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.colorR = clr.getRed();
        this.colorG = clr.getGreen();
        this.colorB = clr.getBlue();
    }

    public static DashParticlePacket decode(FriendlyByteBuf buf){
        return new DashParticlePacket(buf.readUUID(), buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(DashParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.id);
                if(player != null){
                    RandomSource rand = level.getRandom();
                    Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                    Vec3 pos = new Vec3(player.getX(), player.getY(), player.getZ());
                    for(int count = 0; count < msg.count; count++){
                        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
                        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
                        for(int i = 0; i < 10; i += 1){
                            double locDistance = i * 0.5D;
                            double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                            double Y = Math.cos(pitch) * 2;
                            double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                            Random random = new Random();
                            ParticleBuilder.create(ParticleRegistry.SPHERE)
                                    .setColorData(ColorParticleData.create(color, Pal.darkestGray).build())
                                    .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE)
                                    .setTransparencyData(GenericParticleData.create(random.nextFloat(0, 0.6f), 0f).build())
                                    .setScaleData(GenericParticleData.create(0.92f, 0f).build())
                                    .setLifetime(15)
                                    .randomVelocity(msg.velX, msg.velY, msg.velZ)
                                    .randomOffset(0.025f, 0, 0.025f)
                                    .spawn(level, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D));
                        }
                    }
                }else{
                    Valoria.LOGGER.error("Player with UUID {}, not found", msg.id);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeInt(count);
        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}