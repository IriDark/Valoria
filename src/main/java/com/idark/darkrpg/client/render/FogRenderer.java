package com.idark.darkrpg.client.render;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FogRenderer {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onFogRender(ViewportEvent.RenderFog e) {
            Player plr = Minecraft.getInstance().player;
            BlockPos pos = new BlockPos((int)plr.getX(), (int)plr.getEyeY(), (int)plr.getZ());
            BlockState state = plr.level().getBlockState(pos);
            if (!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }

            if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && state.is(ModBlocks.QUICKSAND.get())) {
                e.setCanceled(true);
                e.setNearPlaneDistance(0.0F);
                e.setFarPlaneDistance(1.5F);
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor e) {
            Player plr = Minecraft.getInstance().player;
            BlockPos pos = new BlockPos((int)plr.getX(), (int)plr.getEyeY(), (int)plr.getZ());
            BlockState state = plr.level().getBlockState(pos);
            if (!Minecraft.getInstance().options.getCameraType().isFirstPerson() && e.getCamera().getBlockAtCamera().is(ModBlocks.QUICKSAND.get())) {
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }

            if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && state.is(ModBlocks.QUICKSAND.get())){
                e.setRed(0.57f);
                e.setGreen(0.48f);
                e.setBlue(0.34f);
            }
        }
    }
}