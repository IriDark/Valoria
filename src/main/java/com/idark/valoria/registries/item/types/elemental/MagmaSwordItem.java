package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class MagmaSwordItem extends ValoriaSword implements RadiusItem, OverlayRenderItem, TooltipComponentItem{
    private static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/magma_charge.png");
    public ArcRandom arcRandom = Tmp.rnd;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public MagmaSwordItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.INFERNAL_DAMAGE.get(), new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage - 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static void addCharge(ItemStack stack, int charge){
        CompoundTag nbt = stack.getOrCreateTag();
        int charges = nbt.getInt("charge");
        nbt.putInt("charge", charges + charge);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return super.canApplyAtEnchantingTable(stack, enchant) && enchant != Enchantments.FIRE_ASPECT;
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        if(!(pAttacker instanceof Player player)) return true;
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        CompoundTag nbt = pStack.getOrCreateTag();
        if(nbt.getInt("charge") < 2){
            if(Utils.Items.getAttackStrengthScale(player, 0.9f)){
                if(arcRandom.chance(0.1f)){
                    addCharge(pStack, 1);
                }
            }
        }

        if(EnchantmentHelper.getFireAspect(pAttacker) > 0){
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            pTarget.setSecondsOnFire(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        ItemStack offhandStack = playerIn.getItemInHand(InteractionHand.OFF_HAND);
        CompoundTag nbt = itemstack.getOrCreateTag();
        boolean flag = offhandStack.getItem() instanceof ShieldItem && nbt.getInt("charge") < 2;
        if(!playerIn.isShiftKeyDown() && !flag){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        RandomSource rand = worldIn.getRandom();
        CompoundTag nbt = stack.getOrCreateTag();
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE) + 5) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + 0.3f, player.getZ());
        if(nbt.contains("charge") && nbt.getInt("charge") == 2){
            if(player.isInWaterOrRain()){
                addCharge(stack, 1);
                player.getCooldowns().addCooldown(this, 150);
                player.displayClientMessage(Component.translatable("tooltip.valoria.wet").withStyle(ChatFormatting.GRAY), true);
                worldIn.playSound(player, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1f, 1f);
                if(!player.isCreative()){
                    stack.hurtAndBreak(5, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }

                if(!worldIn.isClientSide() && worldIn instanceof ServerLevel pServer){
                    for(int i = 0; i < 16; i++){
                        pServer.sendParticles(ParticleTypes.POOF, player.getX() + ((rand.nextDouble() - 0.5D) * 3), player.getY() + ((rand.nextDouble() - 0.5D) * 3), player.getZ() + ((rand.nextDouble() - 0.5D) * 3), 1, 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0);
                        pServer.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX() + ((rand.nextDouble() - 0.5D) * 3), player.getY() + ((rand.nextDouble() - 0.5D) * 3), player.getZ() + ((rand.nextDouble() - 0.5D) * 3), 1, 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0);
                    }
                }
            }else{
                List<LivingEntity> hitEntities = new ArrayList<>();
                nbt.putInt("charge", 0);
                player.getCooldowns().addCooldown(this, 300);
                Utils.Particles.inRadius(worldIn, stack, ParticleTypes.LARGE_SMOKE, pos, 0, player.getRotationVector().y, 1);
                Utils.Particles.inRadius(worldIn, stack, ParticleTypes.LARGE_SMOKE, pos, 0, player.getRotationVector().y, 4);
                ValoriaUtils.radiusHit(worldIn, stack, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y, 4);
                for(LivingEntity damagedEntity : hitEntities){
                    if(!player.canAttack(damagedEntity)) continue;

                    damagedEntity.hurt(worldIn.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, damagedEntity.getMobType())) * 1.35f);
                    damagedEntity.knockback(0.4F, player.getX() - entityLiving.getX(), player.getZ() - entityLiving.getZ());
                    damagedEntity.setSecondsOnFire(12);
                }

                worldIn.playSound(null, player.blockPosition(), SoundsRegistry.ERUPTION.get(), SoundSource.AMBIENT, 1f, 1f);
                if(!player.isCreative()){
                    stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }else{
            player.displayClientMessage(Component.translatable("tooltip.valoria.charges").withStyle(ChatFormatting.GRAY), true);
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(
        new AbilitiesComponent(),
        new AbilityComponent(Component.translatable("tooltip.valoria.infernal_sword").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/infernal_strike.png")),
        new ClientTextComponent(Component.translatable("tooltip.valoria.magma_charges", pStack.getOrCreateTag().getInt("charge") + "/2").withStyle(ChatFormatting.YELLOW).withStyle(style -> style.withFont(Valoria.FONT))),
        new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }

    @Override
    public ResourceLocation getTexture(){
        return BAR;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY){
        int barType = ClientConfig.MAGMA_CHARGE_BAR_TYPE.get();
        int xCord = ClientConfig.MAGMA_CHARGE_BAR_X.get() + offsetX;
        int yCord = ClientConfig.MAGMA_CHARGE_BAR_Y.get() + offsetY;
        if(barType == 1){
            gui.blit(BAR, xCord, yCord, 0, 0, 16, 34, 64, 64);
            if(tag.getInt("charge") == 1){
                gui.blit(BAR, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
            }else if(tag.getInt("charge") == 2){
                gui.blit(BAR, xCord + 8, yCord + 18, 0, 34, 4, 25, 64, 64);
                gui.blit(BAR, xCord + 8, yCord + 6, 0, 34, 4, 25, 64, 64);
                gui.blit(BAR, xCord - 2, yCord - 2, 16, 0, 20, 38, 64, 64);
            }
        }else if(barType == 2){
            gui.blit(BAR, xCord, yCord, 20, 42, 22, 22, 64, 64);
            if(tag.getInt("charge") > 0){
                gui.blit(BAR, xCord, yCord, 42, tag.getInt("charge") == 1 ? 20 : 42, 22, 22, 64, 64);
            }
        }
    }
}