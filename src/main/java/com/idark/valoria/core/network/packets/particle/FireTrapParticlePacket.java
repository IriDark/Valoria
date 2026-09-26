package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

public class FireTrapParticlePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<FireTrapParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("fire_trap_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FireTrapParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), FireTrapParticlePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }

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

    public static void handle(FireTrapParticlePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                for(int i = 0; i < 20; i++){
                    Col color = new Col(msg.colorR, msg.colorG, msg.colorB);
                    Col colorTo = new Col(msg.colorToR, msg.colorToG, msg.colorToB);
                    Vec3 pos = new Vec3(msg.posX, msg.posY + 1.2f, msg.posZ);
                    ParticleEffects.fireParticles(level, pos, ColorParticleData.create(color, colorTo).build());
                    ParticleEffects.smokeParticles(level, pos, ColorParticleData.create(Col.black, Pal.smoke).build());
                    level.addParticle(ParticleTypes.LAVA, msg.posX + 0.5, msg.posY + 1.5, msg.posZ + 0.5, 0.0D, 0.0D, 0.0D);
                }

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
