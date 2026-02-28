package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class DashParticlePacket{
    private final UUID id;
    private final float velX, velY, velZ;
    private final int count, colorR, colorG, colorB;

    public DashParticlePacket(UUID id, int count, float velX, float velY, float velZ, int colorR, int colorG, int colorB){
        this.id = id;
        this.count = count;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public DashParticlePacket(UUID id, int count, float velX, float velY, float velZ, Color clr){
        this.id = id;
        this.count = count;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.colorR = clr.getRed();
        this.colorG = clr.getGreen();
        this.colorB = clr.getBlue();
    }

    public static DashParticlePacket decode(FriendlyByteBuf buf){
        return new DashParticlePacket(buf.readUUID(), buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(DashParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
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
                    .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(Col.white, Pal.cyan).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.bounceOut).build())
                    .setScaleData(GenericParticleData.create(2, 1, 0).setEasing(Interp.bounceOut).build())
                    .addTickActor(target)
                    .setGravity(0)
                    .setLifetime(25)
                    .repeat(level, pos.x, pos.y, pos.z, 1);

                }else{
                    Valoria.LOGGER.error("Player with UUID {}, not found", msg.id);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeInt(count);
        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}