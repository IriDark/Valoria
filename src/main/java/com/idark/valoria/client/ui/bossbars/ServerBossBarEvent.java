package com.idark.valoria.client.ui.bossbars;

import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.UpdateBossbarPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class ServerBossBarEvent extends ServerBossEvent{
    private String id;
    private SoundEvent bossMusic;

    public ServerBossBarEvent(Component component, String id, SoundEvent bossMusic){
        super(component, BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
        this.id = id;
        this.bossMusic = bossMusic;
    }

    public ServerBossBarEvent(Component component, String id){
        super(component, BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
        this.id = id;
        this.bossMusic = SoundEvents.EMPTY;
    }

    public void setId(String id){
        if(id != this.id){
            this.id = id;
            PacketHandler.sendToAll(new UpdateBossbarPacket(this.getId(), id, bossMusic));
        }
    }

    public void addPlayer(ServerPlayer serverPlayer){
        PacketHandler.sendNonLocal(new UpdateBossbarPacket(this.getId(), id, bossMusic), serverPlayer);
        super.addPlayer(serverPlayer);
    }

    public void removePlayer(ServerPlayer serverPlayer){
        PacketHandler.sendNonLocal(new UpdateBossbarPacket(this.getId(), "empty", bossMusic), serverPlayer);
        super.removePlayer(serverPlayer);
    }
}