package com.idark.valoria.client.ui.screen;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.render.*;
import pro.komaru.tridot.utilities.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;

public class JewelryBagScreen extends Screen{
    public List<ItemStack> trinkets = new ArrayList<>();
    public ItemStack selectedItem;
    public float mouseAngleI = 0;
    public float hoverAmount = 0;
    public boolean hover = true;

    public JewelryBagScreen(Component titleIn){
        super(titleIn);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        selectedItem = getSelectedItem(mouseX, mouseY);
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        float offset = Math.min((float)this.width / 2 * 0.7f, (float)this.height / 2 * 0.7f);
        if(mouseDistance > (offset * hoverAmount)){
            mouseDistance = (offset * hoverAmount);
        }

        mouseAngleI = mouseDistance;
        if((selectedItem != null)){
            hover = false;
            PacketHandler.sendToServer(new CuriosSetStackPacket(selectedItem));
        }

        return true;
    }


    public List<ItemStack> getTrinkets(){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = player.getInventory().items;
        ArrayList<ItemStack> curioItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof ICurioItem && stack.getItem() != ItemsRegistry.jewelryBag.get()){
                curioItems.add(stack);
            }
        }

        return curioItems;
    }

    public ItemStack getSelectedItem(double X, double Y){
        return getSelectedItem(getTrinkets(), X, Y);
    }

    public ItemStack getSelectedItem(List<ItemStack> pSelected, double X, double Y){
        int x = width / 2;
        int y = height / 2;
        double step = (float)360 / pSelected.size();
        double angle = Math.toDegrees(Math.atan2(Y - y, X - x));
        if(angle < 0D){
            angle += 360D;
        }

        for(int i = 1; i <= pSelected.size(); i += 1){
            if((((i - 1) * step) <= angle) && (((i * step)) > angle)){
                return pSelected.get(i - 1);
            }
        }

        return null;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks){
        super.render(gui, mouseX, mouseY, partialTicks);
        int x = width / 2;
        int y = height / 2;
        float offset = Math.min(x * 0.7f, y * 0.7f);
        float step = (float)360 / trinkets.size();
        float i = 0f;
        selectedItem = getSelectedItem(trinkets, mouseX, mouseY);
        if(hover && hoverAmount < 1){
            hoverAmount += Minecraft.getInstance().getDeltaFrameTime() / 8;
        }else if(!hover && hoverAmount > 0){
            hoverAmount -= Minecraft.getInstance().getDeltaFrameTime() / 4;
        }

        if(hoverAmount > 1){
            hoverAmount = 1;
        }

        if(!hover && hoverAmount <= 0){
            this.onClose();
        }

        if(hover){
            trinkets = getTrinkets();
        }

        float mouseDistance = getMouseDistance(mouseX, mouseY);
        if(mouseDistance > (offset * hoverAmount)){
            mouseDistance = (offset * hoverAmount);
        }

        float bagOffset = 32 * hoverAmount;
        float bagSize = 64 * hoverAmount;
        float trinketSize = 32 * hoverAmount;
        float trinketSizeHover = 48 * hoverAmount;
        float trinketOffset = trinketSize / 2;
        float trinketOffsetHover = trinketSizeHover / 2;
        Utils.Render.renderItemModelInGui(getOpenedBag(), x - bagOffset, y - bagOffset, bagSize, bagSize, bagSize);
        mouseAngleI = mouseDistance;
        for(ItemStack stack : trinkets){
            double dst = Math.toRadians((i * step) + (step / 2));
            int X = (int)(Math.cos(dst) * (offset * Math.sin(Math.toRadians(90 * hoverAmount))));
            int Y = (int)(Math.sin(dst) * (offset * Math.sin(Math.toRadians(90 * hoverAmount))));
            if(stack == selectedItem && mouseDistance > 45){
                Utils.Render.renderItemModelInGui(stack, x + X - trinketOffsetHover, y + Y - trinketOffsetHover, trinketSizeHover, trinketSizeHover, trinketSizeHover);
            }else{
                Utils.Render.renderItemModelInGui(stack, x + X - trinketOffset, y + Y - trinketOffset, trinketSize, trinketSize, trinketSize);
            }

            i = i + hoverAmount;
        }

        if(selectedItem != null && mouseDistance > 45){
            gui.renderTooltip(Minecraft.getInstance().font, selectedItem, mouseX, mouseY);
        }

        int k = ((DyeableLeatherItem)getOpenedBag().getItem()).getColor(getOpenedBag());
        float r = (float)(k >> 16 & 255) / 255.0F;
        float g = (float)(k >> 8 & 255) / 255.0F;
        float b = (float)(k & 255) / 255.0F;

        gui.pose().pushPose();
        gui.pose().translate(x, y, 0);
        gui.pose().mulPose(Axis.ZP.rotationDegrees((float)Math.toDegrees(Math.atan2(mouseY - y, mouseX - x) - 360) + 20));
        RenderBuilder.create().setRenderType(TridotRenderTypes.ADDITIVE)
                .setColorRaw(r, g, b).setAlpha(1f)
                .setSecondAlpha(0)
                .renderRay(gui.pose(), 1f, (height / 2 * 0.7f * hoverAmount), 50f)
                .endBatch();

        gui.pose().popPose();
    }

    public float getMouseDistance(double X, double Y){
        return (float)Math.sqrt(Math.pow(width / 2 - X, 2) + Math.pow(height / 2 - Y, 2));
    }

    @SuppressWarnings("removal")
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
}