package com.idark.valoria.client.ui.screen;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.item.types.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class AbilityWheelScreen extends Screen {
    public record AbilityEntry(CurioAbility ability, ItemStack stack, String slotId, int slotIndex) {}

    /**
     * N=2: dividers at -90°(up), 90°(down) → vertical line. Items at 0°(right), 180°(left).
     * N=3: dividers at -90°(up), 30°, 150° → Y-shape. Items at -30°, 90°, 210°.
     * N=4: dividers at -90°, 0°, 90°, 180° → cross (+). Items at -45°, 45°, 135°, 225°.
     */
    private static final float ANGLE_OFFSET = -90f;

    public List<AbilityEntry> abilities = new ArrayList<>();
    public AbilityEntry selectedEntry;
    public float mouseAngleI = 0;
    public float hoverAmount = 0;
    public boolean hover = true;

    public AbilityWheelScreen(Component titleIn) {
        super(titleIn);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        long window = mc.getWindow().getWindow();
        for (KeyMapping mapping : new KeyMapping[]{
                mc.options.keyUp, mc.options.keyDown, mc.options.keyLeft, mc.options.keyRight,
                mc.options.keyJump, mc.options.keyShift, mc.options.keySprint}) {
            InputConstants.Key key = mapping.getKey();
            if (key.getType() == InputConstants.Type.KEYSYM) {
                mapping.setDown(InputConstants.isKeyDown(window, key.getValue()));
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key key = InputConstants.getKey(keyCode, scanCode);
        KeyMapping.set(key, true);
        if (keyCode == 256 || ValoriaClient.JEWELRY_BONUSES_KEY.isActiveAndMatches(key)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key key = InputConstants.getKey(keyCode, scanCode);
        KeyMapping.set(key, false);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void removed() {
        super.removed();
        KeyMapping.releaseAll();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int closeBtnX = width - 26;
        int closeBtnY = 12;
        int closeBtnW = 16;
        if (mouseX >= closeBtnX && mouseX <= closeBtnX + closeBtnW
                && mouseY >= closeBtnY && mouseY <= closeBtnY + closeBtnW) {
            this.onClose();
            return true;
        }

        selectedEntry = getSelectedEntry(mouseX, mouseY);
        float mouseDistance = getMouseDistance(mouseX, mouseY);
        float offset = Math.min((float) this.width / 2 * 0.7f, (float) this.height / 2 * 0.7f);
        mouseAngleI = Math.min(mouseDistance, offset * hoverAmount);

        if (selectedEntry != null && (abilities.size() == 1 || mouseDistance > 45)) {
            hover = false;
            PacketHandler.sendToServer(new OnKeyInputPacket(
                    selectedEntry.ability().event(), selectedEntry.slotId(), selectedEntry.slotIndex()));
        }

        return true;
    }

    public List<AbilityEntry> getAbilities() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<AbilityEntry> result = new ArrayList<>();

        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler ->
                handler.getCurios().forEach((id, stackHandler) -> {
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        ItemStack stack = stackHandler.getStacks().getStackInSlot(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof AbilityInputListener listener) {
                            for (CurioAbility ability : listener.getCurioAbilities(stack)) {
                                result.add(new AbilityEntry(ability, stack, id, i));
                            }
                        }
                    }
                }));

        return result;
    }

    public AbilityEntry getSelectedEntry(double mx, double my) {
        return getSelectedEntry(getAbilities(), mx, my);
    }

    public AbilityEntry getSelectedEntry(List<AbilityEntry> list, double mx, double my) {
        int i = getSelectedIndex(list, mx, my);
        if (i >= 0 && i < list.size()) return list.get(i);
        return list.isEmpty() ? null : list.get(0);
    }

    public int getSelectedIndex(List<AbilityEntry> list, double mx, double my) {
        if (list.isEmpty()) return -1;
        if (list.size() == 1) return 0;

        int cx = width / 2, cy = height / 2;
        double step = 360.0 / list.size();
        double angle = Math.toDegrees(Math.atan2(my - cy, mx - cx));
        if (angle < 0) angle += 360;

        double adjusted = (angle - ANGLE_OFFSET) % 360;
        if (adjusted < 0) adjusted += 360;
        return Math.min((int) (adjusted / step), list.size() - 1);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);
        int x = width / 2;
        int y = height / 2;
        float offset = Math.min(x * 0.7f, y * 0.7f);

        int closeBtnX = width - 26, closeBtnY = 12, closeBtnW = 16;
        boolean closeHover = mouseX >= closeBtnX && mouseX <= closeBtnX + closeBtnW
                && mouseY >= closeBtnY && mouseY <= closeBtnY + closeBtnW;

        if (hover && hoverAmount < 1) {
            hoverAmount += Minecraft.getInstance().getDeltaFrameTime() / 8;
        } else if (!hover && hoverAmount > 0) {
            hoverAmount -= Minecraft.getInstance().getDeltaFrameTime() / 4;
        }
        hoverAmount = Math.max(0f, Math.min(1f, hoverAmount));
        if (!hover && hoverAmount <= 0) { this.onClose(); return; }

        if (hover) abilities = getAbilities();

        if (abilities.isEmpty()) {
            gui.drawCenteredString(Minecraft.getInstance().font,
                    Component.translatable("gui.valoria.no_abilities"), x, y, 0xFFFFFF);
            return;
        }

        gui.drawCenteredString(Minecraft.getInstance().font,
                Component.translatable("tooltip.tridot.abilities"), x, 25, 0xFFFFFF);

        int n = abilities.size();
        float step = 360f / n;

        selectedEntry = getSelectedEntry(abilities, mouseX, mouseY);
        int selIdx = getSelectedIndex(abilities, mouseX, mouseY);

        float mouseDist = Math.min(getMouseDistance(mouseX, mouseY), offset * hoverAmount);
        mouseAngleI = mouseDist;

        float trinketSize = 32 * hoverAmount;
        float trinketSizeHover = 48 * hoverAmount;
        float rayLen = offset * 255f * hoverAmount;

        if (n == 1) {
            float[] col = getAbilityColor(abilities.get(0));
            float a = 0.15f * hoverAmount;
            for (int r = 0; r < 4; r++) {
                gui.pose().pushPose();
                gui.pose().translate(x, y, 0);
                gui.pose().mulPose(Axis.ZP.rotationDegrees(r * 90f));
                RenderBuilder.create().setRenderType(TridotRenderTypes.ADDITIVE)
                        .setColor(col[0], col[1], col[2]).setAlpha(a)
                        .setSecondAlpha(0)
                        .renderRay(gui.pose(), 1f, rayLen, rayLen)
                        .endBatch();
                gui.pose().popPose();
            }
        } else {
            float sectorSpread = (n == 2) ? (rayLen * 2 + 25f) : ((float)(rayLen * Math.tan(Math.toRadians(step / 2f))) + 25f);
            for (int k = 0; k < n; k++) {
                float sectorDeg = (k + 0.5f) * step + ANGLE_OFFSET;
                boolean isSel = (k == selIdx) && mouseDist > 45;
                float[] col = getAbilityColor(abilities.get(k));
                float a = (isSel ? 0.65f : 0.1f) * hoverAmount;

                gui.pose().pushPose();
                gui.pose().translate(x, y, 0);
                gui.pose().mulPose(Axis.ZP.rotationDegrees(sectorDeg - 90f));
                RenderBuilder.create().setRenderType(TridotRenderTypes.ADDITIVE)
                        .setColor(col[0], col[1], col[2]).setAlpha(a)
                        .setSecondAlpha(0)
                        .renderRay(gui.pose(), 1f, rayLen, sectorSpread)
                        .endBatch();
                gui.pose().popPose();
            }
        }

        float anim = offset * (float) Math.sin(Math.toRadians(90 * hoverAmount));
        for (int k = 0; k < n; k++) {
            AbilityEntry entry = abilities.get(k);

            int X = 0, Y = 0;
            if (n > 1) {
                double rad = Math.toRadians((k + 0.5) * step + ANGLE_OFFSET);
                X = (int) (Math.cos(rad) * anim);
                Y = (int) (Math.sin(rad) * anim);
            }

            boolean isSel = entry.equals(selectedEntry) && (n == 1 || mouseDist > 45);
            float sz = isSel ? trinketSizeHover : trinketSize;
            float off = sz / 2;
            int ix = (int) (x + X - off);
            int iy = (int) (y + Y - off);

            Utils.Render.renderItemModelInGui(entry.stack(), ix, iy, sz, sz, sz);

            gui.pose().pushPose();
            gui.pose().translate(0, 0, 301);

            if (entry.ability().icon() != null) {
                int icoSz = (int) (sz * 0.5f);
                if (icoSz > 0) {
                    RenderSystem.enableBlend();
                    gui.blit(entry.ability().icon(),
                            (int) (ix + sz - icoSz), (int) (iy + sz - icoSz),
                            0, 0, icoSz, icoSz, icoSz, icoSz);
                    RenderSystem.disableBlend();
                }
            }

            if (entry.ability().name() != null) {
                gui.drawCenteredString(Minecraft.getInstance().font,
                        entry.ability().name(), x + X, iy + (int) sz + 4, 0xFFFFFF);
            }

            gui.pose().popPose();
        }

        if (selectedEntry != null && (n == 1 || mouseDist > 45) && !closeHover) {
            Component tip = selectedEntry.ability().name() != null ? selectedEntry.ability().name() : selectedEntry.stack().getHoverName();
            gui.renderTooltip(Minecraft.getInstance().font, tip, mouseX, mouseY);
        }

        gui.fill(closeBtnX, closeBtnY, closeBtnX + closeBtnW, closeBtnY + closeBtnW, closeHover ? 0xCC551111 : 0x88000000);
        gui.drawCenteredString(Minecraft.getInstance().font, "✕", closeBtnX + closeBtnW / 2, closeBtnY + (closeBtnW - 8) / 2, closeHover ? 0xFF5555 : 0xAAAAAA);
        if (closeHover) {
            gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("gui.valoria.close"), mouseX, mouseY);
        }
    }

    public static float[] getAbilityColor(AbilityEntry entry) {
        if (entry != null && entry.stack() != null && !entry.stack().isEmpty()) {
            Style style = entry.stack().getRarity().getStyleModifier().apply(Style.EMPTY);
            if (style != null && style.getColor() != null) {
                int v = style.getColor().getValue();
                return new float[]{((v >> 16) & 0xFF) / 255f, ((v >> 8) & 0xFF) / 255f, (v & 0xFF) / 255f};
            }
        }
        return new float[]{0.5f, 0.5f, 0.5f};
    }

    public float getMouseDistance(double X, double Y) {
        return (float) Math.sqrt(Math.pow(width / 2 - X, 2) + Math.pow(height / 2 - Y, 2));
    }
}
