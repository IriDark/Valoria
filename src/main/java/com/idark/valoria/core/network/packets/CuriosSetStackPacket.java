package com.idark.valoria.core.network.packets;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.function.Supplier;

public class CuriosSetStackPacket{
    private final ItemStack stack;

    public CuriosSetStackPacket(ItemStack stack){
        this.stack = stack.copy();
    }

    public static CuriosSetStackPacket decode(FriendlyByteBuf buf){
        return new CuriosSetStackPacket(buf.readItem());
    }

    public static void handle(CuriosSetStackPacket msg, Supplier<Context> ctx){
        if(!ctx.get().getDirection().getReceptionSide().isServer()) return;
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;
            CuriosApi.getCurio(msg.stack).ifPresent(curio -> CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                for(var entry : handler.getCurios().entrySet()){
                    NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                    IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                    for(int i = 0; i < stackHandler.getSlots(); i++){ //process empty slots
                        SlotContext slotContext = new SlotContext(entry.getKey(), player, i, false, renderStates.size() > i && renderStates.get(i));
                        for(ItemStack toEquip : player.getInventory().items){
                            if(!toEquip.equals(msg.stack, false)) continue;
                            if(stackHandler.getStackInSlot(i).isEmpty() && stackHandler.isItemValid(i, msg.stack) && curio.canEquip(slotContext)){
                                setCurio(curio, toEquip, stackHandler, i, slotContext, player);
                                return;
                            }
                        }
                    }

                    for(int i = 0; i < stackHandler.getSlots(); i++){
                        SlotContext slotContext = new SlotContext(entry.getKey(), player, i, false, renderStates.size() > i && renderStates.get(i));
                        ItemStack currentCurioStack = stackHandler.getStackInSlot(i);
                        for(ItemStack toEquip : player.getInventory().items){
                            if(!toEquip.equals(msg.stack, false)) continue;
                            if(!currentCurioStack.isEmpty() && !toEquip.getOrCreateTag().toString().equals(msg.stack.getOrCreateTag().toString())) continue;
                            if(!stackHandler.isItemValid(i, msg.stack) || !curio.canEquip(slotContext)) continue;
                            equipCurio(curio, toEquip, currentCurioStack, slotContext, player, stackHandler, i);
                            return;
                        }
                    }
                }
            }));
        });

        ctx.get().setPacketHandled(true);
    }

    private static void equipCurio(ICurio curio, ItemStack toEquip, ItemStack currentCurioStack, SlotContext slotContext, ServerPlayer player, IDynamicStackHandler stackHandler, int i){
        if(!currentCurioStack.isEmpty()){
            ICurioItem currentCurio = (ICurioItem)currentCurioStack.getItem();
            if(currentCurio.canUnequip(slotContext, currentCurioStack)){
                player.getInventory().setItem(player.getInventory().findSlotMatchingItem(toEquip), currentCurioStack);
                currentCurio.onUnequip(slotContext, toEquip, currentCurioStack);
                stackHandler.extractItem(i, currentCurioStack.getCount(), false);
                setCurio(curio, toEquip, stackHandler, i, slotContext, player);
            }
        }
    }

    private static void setCurio(ICurio curio, ItemStack item, IDynamicStackHandler stackHandler, int i, SlotContext slotContext, ServerPlayer player){
        stackHandler.insertItem(i, item, false);
        curio.onEquipFromUse(slotContext);
        player.getInventory().removeItem(item);
    }

    public static void encode(CuriosSetStackPacket msg, FriendlyByteBuf buffer){
        buffer.writeItem(msg.stack);
    }
}