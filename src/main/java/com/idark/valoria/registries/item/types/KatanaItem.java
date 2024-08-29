package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.client.gui.overlay.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.interfaces.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
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

import java.lang.Math;
import java.util.*;

public class KatanaItem extends SwordItem implements ICooldownItem{
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public  ArcRandom arcRandom = new ArcRandom();
    // cringe
    private final AttributeModifier dashModifier;

    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.of();
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", 1.8f, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param dashDistance Default value: 1.8f
     * @param pEffects MobEffect instance that applied on hit enemies
     */
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param dashDistance Default value: 1.8f
     * @param chance Chance to apply effects
     * @param pEffects Effects applied on attack
     * <p>
     * <pre>{@code public static final RegistryObject<Item> KATANA_NAME = ITEMS.register("katana_id", () -> new KatanaItem(TIER, ATTACK_DAMAGE, ATTACK_SPEED, DISTANCE, new Item.Properties(), CHANCE, new MobEffectInstance(EFFECT, EFFECT DURATION, EFFECT_LEVEL)));
     * }</pre>
     */
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, float chance, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        this.chance = chance;
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        if(pEquipmentSlot == EquipmentSlot.OFFHAND){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
            atts.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
            return atts.build();
        }

        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static double distance(float distance, Level level, Player player){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = ValoriaUtils.getHitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if(hitresult != null){
            switch(hitresult.getType()){
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, LivingEntity entityLiving){
        if(state.getDestroySpeed(worldIn, pos) != 0.0F){
            stack.hurtAndBreak(5, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public void applyCooldown(Player playerIn){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof KatanaItem){
                playerIn.getCooldowns().addCooldown(item, 75);
            }
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft){
        RandomSource rand = level.getRandom();
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        float dashDistance = (float)player.getAttributeValue(AttributeRegistry.DASH_DISTANCE.get());
        // preventing using the katana when flying on Elytra
        if(!player.isFallFlying()){
            Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
            player.push(dir.x, dir.y * 0.25, dir.z);
            List<LivingEntity> hitEntities = new ArrayList<>();
            if(level instanceof ServerLevel srv){
                for(int i = 0; i < 10; i += 1){
                    double locDistance = i * 0.5D;
                    double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                    double Y = Math.cos(pitch) * locDistance;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                    srv.sendParticles(ParticleTypes.POOF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 1, 0, 0.5, 0, 0);
                    List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                    for(Entity entity : entities){
                        if(entity instanceof LivingEntity enemy){
                            if(!hitEntities.contains(enemy) && (!enemy.equals(player))){
                                hitEntities.add(enemy);
                            }
                        }
                    }

                    if(locDistance >= distance(dashDistance, level, player)){
                        break;
                    }
                }
            }

            float ii = 1F;
            for(LivingEntity entity : hitEntities){
                entity.hurt(level.damageSources().playerAttack(player), (float)((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }

                ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
                ii = ii - (1F / (hitEntities.size() * 2));
            }

            if(!player.isCreative()){
                stack.hurtAndBreak(5, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            for(int i = 0; i < 4; i++){
                level.addParticle(ParticleTypes.POOF, player.getX() + (rand.nextDouble() - 0.5D), player.getY(), player.getZ() + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
            }

            level.playSound(null, player.getOnPos(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.PLAYERS, 1.0F, 1F);
            if(level.isClientSide) DashOverlayRender.showDashOverlay();
            if(!player.isFallFlying()){
                applyCooldown(player);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }
}