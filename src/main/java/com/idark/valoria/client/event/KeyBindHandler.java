package com.idark.valoria.client.event;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import top.theillusivec4.curios.api.*;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler{
    private KeyBindHandler(){}

    @SubscribeEvent
    public static void onInput(InputEvent event){
        if(ValoriaClient.BAG_MENU_KEY.isDown()){
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if(!CuriosApi.getCuriosHelper().findCurios(player, (i) -> i.getItem() instanceof JewelryBagItem).isEmpty()){
                mc.setScreen(new JewelryBagScreen(Component.empty()));
            }
        }
    }
}