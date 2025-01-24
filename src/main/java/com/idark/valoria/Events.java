package com.idark.valoria;

import com.idark.valoria.client.ui.bossbars.Bossbar;
import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.capability.UnloackbleCap;
import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.core.config.ServerConfig;
import com.idark.valoria.core.mixin.client.BossHealthOverlayAccessor;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.UnlockableUpdatePacket;
import com.idark.valoria.core.network.packets.particle.SoulCollectParticlePacket;
import com.idark.valoria.core.proxy.ClientProxy;
import com.idark.valoria.registries.EffectsRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.item.armor.item.HitEffectArmorItem;
import com.idark.valoria.registries.item.armor.item.PercentageArmorItem;
import com.idark.valoria.registries.item.types.SoulCollectorItem;
import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@SuppressWarnings("removal")
public class Events{
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    public ArcRandom arcRandom = new ArcRandom();

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            event.addCapability(new ResourceLocation(Valoria.ID, "pages"), new UnloackbleCap());
        }
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent e){
        if(ValoriaUtils.isIDE){
            ItemStack itemStack = e.getItemStack();
            Stream<ResourceLocation> itemTagStream = itemStack.getTags().map(TagKey::location);
            if(Minecraft.getInstance().options.advancedItemTooltips){
                if(Screen.hasControlDown()){
                    if(!itemStack.getTags().toList().isEmpty()){
                        e.getToolTip().add(Component.empty());
                        e.getToolTip().add(Component.literal("ItemTags: " + itemTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                    }

                    if(itemStack.getItem() instanceof BlockItem blockItem){
                        BlockState blockState = blockItem.getBlock().defaultBlockState();
                        Stream<ResourceLocation> blockTagStream = blockState.getTags().map(TagKey::location);
                        if(!blockState.getTags().map(TagKey::location).toList().isEmpty()){
                            if(itemStack.getTags().toList().isEmpty()){
                                e.getToolTip().add(Component.empty());
                            }

                            e.getToolTip().add(Component.literal("BlockTags: " + blockTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
                }else if(!itemStack.getTags().toList().isEmpty() || itemStack.getItem() instanceof BlockItem blockItem && !blockItem.getBlock().defaultBlockState().getTags().toList().isEmpty()){
                    e.getToolTip().add(Component.empty());
                    e.getToolTip().add(Component.literal("Press [Control] to get tags info").withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }

    @SubscribeEvent
    public void disableBlock(ShieldBlockEvent event){
        if(event.getDamageSource().getDirectEntity() instanceof Player player){
            LivingEntity mob = event.getEntity();
            ItemStack weapon = player.getMainHandItem();
            if(!weapon.isEmpty() && weapon.is(TagsRegistry.CAN_DISABLE_SHIELD) && mob instanceof Player attacked){
                attacked.disableShield(true);
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingBlockDestroy(LivingDestroyBlockEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerBlockDestroy(PlayerEvent.BreakSpeed event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setNewSpeed(-1);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }

        for(ItemStack armorPiece : event.getEntity().getArmorSlots()){
            if(armorPiece.getItem() instanceof HitEffectArmorItem hitEffect){
                if(!event.getEntity().level().isClientSide){
                    hitEffect.onAttack(event);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        if(event.getSource().getEntity() instanceof LivingEntity e){
            if(e.hasEffect(EffectsRegistry.STUN.get())) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event){
        float incomingDamage = event.getAmount();
        float totalMultiplier;
        if(ServerConfig.PERCENT_ARMOR.get()){
            float armor = 0f;
            for(ItemStack armorPiece : event.getEntity().getArmorSlots()){
                if(armorPiece.getItem() instanceof PercentageArmorItem percent){
                    float percentDefense = percent.getPercentDefense();
                    armor += percentDefense;
                }
            }

            totalMultiplier = Math.max(Math.min(1 - (armor), 1), 0);
            float reducedDamage = incomingDamage * totalMultiplier;
            event.setAmount(reducedDamage);
        }
    }

    @SubscribeEvent
    public void onPlayerKill(LivingDeathEvent deathEvent){
        Level level = deathEvent.getEntity().level();
        if(level instanceof ServerLevel serverLevel){
            Entity attacker = deathEvent.getSource().getEntity();
            if(attacker instanceof Player plr){
                for(ItemStack itemStack : plr.getHandSlots()){
                    if(itemStack.is(ItemsRegistry.soulCollectorEmpty.get()) && itemStack.getItem() instanceof SoulCollectorItem soul){
                        Vec3 pos = deathEvent.getEntity().position().add(0, deathEvent.getEntity().getBbHeight() / 2f, 0);
                        PacketHandler.sendToTracking(serverLevel, BlockPos.containing(pos), new SoulCollectParticlePacket(plr.getUUID(), pos.x(), pos.y(), pos.z()));
                        soul.addCount(1, itemStack, plr);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void critDamage(CriticalHitEvent event){
        if(CuriosApi.getCuriosHelper().findEquippedCurio(ItemsRegistry.runeAccuracy.get(), event.getEntity()).isPresent()){
            if(arcRandom.chance(0.1f)){
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), (float)(event.getEntity().getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f));
            }
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event){
        Capability<IUnlockable> PAGE = IUnlockable.INSTANCE;

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(PAGE).ifPresent((k) -> event.getOriginal().getCapability(PAGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>)k).deserializeNBT(((INBTSerializable<CompoundTag>)o).serializeNBT())));
        if(!event.getEntity().level().isClientSide){
            PacketHandler.sendTo((ServerPlayer)event.getEntity(), new UnlockableUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide){
            if(event.getEntity() instanceof Player){
                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new UnlockableUpdatePacket((Player)event.getEntity()));
            }
        }
    }

    // may be improved, not accurate
    public static double calculateDamageReductionPercent(double defensePoints){
        return Mth.clamp(defensePoints / 2, 0, 20) / 25 * 0.4;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public void onOverlayRender(RenderGuiOverlayEvent.Pre ev){
        if(ev.isCanceled()) return;
        Minecraft minecraft = Minecraft.getInstance();
        GuiGraphics gui = ev.getGuiGraphics();
        PoseStack ms = gui.pose();
        if(minecraft.options.hideGui) return;

        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        double armor = 0;
        for(ItemStack armorPiece : minecraft.player.getArmorSlots()){
            if(armorPiece.getItem() instanceof PercentageArmorItem percent){
                int percentDefense = percent.getDefense();
                armor += percentDefense;
            }
        }

        if(armor > 0 && ev.getOverlay() == VanillaGuiOverlay.ARMOR_LEVEL.type() && ServerConfig.PERCENT_ARMOR.get() && new ForgeGui(minecraft).shouldDrawSurvivalElements()){
            armor += calculateDamageReductionPercent(minecraft.player.getAttributeValue(Attributes.ARMOR));
            ms.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            int left = width / 2 - 91;
            int top = height - 49;

            RenderSystem.disableBlend();
            String component = I18n.get("tooltip.valoria.value", Math.round(armor) + "%");
            gui.blit(GUI_ICONS_LOCATION, left, top - 2, 34, 9, 9, 9);
            gui.drawString(minecraft.font, component, left + 12, top - 1, Color.WHITE.getRGB());
            ms.popPose();
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public void onBossInfoRender(CustomizeGuiOverlayEvent.BossEventProgress ev){
        Minecraft mc = Minecraft.getInstance();
        if(ev.isCanceled() || mc.level == null || !ClientConfig.CUSTOM_BOSSBARS.get()) return;
        Map<UUID, LerpingBossEvent> events = ((BossHealthOverlayAccessor)mc.gui.getBossOverlay()).getEvents();
        if(events.isEmpty()) return;
        GuiGraphics pGuiGraphics = ev.getGuiGraphics();
        int screenWidth = pGuiGraphics.guiWidth();
        int offset = 0;
        for(LerpingBossEvent event : events.values()){
            if(ClientProxy.bossbars.containsKey(ev.getBossEvent().getId())){
                String id = ClientProxy.bossbars.get(event.getId());
                Bossbar bossbar = Bossbar.bossbars.getOrDefault(id, null);
                if(bossbar == null){
                    ev.setIncrement(18);
                    drawVanillaBar(pGuiGraphics, screenWidth / 2 - 91, offset, event);
                    int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                    int nameY = offset + 16 - 9;
                    pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                }

                if(bossbar != null){
                    if(ClientConfig.BOSSBAR_TITLE.get()){
                        ev.setIncrement(32);
                        int yOffset = offset + 6;
                        int xOffset = screenWidth / 2 - 91;
                        Minecraft.getInstance().getProfiler().push("BossBar");
                        pGuiGraphics.blit(bossbar.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                        int i = (int)(event.getProgress() * 177.0F);
                        if(i > 0){
                            if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                                RenderSystem.enableBlend();
                                if(Objects.equals(bossbar.getTexture(), new ResourceLocation(Valoria.ID, "textures/gui/bossbars/base.png"))){
                                    Color color = bossbar.rainbow ? ColorUtil.rainbowColor(mc.level.getGameTime() / 1.5f) : bossbar.getColor();
                                    pGuiGraphics.setColor((float)color.getRed() / 255, (float)color.getGreen() / 255, (float)color.getBlue() / 255, 1);
                                }

                                pGuiGraphics.blit(bossbar.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                                RenderSystem.disableBlend();
                                pGuiGraphics.setColor(1, 1, 1, 1);
                            }
                        }

                        int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                        int nameY = offset + 30;
                        pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                    }else{
                        ev.setIncrement(26);
                        int yOffset = offset + 6;
                        int xOffset = screenWidth / 2 - 91;
                        Minecraft.getInstance().getProfiler().push("BossBar");
                        pGuiGraphics.blit(bossbar.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                        int i = (int)(event.getProgress() * 177.0F);
                        if(i > 0){
                            if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                                if(Objects.equals(bossbar.getTexture(), new ResourceLocation(Valoria.ID, "textures/gui/bossbars/base.png"))){
                                    Color color = bossbar.rainbow ? ColorUtil.rainbowColor(mc.level.getGameTime() / 1.5f) : bossbar.getColor();
                                    pGuiGraphics.setColor((float)color.getRed() / 255, (float)color.getGreen() / 255, (float)color.getBlue() / 255, 1);
                                }

                                RenderSystem.enableBlend();
                                pGuiGraphics.blit(bossbar.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                                RenderSystem.disableBlend();
                                pGuiGraphics.setColor(1, 1, 1, 1);
                            }
                        }
                    }
                }

                offset += ev.getIncrement();
                if(offset >= pGuiGraphics.guiHeight() / 4) break;
            }
        }

        ev.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int offset, BossEvent pBossEvent){
        drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, 182, 0);
        int i = (int)(pBossEvent.getProgress() * 183.0F);
        if(i > 0){
            drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, i, 5);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pWidth, int p_281636_){
        pGuiGraphics.blit(ValoriaClient.VANILLA_LOC, pX, pY, 0, pBossEvent.getColor().ordinal() * 5 * 2 + p_281636_, pWidth, 5);
        if(pBossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS){
            RenderSystem.enableBlend();
            pGuiGraphics.blit(ValoriaClient.VANILLA_LOC, pX, pY, 0, 80 + (pBossEvent.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, pWidth, 5);
            RenderSystem.disableBlend();
        }
    }
}