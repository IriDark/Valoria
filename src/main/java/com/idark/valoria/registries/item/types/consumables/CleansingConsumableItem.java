package com.idark.valoria.registries.item.types.consumables;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.component.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class CleansingConsumableItem extends AbstractConsumableItem implements TooltipComponentItem{
    public float voidCleanse;
    public MobEffectInstance effect;
    public int cooldownTicks = 75;

    public CleansingConsumableItem(float cleanse, MobEffectInstance effect, Properties pProperties){
        super(pProperties);
        this.voidCleanse = cleanse;
        this.effect = effect;
    }

    public CleansingConsumableItem(int drinkDuration, float cleanse, MobEffectInstance effect, Properties pProperties){
        super(pProperties);
        this.useDuration = drinkDuration;
        this.voidCleanse = cleanse;
        this.effect = effect;
    }

    public CleansingConsumableItem(int cooldownTicks, int drinkDuration, float cleanse, MobEffectInstance effect, Properties pProperties){
        super(pProperties);
        this.voidCleanse = cleanse;
        this.effect = effect;
        this.useDuration = drinkDuration;
        this.cooldownTicks = cooldownTicks;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.void_cleanse", voidCleanse).withStyle(ChatFormatting.GRAY).withStyle(style -> style.withFont(Valoria.FONT)));
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(new ClientEffectsListClientComponent(List.of(new MobEffectInstance(effect)), Component.translatable("tooltip.tridot.applies").withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public void onConsume(ItemStack pStack, Level pLevel, Player player){
        player.getCapability(INihilityLevel.INSTANCE).ifPresent(k -> {
            k.decrease(player, voidCleanse);
        });

        player.addEffect(new MobEffectInstance(effect));
        for(Item item : ForgeRegistries.ITEMS) {
            if(item instanceof CleansingConsumableItem) {
                player.getCooldowns().addCooldown(item, cooldownTicks);
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity){
        Player player = pLivingEntity instanceof Player ? (Player)pLivingEntity : null;
        if (player != null){
            if(!player.getAbilities().instabuild){
                if(pStack.isEmpty()){
                    return new ItemStack(Items.GLASS_BOTTLE);
                }

                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
