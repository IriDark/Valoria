package com.idark.valoria;

import com.google.common.collect.*;
import com.idark.valoria.api.events.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.client.ui.screen.book.codex.checklist.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
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
import com.idark.valoria.registries.item.types.consumables.*;
import com.idark.valoria.registries.item.types.elemental.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.registries.level.events.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
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
import net.minecraftforge.common.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.TickEvent.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraftforge.event.level.*;
import net.minecraftforge.eventbus.api.Event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.render.text.DotStyleEffects.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.util.ValoriaUtils.*;

@SuppressWarnings("removal")
public class Events{
    public ArcRandom arcRandom = Tmp.rnd;

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event){
        for(var mapping : event.getMappings(ForgeRegistries.Keys.BLOCKS, "valoria")){
            String oldId = mapping.getKey().getPath();
            switch(oldId){
                case "shadewood" -> {
                    mapping.remap(BlockRegistry.shadeWood.get());
                    continue;
                }
                case "stripped_shadewood" -> {
                    mapping.remap(BlockRegistry.strippedShadeWood.get());
                    continue;
                }
                case "stripped_shadelog" -> {
                    mapping.remap(BlockRegistry.strippedShadeLog.get());
                    continue;
                }
                case "shadelog" -> {
                    mapping.remap(BlockRegistry.shadeLog.get());
                    continue;
                }
                case "trapped_shadewood_chest" -> {
                    mapping.remap(BlockRegistry.shadeTrappedChest.get());
                    continue;
                }
                case "potted_shadewood_sappling" -> {
                    mapping.remap(BlockRegistry.pottedShadewoodSapling.get());
                    continue;
                }
            }

            if(oldId.startsWith("dreadwood_")){
                String newId = oldId.replace("dreadwood_", "dread_");
                var newItem = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("valoria", newId));
                if(newItem != null){
                    mapping.remap(newItem);
                    Valoria.LOGGER.error("[REMAP] Remmaping: {} to {}", oldId, newId);
                }else{
                    mapping.ignore();
                }
            }

            if(oldId.startsWith("shadewood_")){
                String newId = oldId.replace("shadewood_", "shade_");
                var newBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("valoria", newId));
                if(newBlock != null){
                    mapping.remap(newBlock);
                    Valoria.LOGGER.error("[REMAP] Remmaping: {} to {}", oldId, newId);
                }else{
                    mapping.ignore();
                }
            }
        }

        for(var mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, "valoria")){
            String oldId = mapping.getKey().getPath();
            switch(oldId){
                case "shadewood" -> {
                    mapping.remap(BlockRegistry.shadeWood.get().asItem());
                    continue;
                }
                case "stripped_shadewood" -> {
                    mapping.remap(BlockRegistry.strippedShadeWood.get().asItem());
                    continue;
                }
                case "stripped_shadelog" -> {
                    mapping.remap(BlockRegistry.strippedShadeLog.get().asItem());
                    continue;
                }
                case "shadelog" -> {
                    mapping.remap(BlockRegistry.shadeLog.get().asItem());
                    continue;
                }
                case "trapped_shadewood_chest" -> {
                    mapping.remap(BlockRegistry.shadeTrappedChest.get().asItem());
                    continue;
                }
            }

            if(oldId.startsWith("dreadwood_")){
                String newId = oldId.replace("dreadwood_", "dread_");
                var newItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("valoria", newId));
                if(newItem != null){
                    mapping.remap(newItem);
                    Valoria.LOGGER.error("[REMAP] Remmaping: {} to {}", oldId, newId);
                }else{
                    mapping.ignore();
                }
            }

            if(oldId.startsWith("shadewood_")){
                String newId = oldId.replace("shadewood_", "shade_");
                var newItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("valoria", newId));
                if(newItem != null){
                    mapping.remap(newItem);
                    Valoria.LOGGER.error("[REMAP] Remmaping: {} to {}", oldId, newId);
                }else{
                    mapping.ignore();
                }
            }
        }
    }

    @SubscribeEvent
    public void onReload(AddReloadListenerEvent event){
        Valoria.LOGGER.info("Reloading Codex Chapters...");
        CodexEntries.initChapters();
        event.addListener(BossEntryLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if(stack.hasTag() && stack.getTag().contains("poison_hits")){
            int hits = stack.getTag().getInt("poison_hits");
            ImmutableList<MobEffectInstance> list = ImmutableList.of(new MobEffectInstance(MobEffects.POISON, 120, 0));
            tooltip.add(Component.translatable("tooltip.valoria.poisoned", hits).withStyle(ChatFormatting.GRAY));
            Utils.Items.effectTooltip(list, tooltip, 1, 1);
        }

        if(Unlockables.getUnlockableByItem(stack.getItem()).isPresent()){
            if(Screen.hasControlDown()){
                tooltip.add(Component.translatable("tooltip.valoria.open", Component.translatable("key.keyboard.left.control"), Component.translatable("key.mouse.right")).withStyle(ChatFormatting.GRAY));
            }else{
                tooltip.add(Component.translatable("tooltip.valoria.info", Component.translatable("key.keyboard.left.control")).withStyle(ChatFormatting.DARK_GRAY));
            }
        }

        if(stack.getItem() instanceof TieredItem tiered){
            if(tiered.getTier() == ItemTierRegistry.HALLOWEEN){
                tooltip.add(1, Component.translatable("tooltip.valoria.soul_on_kill", 2).withStyle(ChatFormatting.AQUA).withStyle(style -> style.withFont(Valoria.FONT)));
            }
        }

        int foodRot = ValoriaUtils.getCurrentNBTValue("ValoriaRot", stack);
        if(foodRot > 0) {
            String stageKey;
            ChatFormatting color;

            if (foodRot >= 60) {
                stageKey = "tooltip.valoria.stage.rotting";
                color = ChatFormatting.RED;
            } else if (foodRot >= 30) {
                stageKey = "tooltip.valoria.stage.stale";
                color = ChatFormatting.YELLOW;
            } else {
                stageKey = "tooltip.valoria.stage.fresh";
                color = ChatFormatting.GREEN;
            }

            var status = Component.translatable(stageKey).withStyle(color);
            var line = Component.translatable("tooltip.valoria.rot_status", status, foodRot).withStyle(ChatFormatting.GRAY);
            tooltip.add(1, line);
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event){
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if(event.isCancelable() && player.hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }

        if(stack.hasTag() && stack.getTag().contains("poison_hits")){
            int hits = stack.getTag().getInt("poison_hits");
            if(hits > 0 && event.getTarget() instanceof LivingEntity target){
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
                stack.getTag().putInt("poison_hits", hits - 1);
                if(hits - 1 == 0){
                    stack.getTag().remove("poison_hits");
                }
            }
        }

        for(ItemStack armorPiece : player.getArmorSlots()){
            if(armorPiece.getItem() instanceof HitEffectArmorItem hitEffect){
                if(!player.level().isClientSide){
                    hitEffect.onAttack(event);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerRespawnEvent ev){
        Player player = ev.getEntity();
        player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> {
            nihilityLevel.setAmountFromServer(player, 0);
        });
    }

    @SubscribeEvent
    public void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event){
        Player player = event.getEntity();
        if(event.getTo() == LevelGen.VALORIA_KEY){
            onValoriaEnter(player);
        }
    }

    public void onValoriaEnter(Player player){
        Level level = player.level();
        if(level instanceof ServerLevel s && player instanceof ServerPlayer sp){
            ResourceLocation loc = Valoria.loc("advancements/valoria/visit_the_valoria.json");
            Advancement adv = s.getServer().getAdvancements().getAdvancement(loc);
            if(adv == null) return;

            if(!sp.getAdvancements().getOrStartProgress(adv).isDone()) {
                player.displayClientMessage(Component.translatable("tooltip.valoria.nihility").withStyle(DotStyle.of().effects(WaveFX.of(0.25f, 0.1f), OutlineFX.of(Pal.amethyst, true))), true);
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        if(!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer){
            if(CommonConfig.NIHILITY.get()){
                player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> {
                    if(!player.getAbilities().instabuild && !player.isSpectator()){
                        NihilityEvent.tick(event, nihilityLevel, player);
                    }
                });
            }

            if(player.tickCount % 60 == 0){
                ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
                Set<Unlockable> unlocked = UnlockUtils.getUnlocked(serverPlayer);
                if(unlocked != null) all.removeAll(unlocked);
                for(Unlockable unknown : all){
                    unknown.tick(serverPlayer);
                }
            }
        }

        if(CommonConfig.NIHILITY.get()){
            if(player.level().isClientSide()){
                player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> NihilityEvent.clientTick(nihilityLevel, player));
            }
        }

        if(CommonConfig.FOOD_ROT.get()){
            if(player.level().dimension().equals(LevelGen.VALORIA_KEY)){
                if(player.tickCount % 60 == 0){
                    Inventory inv = player.getInventory();
                    for(int i = 0; i < inv.getContainerSize(); i++){
                        ItemStack stack = inv.getItem(i);
                        if(stack.isEdible() && stack.getUseAnimation() == UseAnim.EAT && !(stack.getItem() instanceof ValoriaFood)){
                            CompoundTag tag = stack.getOrCreateTag();
                            ValoriaUtils.addNBT("ValoriaRot", 1, 100, stack);
                            int rot = tag.getInt("ValoriaRot");
                            if(rot == 100){
                                convertToRot(event, stack, inv, i);
                            }else{
                                tag.putInt("ValoriaRot", rot);
                                stack.setTag(tag);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void convertToRot(PlayerTickEvent event, ItemStack stack, Inventory inv, int i){
        CompoundTag tag;
        ItemStack rotStack = new ItemStack(ItemsRegistry.rot.get());
        tag = rotStack.getOrCreateTag();

        rotStack.setTag(tag.copy());
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(key == null) return;

        tag.putString("OriginalItem", key.toString());
        rotStack.setTag(tag);
        rotStack.setCount(stack.getCount());
        inv.setItem(i, rotStack);
        event.player.playSound(SoundEvents.FROGSPAWN_PLACE);
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
            event.addCapability(new ResourceLocation(Valoria.ID, "nihility_level"), new NihilityLevelCap());
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
    public void onMobKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity victim = event.getEntity();

            ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
            Set<Unlockable> unlocked = UnlockUtils.getUnlocked(player);
            if(unlocked != null) all.removeAll(unlocked);
            for(Unlockable unknown : all){
                if(unknown instanceof OnMobKilledListener entityU) entityU.checkCondition(player, victim);
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
        if(!(attacker instanceof Player && target instanceof Player)){
            for(ElementalType type : ElementalTypes.ELEMENTALS){
                AttributeInstance attackAttr = attacker.getAttribute(type.damageAttr().get());
                AttributeInstance resistAttr = target.getAttribute(type.resistAttr().get());
                if(attackAttr != null){
                    totalBonus = applyAttackBonus(attackAttr, resistAttr, target, totalBonus);
                }
            }
        }

        event.setAmount(event.getAmount() + totalBonus);
    }

    private static float applyAttackBonus(AttributeInstance attackAttr, AttributeInstance resistAttr, LivingEntity target, float totalBonus){
        float damage = (float)attackAttr.getValue();
        float resistance = (float)(resistAttr != null ? resistAttr.getValue() : 0);

        boolean flag = attackAttr.getAttribute() != AttributeReg.NIHILITY_DAMAGE.get() && target.getAttribute(AttributeReg.ELEMENTAL_RESISTANCE.get()) != null;
        resistance += (float)(flag ? target.getAttributeValue(AttributeReg.ELEMENTAL_RESISTANCE.get()) : 0);
        if(attackAttr.getAttribute() == AttributeReg.NIHILITY_DAMAGE.get()){
            target.getCapability(INihilityLevel.INSTANCE).ifPresent(nihility -> nihility.modifyAmount(target, damage));
        }

        float multiplier = Math.max(1f - (resistance / 100f), 0f);
        totalBonus += damage * multiplier;
        return totalBonus;
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        var pSource = event.getSource();
        var entity = event.getEntity();
        var level = entity.level();
        if(pSource.getEntity() instanceof LivingEntity attacker){
            ILivingEntityData data = (ILivingEntityData)entity;
            if(level instanceof ServerLevel s){
                var pushDirection = new Vec3(entity.getX() + attacker.getX(), 0.0D, entity.getZ() + attacker.getX()).normalize();
                if(attacker.getAttribute(AttributeReg.MISS_CHANCE.get()) != null && Tmp.rnd.chance(attacker.getAttributeValue(AttributeReg.MISS_CHANCE.get()) / 100)){
                    level.playSound(null, attacker.blockPosition(), SoundsRegistry.MISS.get(), SoundSource.HOSTILE);
                    s.sendParticles(ParticleTypes.SMOKE, attacker.getX(), attacker.getY(), attacker.getZ(), 16, 1, 1, 1, 0.025f);

                    data.valoria$missTime(10);
                    if(attacker instanceof Player player) player.displayClientMessage(Component.translatable("popup.valoria.miss"), true);
                    event.setCanceled(true);
                }

                if(entity.getAttribute(AttributeReg.DODGE_CHANCE.get()) != null && Tmp.rnd.chance(entity.getAttributeValue(AttributeReg.DODGE_CHANCE.get()) / 100)){
                    level.playSound(null, entity.blockPosition(), SoundsRegistry.DODGE.get(), SoundSource.HOSTILE);
                    knockbackEntity(entity, pushDirection.reverse());
                    s.sendParticles(ParticleTypes.ENCHANTED_HIT, entity.getX(), entity.getY(), entity.getZ(), 16, 1, 1, 1, 0.025f);

                    data.valoria$dodgeTime(10);
                    if(entity instanceof Player player) player.displayClientMessage(Component.translatable("popup.valoria.dodge"), true);
                    event.setCanceled(true);
                }
            }
        }

        if(entity instanceof Player plr){
            if(pSource.is(DamageTypes.EXPLOSION) || pSource.is(DamageTypes.PLAYER_EXPLOSION)){
                if(SuitArmorItem.hasCorrectArmorOn(ArmorRegistry.PYRATITE, plr)) event.setCanceled(true);
            }
        }

        if((pSource.is(DamageTypes.IN_FIRE) || pSource.is(DamageTypes.ON_FIRE) || pSource.is(DamageTypes.HOT_FLOOR) || pSource.is(DamageTypes.UNATTRIBUTED_FIREBALL) || pSource.is(DamageTypes.FIREBALL)) && isEquippedCurio(TagsRegistry.FIRE_IMMUNE, entity)) event.setCanceled(true);
        if(pSource.getEntity() instanceof LivingEntity e){
            if(e.hasEffect(EffectsRegistry.STUN.get())) event.setCanceled(true);
        }

        if(pSource.getDirectEntity() instanceof Player player){
            float f2 = player.getAttackStrengthScale(0.5F);
            boolean flag = f2 > 0.9F;
            if(isEquippedCurio(TagsRegistry.INFLICTS_FIRE, player) && flag) {
                entity.setSecondsOnFire(15);
            }
        }
    }

    private static void knockbackEntity(LivingEntity entity, Vec3 pushDirection){
        entity.hurtMarked = true;
        entity.knockback(0.5f, pushDirection.x, pushDirection.z);
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
                    if(itemStack.getItem() instanceof SoulCollectorItem soul){
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
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(IUnlockable.INSTANCE).ifPresent((k) -> event.getOriginal().getCapability(IUnlockable.INSTANCE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>)k).deserializeNBT(((INBTSerializable<CompoundTag>)o).serializeNBT())));
        if(!event.getEntity().level().isClientSide){
            PacketHandler.sendTo((ServerPlayer)event.getEntity(), new UnlockableUpdatePacket(event.getEntity()));
        }

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(INihilityLevel.INSTANCE).ifPresent((k) -> event.getOriginal().getCapability(INihilityLevel.INSTANCE).ifPresent((o) ->
        ((INBTSerializable<CompoundTag>)k).deserializeNBT(((INBTSerializable<CompoundTag>)o).serializeNBT())));
        if(!event.getEntity().level().isClientSide){
            PacketHandler.sendTo((ServerPlayer)event.getEntity(), new NihilityPacket(new NihilityLevelProvider(), event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide){
            if(event.getEntity() instanceof Player player){
                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new UnlockableUpdatePacket(player));
                player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihility -> {
                    nihility.modifyAmount(player, 1);
                    nihility.decrease(player, 1);
                });

                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new NihilityPacket(new NihilityLevelProvider(), player));

            }
        }
    }
}