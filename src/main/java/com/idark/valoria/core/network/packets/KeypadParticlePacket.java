package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.util.*;
import java.util.function.*;

public class KeypadParticlePacket{
    private final double posX;
    private final double posY;
    private final double posZ;
    private final double targetPosX;
    private final double targetPosY;
    private final double targetPosZ;

    public KeypadParticlePacket(double posX, double posY, double posZ, double targetPosX, double targetPosY, double targetPosZ){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.targetPosX = targetPosX;
        this.targetPosY = targetPosY;
        this.targetPosZ = targetPosZ;
    }

    public static KeypadParticlePacket decode(FriendlyByteBuf buf){
        return new KeypadParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(KeypadParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getWorld();
                Random rand = new Random();
                for(int a = 0; a < 3; a++){
                    Vec3 position = new Vec3(msg.posX + (rand.nextDouble() * 1.25), msg.posY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.posZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    Vec3 targetPosition = new Vec3(msg.targetPosX + (rand.nextDouble() * 1.25), msg.targetPosY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.targetPosZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    ParticleEffects.transformParticle(pLevel, position, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build()).spawnParticles();
                    ParticleEffects.transformParticle(pLevel, targetPosition, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build()).spawnParticles();
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeDouble(targetPosX);
        buf.writeDouble(targetPosY);
        buf.writeDouble(targetPosZ);
    }
}