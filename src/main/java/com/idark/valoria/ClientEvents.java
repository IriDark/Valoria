package com.idark.valoria;


import com.idark.valoria.core.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.ClickEvent.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.config.*;
import net.minecraftforge.fml.config.ModConfig.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;

import java.io.*;
import java.text.*;
import java.util.*;

public class ClientEvents{
    public static final DecimalFormat FORMAT = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final ResourceLocation FLAME_ICON = Valoria.loc("textures/gui/flame_icon.png");

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.FOOD_LEVEL.type()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;
        player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magmaLevel -> {
            float max = magmaLevel.getMaxAmount(player);
            float amount = magmaLevel.getAmount();
            if(max <= 0 || amount >= max) return;

            GuiGraphics gui = event.getGuiGraphics();
            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();

            int left = width / 2 + 10;
            int startY = height - 39 - 10;

            int bubblesToDraw = (int) Math.ceil(amount);
            RandomSource random = player.getRandom();
            boolean shouldShake = player.isInLava() || player.isOnFire();
            int shakeTick = mc.gui.getGuiTicks();

            for (int i = 0; i < bubblesToDraw; i++) {
                int row = i / 10;
                int col = i % 10;

                int x = left + (col * 8);
                int y = startY - (row * 10);
                if (shouldShake) {
                    if ((shakeTick + i * 2) % 7 == 0) {
                        y += random.nextInt(3) - 1;
                    }
                }

                gui.blit(FLAME_ICON, x, y, 0, 0, 9, 9, 9, 9);
            }
        });
    }

    @SubscribeEvent
    public static void onEntityRender(RenderLivingEvent.Post<LivingEntity, ?> event) {
        LivingEntity entity = event.getEntity();
        ILivingEntityData data = (ILivingEntityData) entity;
        float lastDamage = data.valoria$getLastDamage();
        if(!entity.getType().is(TagsRegistry.DAMAGE_INDICATOR_IGNORED)){
            if(ClientConfig.DAMAGE_INDICATOR.get() || entity instanceof MannequinEntity){
                if(!(lastDamage > 0 && entity.hurtTime > 0)) return;

                Col textColor = Col.red;
                Component component = Component.literal(FORMAT.format(lastDamage));
                for(DamageData damageData : DamageData.dataTypes){
                    if(data.valoria$getLastDamageSource() != null && damageData.predicate().test(data.valoria$getLastDamageSource())){
                        if(damageData.getText() != null) component = damageData.getText();
                        textColor = damageData.getColor();
                    }
                }

                ValoriaUtils.renderText(entity, textColor, component, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), entity.hurtTime);
            }
        }

        if(data.valoria$getMissTime() > 0) {
            ValoriaUtils.renderText(entity, Col.lightGray, Component.translatable("popup.valoria.miss"), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.valoria$getMissTime());
        }

        if(data.valoria$getDodgeTime() > 0) {
            ValoriaUtils.renderText(entity, Col.lightGray, Component.translatable("popup.valoria.dodge"), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.valoria$getDodgeTime());
        }
    }

    @SubscribeEvent
    public static void onClientJoin(ClientPlayerNetworkEvent.LoggingIn event){
        var modInfo = ModList.get().getModFileById(Valoria.ID).getMods().get(0);
        var result = VersionChecker.getResult(modInfo);
        if(ClientConfig.SHOW_UPDATES.get()){
            if(!modInfo.getVersion().getQualifier().equals("0.0NONE") && result.status().shouldDraw()){
                var newVersion = result.target().toString();
                Component message = Component.literal("\uD83E\uDEB7 Valoria: ").withStyle(style -> DotStyle.of().color(Pal.verySoftPink)).append(Component.translatable("tooltip.valoria.update_available", newVersion).withStyle(ChatFormatting.WHITE));
                var actions = Component.translatable("tooltip.valoria.download").withStyle(style -> style.withUnderlined(true).withFont(Valoria.FONT).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/valoria")))
                .append(Component.literal(" | ")
                .append(Component.translatable("tooltip.valoria.hide").withStyle(style -> style.withUnderlined(true).withFont(Valoria.FONT).withClickEvent(new ClickEvent(Action.OPEN_FILE, new File(ConfigTracker.INSTANCE.getConfigFileName(Valoria.ID, Type.CLIENT)).getAbsolutePath()))))
                .append(Component.literal(" | "))
                .append(Component.translatable("tooltip.valoria.patreon").withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/c/valoriamod"))))
                );

                var separator = Component.literal("<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->").withStyle(style -> DotStyle.of().color(Pal.verySoftPink.copy().darker()));

                event.getPlayer().displayClientMessage(separator, false);

                event.getPlayer().displayClientMessage(message, false);
                event.getPlayer().displayClientMessage(Component.empty(), false);
                event.getPlayer().displayClientMessage(actions, false);

                event.getPlayer().displayClientMessage(separator, false);
            }
        }
    }
}