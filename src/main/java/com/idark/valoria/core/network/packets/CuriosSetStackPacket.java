package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.network.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;
import top.theillusivec4.curios.api.type.inventory.*;

public class CuriosSetStackPacket extends RateLimitedPacket{
    private final ItemStack stack;

    public CuriosSetStackPacket(ItemStack stack){
        this.stack = stack.copy();
    }

    public static CuriosSetStackPacket decode(FriendlyByteBuf buf){
        return new CuriosSetStackPacket(buf.readItem());
    }

    public void execute(ServerPlayer player){
        ItemStack toEquip = ItemStack.EMPTY;
        for(ItemStack item : player.getInventory().items){
            if(!item.isEmpty() && item.equals(this.stack, false)){
                toEquip = item;
                break;
            }
        }

        if(toEquip.isEmpty()) return;
        final ItemStack finalToEquip = toEquip;
        CuriosApi.getCurio(finalToEquip).ifPresent(curio -> CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            if(tryEquip(handler, finalToEquip, curio, player, true)) return;
            tryEquip(handler, finalToEquip, curio, player, false);
        }));
    }

    private boolean tryEquip(ICuriosItemHandler handler, ItemStack toEquip, ICurio curio, ServerPlayer player, boolean onlyEmpty) {
        for (var entry : handler.getCurios().entrySet()) {
            NonNullList<Boolean> renderStates = entry.getValue().getRenders();
            IDynamicStackHandler stackHandler = entry.getValue().getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack currentStack = stackHandler.getStackInSlot(i);
                if (onlyEmpty && !currentStack.isEmpty()) continue;

                SlotContext slotContext = new SlotContext(entry.getKey(), player, i, false, renderStates.size() > i && renderStates.get(i));
                if (stackHandler.isItemValid(i, toEquip) && curio.canEquip(slotContext)) {
                    return tryEquipOrReplace(toEquip, curio, player, currentStack, slotContext, stackHandler, i);
                }
            }
        }

        return false;
    }

    private boolean tryEquipOrReplace(ItemStack toEquip, ICurio curio, ServerPlayer player, ItemStack currentStack, SlotContext slotContext, IDynamicStackHandler stackHandler, int i){
        if (!currentStack.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(currentStack, toEquip) && currentStack.getItem() instanceof ICurioItem currentCurioItem && currentCurioItem.canUnequip(slotContext, currentStack)) {
                doSwap(curio, currentCurioItem, toEquip, currentStack, stackHandler, i, slotContext, player);
                return true;
            }
        } else {
            doEquip(curio, toEquip, stackHandler, i, slotContext, player);
            return true;
        }

        return false;
    }

    private void doEquip(ICurio curio, ItemStack toEquip, IDynamicStackHandler stackHandler, int i, SlotContext ctx, ServerPlayer player) {
        stackHandler.insertItem(i, toEquip, false);
        curio.onEquipFromUse(ctx);
        player.getInventory().removeItem(toEquip);
    }

    private void doSwap(ICurio curio, ICurioItem oldItemNode, ItemStack toEquip, ItemStack oldStack, IDynamicStackHandler stackHandler, int i, SlotContext ctx, ServerPlayer player) {
        int invSlot = player.getInventory().findSlotMatchingItem(toEquip);
        player.getInventory().setItem(invSlot, oldStack);
        oldItemNode.onUnequip(ctx, toEquip, oldStack);
        stackHandler.extractItem(i, toEquip.getCount(), false);
        doEquip(curio, toEquip, stackHandler, i, ctx, player);
    }

    public static void encode(CuriosSetStackPacket msg, FriendlyByteBuf buffer){
        buffer.writeItem(msg.stack);
    }
}