package com.idark.valoria.client.event;

import com.idark.valoria.*;
import com.idark.valoria.client.gui.screen.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler{

    private KeyBindHandler() {}

    @SubscribeEvent
    public static void onInput(InputEvent event) {
        if (ValoriaClient.BAG_MENU_KEY.isDown()) {
            jewelryBagMenu();
        }
    }

    public static void jewelryBagMenu() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.stack().getItem() instanceof JewelryBagItem){
                items.add(slot.stack());
            }
        }

        if (!items.isEmpty()) {
            Minecraft.getInstance().setScreen(new JewelryBagScreen(Component.empty()));
        }
    }
}
