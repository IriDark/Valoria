package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

public class UnlockableUpdatePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<UnlockableUpdatePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("unlockable_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UnlockableUpdatePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> encode(msg, buf), UnlockableUpdatePacket::decode);

    UUID uuid;
    CompoundTag tag;

    public UnlockableUpdatePacket(UUID uuid, CompoundTag tag){
        this.uuid = uuid;
        this.tag = tag;
    }

    public UnlockableUpdatePacket(Player entity){
        this.uuid = entity.getUUID();
        this.tag = new CompoundTag();
        IUnlockable.of(entity).ifPresent((k) -> {
            if(k instanceof UnlockableProvider provider){
                this.tag = provider.serializeNBT(entity.registryAccess());
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }

    public static void encode(UnlockableUpdatePacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static UnlockableUpdatePacket decode(FriendlyByteBuf buffer){
        return new UnlockableUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(UnlockableUpdatePacket packet, IPayloadContext ctx){
        ctx.enqueueWork(() -> {
            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null && packet.tag != null){
                IUnlockable.of(player).ifPresent((k) -> {
                    if(k instanceof UnlockableProvider provider){
                        provider.deserializeNBT(player.registryAccess(), packet.tag);
                    }
                });
            }
        });
    }
}