package com.idark.valoria;

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
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
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
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import top.theillusivec4.curios.api.*;

@SuppressWarnings("removal")
public class Events{
    public ArcRandom arcRandom = Tmp.rnd;

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
        if(event.isCancelable() && event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
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
                        soul.addCount(1, itemStack, plr);
                    }
                }
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