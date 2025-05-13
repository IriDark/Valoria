package com.idark.valoria.client.ui.toast;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.toasts.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.util.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class PageToast implements Toast{
    public static PageToast instance;
    public ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/gui/toast.png");
    public boolean pUnlock;
    private long lastChanged;
    private boolean changed;
    private final List<Item> items = Lists.newArrayList();

    public PageToast(Item icon, boolean pUnlock){
        this.items.add(icon);
        this.pUnlock = pUnlock;
    }

    @Override
    public Visibility render(GuiGraphics pGuiGraphics, ToastComponent pToastComponent, long pTimeSinceLastVisible){
        if (this.changed) {
            this.lastChanged = pTimeSinceLastVisible;
            this.changed = false;
        }

        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height(), 256, 32);
        Item itemstack = this.items.get((int)((double)pTimeSinceLastVisible / Math.max(1.0D, 5000.0D * pToastComponent.getNotificationDisplayTimeMultiplier() / (double)this.items.size()) % (double)this.items.size()));

        pGuiGraphics.renderFakeItem(itemstack.getDefaultInstance(), 8, 8);
        pGuiGraphics.renderFakeItem(ItemsRegistry.codex.get().getDefaultInstance(), 156, 8);

        var component = pUnlock ? Component.translatable("codex.valoria.new_page") : Component.translatable("codex.valoria.delete_page");
        List<FormattedCharSequence> list = pToastComponent.getMinecraft().font.split(component, 125);
        int l = this.height() / 2 - list.size() * 9 / 2;
        if (list.size() == 1) {
            pGuiGraphics.drawString(pToastComponent.getMinecraft().font, list.get(0), 30, l, Col.packColor(255, 220, 200, 180), false);
        }else{
            for(FormattedCharSequence formattedcharsequence : list){
                pGuiGraphics.drawString(pToastComponent.getMinecraft().font, formattedcharsequence, 30, l, Col.packColor(255, 220, 200, 180), false);
                l += 9;
            }
        }

        return (double)(pTimeSinceLastVisible - this.lastChanged) < 5000L * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
    }

    public void reset(Item icon, boolean pUnlock) {
        this.items.add(icon);
        this.pUnlock = pUnlock;
        this.changed = true;
    }

    public static void addOrUpdate(ToastComponent pToastGui, Item icon, boolean pUnlock) {
        PageToast toast = pToastGui.getToast(PageToast.class, NO_TOKEN);
        if (toast == null) {
            pToastGui.addToast(new PageToast(icon, pUnlock));
        } else {
            toast.reset(icon, pUnlock);
        }
    }

    @Override
    public int width(){
        return 180;
    }
}
