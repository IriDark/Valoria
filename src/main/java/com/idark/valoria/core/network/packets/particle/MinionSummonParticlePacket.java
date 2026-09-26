package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class MinionSummonParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<MinionSummonParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("minion_summon_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MinionSummonParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), MinionSummonParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final int id;
    private final BlockPos pos;

    public MinionSummonParticlePacket(int id, BlockPos pos){
        this.id = id;
        this.pos = pos;
    }

    public static MinionSummonParticlePacket decode(FriendlyByteBuf buf){
        return new MinionSummonParticlePacket(buf.readInt(), buf.readBlockPos());
    }

    public static void handle(MinionSummonParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                final Consumer<GenericParticle> blockTarget = p -> {
                    var entity = pLevel.getEntity(msg.id);
                    if(entity == null) return;

                    Vec3 entityPos = entity.position();
                    Vec3 pPos = p.getPosition();
                    double dX = entityPos.x - pPos.x();
                    double dY = entityPos.y - pPos.y();
                    double dZ = entityPos.z - pPos.z();
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                    float speed = 0.01f;
                    float x = (float)(Math.sin(pitch) * Math.cos(yaw) * speed);
                    float y = (float)(Math.cos(pitch) * speed);
                    float z = (float)(Math.sin(pitch) * Math.sin(yaw) * speed);

                    p.setSpeed(p.getSpeed().subtract(x, y, z));
                };

                ParticleBuilder.create(TridotParticles.DOT)
                        .setColorData(ColorParticleData.create(Pal.vividGreen, Pal.amethyst).build())
                        .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Interp.bounce).build())
                        .setScaleData(GenericParticleData.create(0.045f, 0.075f, 0).setEasing(Interp.circleOut).build())
                        .addTickActor(blockTarget)
                        .setLifetime(85)
                        .randomVelocity(0.15f)
                        .disablePhysics()
                        .repeat(pLevel, msg.pos.getX(), msg.pos.getY(), msg.pos.getZ(), 6);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }
}
