package com.idark.valoria.util;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.mixin.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.items.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.func.*;
import top.theillusivec4.curios.api.*;

import javax.annotation.*;
import java.awt.*;
import java.lang.Math;
import java.util.*;
import java.util.List;
import java.util.function.*;

public class ValoriaUtils{

    /**
     * Spawns a bunch of entities with the equipment gathered from a LootTable (equipment chances are calculated inside the LootTable itself)
     * @param pEntityType Entity type that will be spawned
     * @param totalTries Total tries that will be executed in the loop to perform a spawn try
     * @param loot LootTable resource location
     */
    public static void spawnEntities(int totalTries, EntityType<? extends LivingEntity> pEntityType, ServerLevel pLevel, BlockPos pPos, ResourceLocation loot){
        RandomSource rand = pLevel.getRandom();
        for(int tries = 0; tries < totalTries; tries++){
            double x = (double)pPos.getX() + (rand.nextDouble() - rand.nextDouble()) * 6;
            double y = pPos.getY() + rand.nextInt(0, 3);
            double z = (double)pPos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 6;
            if(pLevel.noCollision(null, pEntityType.getAABB(x, y, z))){
                LivingEntity pEntity = pEntityType.create(pLevel);
                if(pEntity == null) continue;

                pEntity.moveTo(x, y, z, rand.nextFloat() * 360.0F, 0.0F);
                applyLootGear(pLevel, pPos, loot, pEntity);
                pLevel.addFreshEntity(pEntity);
            }
        }
    }

