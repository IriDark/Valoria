package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;
import pro.komaru.tridot.core.math.*;

import java.util.function.*;

public class MinionSummonParticlePacket{
    private final int id;
    private final BlockPos pos;

    public MinionSummonParticlePacket(int id, BlockPos pos){
        this.id = id;
        this.pos = pos;
    }

    public static MinionSummonParticlePacket decode(FriendlyByteBuf buf){
        return new MinionSummonParticlePacket(buf.readInt(), buf.readBlockPos());
    }

    public static void handle(MinionSummonParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                final Consumer<GenericParticle> blockTarget = p -> {
                    Vec3 entityPos = pLevel.getEntity(msg.id).position();
                    if(entityPos == null) return;
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

                ParticleBuilder.create(TridotParticles.DOT)
                        .setColorData(ColorParticleData.create(Pal.vividGreen, Pal.amethyst).build())
                        .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Interp.bounce).build())
                        .setScaleData(GenericParticleData.create(0.045f, 0.075f, 0).setEasing(Interp.circleOut).build())
                        .addTickActor(blockTarget)
                        .setLifetime(85)
                        .randomVelocity(0.15f)
                        .disablePhysics()
                        .repeat(pLevel, msg.pos.getX(), msg.pos.getY(), msg.pos.getZ(), 6);
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }
}
