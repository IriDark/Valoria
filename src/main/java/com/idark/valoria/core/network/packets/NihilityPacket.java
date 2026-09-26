package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.network.handling.*;

import javax.annotation.*;

public class NihilityPacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<NihilityPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("nihility_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NihilityPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> NihilityPacket.encode(msg, buf), NihilityPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final float max;
    private final float nihilityLevel;

    public NihilityPacket(float max, float amount){
        this.max = max;
        this.nihilityLevel = amount;
    }

    public NihilityPacket(INihilityLevel nihility, @Nullable LivingEntity entity) {
        this.max = nihility.getMaxAmount(entity);
        this.nihilityLevel = nihility.getAmount();
    }

    public static void encode(NihilityPacket object, FriendlyByteBuf buffer){
        buffer.writeFloat(object.max);
        buffer.writeFloat(object.nihilityLevel);
    }

    public static NihilityPacket decode(FriendlyByteBuf buffer){
        return new NihilityPacket(buffer.readFloat(), buffer.readFloat());
    }

    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = Valoria.proxy.getPlayer();
            if(player == null) return;
            INihilityLevel.of(player).ifPresent(nihility -> {
                nihility.setMaxAmount(this.max);
                nihility.setAmount(this.nihilityLevel);
            });
        });

    }
}