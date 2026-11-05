package com.idark.valoria.client.ui.overlay;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.gui.overlay.*;

public class HeavyAnvilOverlay {
    private static final ResourceLocation texture = Valoria.loc("textures/gui/heavy_anvil.png");
    public static final IGuiOverlay instance = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        HitResult hit = mc.hitResult;
        if (mc.level == null || mc.player == null || hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos pos = blockHit.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        if (state.getBlock() instanceof HeavyAnvil anvil) {
            var anvilBe = anvil.getAnvilBlockEntity(mc.level, pos, state);
            if (anvilBe == null) return;

            Font font = mc.font;
            boolean holdingHammer = mc.player.getMainHandItem().is(TagsRegistry.HEAVY_ANVIL_TOOL);
            ItemStack inAnvil = anvilBe.itemHandler.getStackInSlot(0);
            if (inAnvil.isEmpty()) {
                Component emptyPrompt = Component.translatable("hud.valoria.heavy_anvil.empty");
                renderPrompt(guiGraphics, font, emptyPrompt, width / 2, height / 2 + 16, 0xEEEEEE);
            } else if (!holdingHammer) {
                Component needHammer = Component.translatable("hud.valoria.heavy_anvil.need_hammer");
                renderPrompt(guiGraphics, font, needHammer, width / 2, height / 2 + 16, 0xFFAAAA);
                Component takePrompt = Component.translatable("hud.valoria.heavy_anvil.take_prompt");
                renderPrompt(guiGraphics, font, takePrompt, width / 2, height / 2 + 28, 0xAAAAAA);
            } else if (anvilBe.requiredHits == 0) {
                Component noRecipe = Component.translatable("hud.valoria.heavy_anvil.no_recipe");
                renderPrompt(guiGraphics, font, noRecipe, width / 2, height / 2 + 16, 0xFFAAAA);
                Component takePrompt = Component.translatable("hud.valoria.heavy_anvil.take_prompt");
                renderPrompt(guiGraphics, font, takePrompt, width / 2, height / 2 + 28, 0xAAAAAA);
            } else {
                drawMiniGame(guiGraphics, mc.level, anvilBe, width, height, partialTick);
                Component hitPrompt = Component.translatable("hud.valoria.heavy_anvil.hit_prompt");
                renderPrompt(guiGraphics, font, hitPrompt, width / 2 + 24, height / 2 + 42, 0xFFE066);
            }
        }
    };

    private static void renderPrompt(GuiGraphics graphics, Font font, Component text, int x, int y, int color) {
        int strWidth = font.width(text);
        int bgX = x - strWidth / 2;
        graphics.fill(bgX - 4, y - 2, bgX + strWidth + 4, y + font.lineHeight + 1, 0x77000000);
        graphics.drawCenteredString(font, text, x, y, color);
    }

    private static void drawMiniGame(GuiGraphics graphics, Level level, HeavyAnvilBlockEntity anvil, int width, int height, float partialTick) {
        int barWidth = 12;
        int barHeight = 64;
        int x = width / 2 + 20;
        int y = height / 2 - barHeight / 2;

        int uOffset = 0;
        if (anvil.instability > 0 && anvil.maxInstability > 0) {
            int frame = (int) Math.ceil(((double) anvil.instability / anvil.maxInstability) * 3);
            uOffset = Math.min(3, frame) * 12;
        }

        graphics.fill(x - 2, y - 2, x + barWidth + 2, y + barHeight + 2, 0x55000000);
        graphics.blit(texture, x, y, uOffset, 0, barWidth, barHeight, 128, 128);
        int minY = y + (int) (anvil.sweetSpotMin * barHeight);
        int maxY = y + (int) (anvil.sweetSpotMax * barHeight);
        int spotHeight = maxY - minY;
        if (spotHeight < 10) {
            spotHeight = 10;
            maxY = minY + spotHeight;
            if (maxY > y + barHeight) {
                maxY = y + barHeight;
                minY = maxY - spotHeight;
            }
        }

        graphics.blitNineSliced(texture, x, minY, barWidth, spotHeight, 5, 12, 20, 96, 10);
        int cursorY = y + (int) (anvil.getLinePosition(level, partialTick) * barHeight);
        graphics.blit(texture, x - 3, cursorY - 2, 48, 0, 18, 5, 128, 128);

        Font font = Minecraft.getInstance().font;
        String progressStr = anvil.progress + "/" + anvil.requiredHits;
        int pWidth = font.width(progressStr);
        graphics.fill(x + 18, y + 8, x + 22 + pWidth, y + 18, 0x66000000);
        graphics.drawString(font, progressStr, x + 20, y + 9, 0xFFFFFF, true);
        String instStr = anvil.instability + "/" + anvil.maxInstability;
        int iWidth = font.width(instStr);
        int instColor = anvil.instability == 0 ? 0xAAAAAA : (anvil.instability >= anvil.maxInstability - 1 ? 0xFF4444 : 0xFFAA00);
        graphics.fill(x + 18, y + 46, x + 22 + iWidth, y + 56, 0x66000000);
        graphics.drawString(font, instStr, x + 20, y + 47, instColor, true);
    }
}
