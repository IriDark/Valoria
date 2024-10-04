package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;

import java.awt.*;
import java.util.function.*;

public class FireTrapParticlePacket {

    private final double posX, posY, posZ;
    private final float colorR, colorG, colorB;
    private final float colorToR, colorToG, colorToB;
    public FireTrapParticlePacket(double posX, double posY, double posZ, float colorR, float colorG, float colorB, float colorToR, float colorToG, float colorToB) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        this.colorToR = colorToR;
        this.colorToG = colorToG;
        this.colorToB = colorToB;
    }

    public static FireTrapParticlePacket decode(FriendlyByteBuf buf) {
        return new FireTrapParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(FireTrapParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                for (int i = 0; i < 20; i++) {
                    Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                    Color colorTo = new Color(msg.colorToR, msg.colorToG, msg.colorToB);
                    Vec3 pos = new Vec3(msg.posX, msg.posY + 1.2f, msg.posZ);
                    ParticleEffects.fireParticles(level, pos, ColorParticleData.create(color, colorTo).build());
                    ParticleEffects.smokeParticles(level, pos, ColorParticleData.create(Color.black, Pal.smoke).build());
                    level.addParticle(ParticleTypes.LAVA, msg.posX + 0.5, msg.posY + 1.5, msg.posZ + 0.5, 0.0D, 0.0D, 0.0D);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
        buf.writeFloat(colorToR);
        buf.writeFloat(colorToG);
        buf.writeFloat(colorToB);
    }
}
