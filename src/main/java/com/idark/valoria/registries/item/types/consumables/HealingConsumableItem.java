package com.idark.valoria.registries.item.types.consumables;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class HealingConsumableItem extends AbstractConsumableItem{
    public float voidHarm;
    public float health;
    public int cooldownTicks = 75;

    public HealingConsumableItem(float voidHarm, float health, Properties pProperties){
        super(pProperties);
        this.voidHarm = voidHarm;
        this.health = health;
    }

    public HealingConsumableItem(int drinkDuration, float voidHarm, float health, Properties pProperties){
        super(pProperties);
        this.voidHarm = voidHarm;
        this.health = health;
        this.useDuration = drinkDuration;
    }

    public HealingConsumableItem(int cooldownTicks, int drinkDuration, float voidHarm, float health, Properties pProperties){
        super(pProperties);
        this.voidHarm = voidHarm;
        this.health = health;
        this.useDuration = drinkDuration;
        this.cooldownTicks = cooldownTicks;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(voidHarm > 0) pTooltipComponents.add(Component.translatable("tooltip.valoria.void_harm", voidHarm).withStyle(ChatFormatting.GRAY).withStyle(style -> style.withFont(Valoria.FONT)));
        pTooltipComponents.add(Component.translatable("tooltip.valoria.healing_consumable", (health / 2)).withStyle(ChatFormatting.GRAY));
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

    @Override
    public void onConsume(ItemStack pStack, Level pLevel, Player player){
        player.getCapability(INihilityLevel.INSTANCE).ifPresent(k -> {
            k.modifyAmount(player, voidHarm);
        });

        player.heal(health);
        for(Item item : ForgeRegistries.ITEMS) {
            if(item instanceof HealingConsumableItem) {
                player.getCooldowns().addCooldown(item, cooldownTicks);
            }
        }
    }
}
