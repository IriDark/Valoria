package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
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

public class JumpParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<JumpParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("jump_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, JumpParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), JumpParticlePacket::decode);
    private final UUID id;

    public JumpParticlePacket(UUID id){
        this.id = id;
    }

    public static JumpParticlePacket decode(FriendlyByteBuf buf){
        return new JumpParticlePacket(buf.readUUID());
    }

    public static void handle(JumpParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.id);
                if(player != null){
                    Vec3 delta = player.getDeltaMovement().normalize();
                    var y = player.getY() + 1;
                    Vec3 trailPos = new Vec3(player.getX() + delta.x() * 0.00015, y + delta.y() * 0.00015, player.getZ() + delta.z() * 0.00015);
                    final Vec3[] cachePos = {new Vec3(trailPos.x, trailPos.y, trailPos.z)};
                    final Consumer<GenericParticle> target = p -> {
                        Vec3 pos = new Vec3(player.getX(), player.getY() + 1, player.getZ());

                        float lenBetweenArrowAndParticle = (float)(pos.subtract(cachePos[0])).length();
                        Vec3 vector = (pos.subtract(cachePos[0]));
                        if(lenBetweenArrowAndParticle > 0){
                            cachePos[0] = cachePos[0].add(vector);
                            p.setPosition(cachePos[0]);
                        }
                    };

                    Vec3 pos = new Vec3(player.getX(), y, player.getZ());
                    ParticleBuilder.create(TridotParticles.TRAIL)
                    .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE)
                    .setBehavior(TrailParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(Col.white, Pal.darkishGray).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.bounceOut).build())
                    .setScaleData(GenericParticleData.create(2, 1, 0).setEasing(Interp.bounceOut).build())
                    .addTickActor(target)
                    .setGravity(0)
                    .setLifetime(25)
                    .repeat(level, pos.x, pos.y, pos.z, 1);
                }else{
                    Valoria.LOGGER.error("Player with UUID {}, not found", msg.id);
                }
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}