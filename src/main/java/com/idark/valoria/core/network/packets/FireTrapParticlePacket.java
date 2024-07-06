package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.function.*;

public class FireTrapParticlePacket{

    private final double posX, posY, posZ;
    private final int colorR, colorG, colorB;
    private final int colorToR, colorToG, colorToB;

    public FireTrapParticlePacket(double posX, double posY, double posZ, int colorR, int colorG, int colorB, int colorToR, int colorToG, int colorToB){
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

    public static FireTrapParticlePacket decode(FriendlyByteBuf buf){
        return new FireTrapParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(FireTrapParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getWorld();
                for(int i = 0; i < 20; i++){
                    Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                    Color colorTo = new Color(msg.colorToR, msg.colorToG, msg.colorToB);
                    Vec3 pos = new Vec3(msg.posX, msg.posY + 1.2f, msg.posZ);
                    ParticleEffects.fireParticles(level, pos, ColorParticleData.create(color, colorTo).build()).spawnParticles();
                    ParticleEffects.smokeParticles(level, pos, ColorParticleData.create(Color.black, Pal.smoke).build(), new WorldParticleOptions(LodestoneParticleRegistry.SMOKE_PARTICLE)).spawnParticles();
                    level.addParticle(ParticleTypes.LAVA, msg.posX + 0.5, msg.posY + 1.5, msg.posZ + 0.5, 0.0D, 0.0D, 0.0D);
                }

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
        buf.writeInt(colorToR);
        buf.writeInt(colorToG);
        buf.writeInt(colorToB);
    }
}
