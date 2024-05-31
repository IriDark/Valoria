package com.idark.valoria.core.network.packets;

import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraftforge.network.NetworkEvent.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.inventory.*;

import java.util.*;
import java.util.function.*;

public class CuriosSetStackPacket{

    private ItemStack stack;
    public CuriosSetStackPacket(ItemStack stack){
        this.stack = stack.copy();
    }

    public static CuriosSetStackPacket decode(FriendlyByteBuf buf){
        return new CuriosSetStackPacket(buf.readItem());
    }

    public static void handle(CuriosSetStackPacket msg, Supplier<Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isServer()){
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();

                CuriosApi.getCurio(msg.stack).ifPresent(
                curio -> CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                    Map<String, ICurioStacksHandler> curios = handler.getCurios();
                    for(Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()){
                        IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                        for(int i = 0; i < stackHandler.getSlots(); i++){
                            NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                            SlotContext slotContext = new SlotContext(entry.getKey(), player, i, false, renderStates.size() > i && renderStates.get(i));
                            if(stackHandler.isItemValid(i, msg.stack) && curio.canEquipFromUse(slotContext)){
                                ItemStack present = stackHandler.getStackInSlot(i);
                                if(present.isEmpty()){
                                    stackHandler.setStackInSlot(i, msg.stack);
                                    curio.onEquipFromUse(slotContext);
                                    if(!player.isCreative()){
                                        int count = msg.stack.getCount();
                                        msg.stack.shrink(count);
                                    }
                                }
                            }
                        }
                    }
                }));
            });

            ctx.get().setPacketHandled(true);
        }
    }

    public static void encode(CuriosSetStackPacket msg, FriendlyByteBuf buffer){
        buffer.writeItem(msg.stack);
    }
}