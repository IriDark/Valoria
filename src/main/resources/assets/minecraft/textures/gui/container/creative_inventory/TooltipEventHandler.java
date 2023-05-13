package mod.maxbogomol.wizards_artifacts.client.render.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_artifacts.WizardsArtifacts;
import mod.maxbogomol.wizards_artifacts.api.item.LensItem;
import mod.maxbogomol.wizards_artifacts.api.spell.SpellClass;
import mod.maxbogomol.wizards_artifacts.api.spell.SpellUpgrade;
import mod.maxbogomol.wizards_artifacts.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_artifacts.common.item.ArcaneGoldWandItem;
import mod.maxbogomol.wizards_artifacts.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class TooltipEventHandler {

    private TooltipEventHandler() {}

    public static void onPostTooltipEvent(RenderTooltipEvent.PostText event) {
        ItemStack stack = event.getStack();

        int x = event.getX();
        int y = event.getY();
        int width = event.getWidth();
        int height = event.getHeight();
        MatrixStack ms = event.getMatrixStack();

        Minecraft mc = Minecraft.getInstance();

        CompoundNBT nbt = stack.getTag();
        if (nbt!=null) {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            if (stack.getItem() instanceof ArcaneGoldWandItem) {
                if (nbt.contains("lens_id")) {
                    mc.textureManager.bindTexture(new ResourceLocation(WizardsArtifacts.MOD_ID + ":textures/gui/lens_frame.png"));
                    AbstractGui.blit(ms, x, y - 50, 0, 0, 64, 64, 64, 64);

                    if (nbt.getInt("lens") > 0) {
                        SpellClass spell = WizardsArtifacts.WandLens.getSpell(nbt.getString("lens_id"));
                        RenderUtils.renderItemModelInGui(spell.getItem().getDefaultInstance(), x+4+16, y-48+4+16, 32, 32, 32);
                        Inventory item = ArcaneGoldWandItem.getInventory(stack);
                        ItemStack lens = item.getStackInSlot(0);
                        drawLensUpgrades(ms, spell, lens, x+42, y-6-16);
                    }
                }
            }

            if (stack.getItem() instanceof LensItem) {
                SpellClass spell = WizardsArtifacts.WandLens.getSpellOnId(stack.getItem().getRegistryName());
                drawLensUpgrades(ms, spell, stack, x, y-6-16);
            }

            RenderSystem.disableBlend();
            RenderSystem.color4f(1, 1, 1, 1);
        }
        if (stack.getItem() instanceof IWissenItem) {
            IWissenItem item = (IWissenItem) stack.getItem();

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            int yy = 4;
            if (stack.getItem() instanceof ICurioItem) {
                yy = 16;
            }

            mc.textureManager.bindTexture(new ResourceLocation(WizardsArtifacts.MOD_ID + ":textures/gui/wissen_frame.png"));
            AbstractGui.blit(ms, x, y + 8 + yy, 0, 0, 96, 32, 96, 32);
            if (nbt!=null) {
                if (nbt.contains("wissen")) {
                    int width_wissen = 64;
                    width_wissen /= item.getMaxWissen() / (double) nbt.getInt("wissen");
                    if (width_wissen % 2 != 0) {
                        width_wissen = width_wissen + 1;
                    }
                    mc.textureManager.bindTexture(new ResourceLocation(WizardsArtifacts.MOD_ID + ":textures/gui/wand_wissen.png"));
                    AbstractGui.blit(ms, x + 2, y + 10 + yy, 0, 0, width_wissen, 32, 64, 32);
                }
            }

            RenderSystem.disableBlend();
            RenderSystem.color4f(1, 1, 1, 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IWissenItem) {
                List<ITextComponent> tooltip = event.getToolTip();
                tooltip.add(1, new StringTextComponent("                "));
                tooltip.add(2, new StringTextComponent("                "));
            }
        }
    }

    public static void drawLensUpgrades(MatrixStack ms, SpellClass spell, ItemStack lens, int x, int y) {
        Minecraft mc = Minecraft.getInstance();

        ArrayList<SpellUpgrade> upgrades = spell.getUpgrades();
        spell.setLensUprgradesDefault(lens);
        int ux=0;
        int uy=0;
        for (SpellUpgrade upgrade : upgrades) {
            if (spell.getLensUprgrade(lens, upgrade.getId()) > 0) {
                mc.textureManager.bindTexture(new ResourceLocation(WizardsArtifacts.MOD_ID + ":textures/gui/spell_upgrade/frame.png"));
                AbstractGui.blit(ms, x+(18*ux), y+(18*uy), 0, 0, 16, 16, 16, 16);
                mc.textureManager.bindTexture(upgrade.getTexture());
                AbstractGui.blit(ms, x+(18*ux), y+(18*uy), 0, 0, 16, 16, 16, 16);

                ux++;
                if (ux >= 4) {
                    ux = 0;
                    uy--;
                }
            }
        }

        ux=0;
        uy=0;
        for (SpellUpgrade upgrade : upgrades) {
            if (spell.getLensUprgrade(lens, upgrade.getId()) > 0) {
                FontRenderer font_renderer = Minecraft.getInstance().fontRenderer;
                String text = (String) new TranslationTextComponent("enchantment.level."+spell.getLensUprgrade(lens, upgrade.getId())).getString();
                int stringWidth = font_renderer.getStringWidth(text);

                font_renderer.drawStringWithShadow(ms, text, x+(18*ux)-(stringWidth/2)-font_renderer.FONT_HEIGHT+14+9, y+(18*uy)-font_renderer.FONT_HEIGHT+18, 0xffffff);

                ux++;
                if (ux >= 4) {
                    ux = 0;
                    uy--;
                }
            }
        }
    }
}