    public static void applyLootGear(ServerLevel pLevel, BlockPos pPos, ResourceLocation loot, LivingEntity pEntity){
        var params = new LootParams.Builder(pLevel).withParameter(LootContextParams.THIS_ENTITY, pEntity).withParameter(LootContextParams.ORIGIN, pPos.getCenter()).create(LootContextParamSets.GIFT);
        Utils.Items.createLoot(loot, params)
        .forEach(stack -> {
            EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);
            if(pEntity.hasItemInSlot(slot)) return;

            pEntity.setItemSlot(slot, stack);
            if (pEntity instanceof Mob mob) {
                mob.setDropChance(slot, 0.0F);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getRandomItemFromTag(RandomSource randomSource, TagKey<Item> weaponTag) {
        var optionalTag = BuiltInRegistries.ITEM.getTag(weaponTag);
        if (optionalTag.isPresent()) {
            Holder<Item> randomItem = optionalTag.get().getRandomElement(randomSource).orElse(null);
            if (randomItem != null) {
                return new ItemStack(randomItem);
            }
        }

        return ItemStack.EMPTY;
    }

    @SuppressWarnings("deprecation")
    public static EntityType<?> getRandomEntityFromTag(RandomSource randomSource, TagKey<EntityType<?>> entityTag) {
        var optionalTag = BuiltInRegistries.ENTITY_TYPE.getTag(entityTag);
        if (optionalTag.isPresent()) {
            Holder<EntityType<?>> randomEntity = optionalTag.get().getRandomElement(randomSource).orElse(null);
            if (randomEntity != null) {
                return randomEntity.value();
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static MobEffect getRandomEffectFromTag(RandomSource randomSource, TagKey<MobEffect> effectTag) {
        var optionalTag = BuiltInRegistries.MOB_EFFECT.getTag(effectTag);
        if (optionalTag.isPresent()) {
            Holder<MobEffect> randomEffect = optionalTag.get().getRandomElement(randomSource).orElse(null);
            if (randomEffect != null) {
                return randomEffect.value();
            }
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    public static List<MobEffect> getEffectsFromTag(TagKey<MobEffect> effectTag) {
        List<MobEffect> effects = new ArrayList<>();
        var optionalTag = BuiltInRegistries.MOB_EFFECT.getTag(effectTag);
        if (optionalTag.isPresent()) {
            for (Holder<MobEffect> holder : optionalTag.get()) {
                effects.add(holder.value());
            }
        }

        return effects;
    }

    public static int getCurrentNBTValue(String key, ItemStack pStack) {
        return pStack.getOrCreateTag().getInt(key);
    }

    public static void shrinkNBT(String key, int count, ItemStack pStack){
        pStack.getOrCreateTag().putInt(key, Math.max(getCurrentNBTValue(key, pStack) - count, 0));
    }

    public static void addNBT(String key, int count, int max, ItemStack pStack){
        if(getCurrentNBTValue(key, pStack) < max){
            pStack.getOrCreateTag().putInt(key, getCurrentNBTValue(key, pStack) + count);
        }
    }

    public static String formatDuration(int tickDuration, float pDurationFactor) {
        int i = Mth.floor((float)tickDuration * pDurationFactor);
        return StringUtil.formatTickDuration(i);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderText(LivingEntity entityIn, Col textColor, Component component, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, int time){
        if (!(entityIn instanceof ILivingEntityData data)) return;
        float partialTicks = Minecraft.getInstance().getPartialTick();
        data.valoria$setTextOffset(Mth.lerp(partialTicks, data.valoria$getTextOffset(), (float)Math.abs(Math.sin(((float)time) / 4f))));
        data.valoria$setTextOffsetPrev(data.valoria$getTextOffset());
        float alpha = data.valoria$getTextOffset();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0, entityIn.getBbHeight() + 0.25f +  data.valoria$getTextOffset(), 0.0D);
        matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        matrixStackIn.scale(-data.valoria$getTextOffset() / 20f, -data.valoria$getTextOffset() / 20f, data.valoria$getTextOffset() / 20f);
        Matrix4f matrix4f = matrixStackIn.last().pose();

        Font font = Minecraft.getInstance().font;
        Color color = new Color(textColor.r, textColor.g, textColor.b, alpha);
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;
        float f2 =(float)(-font.width(component) / 2);

        font.drawInBatch(component, f2 * data.valoria$getTextOffset(), (float)0, 553648127, false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, j, packedLightIn);
        font.drawInBatch(component, f2 * data.valoria$getTextOffset(), data.valoria$getTextOffset(), color.getRGB(), false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, 0, packedLightIn);
        matrixStackIn.popPose();
    }

    public static boolean isVisibleInScissor(GuiGraphics gui, int x, int y, int w, int h, int scissorX, int scissorY, int scissorW, int scissorH) {
        AbsRect s = AbsRect.xywhDef((float)scissorX, (float)scissorY, (float)scissorW, (float)scissorH).pose(gui.pose());
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(gui.pose());
        return r.x < s.x2 && r.x2 > s.x && r.y < s.y2 && r.y2 > s.y;
    }

    @Nullable
    public static ItemStack getEquippedCurio(Predicate<ItemStack> filter, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio(filter, entity);
        return curio.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(null);
    }

    public static boolean isEquippedCurio(Predicate<ItemStack> filter, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio(filter, entity);
        return curio.isPresent();
    }

    public static boolean isEquippedCurio(TagKey<Item> tag, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(tag), entity);
        return curio.isPresent();
    }

    public static boolean isEquippedCurio(Item pItem, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(pItem), entity);
        return curio.isPresent();
    }

    public static void addHandPlayerItem(Level level, Player player, InteractionHand hand, ItemStack stack, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) >= 0) {
            addPlayerItem(level, player, addStack);
        } else if (stack.isEmpty()) {
            player.setItemInHand(hand, addStack.copy());
        } else if (ItemHandlerHelper.canItemStacksStack(stack, addStack) && (stack.getCount() + addStack.getCount() <= addStack.getMaxStackSize())) {
            stack.setCount(stack.getCount() + addStack.getCount());
            player.setItemInHand(hand, stack);
        } else {
            addPlayerItem(level, player, addStack);
        }
    }

    public static void addPlayerItem(Level level, Player player, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) != -1 || player.getInventory().getFreeSlot() > -1) {
            player.getInventory().add(addStack.copy());
        } else {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), addStack.copy()));
        }
    }

    public static void SUpdateTileEntityPacket(BlockEntity tile){
        if(tile.getLevel() instanceof ServerLevel){
            Packet<?> packet = tile.getUpdatePacket();
            if(packet != null){
                BlockPos pos = tile.getBlockPos();
                ((ServerChunkCache)tile.getLevel().getChunkSource()).chunkMap
                .getPlayers(new ChunkPos(pos), false)
                .forEach(e -> e.connection.send(packet));
            }
        }
    }

    public static float enchantmentAccuracy(ItemStack stack) {
        int i = stack.getEnchantmentLevel(EnchantmentsRegistry.ACCURACY.get());
        return i > 0 ? i + 0.5f : 0.0F;
    }

    /**
     * Performs a circled attack near player
     *
     * @param baseRadius  Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, ItemStack stack, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float baseRadius){
        float radius = baseRadius + Utils.Items.enchantmentRadius(stack);
        radiusHit(level, player, type, hitEntities, pos, pitchRaw, yawRaw, radius);
    }

    /**
     * Performs a circled attack near player
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius){
        AABB hitBox = new AABB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, hitBox);
        double radiusSqr = radius * radius;
        for(LivingEntity target : entities){
            if(target == player || hitEntities.contains(target)) continue;
            if(target.distanceToSqr(pos.x, pos.y, pos.z) <= radiusSqr){
                if(Utils.Entities.canHitTarget(player, target)){
                    hitEntities.add(target);
                }
            }
        }

        if(type != null && !level.isClientSide() && level instanceof ServerLevel pServer){
            double pitchRad = ((pitchRaw + 90) * Math.PI) / 180;
            double yawRad = ((yawRaw + 90) * Math.PI) / 180;

            double cosPitch = Math.cos(pitchRad);
            double sinPitch = Math.sin(pitchRad);

            double visualRadius = radius * 0.75F;
            for(int i = 0; i < 360; i += 10){
                double yawOffsetRad = yawRad + (i * Math.PI / 180);

                double xOffset = sinPitch * Math.cos(yawOffsetRad) * visualRadius;
                double yOffset = cosPitch * visualRadius;
                double zOffset = sinPitch * Math.sin(yawOffsetRad) * visualRadius;

                pServer.sendParticles(type, pos.x + xOffset, pos.y + yOffset + ((Math.random() - 0.5D) * 0.2F), pos.z + zOffset, 1, 0, 0, 0, 0);
            }
        }
    }

    public static void stunNearby(Level level, LivingEntity caster, Vector3d pos, float pitchRaw, float yawRaw, float radius){
        AABB hitBox = new AABB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, hitBox);
        double radiusSqr = radius * radius;
        for(LivingEntity target : entities){
            if(!target.equals(caster)) continue;
            if(target.distanceToSqr(pos.x, pos.y, pos.z) <= radiusSqr){
                if(Utils.Entities.canHitTarget(caster, target)){
                    target.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 30, 0));
                }
            }
        }
    }

    public static List<ItemStack> getLootTableItems(LootTable table) {
        List<ItemStack> allDrops = new ArrayList<>();
        List<LootPool> pools = ((LootTableAccessor) table).getPools();
        for (LootPool pool : pools) {
            LootPoolEntryContainer[] entries = pool.entries;
            for (LootPoolEntryContainer entry : entries) {
                if (entry instanceof LootItem itemEntry) {
                    Item item = itemEntry.item;
                    allDrops.add(new ItemStack(item));
                }
            }
        }

        return allDrops;
    }

    /**
     * Searches items in player inventory that equals an instance of GunpowderCharge
     */
    public static ItemStack getProjectile(Player player, ItemStack pShootable){
        Boolf<ItemStack> predicate = (stack) -> stack.getItem() instanceof GunpowderCharge;
        return Utils.Items.getProjectile(player, pShootable, predicate);
    }

    /**
     * Determines whether the given effect can be cured using curative items.
     * <p>
     * Returns true if the effect is negative and non-instantaneous (is harmful but wider).
     */
    public static boolean isCurable(MobEffectInstance e) {
        var effect = e.getEffect();
        return !effect.isBeneficial() && !effect.isInstantenous();
    }

    @SuppressWarnings({"removal", "UnstableApiUsage", "deprecation"})
    public static boolean onePerTypeEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.cosmetic()) return true;
        return CuriosApi.getCuriosHelper().findCurios(slotContext.entity(), stack.getItem())
                .stream().allMatch(result ->
                        result.slotContext().identifier().equals(slotContext.identifier())
                                && result.slotContext().index() == slotContext.index());
    }
}