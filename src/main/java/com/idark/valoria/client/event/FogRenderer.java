package com.idark.valoria.client.event;

import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.registries.world.levelgen.LevelGen;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

public class FogRenderer {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onFogRender(ViewportEvent.RenderFog e) {
            BlockState blockState = e.getCamera().getBlockAtCamera();
            if (!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }

            if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }

            if (!blockState.liquid() && Minecraft.getInstance().player.level().dimension() == LevelGen.VALORIA_KEY) {
                e.setCanceled(true);
                e.setNearPlaneDistance(0.1F);
                e.setFarPlaneDistance(35.5F);
                e.setFogShape(FogShape.CYLINDER);
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor e) {
            BlockState blockState = e.getCamera().getBlockAtCamera();
            if (!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }

            if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }

            if (!blockState.liquid() && Minecraft.getInstance().player.level().dimension() == LevelGen.VALORIA_KEY) {
                e.setRed(0.140f);
                e.setGreen(0.060f);
                e.setBlue(0.156f);
            }
        }
    }
}