package com.idark.valoria.registries.item.types.shield;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ValoriaShieldItem extends ShieldItem{
    public boolean infiniteUse = true;
    public float blockedPercent = 100;
    public int useDuration;
    public int cooldownTicks = 135;

    public ValoriaShieldItem(Properties pProperties){
        super(pProperties);
    }

    public ValoriaShieldItem(float defPercent, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
    }

    public ValoriaShieldItem(float defPercent, int useDuration, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
        this.useDuration = useDuration;
        this.infiniteUse = false;
    }

    public ValoriaShieldItem(float defPercent, int useDuration, int cooldown, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
        this.useDuration = useDuration;
        this.cooldownTicks = cooldown;
        this.infiniteUse = false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("tooltip.valoria.shield.block", String.format("%.1f%%", this.blockedPercent)).withStyle(ChatFormatting.GRAY));
        pTooltip.add(Component.translatable("tooltip.valoria.shield.time", formatDuration(this.useDuration)).withStyle(ChatFormatting.GRAY));
        if(!pStack.getItem().canBeDepleted()){
            pTooltip.add(Component.empty());
            pTooltip.add(Component.translatable("item.unbreakable").withStyle(ChatFormatting.BLUE));
        }
    }   

    public Component formatDuration(int useDuration) {
        if (this.infiniteUse) {
            return Component.translatable("effect.duration.infinite");
        } else {
            int i = Mth.floor((float)useDuration);
            return Component.literal(StringUtil.formatTickDuration(i));
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack){
        return infiniteUse ? 72000 : useDuration;
    }

    public void onShieldDisable(ItemStack itemStack,Level level, Player player) {
    }

    public void onShieldBlock(DamageSource source, float pAmount, ItemStack itemStack, LivingEntity entity){
    }

    @Override
    @NotNull
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !infiniteUse) {
            player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS);
            itemStack.hurtAndBreak((int) (itemStack.getMaxDamage()*0.075f), player, (p1) -> p1.broadcastBreakEvent(player.getUsedItemHand()));
            for (Item item : ForgeRegistries.ITEMS) {
                if(item instanceof ValoriaShieldItem) {
                    player.getCooldowns().addCooldown(item, cooldownTicks);
                    onShieldDisable(itemStack, level, player);
                    player.disableShield(false);
                }
            }
        }

        return super.finishUsingItem(itemStack, level, entity);
    }
}