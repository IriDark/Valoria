package com.idark.valoria.client.event;

import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.ui.screen.JewelryBagScreen;
import com.idark.valoria.registries.item.types.curio.JewelryBagItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {
    private KeyBindHandler() {
    }

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
        for (SlotResult slot : curioSlots) {
            if (slot.stack().getItem() instanceof JewelryBagItem) {
                items.add(slot.stack());
            }
        }

        if (!items.isEmpty()) {
            mc.setScreen(new JewelryBagScreen(Component.empty()));
        }
    }
}
