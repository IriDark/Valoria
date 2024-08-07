package com.idark.valoria.registries.item.types.ranged;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
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
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.common.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

public class SpearItem extends SwordItem implements Vanishable{
    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeModifiers = Suppliers.memoize(this::createAttributes);
    public final float attackDamage;
    public final float attackSpeed;
    public final float projectileDamage;
    public final boolean throwable;
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;

    /**
     * @param pEffects Effects applied on attack
     */
    public SpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, float projectileDamageIn, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.projectileDamage = projectileDamageIn;
        this.effects = ImmutableList.copyOf(pEffects);
        throwable = true;
    }

    /**
     * @param pChance Chance to apply effects
     * @param pEffects Effects applied on attack
     */
    public SpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, float projectileDamageIn, float pChance, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.projectileDamage = projectileDamageIn;
        this.effects = ImmutableList.copyOf(pEffects);
        this.chance = pChance;
        throwable = true;
    }

    public SpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, boolean pThrowable, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.throwable = pThrowable;
        this.projectileDamage = 0f;
        this.effects = ImmutableList.of();
    }

    private static Set<ToolAction> of(ToolAction... actions){
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }

    private Multimap<Attribute, AttributeModifier> createAttributes(){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Spear modifier", 1, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(UUID.fromString("5334b818-69d4-417e-b4b8-1869d4917e29"), "Tool modifier", projectileDamage, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category.canEnchant(stack.getItem()) || enchant == Enchantments.PIERCING || enchant == Enchantments.LOYALTY;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(throwable && !playerIn.isShiftKeyDown()){
            if(!playerIn.isShiftKeyDown()){
                playerIn.startUsingItem(handIn);
                return InteractionResultHolder.consume(itemstack);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        if(!effects.isEmpty()){
            if(chance < 1){
                for(MobEffectInstance effectInstance : effects){
                    if(RandomUtil.percentChance(chance)){
                        pTarget.addEffect(new MobEffectInstance(effectInstance));
                    }
                }
            }else{
                for(MobEffectInstance effectInstance : effects){
                    pTarget.addEffect(new MobEffectInstance(effectInstance));
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack) - timeLeft;
            if(i >= 6){
                if(!worldIn.isClientSide){
                    stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    ThrownSpearEntity spear = new ThrownSpearEntity(worldIn, playerEntity, stack, 2, 4);
                    spear.setItem(stack);
                    spear.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
                    if(playerEntity.getAbilities().instabuild){
                        spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
                        spear.setSecondsOnFire(100);
                    }

                    spear.setEffectsFromList(effects);
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

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers.get() : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
        Level worldIn = context.getLevel();
        RandomSource rand = worldIn.getRandom();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        InteractionHand handIn = context.getHand();
        if((state.is(BlockRegistry.CHARGED_VOID_PILLAR.get())) || (state.is(BlockRegistry.VOID_PILLAR_AMETHYST.get()))){
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1, 1);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
            for(int i = 0; i < 16; i++){
                worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5F + rand.nextDouble() * 1.1, pos.getZ() + 0.5F + rand.nextDouble(), 0d, 0.05d, 0d);
            }

            worldIn.setBlockAndUpdate(pos, BlockRegistry.VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
            if(!worldIn.isClientSide){
                if(!player.getAbilities().instabuild){
                    player.drop(new ItemStack(ItemsRegistry.UNCHARGED_SHARD.get()), true);
                    stack.hurtAndBreak(10, player, (playerEntity) -> playerEntity.broadcastBreakEvent(handIn));
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.onItemUseFirst(stack, context);
    }

    public static final Set<ToolAction> SPEAR = of(net.minecraftforge.common.ToolActions.SWORD_DIG);

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.spear").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction){
        return SPEAR.contains(toolAction);
    }
}