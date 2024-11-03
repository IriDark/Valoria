package com.idark.valoria.client.ui.bossbars;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;

public class ServerBossBarEvent extends ServerBossEvent {
    private String id;
    public ServerBossBarEvent(Component component, String id) {
        super(component, BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
        this.id = id;
    }

    public void setId(String id) {
        if (id != this.id) {
            this.id = id;
            PacketHandler.sendToAll(new UpdateBossbarPacket(this.getId(), id));
        }
    }

    public void addPlayer(ServerPlayer serverPlayer) {
        PacketHandler.sendNonLocal(new UpdateBossbarPacket(this.getId(), id), serverPlayer);
        super.addPlayer(serverPlayer);
    }

    public void removePlayer(ServerPlayer serverPlayer) {
        PacketHandler.sendNonLocal(new UpdateBossbarPacket(this.getId(), "empty"), serverPlayer);
        super.removePlayer(serverPlayer);
    }
}