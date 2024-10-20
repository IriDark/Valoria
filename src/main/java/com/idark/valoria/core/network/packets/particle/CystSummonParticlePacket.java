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
import net.minecraft.world.phys.*;
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
                final Consumer<GenericParticle> blockTarget = p -> {
                    Vec3 entityPos = pLevel.getEntity(msg.id).getPosition(0);
                    Vec3 pPos = p.getPosition();
                    double dX = entityPos.x - pPos.x();
                    double dY = entityPos.y - pPos.y();
                    double dZ = entityPos.z - pPos.z();
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                    float speed = 0.01f;
                    double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                    double y = Math.cos(pitch) * speed;
                    double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                    p.setSpeed(p.getSpeed().subtract(x, y, z));
                };

                ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(Pal.vividGreen, Pal.strongRed).build())
                .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.045f, 0.075f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(blockTarget)
                .setLifetime(85)
                .randomVelocity(0.05f)
                .disablePhysics()
                .repeat(pLevel, msg.pos.getX(), msg.pos.getY(), msg.pos.getZ(), 6);
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }
}
