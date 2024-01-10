package com.idark.valoria.network;

import com.idark.valoria.capability.IPage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PageUpdatePacket {
    UUID uuid;
    CompoundTag tag;

    public PageUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public PageUpdatePacket(Player entity) {
        this.uuid = entity.getUUID();
        entity.getCapability(IPage.INSTANCE, null).ifPresent((k) -> {
            this.tag = ((INBTSerializable<CompoundTag>)k).serializeNBT();
        });
    }

    public static void encode(PageUpdatePacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeNbt(object.tag);
    }

    public static PageUpdatePacket decode(FriendlyByteBuf buffer) {
        return new PageUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void handle(PageUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Minecraft.getInstance().level;
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                player.getCapability(IPage.INSTANCE, null).ifPresent((k) -> {
                    ((INBTSerializable<CompoundTag>)k).deserializeNBT(packet.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}