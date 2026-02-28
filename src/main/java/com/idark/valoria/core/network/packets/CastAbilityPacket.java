package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.ability.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;

public class CastAbilityPacket extends RateLimitedPacket{
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
        CastType type = CastType.values()[this.event];
        AbilityHelper.tryCast(player, stack, type);
    }
}