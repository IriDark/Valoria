package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;

public class CrushParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<CrushParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("crush_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CrushParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), CrushParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final BlockPos feetPos;
    private final double spawnX, spawnY, spawnZ;

    public CrushParticlePacket(BlockPos feetBlockPos, double spawnX, double spawnY, double spawnZ){
        this.feetPos = feetBlockPos;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
    }

    public static CrushParticlePacket decode(FriendlyByteBuf buf){
        return new CrushParticlePacket(buf.readBlockPos(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(CrushParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                if(level != null){
                    var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), level.getBlockState(msg.feetPos));

                    ParticleBuilder.create(opt)
                    .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
                    .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                    .setLifetime(30)
                    .randomVelocity(0.45, 0.75, 0.45)
                    .randomOffset(0.125, 0.125)
                    .setGravity(0.75f)
                    .repeat(level, msg.spawnX, msg.spawnY, msg.spawnZ, 64);
                }

            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(feetPos);

        buf.writeDouble(spawnX);
        buf.writeDouble(spawnY);
        buf.writeDouble(spawnZ);
    }
}
