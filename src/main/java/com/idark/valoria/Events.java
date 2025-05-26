package com.idark.valoria;

import com.idark.valoria.api.events.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.effect.*;
import com.idark.valoria.registries.item.armor.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.level.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.Tags.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.*;
import net.minecraftforge.eventbus.api.Event.*;
import net.minecraftforge.eventbus.api.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

@SuppressWarnings("removal")
public class Events{
    public ArcRandom arcRandom = Tmp.rnd;

    @SubscribeEvent
    public void onReload(AddReloadListenerEvent event) {
        Valoria.LOGGER.info("Reloading Codex Chapters...");
        CodexEntries.initChapters();
    }

    @SubscribeEvent
    public static void onMouseClick(ScreenEvent.MouseButtonPressed event) {
        Minecraft mc = Minecraft.getInstance();
        if(event.getScreen() instanceof EffectRenderingInventoryScreen<?> inventoryScreen){
            if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
                if(!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)) return;

                Slot hovered = inventoryScreen.getSlotUnderMouse();
                if(hovered == null) return;
                var unlockable = Unlockables.getUnlockableByItem(hovered.getItem().getItem());
                if(unlockable.isPresent()){
                    var node = CodexEntries.getNode(unlockable.get());

                    mc.player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    mc.setScreen(new BookGui(node.chapter, true));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (Unlockables.getUnlockableByItem(stack.getItem()).isPresent()) {
            if (Screen.hasControlDown()) {
                event.getToolTip().add(Component.translatable("tooltip.valoria.open", Component.translatable("key.keyboard.left.control"), Component.translatable("key.mouse.right")).withStyle(ChatFormatting.GRAY));
            } else {
                event.getToolTip().add(Component.translatable("tooltip.valoria.info", Component.translatable("key.keyboard.left.control")).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    @SubscribeEvent
    public static void onTooltipRender(RenderTooltipEvent.Pre event){
        ItemStack stack = event.getItemStack();
        if(Unlockables.getUnlockableByItem(stack.getItem()).isPresent()){
            GuiGraphics gfx = event.getGraphics();
            PoseStack pose = gfx.pose();

            pose.pushPose();
            pose.translate(0, 0, 500);

            int tooltipHeight = event.getComponents().size() * event.getFont().lineHeight;
            int iconX = event.getX();
            int iconY = event.getY() + tooltipHeight - 18;
            gfx.renderItem(ItemsRegistry.codex.get().getDefaultInstance(), iconX, iconY);
            pose.popPose();
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            if (player.tickCount % 60 == 0) {
                ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
                Set<Unlockable> unlocked = UnlockUtils.getUnlocked(serverPlayer);
                if (unlocked != null) all.removeAll(unlocked);
                for (Unlockable unknown : all) {
                    unknown.tick(serverPlayer);
                }
            }
        }
    }

    @SubscribeEvent
    public void onFluid(BlockEvent.FluidPlaceBlockEvent e) {
        if(e.getNewState().is(Blocks.STONE) || e.getNewState().is(Blocks.COBBLESTONE)){
            if(e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY){
                e.setNewState(BlockRegistry.picrite.get().defaultBlockState());
            }
        }
    }

    @SubscribeEvent
    public void convertEvent(LivingConversionEvent.Pre ev){
        if(ev.getEntity() instanceof Zombie zombie){
            if(zombie.level().getBiome(zombie.blockPosition()).is(Biomes.IS_SWAMP)) {
                zombie.convertTo(EntityTypeRegistry.SWAMP_WANDERER.get(), false);
            }
        }
    }

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            event.addCapability(new ResourceLocation(Valoria.ID, "pages"), new UnloackbleCap());
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
    public void onAttack(AttackEntityEvent event){
        var attacker = event.getEntity();
        var target = event.getTarget();
        if(event.isCancelable() && attacker.hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }

        for(ItemStack armorPiece : attacker.getArmorSlots()){
            if(armorPiece.getItem() instanceof HitEffectArmorItem hitEffect){
                if(!attacker.level().isClientSide){
                    hitEffect.onAttack(event);
                }
            }
        }

        if(target instanceof LivingEntity living){
            if(!SuitArmorItem.hasCorrectArmorOn(ArmorRegistry.CRIMTANE, attacker)) return;
            for(var entry : AbstractArmorRegistry.EFFECTS.entrySet()){
                for(var effectData : entry.getValue()){
                    if(entry.getKey() != ArmorRegistry.CRIMTANE) continue;
                    if(Tmp.rnd.chance(0.25f)) return;

                    if(effectData.condition().test(attacker)){
                        MobEffect effect = effectData.effect().get();
                        if(living.hasEffect(effect)) return;

                        living.addEffect(new MobEffectInstance(effect, 400));
                        if(Tmp.rnd.nextFloat() > 0.4f){
                            attacker.getInventory().hurtArmor(attacker.damageSources().magic(), 2f, Inventory.ALL_ARMOR_SLOTS);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEffectApply(MobEffectEvent.Applicable event){
        var entity = event.getEntity();
        var effect = event.getEffectInstance();
        if(effect.getEffect() instanceof AbstractImmunityEffect immunityEffect){
            if(immunityEffect.effectRemoveReason(entity)){
                event.setResult(Result.DENY);
            }
        }

        if(effect.getEffect() == MobEffects.POISON){
            if(CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(TagsRegistry.POISON_IMMUNE), entity).isPresent()){
                event.setResult(Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        var pSource = event.getSource();
        var entity = event.getEntity();
        if(pSource.getEntity() instanceof LivingEntity e){
            if(e.hasEffect(EffectsRegistry.STUN.get())) event.setCanceled(true);
        }

        if(entity instanceof Player plr){
            if(pSource.is(DamageTypes.EXPLOSION) || pSource.is(DamageTypes.PLAYER_EXPLOSION)){
                if(SuitArmorItem.hasCorrectArmorOn(ArmorRegistry.PYRATITE, plr)) event.setCanceled(true);
            }
        }

        if(pSource.getEntity() instanceof LivingEntity e){
            if(getEquippedCurio(TagsRegistry.INFLICTS_FIRE, e)) entity.setSecondsOnFire(15);
        }

        if(pSource.is(DamageTypeTags.IS_FIRE) && getEquippedCurio(TagsRegistry.FIRE_IMMUNE, entity)) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getEffect(EffectsRegistry.STUN.get()) != null){
            entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.0D, entity.getDeltaMovement().z());
        }
    }

    @SubscribeEvent
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (event.isCancelable() && player.hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        LivingEntity living = event.getEntity();
        if (event.isCancelable() && living.hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living) {
            if (event.isCancelable() && living.hasEffect(EffectsRegistry.STUN.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event) {
        LivingEntity living = event.getEntity();
        if (living != null) {
            if (event.isCancelable() && living.hasEffect(EffectsRegistry.STUN.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        if (event.isCancelable() && event.getPlayer().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickEmpty event) {
        if (event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.LeftClickEmpty event){
        if(event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onHeal(LivingHealEvent event) {
        if (event.getEntity().hasEffect(EffectsRegistry.EXHAUSTION.get())) {
            int amplifier = event.getEntity().getEffect(EffectsRegistry.EXHAUSTION.get()).getAmplifier();
            float healMultiplier = 1.0f / (1.0f + 0.5f * (amplifier + 1));
            event.setAmount(event.getAmount() * healMultiplier);
        }

        if (event.getEntity().hasEffect(EffectsRegistry.RENEWAL.get())) {
            int amplifier = event.getEntity().getEffect(EffectsRegistry.RENEWAL.get()).getAmplifier();
            float healMultiplier = 1.0f + 0.5f * (amplifier + 1);
            event.setAmount(event.getAmount() * healMultiplier);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())) {
            event.setCanceled(true);
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

                        var event = new SoulEvent.Added(plr.getMainHandItem(), 1);
                        if(!MinecraftForge.EVENT_BUS.post(event)){
                            soul.addCount(event.count, itemStack, plr);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSoulCollect(SoulEvent.Added event) {
        if(event.stack.getItem() instanceof TieredItem tiered) {
            if(tiered.getTier() == ItemTierRegistry.HALLOWEEN) {
                event.addCount(2);
            }
        }
    }

    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event) {
        if(event.getItemStack().getItem() instanceof TieredItem tiered){
            if(tiered.getTier() == ItemTierRegistry.HALLOWEEN){
                var list = event.getToolTip();
                list.add(1, Component.translatable("tooltip.valoria.soul_on_kill", 2).withStyle(ChatFormatting.AQUA));
            }
        }
    }

    public boolean getEquippedCurio(TagKey<Item> tag, LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(tag), entity).isPresent();
    }


    public boolean getEquippedCurio(Item item, LivingEntity entity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(item, entity).isPresent();
    }

    @SubscribeEvent
    public void critDamage(CriticalHitEvent event){
        Player plr = event.getEntity();
        float f2 = plr.getAttackStrengthScale(0.5F);
        boolean flag = f2 > 0.9F;
        if(flag && plr.onGround()){
            if(getEquippedCurio(ItemsRegistry.lesserRuneAccuracy.get(), event.getEntity())){
                if(arcRandom.chance(0.25f)){
                    event.setResult(Result.ALLOW);
                    event.setDamageModifier(1.25f);
                }
            }

            if(getEquippedCurio(ItemsRegistry.runeAccuracy.get(), event.getEntity())){
                if(arcRandom.chance(0.5f)){
                    event.setResult(Result.ALLOW);
                    event.setDamageModifier(1.25f);
                }
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
}