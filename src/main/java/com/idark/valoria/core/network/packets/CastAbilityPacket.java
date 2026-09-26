package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.ability.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;

public class CastAbilityPacket extends RateLimitedPacket{
    public static final CustomPacketPayload.Type<CastAbilityPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("cast_ability_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CastAbilityPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> CastAbilityPacket.encode(msg, buf), CastAbilityPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final int event;

    public CastAbilityPacket(int event) {
        this.event = event;
    }

    public static void encode(CastAbilityPacket msg, FriendlyByteBuf buffer){
        buffer.writeVarInt(msg.event);
    }

    public static CastAbilityPacket decode(FriendlyByteBuf buffer){
        return new CastAbilityPacket(buffer.readVarInt());
    }

    public void execute(ServerPlayer player){
        ItemStack stack = player.getMainHandItem();
        CastType type = CastType.fromEvent(this.event);
        AbilityHelper.tryCast(player, stack, type);
    }
}