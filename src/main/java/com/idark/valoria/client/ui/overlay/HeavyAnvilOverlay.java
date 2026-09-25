package com.idark.valoria.client.ui.overlay;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.block.types.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.gui.overlay.*;

import java.util.*;

public class HeavyAnvilOverlay {
    private static final ResourceLocation texture = Valoria.loc("textures/gui/heavy_anvil.png");
    public static final IGuiOverlay instance = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        HitResult hit = mc.hitResult;
        if(mc.level == null || mc.player == null || hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult)hit;
        BlockPos pos = blockHit.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        if(state.getBlock() instanceof HeavyAnvil anvil){
            PoseStack pose = guiGraphics.pose();
            Font font = mc.font;
            if(mc.player.getMainHandItem().is(TagsRegistry.HEAVY_ANVIL_TOOL)){
                var anvilBe = anvil.getAnvilBlockEntity(mc.level, pos, state);
                if(anvilBe != null && !anvilBe.itemHandler.getStackInSlot(0).isEmpty() && anvilBe.requiredHits != 0){ // hacky way to check if recipe is actually present without syncing anything
                    drawMiniGame(guiGraphics, mc.level, anvilBe, width, height, partialTick);
                }
            }else{
                pose.pushPose();
                pose.translate(0.0D, 0.0D, -700.0D);
                int x = width / 2;
                int y = height / 2 + 30;
                var text = Component.literal("Should hold a hammer");
                guiGraphics.renderTooltip(font, List.of(text), Optional.empty(), (x - font.width(text) / 2) - 12, y);
                pose.popPose();
            }
        }
    };

    private static void drawMiniGame(GuiGraphics graphics, Level level, HeavyAnvilBlockEntity anvil, int width, int height, float partialTick) {
        int barWidth = 12;
        int barHeight = 64;
        int x = 120 + (width - barWidth) / 2;
        int y = height / 2 + 30;

        int uOffset = 0;
        if (anvil.instability > 0 && anvil.maxInstability > 0) {
            int frame = (int) Math.ceil(((double) anvil.instability / anvil.maxInstability) * 3);
            uOffset = Math.min(3, frame) * 12;
        }

        graphics.blit(texture, x, y, uOffset, 0, barWidth, barHeight, 128, 128);

        int minY = y + (int)(anvil.sweetSpotMin * barHeight);
        int maxY = y + (int)(anvil.sweetSpotMax * barHeight);
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

        int cursorY = y + (int)(anvil.getLinePosition(level, partialTick) * barHeight);

        graphics.blit(texture, x - 3, cursorY - 2, 48, 0, 18, 5, 128, 128);
        graphics.drawCenteredString(Minecraft.getInstance().font, anvil.progress + "/" + anvil.requiredHits, x + 30, y, CommonColors.WHITE);
        graphics.drawCenteredString(Minecraft.getInstance().font, anvil.instability + "/" + anvil.maxInstability, x + 30, y + 10, CommonColors.RED);
    }
}
