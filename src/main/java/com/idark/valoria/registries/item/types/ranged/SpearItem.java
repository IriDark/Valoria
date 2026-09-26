package com.idark.valoria.registries.item.types.ranged;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

import static com.idark.valoria.Valoria.BASE_ENTITY_REACH_ID;
import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_ID;

public class SpearItem extends SwordItem{
    private final Supplier<ItemAttributeModifiers> attributeModifiers = Suppliers.memoize(this::createAttributes);
    public ArcRandom arcRandom = Tmp.rnd;
    public AbstractSpearBuilder<? extends SpearItem> builder;

    public SpearItem(AbstractSpearBuilder<? extends SpearItem> builder){
        super(builder.tier, builder.itemProperties);
        this.builder = builder;
    }

    /**
     * @param pEffects Effects applied on attack
     */
    public SpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn, MobEffectInstance... pEffects){
        this(new SpearItem.Builder(attackDamageIn, attackSpeedIn, builderIn).setEffects(pEffects).setTier(tier));
    }

    /**
     * @param pChance  Chance to apply effects
     * @param pEffects Effects applied on attack
     */
    public SpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, float pChance, Item.Properties builderIn, MobEffectInstance... pEffects){
        this(new SpearItem.Builder(attackDamageIn, attackSpeedIn, builderIn).setEffects(pChance, pEffects).setTier(tier));
    }

    public SpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, boolean pThrowable, Item.Properties builderIn){
        this(new SpearItem.Builder(attackDamageIn, attackSpeedIn, builderIn).setThrowable(pThrowable).setTier(tier));
    }

    private static Set<ItemAbility> of(ItemAbility... actions){
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }

    public ItemAttributeModifiers createAttributes(){
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.builder.attackDamageIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        if(this.builder.projectileDamageIn > 0) builder.add(AttributeRegistry.PROJECTILE_DAMAGE, new AttributeModifier(BASE_PROJECTILE_DAMAGE_ID, this.builder.projectileDamageIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, this.builder.attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_REACH_ID, this.builder.entityReach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment){
        return super.supportsEnchantment(stack, enchantment) || this.builder.throwable && (enchantment.is(Enchantments.PIERCING) || enchantment.is(Enchantments.LOYALTY));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(this.builder.throwable && !playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        Utils.Entities.applyWithChance(pTarget, this.builder.effects, this.builder.chance, arcRandom);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if(i >= 6){
                if(!worldIn.isClientSide){
                    ThrownSpearEntity spear = shootProjectile(stack, worldIn, playerEntity);
                    worldIn.addFreshEntity(spear);
                    worldIn.playSound(null, spear, SoundsRegistry.SPEAR_THROW.get(), SoundSource.PLAYERS, 1.0F, 0.9F);
                    if(!playerEntity.getAbilities().instabuild){
                        playerEntity.getInventory().removeItem(stack);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private @NotNull ThrownSpearEntity shootProjectile(ItemStack stack, Level worldIn, Player playerEntity){
        ThrownSpearEntity spear = new ThrownSpearEntity(worldIn, playerEntity, stack);
        int pierceLevel = EnchantmentsRegistry.getLevel(worldIn, stack, Enchantments.PIERCING);
        if(pierceLevel > 0){
            spear.setPierceLevel((byte)pierceLevel);
        }

        if(EnchantmentsRegistry.getLevel(worldIn, stack, Enchantments.FIRE_ASPECT) > 0){
            spear.igniteForSeconds(100);
        }

        spear.setEffectsFromList(this.builder.effects);
        spear.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 3F, 1.0F);
        if(playerEntity.getAbilities().instabuild){
            spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        return spear;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.attributeModifiers.get();
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
        Level worldIn = context.getLevel();
        RandomSource rand = worldIn.getRandom();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        InteractionHand handIn = context.getHand();
        if((state.is(BlockRegistry.chargedVoidPillar.get())) || (state.is(BlockRegistry.voidPillarAmethyst.get()))){
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1, 1);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
            for(int i = 0; i < 16; i++){
                worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5F + rand.nextDouble() * 1.1, pos.getZ() + 0.5F + rand.nextDouble(), 0d, 0.05d, 0d);
            }

            worldIn.setBlockAndUpdate(pos, BlockRegistry.voidPillar.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
            if(!worldIn.isClientSide){
                if(!player.getAbilities().instabuild){
                    worldIn.addFreshEntity(new ItemEntity(worldIn, player.getX(), player.getY(), player.getZ(), ItemsRegistry.unchargedShard.get().getDefaultInstance()));
                    stack.hurtAndBreak(10, player, LivingEntity.getSlotForHand(handIn));
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.onItemUseFirst(stack, context);
    }

    public static final Set<ItemAbility> SPEAR = of(ItemAbilities.SWORD_DIG);

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(this.builder.throwable){
            if(stack.is(ItemsRegistry.pyratiteSpear.get())){
                tooltip.add(Component.translatable("tooltip.valoria.pyratite_spear").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.empty());
            }

            tooltip.add(Component.translatable("tooltip.valoria.spear").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.spear_pillars").withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(this.builder.effects, tooltip, 1, this.builder.chance);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility){
        return SPEAR.contains(itemAbility);
    }

    public static class Builder extends AbstractSpearBuilder<SpearItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn, attackSpeedIn, itemProperties);
        }

        @Override
        public SpearItem build(){
            return new SpearItem(this);
        }
    }
}