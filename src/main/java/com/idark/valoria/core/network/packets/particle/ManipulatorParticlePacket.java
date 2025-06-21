package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.NetworkEvent.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;

import java.util.function.*;

public class ManipulatorParticlePacket{
    public BlockPos pos;
    public int given;
    public ColorParticleData data;
    public ManipulatorParticlePacket(BlockPos pos, int given, ColorParticleData data) {
        this.pos = pos;
        this.given = given;
        this.data = data;
    }

    public static ManipulatorParticlePacket decode(FriendlyByteBuf buf){
        return new ManipulatorParticlePacket(buf.readBlockPos(), buf.readInt(), ColorParticleData.create(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat()).build());
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
        buf.writeInt(given);

        buf.writeFloat(data.r1);
        buf.writeFloat(data.g1);
        buf.writeFloat(data.b1);
        buf.writeFloat(data.r2);
        buf.writeFloat(data.g2);
        buf.writeFloat(data.b2);
    }

    public static void handle(ManipulatorParticlePacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                for(int a = 0; a < msg.given; a++){
                    double angle = (a / (double)msg.given) * (2 * Math.PI);
                    double x = Math.cos(angle) * 1;
                    double z = Math.sin(angle) * 1;

                    ParticleBuilder.create(TridotParticles.WISP)
                    .setColorData(msg.data)
                    .setTransparencyData(GenericParticleData.create(0.5f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.1f, 0).build())
                    .setLifetime(35)
                    .spawn(pLevel, msg.pos.getX() + 0.5f + x, msg.pos.getY() + 1, msg.pos.getZ() + 0.5f + z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }
}
