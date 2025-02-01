package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;
import pro.komaru.tridot.core.math.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class SoulCollectParticlePacket{
    private final double posX, posY, posZ;
    private final UUID uuid;

    public SoulCollectParticlePacket(UUID uuid, double posX, double posY, double posZ){
        this.uuid = uuid;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static SoulCollectParticlePacket decode(FriendlyByteBuf buf){
        return new SoulCollectParticlePacket(buf.readUUID(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(SoulCollectParticlePacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Vec3 pos = new Vec3(msg.posX, msg.posY, msg.posZ);
                final Consumer<GenericParticle> blockTarget = p -> {
                    Player player = level.getPlayerByUUID(msg.uuid);
                    Vec3 pPos = p.getPosition();
                    if(player != null){
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
                    }
                };

                ParticleBuilder.create(ParticleRegistry.SKULL)
                        .setColorData(ColorParticleData.create(Pal.cyan, Color.white).build())
                        .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Interp.circle).build())
                        .setScaleData(GenericParticleData.create(0.06f, 0.15f, 0).setEasing(Interp.circle).build())
                        .addTickActor(blockTarget)
                        .setLifetime(65)
                        .randomVelocity(0.25f)
                        .disablePhysics()
                        .repeat(level, pos.x, pos.y, pos.z, 8);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(uuid);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}