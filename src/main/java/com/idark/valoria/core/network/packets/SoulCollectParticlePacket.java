package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class SoulCollectParticlePacket{
    private final double posX, posY, posZ;
    private final UUID uuid;
    public SoulCollectParticlePacket(UUID uuid, double posX, double posY, double posZ) {
        this.uuid = uuid;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static SoulCollectParticlePacket decode(FriendlyByteBuf buf) {
        return new SoulCollectParticlePacket(buf.readUUID(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(SoulCollectParticlePacket msg, Supplier<Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.uuid);
                Vec3 pos = new Vec3(msg.posX, msg.posY * 0.2F, msg.posZ);
                final Consumer<GenericParticle> blockTarget = p -> {
                    Vec3 pPos = p.getPosition();

                    double dX = player.getX() - pPos.x();
                    double dY = player.getY() - pPos.y();
                    double dZ = player.getZ() - pPos.z();
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                    float speed = 0.01f;
                    double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                    double y = Math.cos(pitch) * speed;
                    double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                    p.setSpeed(p.getSpeed().subtract(x, y, z));
                };

                ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(Pal.cyan, Color.white).build())
                .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.025f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(blockTarget)
                .setLifetime(60)
                .randomVelocity(0.35f)
                .disablePhysics()
                .repeat(level, pos.x, pos.y, pos.z, 6);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}