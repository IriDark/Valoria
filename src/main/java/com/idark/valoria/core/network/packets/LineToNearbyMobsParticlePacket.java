package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

public class LineToNearbyMobsParticlePacket{
    private final double posX, posY, posZ;
    private final float yawRaw;
    private final float rad;
    private final int colorR, colorG, colorB;

    public LineToNearbyMobsParticlePacket(double posX, double posY, double posZ, float yawRaw, float radius, int colorR, int colorG, int colorB){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.yawRaw = yawRaw;
        this.rad = radius;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static LineToNearbyMobsParticlePacket decode(FriendlyByteBuf buf){
        return new LineToNearbyMobsParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(LineToNearbyMobsParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getWorld();
                double pitch = (90 * Math.PI) / 180;
                double yaw = ((msg.yawRaw + 90) * Math.PI) / 180;

                List<LivingEntity> hitEntities = new ArrayList<>();
                double locYaw = 0;
                double locPitch = 0;
                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * msg.rad;
                double Y = Math.cos(locPitch + pitch) * msg.rad;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * msg.rad;

                AABB boundingBox = new AABB(msg.posX, msg.posY - 8 + ((Math.random() - 0.5D) * 0.2F), msg.posZ, msg.posX + X, msg.posY + Y + ((Math.random() - 0.5D) * 0.2F), msg.posZ + Z);
                List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
                for(Entity entity : entities){
                    if(entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity)){
                        hitEntities.add(livingEntity);
                        if(!livingEntity.isAlive()){
                            continue;
                        }

                        Vec3 pos = new Vec3(msg.posX, msg.posY, msg.posZ);
                        Vec3 pTo = new Vec3(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                        Color color = new Color(msg.colorR, msg.colorG, msg.colorB);

                        double distance = pos.distanceTo(pTo);
                        double distanceInBlocks = Math.floor(distance);
                        for(int i = 0; i < distanceInBlocks; i++){
                            double dX = msg.posX - pTo.x;
                            double dY = msg.posY - pTo.y;
                            double dZ = msg.posZ - pTo.z;
                            float x = (float)(dX / distanceInBlocks);
                            float y = (float)(dY / distanceInBlocks);
                            float z = (float)(dZ / distanceInBlocks);

                            Vec3 particlePos = new Vec3(pos.x - (x * i), pos.y + 0.2f - (y * i), pos.z - (z * i));
                            ParticleEffects.particles(pLevel, particlePos, ColorParticleData.create(color, Color.white).build()).spawnParticles();
                        }
                    }
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeFloat(yawRaw);
        buf.writeFloat(rad);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}