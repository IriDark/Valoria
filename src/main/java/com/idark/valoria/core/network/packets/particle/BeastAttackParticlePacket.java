package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.NetworkEvent.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.awt.*;
import java.util.function.*;

public class BeastAttackParticlePacket{
    private final double posX, posY, posZ;
    private final int colorR, colorG, colorB;

    public BeastAttackParticlePacket(double posX, double posY, double posZ, Color color){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = color.getRed();
        this.colorG = color.getGreen();
        this.colorB = color.getBlue();
    }

    public static BeastAttackParticlePacket decode(FriendlyByteBuf buf){
        return new BeastAttackParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), new Color(buf.readInt(), buf.readInt(), buf.readInt()));
    }

    public static void handle(BeastAttackParticlePacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                ParticleBuilder.create(ParticleRegistry.SMOKE)
                        .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                        .setColorData(ColorParticleData.create(color, color.darker()).setEasing(Interp.bounceIn).build())
                        .setTransparencyData(GenericParticleData.create().setRandomValue(1, 0.0f).setEasing(Interp.sineOut).build())
                        .setScaleData(GenericParticleData.create(0.7f, 0.4f, 0f).build())
                        .setLifetime(95 + level.random.nextInt(100))
                        .randomVelocity(0.05, 0.15, 0.05)
                        .randomOffset(0.025f)
                        .spawn(level, msg.posX, msg.posY, msg.posZ);

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}
