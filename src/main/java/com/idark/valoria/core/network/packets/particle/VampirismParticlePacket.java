package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;
import java.util.function.*;

public class VampirismParticlePacket implements CustomPacketPayload{ // PORT NOTE: SimpleChannel message -> CustomPacketPayload
    public static final CustomPacketPayload.Type<VampirismParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("vampirism_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, VampirismParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), VampirismParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final double posX, posY, posZ;
    private final UUID uuid;

    public VampirismParticlePacket(UUID uuid, double posX, double posY, double posZ){
        this.uuid = uuid;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static VampirismParticlePacket decode(FriendlyByteBuf buf){
        return new VampirismParticlePacket(buf.readUUID(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(VampirismParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
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
                        float x = (float)(Math.sin(pitch) * Math.cos(yaw) * speed);
                        float y = (float)(Math.cos(pitch) * speed);
                        float z = (float)(Math.sin(pitch) * Math.sin(yaw) * speed);
                        p.setSpeed(p.getSpeed().subtract(x, y, z));
                    }
                };

                ParticleBuilder.create(TridotParticles.TRAIL)
                .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE)
                .setBehavior(TrailParticleBehavior.create().build())
                .setScaleData(GenericParticleData.create(0.035f + Tmp.rnd.nextFloat(0.085f), 0.15f + Tmp.rnd.nextFloat(0.05f), 0).setEasing(Interp.bounce).build())
                .setColorData(ColorParticleData.create(Tmp.rnd.fiftyFifty() ? Pal.darkRed : Pal.darkRed.copy().brighter(), Pal.flesh).build())
                .addTickActor(blockTarget)
                .setLifetime(65)
                .flatRandomOffset(0.15f, 0f, 0.15f)
                .setFriction(Tmp.rnd.nextFloat(0.85f, 1f))
                .randomVelocity(0.0825f + Tmp.rnd.nextFloat(0.04f, 0.05f))
                .disablePhysics()
                .repeat(level, pos.x, pos.y, pos.z, 12);

                ParticleBuilder.create(ParticleRegistry.FLESH)
                .randomOffset(0.4f)
                .setGravity(0.8f)
                .setFriction(Tmp.rnd.nextFloat(0.65f, 1.1f))
                .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                .setScaleData(GenericParticleData.create(0.15f).build())
                .repeat(level, pos.x, pos.y, pos.z, 8);

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