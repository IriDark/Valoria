package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.awt.*;
import java.util.function.*;

public class BeastAttackParticlePacket{
    private final double posX, posY, posZ;
    private final int colorR, colorG, colorB;

    public BeastAttackParticlePacket(double posX, double posY, double posZ, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = color.getRed();
        this.colorG = color.getGreen();
        this.colorB = color.getBlue();
    }

    public static BeastAttackParticlePacket decode(FriendlyByteBuf buf) {
        return new BeastAttackParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), new Color(buf.readInt(), buf.readInt(), buf.readInt()));
    }

    public static void handle(BeastAttackParticlePacket msg, Supplier<Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                ParticleBuilder.create(ParticleRegistry.SMOKE)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(new Color(msg.colorR, msg.colorG, msg.colorB), new Color(msg.colorR, msg.colorG, msg.colorB).darker()).setEasing(Easing.BOUNCE_IN).build())
                .setTransparencyData(GenericParticleData.create().setRandomValue(1, 0.0f).setEasing(Easing.CUBIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.7f, 0.4f, 0f).build())
                .setLifetime(95 + level.random.nextInt(100))
                .randomVelocity(0.05, 0.15, 0.05)
                .randomOffset(0.025f)
                .spawn(level, msg.posX, msg.posY, msg.posZ);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}
