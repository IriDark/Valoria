package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class ManipulatorEmptyParticlePacket{

    private final float posX;
    private final float posY;
    private final float posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final int colorR, colorG, colorB;

    public ManipulatorEmptyParticlePacket(float posX, float posY, float posZ, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static ManipulatorEmptyParticlePacket decode(FriendlyByteBuf buf){
        return new ManipulatorEmptyParticlePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ManipulatorEmptyParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                double pitch = ((90) * Math.PI) / 180;
                float pRadius = 0.25f;
                for(int i = 0; i < 360; i += 10){
                    double yaw = ((i + 90) * Math.PI) / 180;
                    double X = Math.sin(pitch) * Math.cos(yaw) * pRadius * 0.75F, Y = Math.cos(pitch) * pRadius * 0.75F, Z = Math.sin(pitch) * Math.sin(yaw) * pRadius * 0.75F;
                    Vec3 particlePos = new Vec3(msg.posX + X, msg.posY + Y + ((Math.random() - 0.5D) * 0.2F), msg.posZ + Z);
                    Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                    ParticleBuilder.create(TridotParticles.WISP)
                            .setColorData(ColorParticleData.create(color, Col.black).build())
                            .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.025f, 0).build())
                            .setLifetime(4)
                            .setVelocity(0, 0.1f, 0)
                            .spawn(pLevel, particlePos.x, particlePos.y, particlePos.z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }
}