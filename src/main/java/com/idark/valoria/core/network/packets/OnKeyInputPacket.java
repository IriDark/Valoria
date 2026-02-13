package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class OnKeyInputPacket extends RateLimitedPacket{
    private final int event;
    public OnKeyInputPacket(int event){
        this.event = event;
    }

    public static void encode(OnKeyInputPacket msg, FriendlyByteBuf buffer){
        buffer.writeVarInt(msg.event);
    }

    public static OnKeyInputPacket decode(FriendlyByteBuf buffer){
        return new OnKeyInputPacket(buffer.readVarInt());
    }

    public void execute(ServerPlayer player){
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            handler.getCurios().forEach((id, stackHandler) -> {
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack serverStack = stackHandler.getStacks().getStackInSlot(i);
                    if (!serverStack.isEmpty() && serverStack.getItem() instanceof InputListener listener) {
                        listener.onInput(player, serverStack, this.event);
                    }
                }
            });
        });
    }
}