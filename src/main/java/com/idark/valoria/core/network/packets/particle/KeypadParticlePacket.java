package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

public class KeypadParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<KeypadParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("keypad_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, KeypadParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), KeypadParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
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

    public static void handle(KeypadParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                var rand = Tmp.rnd;
                for(int a = 0; a < 3; a++){
                    Vec3 position = new Vec3(msg.posX + (rand.nextDouble() * 1.25), msg.posY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.posZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    Vec3 targetPosition = new Vec3(msg.targetPosX + (rand.nextDouble() * 1.25), msg.targetPosY + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), msg.targetPosZ + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    ParticleEffects.transformParticle(pLevel, position, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build());
                    ParticleEffects.transformParticle(pLevel, targetPosition, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build());
                }

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