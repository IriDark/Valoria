package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class UnlockableUpdatePacket {
    UUID uuid;
    CompoundTag tag;
    public UnlockableUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public UnlockableUpdatePacket(Player entity) {
        this.uuid = entity.getUUID();
        entity.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
            if (k instanceof INBTSerializable) {
                this.tag = ((INBTSerializable<CompoundTag>) k).serializeNBT();
            }
        });
    }

    public static void encode(UnlockableUpdatePacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static UnlockableUpdatePacket decode(FriendlyByteBuf buffer) {
        return new UnlockableUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(UnlockableUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                player.getCapability(IUnlockable.INSTANCE, null).ifPresent((k) -> {
                    if (k instanceof INBTSerializable) {
                        ((INBTSerializable<CompoundTag>) k).deserializeNBT(packet.tag);
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}