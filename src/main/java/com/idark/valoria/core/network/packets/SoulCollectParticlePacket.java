package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
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
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.uuid);
                Vec3 pos = new Vec3(msg.posX, msg.posY * 0.2F, msg.posZ);
                for(int i = 0; i < 6; i++){
                    Color color = new Color(new ArcRandom().nextInt(255), new ArcRandom().nextInt(255), new ArcRandom().nextInt(255)).brighter();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(color, Color.black).build())
                    .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                    .setLifetime(6)
                    .setVelocity(player.getX(), player.getY(), player.getZ())
                    .spawn(level, pos.x + new ArcRandom().nextDouble(3), pos.y + new ArcRandom().nextDouble(3), pos.z + new ArcRandom().nextDouble(3));

                    ParticleEffects.smokeParticles(level, pos, ColorParticleData.create(Pal.amber, Color.black).build());
                }

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