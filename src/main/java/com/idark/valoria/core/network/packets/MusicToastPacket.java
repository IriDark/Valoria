package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.toast.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.network.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class MusicToastPacket {
    private final UUID uuid;
    public String music;
    public String author;

    public MusicToastPacket(UUID uuid, String music, String author){
        this.uuid = uuid;
        this.author = author;
        this.music = music;
    }

    public MusicToastPacket(Player entity, String music, String author){
        this.uuid = entity.getUUID();
        this.author = author;
        this.music = music;
    }

    public MusicToastPacket(Player entity, SoundEvent event){
        this.uuid = entity.getUUID();
        this.author = I18n.get(event.getLocation().toLanguageKey() + ".author");
        this.music = I18n.get(event.getLocation().toLanguageKey() + ".name");
    }

    public static void encode(MusicToastPacket object, FriendlyByteBuf buffer){
        buffer.writeUUID(object.uuid);
        buffer.writeUtf(object.music);
        buffer.writeUtf(object.author);
    }

    public static MusicToastPacket decode(FriendlyByteBuf buffer){
        return new MusicToastPacket(buffer.readUUID(), buffer.readUtf(), buffer.readUtf());
    }

    public static void handle(MusicToastPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Valoria.proxy.getLevel();
            Player player = world.getPlayerByUUID(packet.uuid);
            if(player != null){
                toast(packet);
            }
        });

        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(MusicToastPacket packet){
        Minecraft.getInstance().getToasts().addToast(new MusicToast(packet.music, packet.author));
    }
}