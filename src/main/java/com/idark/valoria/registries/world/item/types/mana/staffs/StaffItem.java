package com.idark.valoria.registries.world.item.types.mana.staffs;

import com.idark.valoria.Valoria;
import com.idark.valoria.config.ClientConfig;
import com.idark.valoria.registries.world.item.types.mana.IManaItem;
import com.idark.valoria.registries.world.item.types.mana.ManaItemType;
import com.idark.valoria.registries.world.item.types.mana.ManaItemUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

// TODO: Completely (maybe) redone this
public class StaffItem extends Item implements IManaItem {

    //This thing is a huge experiment don't take it serious lmao
    public StaffItem(Item.Properties props) {
        super(props);
    }

    public static int spell;
    public static int bar;
    public static int cd = 57;
    public static int max;

    @Override
    public int getMaxMana() {
        max = 100;
        return 100;
    }

    @Override
    public ManaItemType getManaItemType() {
        return ManaItemType.USING;
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("mana")) {
            nbt.putInt("mana", 0);
        }

        if (nbt.contains("mana")) {
            if (!world.isClientSide()) {
                ManaItemUtils.existMana(stack);
            }

            if (entity instanceof Player plr) {
                bar = ManaItemUtils.getMana(stack);
                bar /= ((double) this.getMaxMana() / (double) ManaItemUtils.getMana(stack));
                if (plr.getCooldowns().isOnCooldown(stack.getItem())) {
                    cd = 0;
                } else {
                    if (cd < 57) {
                        cd += 5;
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (!pLevel.isClientSide) {
            if (!player.isShiftKeyDown()) {
                if (spell < getMaxMana()) {
                    spell += 22;
                    ManaItemUtils.removeMana(stack, 10);
                } else {
                    player.getCooldowns().addCooldown(stack.getItem(), 25);
                    spell -= getMaxMana();
                }

                return new InteractionResultHolder<>(InteractionResult.CONSUME, stack);
            } else if (player.isShiftKeyDown()) {
                pLevel.addParticle(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 1, 1, 1);
                ManaItemUtils.addMana(stack, 5, 100);
            }
        }

        if (pLevel.isClientSide) {
            for (int i = 0; i < 10; i++) {
                pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, player.getX() + new Random().nextInt() * 0.1f, player.getY(), player.getZ() + new Random().nextInt() * 0.1f, 0, 0, 0);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.CONSUME_PARTIAL, stack);
    }

    private static final ResourceLocation BAR = new ResourceLocation(Valoria.MOD_ID + ":textures/gui/magic_ui.png");

    @OnlyIn(Dist.CLIENT)
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
        GuiGraphics gui = event.getGuiGraphics();
        Player player = mc.player;
        ItemStack stack = main;
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Integer barType = ClientConfig.MANA_BAR_TYPE.get();
        boolean renderBar = false;
        if (barType < 3 && !main.isEmpty() && main.getItem() instanceof StaffItem) {
            renderBar = true;
        } else {
            if (barType < 3 && !offhand.isEmpty() && offhand.getItem() instanceof StaffItem) {
                renderBar = true;
                stack = offhand;
            }
        }

        CompoundTag tag = stack.getTag();
        if (renderBar) {
            if (!player.isSpectator()) {
                Integer xCord = ClientConfig.MANA_BAR_X.get();
                Integer yCord = ClientConfig.MANA_BAR_Y.get();
                mc.textureManager.bindForSetup(BAR);
                if (barType == 1) {
                    // UI
                    gui.blit(BAR, xCord, yCord, 0, 0, 96 * 2, 20 * 2, 512 * 2, 512 * 2);

                    // Spell
                    gui.blit(BAR, xCord + 12, yCord + 6, spell, 219 * 2, 11 * 2, 11 * 2, 512 * 2, 512 * 2);

                    // Mana Bar
                    gui.blit(BAR, xCord + 42, yCord + 8, 192 * 2, 71 * 2, (bar * 2), 7 * 2, 512 * 2, 512 * 2);

                    if (bar > 0 && bar < max - 25) {
                        // Mana bar ending
                        gui.blit(BAR, xCord + (bar * 2) + 40, yCord + 10, 193 * 2, 91 * 2, 2, 7 * 2, 512 * 2, 512 * 2);
                    }

                    // Cooldown Bar
                    gui.blit(BAR, xCord + 62, yCord + 28, 192 * 2, 85 * 2, (cd * 2), 3 * 2, 512 * 2, 512 * 2);

                    if (cd > 0 && cd < 57) {
                        // Cooldown bar ending
                        gui.blit(BAR, xCord + (cd * 2) + 60, yCord + 28, 192 * 2, 91 * 2, 2, 7 * 2, 512 * 2, 512 * 2);
                    }

                } else if (barType == 2) {
                    gui.blit(BAR, xCord, yCord, 20, 42, 22, 22, 64, 64);
                    if (tag.getInt("charge") == 1) {
                        gui.blit(BAR, xCord, yCord, 42, 20, 22, 22, 64, 64);
                    } else if (tag.getInt("charge") == 2) {
                        gui.blit(BAR, xCord, yCord, 42, 42, 22, 22, 64, 64);
                    }
                }
            }
        }

        RenderSystem.disableBlend();
    }

    public static String getModeString(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("mana")) {
            nbt.putInt("mana", 0);
        }

        return String.valueOf(ManaItemUtils.getAddManaRemain(stack, 100, 100));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.current_mana").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(" " + getModeString(stack) + "/100").withStyle(ChatFormatting.BLUE)));
    }
}
