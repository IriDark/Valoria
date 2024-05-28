package com.idark.valoria.client.gui.screen;

import com.idark.valoria.registries.item.types.curio.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.platform.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;
import top.theillusivec4.curios.api.type.inventory.*;

import java.util.*;

public class JewelryBagScreen extends Screen{
    public List<ItemStack> trinkets = new ArrayList<>();
    public ItemStack selectedItem;
    public float mouseAngleI = 0;
    public float hoveramount = 0;
    public boolean hover = true;

    public JewelryBagScreen(Component titleIn){
        super(titleIn);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc(){
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        Player player = minecraft.player;
        selectedItem = getSelectedItem(mouseX, mouseY);
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        float offset = Math.min((float)this.width / 2 * 0.7f,  (float)this.height / 2 * 0.7f);
        if (mouseDistance > (offset * hoveramount)) {
            mouseDistance = (offset * hoveramount);
        }

        mouseAngleI = mouseDistance;
        if((selectedItem != null)){
            CuriosApi.getCurio(selectedItem).ifPresent(
            curio -> CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for(Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()){
                    IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                    for(int i = 0; i < stackHandler.getSlots(); i++){
                        NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                        SlotContext slotContext = new SlotContext(entry.getKey(), player, i, false, renderStates.size() > i && renderStates.get(i));
                        if(stackHandler.isItemValid(i, selectedItem) && curio.canEquipFromUse(slotContext)){
                            ItemStack present = stackHandler.getStackInSlot(i);
                            if(present.isEmpty()){
                                stackHandler.setStackInSlot(i, selectedItem.copy());
                                curio.onEquipFromUse(slotContext);
                                if(!player.isCreative()){
                                    int count = selectedItem.getCount();
                                    selectedItem.shrink(count);
                                }

                                this.onClose();
                            }
                        }
                    }
                }
            }));
        }

        return true;
    }


    public List<ItemStack> getTrinkets(){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> true);
        for(SlotResult slot : curioSlots){
            ICurioItem curio = (ICurioItem)slot.stack().getItem();
            if(slot.stack() != null && curio.canEquipFromUse(slot.slotContext(), slot.stack())){
                items.add(slot.stack());
            }
        }

        ArrayList<ItemStack> crystals = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof ICurioItem) {
                crystals.add(stack);
            }
        }

        return crystals;
    }

    public ItemStack getSelectedItem(double X, double Y) {
        return getSelectedItem(getTrinkets(), X, Y);
    }

    public ItemStack getSelectedItem(List<ItemStack> pSelected, double X, double Y) {
        double step = (float) 360 / pSelected.size();
        double x = width / 2;
        double y = height / 2;
        double angle =  Math.toDegrees(Math.atan2(Y-y,X-x));
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= pSelected.size(); i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return pSelected.get(i - 1);
            }
        }
        return null;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        super.render(gui, mouseX, mouseY, partialTicks);
        if(hover && hoveramount < 1){
            hoveramount += Minecraft.getInstance().getFrameTime() / 10;
        }else if(!hover && hoveramount > 0){
            hoveramount -= Minecraft.getInstance().getFrameTime() / 5;
        }

        if(hoveramount > 1){
            hoveramount = 1;
        }

        if(!hover && hoveramount <= 0){
            minecraft.player.closeContainer();
        }

        int x = width / 2;
        int y = height / 2;
        float offset = Math.min(x * 0.7f,  y * 0.7f);
        RenderUtils.renderItemModelInGui(getOpenedBag(), x - 32, y - 32, 64, 64, 64);
        if (hover) {
            trinkets = getTrinkets();
        }

        selectedItem = getSelectedItem(trinkets, mouseX, mouseY);
        float step = (float) 360 / trinkets.size();
        float i = 0f;
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        if (mouseDistance > (offset * hoveramount)) {
            mouseDistance = (offset * hoveramount);
        }

        mouseAngleI = mouseDistance;
        for (ItemStack stack : trinkets) {
            double dst = Math.toRadians((i * step) + (step / 2));
            int X = (int) (Math.cos(dst) * (offset * Math.sin(Math.toRadians(90 * hoveramount))));
            int Y = (int) (Math.sin(dst) * (offset * Math.sin(Math.toRadians(90 * hoveramount))));
            if (stack == selectedItem && mouseDistance > 35) {
                RenderUtils.renderItemModelInGui(stack, x + X - 24, y + Y - 24, 48, 48, 48);
            } else {
                RenderUtils.renderItemModelInGui(stack, x + X - 16, y + Y - 16, 32, 32, 32);
            }

            i = i + 1F;
        }

        if (selectedItem != null && mouseDistance > 35) {
            gui.renderTooltip(Minecraft.getInstance().font, selectedItem, mouseX, mouseY);
        }

        if (mouseDistance <= 35) {
            gui.renderTooltip(Minecraft.getInstance().font, getOpenedBag(), mouseX, mouseY);
        }
    }

    public float getMouseDistance(double X, double Y) {
        double x = width / 2;
        double y = height / 2;

        return (float) Math.sqrt(Math.pow(x - X, 2) + Math.pow(y - Y, 2));
    }

    public ItemStack getOpenedBag(){
        Player player = minecraft.player;
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.stack().getItem() instanceof JewelryBagItem){
                return slot.stack();
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers){
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)){
            this.onClose();
            return true;
        }

        return (super.keyPressed(keyCode, scanCode, modifiers));
    }
}