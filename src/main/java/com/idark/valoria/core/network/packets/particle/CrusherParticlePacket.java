package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import net.minecraft.network.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.NetworkEvent.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class CrusherParticlePacket{
    private final double posX, posY, posZ;
    private final ItemStack stack;

    public CrusherParticlePacket(double posX, double posY, double posZ, ItemStack stack){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.stack = stack;
    }

    public static CrusherParticlePacket decode(FriendlyByteBuf buf){
        return new CrusherParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readItem());
    }

    public static void handle(CrusherParticlePacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                ItemParticleOptions options = new ItemParticleOptions(TridotParticles.ITEM.get(), msg.stack);
                ParticleBuilder.create(options)
                .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                .setLifetime(30)
                .randomVelocity(0.25, 0.75, 0.25)
                .randomOffset(0.125, 0.125)
                .setGravity(0.75f)
                .repeat(level, msg.posX, msg.posY, msg.posZ, 6);

                ParticleBuilder.create(TridotParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setScaleData(GenericParticleData.create(0.00125f, 0.02f, 0).setEasing(Interp.bounce).build())
                .setLifetime(15)
                .setColorData(ColorParticleData.create(Col.white, Col.yellow).setEasing(Interp.bounce).build())
                .randomVelocity(0.125, 0.25, 0.125)
                .setHasPhysics(false)
                .repeat(level, msg.posX, msg.posY, msg.posZ, 6);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeItem(stack);
    }
}
