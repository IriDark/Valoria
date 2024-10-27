package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;

import java.util.function.*;

public class CystSummonParticlePacket{
    private final int id;
    private final BlockPos pos;
    public CystSummonParticlePacket(int id, BlockPos pos) {
        this.id = id;
        this.pos = pos;
    }

    public static CystSummonParticlePacket decode(FriendlyByteBuf buf) {
        return new CystSummonParticlePacket(buf.readInt(), buf.readBlockPos());
    }

    public static void handle(CystSummonParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                for(int i = 0; i < 8; i++){
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(Pal.kiwi.darker(), Pal.mindaro).build())
                    .setScaleData(GenericParticleData.create(0.045f, 0.075f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setLifetime(65)
                    .setGravity(0.0125f)
                    .flatRandomVelocity(0.025, new ArcRandom().randomValueUpTo(0.055), 0.025)
                    .spawn(pLevel, msg.pos.getCenter().x, msg.pos.getCenter().y + 0.2, msg.pos.getCenter().z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }
}
