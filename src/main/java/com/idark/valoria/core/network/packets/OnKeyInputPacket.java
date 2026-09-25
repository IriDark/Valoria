package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class OnKeyInputPacket extends RateLimitedPacket{
    private final int event;
    private final String slotId;
    private final int slotIndex;

    public OnKeyInputPacket(int event){
        this.event = event;
        this.slotId = "";
        this.slotIndex = -1;
    }

    public OnKeyInputPacket(int event, String slotId, int slotIndex){
        this.event = event;
        this.slotId = slotId;
        this.slotIndex = slotIndex;
    }

    public static void encode(OnKeyInputPacket msg, FriendlyByteBuf buffer){
        buffer.writeVarInt(msg.event);
        buffer.writeUtf(msg.slotId);
        buffer.writeVarInt(msg.slotIndex);
    }

    public static OnKeyInputPacket decode(FriendlyByteBuf buffer){
        return new OnKeyInputPacket(buffer.readVarInt(), buffer.readUtf(), buffer.readVarInt());
    }

    public void execute(ServerPlayer player){
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            if (this.slotId != null && !this.slotId.isEmpty() && this.slotIndex >= 0) {
                var stackHandler = handler.getCurios().get(this.slotId);
                if(stackHandler != null){
                    if (this.slotIndex < stackHandler.getSlots()) {
                        ItemStack serverStack = stackHandler.getStacks().getStackInSlot(this.slotIndex);
                        if (!serverStack.isEmpty() && serverStack.getItem() instanceof AbilityInputListener listener) {
                            for (CurioAbility ability : listener.getCurioAbilities(serverStack)) {
                                if (ability.event() == this.event) {
                                    ability.action().accept(player, serverStack);
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                handler.getCurios().forEach((id, stackHandler) -> {
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        ItemStack serverStack = stackHandler.getStacks().getStackInSlot(i);
                        if (!serverStack.isEmpty() && serverStack.getItem() instanceof AbilityInputListener listener) {
                            for (CurioAbility ability : listener.getCurioAbilities(serverStack)) {
                                if (ability.event() == this.event) {
                                    ability.action().accept(player, serverStack);
                                }
                            }
                        }
                    }
                });
            }
        });
    }
}