package com.idark.valoria.client.event;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.level.LevelGen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
public class FogRenderer{

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void onFogRender(ViewportEvent.RenderFog e){
            Entity entity = e.getCamera().getEntity();

            //was planned as day night cycle, however day was planned a bit lighter than overall Valoria env without fog, while night's will be dark and foggy (dredge reference omg)
            if(entity.level().dimension() == LevelGen.VALORIA_KEY){
                long time = entity.level().getDayTime() % 24000;
                float startFadeTime = 8000;
                float endFadeTime = 22000;

                float nearPlaneDefault = 25.0F;
                float nearPlaneMin = 0.0F;

                float farPlaneDefault = 165.5F;
                float farPlaneMin = 45.5F;

                float nearPlane = nearPlaneDefault;
                float farPlane = farPlaneDefault;
                if(time > startFadeTime && time <= endFadeTime){
                    float fadeFactor = (time - startFadeTime) / (endFadeTime - startFadeTime);
                    nearPlane = Mth.lerp(fadeFactor, nearPlaneDefault, nearPlaneMin);
                    farPlane = Mth.lerp(fadeFactor, farPlaneDefault, farPlaneMin);
                }else if(time > 20000){
                    float fadeFactor = (time - endFadeTime) / (24000 - endFadeTime);
                    nearPlane = Mth.lerp(fadeFactor, nearPlaneMin, nearPlaneDefault);
                    farPlane = Mth.lerp(fadeFactor, farPlaneMin, farPlaneDefault);
                }

                if(time > 9000){
                    e.setCanceled(true);
                    e.setNearPlaneDistance(nearPlane);
                    e.setFarPlaneDistance(farPlane);
                }
            }

            if(e.getCamera().getBlockAtCamera().is(BlockRegistry.quicksand.get())){
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor e){
            if(e.getCamera().getBlockAtCamera().is(BlockRegistry.quicksand.get())){
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }
        }
    }
}