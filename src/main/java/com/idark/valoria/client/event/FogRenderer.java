package com.idark.valoria.client.event;

import com.idark.valoria.registries.*;
import net.minecraft.client.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@OnlyIn(Dist.CLIENT)
public class FogRenderer{

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void onFogRender(ViewportEvent.RenderFog e){
            if(!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(BlockRegistry.QUICKSAND.get())){
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }

            if(Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(BlockRegistry.QUICKSAND.get())){
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor e){
            if(!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(BlockRegistry.QUICKSAND.get())){
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }

            if(Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(BlockRegistry.QUICKSAND.get())){
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }
        }
    }
}