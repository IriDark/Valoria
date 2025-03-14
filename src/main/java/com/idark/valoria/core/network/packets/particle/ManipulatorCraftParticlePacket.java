package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class ManipulatorCraftParticlePacket{

    private final double posX;
    private final double posY;
    private final double posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final int colorR, colorG, colorB;

    public ManipulatorCraftParticlePacket(double posX, double posY, double posZ, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB){
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

    public static ManipulatorCraftParticlePacket decode(FriendlyByteBuf buf){
        return new ManipulatorCraftParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ManipulatorCraftParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                Vec3 particlePos = new Vec3(msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.85f);
                ParticleBuilder.create(TridotParticles.WISP)
                        .setColorData(ColorParticleData.create(Pal.infernalBright, Col.black).build())
                        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.1f).build())
                        .setLifetime(8)
                        .setVelocity(-0.05f, 0.025f, -0.05f)
                        .spawn(pLevel, particlePos.x, particlePos.y, particlePos.z);

                Vec3 particlePos1 = new Vec3(msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.15f);
                ParticleBuilder.create(TridotParticles.WISP)
                        .setColorData(ColorParticleData.create(Pal.nature, Col.black).build())
                        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.1f).build())
                        .setLifetime(8)
                        .setVelocity(0.05f, 0.025f, 0.05f)
                        .spawn(pLevel, particlePos1.x, particlePos1.y, particlePos1.z);

                Vec3 particlePos2 = new Vec3(msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.15f);
                ParticleBuilder.create(TridotParticles.WISP)
                        .setColorData(ColorParticleData.create(Pal.oceanic, Col.black).build())
                        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.1f).build())
                        .setLifetime(8)
                        .setVelocity(-0.05f, 0.025f, 0.05f)
                        .spawn(pLevel, particlePos2.x, particlePos2.y, particlePos2.z);

                Vec3 particlePos3 = new Vec3(msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.85f);
                ParticleBuilder.create(TridotParticles.WISP)
                        .setColorData(ColorParticleData.create(Pal.vividCyan, Col.black).build())
                        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.1f).build())
                        .setLifetime(8)
                        .setVelocity(0.05f, 0.025f, -0.05f)
                        .spawn(pLevel, particlePos3.x, particlePos3.y, particlePos3.z);

                Vec3 particlePos4 = new Vec3(msg.posX + 0.5f, msg.posY + 1, msg.posZ + 0.5f);
                ParticleBuilder.create(TridotParticles.WISP)
                        .setColorData(ColorParticleData.create(Col.white, Col.black).build())
                        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.1f).build())
                        .setLifetime(12)
                        .setVelocity(0, 0.025f, 0)
                        .spawn(pLevel, particlePos4.x, particlePos4.y, particlePos4.z);
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}