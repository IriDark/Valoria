package com.idark.valoria.core.network.packets;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.ParticleEffects;
import com.idark.valoria.util.Pal;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class ManipulatorCraftParticlePacket {

    private final double posX;
    private final double posY;
    private final double posZ;
    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final int colorR, colorG, colorB;

    public ManipulatorCraftParticlePacket(double posX, double posY, double posZ, float posToX, float posToY, float posToZ, int colorR, int colorG, int colorB) {
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

    public static ManipulatorCraftParticlePacket decode(FriendlyByteBuf buf) {
        return new ManipulatorCraftParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(ManipulatorCraftParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                Vec3 particlePos = new Vec3(msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.85f);
                ParticleEffects.particles(pLevel, particlePos, ColorParticleData.create(Pal.infernalBright, Color.black).build()).getBuilder().setMotion(-0.05f, 0.025f, -0.05f).setLifetime(8).setScaleData(GenericParticleData.create(0.025f, 0.1f).build()).spawn(pLevel, particlePos.x, particlePos.y, particlePos.z);

                Vec3 particlePos1 = new Vec3(msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.15f);
                ParticleEffects.particles(pLevel, particlePos1, ColorParticleData.create(Pal.nature, Color.black).build()).getBuilder().setMotion(0.05f, 0.025f, 0.05f).setLifetime(8).setScaleData(GenericParticleData.create(0.025f, 0.1f).build()).spawn(pLevel, particlePos1.x, particlePos1.y, particlePos1.z);

                Vec3 particlePos2 = new Vec3(msg.posX + 0.85f, msg.posY + 1.10f, msg.posZ + 0.15f);
                ParticleEffects.particles(pLevel, particlePos2, ColorParticleData.create(Pal.oceanic, Color.black).build()).getBuilder().setMotion(-0.05f, 0.025f, 0.05f).setLifetime(8).setScaleData(GenericParticleData.create(0.025f, 0.1f).build()).spawn(pLevel, particlePos2.x, particlePos2.y, particlePos2.z);

                Vec3 particlePos3 = new Vec3(msg.posX + 0.15f, msg.posY + 1.10f, msg.posZ + 0.85f);
                ParticleEffects.particles(pLevel, particlePos3, ColorParticleData.create(Pal.vividCyan, Color.black).build()).getBuilder().setMotion(0.05f, 0.025f, -0.05f).setLifetime(8).setScaleData(GenericParticleData.create(0.025f, 0.1f).build()).spawn(pLevel, particlePos3.x, particlePos3.y, particlePos3.z);

                Vec3 particlePos4 = new Vec3(msg.posX + 0.5f, msg.posY + 1, msg.posZ + 0.5f);
                ParticleEffects.particles(pLevel, particlePos4, ColorParticleData.create(Color.white, Color.black).build()).getBuilder().setMotion(0, 0.025f, 0).setLifetime(12).setScaleData(GenericParticleData.create(0.025f, 0.1f).build()).spawn(pLevel, particlePos4.x, particlePos4.y, particlePos4.z);
                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf) {
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