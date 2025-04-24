package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.toast.*;
import com.idark.valoria.core.config.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class PageToastPacket{
    private final UUID uuid;
    private final boolean unlock;

    public PageToastPacket(UUID uuid, boolean pUnlock){
        this.uuid = uuid;
        unlock = pUnlock;
    }

    public PageToastPacket(Player entity, boolean pUnlock){
        this.uuid = entity.getUUID();
        unlock = pUnlock;
    }

    public static void encode(PageToastPacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeBoolean(object.unlock);
    }

    public static PageToastPacket decode(FriendlyByteBuf buffer){
        return new PageToastPacket(buffer.readUUID(), buffer.readBoolean());
    }

    public static void handle(PageToastPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

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

        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(PageToastPacket packet){
        if (ClientConfig.SHOW_TOASTS.get()) {
            PageToast.addOrUpdate(Minecraft.getInstance().getToasts(), packet.unlock);
        }
    }
}