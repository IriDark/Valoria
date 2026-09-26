package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.toast.*;
import com.idark.valoria.core.config.*;
import net.minecraft.client.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

public class PageToastPacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<PageToastPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("page_toast_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PageToastPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> PageToastPacket.encode(msg, buf), PageToastPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final Item stack;
    private final UUID uuid;
    private final boolean unlock;

    public PageToastPacket(UUID uuid, Item stack, boolean pUnlock){
        this.uuid = uuid;
        this.stack = stack;
        this.unlock = pUnlock;
    }

    public PageToastPacket(Player entity, Item stack, boolean pUnlock){
        this.uuid = entity.getUUID();
        this.stack = stack;
        this.unlock = pUnlock;
    }

    public static void encode(PageToastPacket object, RegistryFriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        ByteBufCodecs.registry(Registries.ITEM).encode(buffer, object.stack);
        buffer.writeBoolean(object.unlock);
    }

    public static PageToastPacket decode(RegistryFriendlyByteBuf buffer){
        return new PageToastPacket(buffer.readUUID(), ByteBufCodecs.registry(Registries.ITEM).decode(buffer), buffer.readBoolean());
    }

    public static void handle(PageToastPacket packet, IPayloadContext ctx){
        ctx.enqueueWork(() -> {

            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null){
                if(packet.unlock){
                    player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.5f, 1);
                    player.playSound(SoundEvents.BOOK_PAGE_TURN, 1f, 1);
                }

                toast(packet);
            }
        });

    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(PageToastPacket packet){
        if (ClientConfig.SHOW_TOASTS.get()) {
            PageToast.addOrUpdate(Minecraft.getInstance().getToasts(), packet.stack, packet.unlock);
        }
    }
}