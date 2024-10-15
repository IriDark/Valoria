package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.client.ui.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import org.joml.*;

import java.awt.*;
import java.lang.Math;
import java.util.List;

import static com.idark.valoria.Valoria.BASE_DASH_DISTANCE_UUID;
import static com.idark.valoria.util.ValoriaUtils.addContributorTooltip;

public class KatanaItem extends SwordItem implements CooldownNotifyItem {
    public float chance = 1;
    public int overlayTime = 35;
    public int cooldownTime = 75;
    public int chargeTime = 0;
    public boolean usePacket = false;
    public boolean hasLargeModel = true;
    public Color color;
    public final ImmutableList<MobEffectInstance> effects;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public ArcRandom arcRandom = new ArcRandom();
    private final AttributeModifier dashModifier;
    public ResourceLocation texture = new ResourceLocation(Valoria.ID, "textures/gui/overlay/speedlines.png");
    public ParticleOptions particleOptions = ParticleTypes.POOF;
    public SoundEvent soundEvent = SoundsRegistry.SWIFTSLICE.get();
    public SoundEvent chargedEvent;

    public KatanaItem(Builder builderIn) {
        super(builderIn.tier, builderIn.attackDamageIn, builderIn.attackSpeedIn, builderIn.itemProperties);
        this.effects = builderIn.effects;
        this.chargeTime = builderIn.chargeTime;
        this.overlayTime = builderIn.overlayTime;
        this.texture = builderIn.texture != null ? builderIn.texture : texture;
        this.particleOptions = builderIn.particleOptions;
        this.soundEvent = builderIn.soundEvent;
        this.chargedEvent = builderIn.chargedEvent;
        this.dashModifier = new AttributeModifier(BASE_DASH_DISTANCE_UUID, "Tool modifier", builderIn.dashDist, AttributeModifier.Operation.ADDITION);
        this.usePacket = builderIn.usePacket;
        this.hasLargeModel = builderIn.hasLargeModel;
        this.color = builderIn.dashColor;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", builderIn.attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", builderIn.attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.of();
        dashModifier = new AttributeModifier(BASE_DASH_DISTANCE_UUID, "Tool modifier", 1.8f, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    @Deprecated
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        dashModifier = new AttributeModifier(BASE_DASH_DISTANCE_UUID, "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    @Deprecated
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, float chance, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        this.chance = chance;
        dashModifier = new AttributeModifier(BASE_DASH_DISTANCE_UUID, "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count) {
        Player player = (Player) livingEntityIn;
        if (player.getTicksUsingItem() == chargeTime) {
            if(chargedEvent != null) {
                player.playNotifySound(chargedEvent, SoundSource.PLAYERS, 0.25f, 1);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    public static class Builder {
        public Tier tier = ModItemTier.NONE;
        public Item.Properties itemProperties;
        private ResourceLocation texture;
        public SoundEvent soundEvent;
        public SoundEvent chargedEvent;
        public Color dashColor;
        public boolean usePacket = false;
        public boolean hasLargeModel = true;
        public int attackDamageIn;
        public float attackSpeedIn;
        public float chance = 1;
        public int overlayTime = 35;
        public int cooldownTime = 75;
        public int chargeTime = 0;
        public float dashDist = 0.8f;
        public ImmutableList<MobEffectInstance> effects = ImmutableList.of();
        public ParticleOptions particleOptions;
        public Builder(int attackDamageIn, float attackSpeedIn, Properties itemProperties) {
            this.attackDamageIn = attackDamageIn;
            this.attackSpeedIn = attackSpeedIn;
            this.itemProperties = itemProperties;
        }

        public Builder setTier(Tier tier){
            this.tier = tier;
            return this;
        }

        /**
         * @param event Sound that will be played when dash is performed
         */
        public Builder setSound(SoundEvent event){
            this.soundEvent = event;
            return this;
        }

        /**
         * Currently a bit buged, called two times instead of one, but anyway :d
         * @param event Sound that will be played when Katana is ready to perform dash
         */
        public Builder setChargedSound(SoundEvent event){
            this.chargedEvent = event;
            return this;
        }

        /**
         * @param particleOptions Particle trail that will appear after dashing
         */
        public Builder setParticles(ParticleOptions particleOptions){
            this.particleOptions = particleOptions;
            return this;
        }

        /**
         * Particle trail that will appear after dashing, but sent through a DashParticlePacket
         * @param color Particle color
         */
        public Builder usePacket(Color color){
            this.usePacket = true;
            this.dashColor = color;
            return this;
        }

        public Builder removeLargeModelCheck(){
            this.hasLargeModel = false;
            return this;
        }

        /**
         * @param chance Chance of applying effects to target
         * @param pEffects Effects that will be applied to target
         */
        public Builder setEffects(float chance, MobEffectInstance... pEffects){
            this.chance = chance;
            this.effects = ImmutableList.copyOf(pEffects);
            return this;
        }

        /**
         * @param pEffects Effects that will be applied to target
         */
        public Builder setEffects(MobEffectInstance... pEffects){
            this.effects = ImmutableList.copyOf(pEffects);
            return this;
        }

        public Builder setTimeToCharge(int useTime){
            this.chargeTime = useTime;
            return this;
        }

        public Builder setOverlayTime(int time){
            this.overlayTime = time;
            return this;
        }

        public Builder setCooldownTime(int cooldownTime){
            this.cooldownTime = cooldownTime;
            return this;
        }

        public Builder setDashDistance(float distance) {
            this.dashDist = distance;
            return this;
        }

        /**
         * @param texture a ResourceLocation of texture that will be shown after dash is performed
         */
        public Builder setOverlay(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        /**
         * @return Build of KatanaItem with all the configurations you set :p
         */
        public KatanaItem build() {
            return new KatanaItem(this);
        }
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static double distance(double distance, Level level, Player player) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = ValoriaUtils.getHitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if (hitresult != null) {
            switch (hitresult.getType()) {
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult) hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(5, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public void applyCooldown(Player playerIn) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof KatanaItem) {
                playerIn.getCooldowns().addCooldown(item, cooldownTime);
            }
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isShiftKeyDown() && handIn != InteractionHand.OFF_HAND) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public double getDashDistance(Player player) {
        return player.getAttributeValue(AttributeRegistry.DASH_DISTANCE.get());
    }

    public int getHurtAmount(List<LivingEntity> detectedEntities) {
        return 5 + detectedEntities.size();
    }

    public void performEffects(LivingEntity targets, Player player) {
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if (EnchantmentHelper.getFireAspect(player) > 0) {
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.push(dir.x, dir.y * 0.25, dir.z);
        double ii = 1D;
        if(level instanceof ServerLevel srv){
            if(!usePacket){
                for(int i = 0; i < 10; i += 1){
                    double locDistance = i * 0.5D;
                    double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                    double Y = Math.cos(pitch) * 2;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                    srv.sendParticles(particleOptions, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 1, 0, 0.5, 0, 0);
                    List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                    for(LivingEntity entity : detectedEntities){
                        if(!entity.equals(player)){
                            entity.hurt(level.damageSources().playerAttack(player), (float)((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                            performEffects(entity, player);
                            ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
                            if(!player.isCreative()){
                                stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            }
                        }

                        ii = ii - (1D / (detectedEntities.size() * 2));
                    }

                    if(locDistance >= distance(dashDistance, level, player)){
                        break;
                    }
                }
            }else{
                PacketHandler.sendToTracking(srv, player.getOnPos(), new DashParticlePacket(player.getUUID(), 1, 0, 0, 0, color));
            }
        }
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        RandomSource rand = level.getRandom();
        Player player = (Player) entityLiving;
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        if (!player.isFallFlying() && player.getTicksUsingItem() >= chargeTime) {
            player.awardStat(Stats.ITEM_USED.get(this));
            applyCooldown(player);
            performDash(stack, level, player, pos, rand);
            level.playSound(null, player.getOnPos(), soundEvent, SoundSource.PLAYERS, 1F, 1F);
            if (level.isClientSide) {
                OverlayRender.setOverlayTexture(texture);
                OverlayRender.showDashOverlay(overlayTime);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        addContributorTooltip(stack, tooltip);
        tooltip.add(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }
}