package com.idark.valoria.client.cinema;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.render.screenshake.*;

@Mod.EventBusSubscriber(modid = Valoria.ID, value = Dist.CLIENT)
public class CutsceneClientEvents{

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && CutsceneManager.active) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.keyJump.isDown()) {
                CutsceneManager.skipTicks++;
                if (CutsceneManager.skipTicks >= CutsceneManager.skipThreshold) {
                    CutsceneManager.stop();
                    PacketHandler.sendToServer(new CutsceneSkippedPacket());
                    return;
                }
            } else {
                CutsceneManager.skipTicks = Math.max(0, CutsceneManager.skipTicks - 2);
            }

            CutsceneManager.ticks++;
            CutsceneManager.ticksInCurrentNode++;
            if (CutsceneManager.nodes != null && CutsceneManager.currentNodeIndex < CutsceneManager.nodes.size) {
                CutsceneNode currentNode = CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex);

                float nodeDelta = (float) CutsceneManager.ticksInCurrentNode / currentNode.duration;
                Vec3 prevPos = CutsceneManager.currentNodeIndex == 0 ? CutsceneManager.startPos : CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex - 1).pos;

                double x = currentNode.easing.apply(nodeDelta, (float) prevPos.x, (float) currentNode.pos.x);
                double y = currentNode.easing.apply(nodeDelta, (float) prevPos.y, (float) currentNode.pos.y);
                double z = currentNode.easing.apply(nodeDelta, (float) prevPos.z, (float) currentNode.pos.z);

                ArmorStand dummy = CutsceneManager.getDummy();

                dummy.xo = dummy.getX();
                dummy.yo = dummy.getY();
                dummy.zo = dummy.getZ();
                dummy.setPos(x, y, z);

                dummy.yRotO = dummy.getYRot();
                dummy.xRotO = dummy.getXRot();

                dummy.setYRot(currentNode.yaw);
                dummy.setXRot(currentNode.pitch);

                if (CutsceneManager.lastTriggeredNodeIndex != CutsceneManager.currentNodeIndex) {
                    CutsceneManager.lastTriggeredNodeIndex = CutsceneManager.currentNodeIndex;
                    if(currentNode.event != null) {
                        mc.player.playSound(currentNode.event, 1, 1);
                    }
                    if(currentNode.screenshakeInstance != null) {
                        ScreenshakeHandler.add(currentNode.screenshakeInstance);
                    }
                }

                if (CutsceneManager.ticksInCurrentNode >= currentNode.duration) {
                    CutsceneManager.ticksInCurrentNode = 0;
                    CutsceneManager.currentNodeIndex++;
                }
            }

            if (CutsceneManager.ticks >= CutsceneManager.maxTicks) {
                CutsceneManager.stop();
            }
        }
    }

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeFov event) {
        if (CutsceneManager.active && CutsceneManager.nodes != null && CutsceneManager.currentNodeIndex < CutsceneManager.nodes.size) {
            CutsceneNode currentNode = CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex);
            float partialTicks = Minecraft.getInstance().getPartialTick();
            float nodeDelta = (CutsceneManager.ticksInCurrentNode + partialTicks) / currentNode.duration;
            nodeDelta = Mth.clamp(nodeDelta, 0.0f, 1.0f);

            float prevFov = CutsceneManager.currentNodeIndex == 0 ? CutsceneManager.startFov : CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex - 1).fov;
            float finalFov = currentNode.easing.apply(nodeDelta, prevFov, currentNode.fov);

            if (ScreenshakeHandler.intensityFov >= 0) {
                finalFov += ScreenshakeHandler.randomizeOffset(ScreenshakeHandler.intensityFov);
            }

            if (ScreenshakeHandler.intensityFovNormalize != 0) {
                finalFov += ScreenshakeHandler.intensityFovNormalize;
            }

            event.setFOV(finalFov);
        }
    }

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (CutsceneManager.active && CutsceneManager.nodes != null && CutsceneManager.currentNodeIndex < CutsceneManager.nodes.size) {
            CutsceneNode currentNode = CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex);
            float partialTicks = ClientTick.partialTicks;
            float nodeDelta = (CutsceneManager.ticksInCurrentNode + partialTicks) / currentNode.duration;
            nodeDelta = Mth.clamp(nodeDelta, 0.0f, 1.0f);

            float prevPitch = CutsceneManager.currentNodeIndex == 0 ? CutsceneManager.startPitch : CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex - 1).pitch;
            float prevYaw = CutsceneManager.currentNodeIndex == 0 ? CutsceneManager.startYaw : CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex - 1).yaw;

            float wrapYaw = Mth.wrapDegrees(currentNode.yaw - prevYaw);
            float targetYaw = prevYaw + wrapYaw;

            float wrapPitch = Mth.wrapDegrees(currentNode.pitch - prevPitch);
            float targetPitch = prevPitch + wrapPitch;

            float finalPitch = currentNode.easing.apply(nodeDelta, prevPitch, targetPitch);
            float finalYaw = currentNode.easing.apply(nodeDelta, prevYaw, targetYaw);

            if (ScreenshakeHandler.intensityRotation > 0) {
                finalPitch += ScreenshakeHandler.randomizeOffset(ScreenshakeHandler.intensityRotation);
                finalYaw += ScreenshakeHandler.randomizeOffset(ScreenshakeHandler.intensityRotation);
            }

            event.setPitch(finalPitch);
            event.setYaw(finalYaw);
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (CutsceneManager.active) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiEvent.Pre event) {
        if (CutsceneManager.active) {
            event.setCanceled(true);
            Minecraft mc = Minecraft.getInstance();
            float progress = Math.min(1.0f, CutsceneManager.ticks / 60.0f);

            int screenWidth = event.getWindow().getGuiScaledWidth();
            int screenHeight = event.getWindow().getGuiScaledHeight();

            int maxBarHeight = (int) (screenHeight * 0.15f);
            int currentBarHeight = (int) (maxBarHeight * progress);
            int color = 0xFF000000;

            event.getGuiGraphics().fill(0, 0, screenWidth, currentBarHeight, color);
            event.getGuiGraphics().fill(0, screenHeight - currentBarHeight, screenWidth, screenHeight, color);
            if (CutsceneManager.skipTicks > 0) {
                float skipPercent = (float) CutsceneManager.skipTicks / CutsceneManager.skipThreshold;
                int barWidth = 100;
                int barHeight = 2;
                int x = (screenWidth - barWidth) / 2;
                int y = 20;

                event.getGuiGraphics().fill(x, y, x + barWidth, y + barHeight, 0x88000000);
                event.getGuiGraphics().fill(x, y, x + (int)(barWidth * skipPercent), y + barHeight, 0xFFFFFFFF);

                event.getGuiGraphics().drawCenteredString(mc.font, Component.translatable("valoria.cutscenes.skip_hold"), screenWidth / 2, y + 5, 0xFFFFFF);
            } else {
                event.getGuiGraphics().drawCenteredString(mc.font, Component.translatable("valoria.cutscenes.skip_hint"), screenWidth / 2, 20, 0xAAFFFFFF);
            }

            if (CutsceneManager.nodes != null && CutsceneManager.currentNodeIndex < CutsceneManager.nodes.size) {
                CutsceneNode currentNode = CutsceneManager.nodes.get(CutsceneManager.currentNodeIndex);
                if (currentNode.component != null) {
                    int textWidth = mc.font.width(currentNode.component);
                    int x = (screenWidth - textWidth) / 2;

                    int y = screenHeight - currentBarHeight - 30;
                    event.getGuiGraphics().drawCenteredString(mc.font, currentNode.component, screenWidth / 2, y, 0xFFFFFF);
                }
            }

            int ticksLeft = CutsceneManager.maxTicks - CutsceneManager.ticks;
            if (ticksLeft <= 20) {
                float alphaFloat = 1.0f - ((float) ticksLeft / 20);
                int alphaInt = (int) (alphaFloat * 255.0f);
                event.getGuiGraphics().fill(0, 0, screenWidth, screenHeight, (alphaInt << 24));
            }
        }
    }
}
