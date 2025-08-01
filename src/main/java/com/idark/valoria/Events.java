package com.idark.valoria;

import com.idark.valoria.api.events.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.AttributeReg.*;
import com.idark.valoria.registries.effect.*;
import com.idark.valoria.registries.entity.*;
import com.idark.valoria.registries.item.armor.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.elemental.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
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
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.util.ValoriaUtils.*;

@SuppressWarnings("removal")
public class Events{
    public ArcRandom arcRandom = Tmp.rnd;

    @SubscribeEvent
    public void onReload(AddReloadListenerEvent event) {
        Valoria.LOGGER.info("Reloading Codex Chapters...");
        CodexEntries.initChapters();
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
                        MobEffect effect = effectData.instance().get().getEffect();
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
            if(isEquippedCurio(TagsRegistry.POISON_IMMUNE, entity)){
                event.setResult(Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event){
        if(event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) return;

        Entity attackerEntity = event.getSource().getEntity();
        if (!(attackerEntity instanceof LivingEntity attacker)) return;

        LivingEntity target = event.getEntity();
        float totalBonus = 0f;
        for (ElementalType type : ElementalTypes.ELEMENTALS){
            AttributeInstance attackAttr = attacker.getAttribute(type.damageAttr().get());
            AttributeInstance resistAttr = target.getAttribute(type.resistAttr().get());
            if(attackAttr != null){
                float damage = (float)attackAttr.getValue();
                float resistance = (float)(resistAttr != null ? resistAttr.getValue() : 0);

                boolean flag = attackAttr.getAttribute() != AttributeReg.VOID_DAMAGE.get() && target.getAttribute(AttributeReg.ELEMENTAL_RESISTANCE.get()) != null;
                resistance += (float)(flag ? target.getAttributeValue(AttributeReg.ELEMENTAL_RESISTANCE.get()) : 0);

                float multiplier = Math.max(1f - (resistance / 100f), 0f);
                totalBonus += damage * multiplier;
            }
        }

        event.setAmount(event.getAmount() + totalBonus);
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

        if(pSource.getDirectEntity() instanceof LivingEntity e){
            if(isEquippedCurio(TagsRegistry.INFLICTS_FIRE, e)) entity.setSecondsOnFire(15);
        }

        if(pSource.is(DamageTypeTags.IS_FIRE) && isEquippedCurio(TagsRegistry.FIRE_IMMUNE, entity)) event.setCanceled(true);
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
    public void onLivingHeal(LivingHealEvent event) {
        float amount = event.getAmount();
        if (event.getEntity().hasEffect(EffectsRegistry.EXHAUSTION.get())) {
            int amplifier = event.getEntity().getEffect(EffectsRegistry.EXHAUSTION.get()).getAmplifier();
            float healMultiplier = 1.0f - 0.1f * (amplifier + 1);

            healMultiplier = Math.max(0.5f, healMultiplier);
            amount = amount * healMultiplier;
        }

        if (event.getEntity().hasEffect(EffectsRegistry.RENEWAL.get())) {
            int amplifier = event.getEntity().getEffect(EffectsRegistry.RENEWAL.get()).getAmplifier();
            float healMultiplier = 1.0f + 0.1f * (amplifier + 1);

            healMultiplier = Math.min(1.5f, healMultiplier);
            amount = amount * healMultiplier;
        }

        event.setAmount(amount);
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

                    if(itemStack.getItem() instanceof EtherealSwordItem soul){
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

    @SubscribeEvent
    public void critDamage(CriticalHitEvent event){
        Player plr = event.getEntity();
        float f2 = plr.getAttackStrengthScale(0.5F);
        boolean flag = f2 > 0.9F;
        if(flag && plr.onGround()){
            var curioStack = getEquippedCurio((item) -> item.getItem() instanceof CritDamageItem, event.getEntity());
            if(curioStack != null){
                ((CritDamageItem)curioStack.getItem()).critDamage(event);
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