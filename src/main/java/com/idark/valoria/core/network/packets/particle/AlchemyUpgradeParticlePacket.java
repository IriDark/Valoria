package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.NetworkEvent.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class AlchemyUpgradeParticlePacket{
    private final double posX, posY, posZ;
    private final int tier;
    public AlchemyUpgradeParticlePacket(int tier, double posX, double posY, double posZ){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.tier = tier;
    }

    public static AlchemyUpgradeParticlePacket decode(FriendlyByteBuf buf){
        return new AlchemyUpgradeParticlePacket(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(AlchemyUpgradeParticlePacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                ColorParticleData data;
                if(msg.tier == 2) {
                    data = ColorParticleData.create(Pal.vividPink, Pal.darkRed).setEasing(Interp.bounceOut).build();
                } else if(msg.tier == 3) {
                    data = ColorParticleData.create().setRandomColor().setEasing(Interp.bounceOut).build();
                }  else if(msg.tier == 4){
                    data = ColorParticleData.create(Pal.amethyst, Pal.americanViolet).setEasing(Interp.bounceOut).build();
                } else {
                    data = ColorParticleData.create().setRandomColor().setEasing(Interp.bounceOut).build();
                }

                Level level = Valoria.proxy.getLevel();
                ParticleBuilder.create(TridotParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setScaleData(GenericParticleData.create(0.0725f, 0.025f, 0).setEasing(Interp.bounce).build())
                .setLifetime(15)
                .setColorData(data)
                .randomVelocity(1, 1, 1)
                .setGravity(0.25f)
                .setHasPhysics(false)
                .repeat(level, msg.posX, msg.posY, msg.posZ, 64);
            });
        }

        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeInt(tier);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}
