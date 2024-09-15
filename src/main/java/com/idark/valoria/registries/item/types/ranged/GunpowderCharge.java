package com.idark.valoria.registries.item.types.ranged;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public class GunpowderCharge extends Item {
    private final float radius;
    private final float damage;
    private final float knockback;

    public GunpowderCharge(float pRadius, float pDamage, float pKnockback, Properties pProperties) {
        super(pProperties);
        radius = pRadius;
        damage = pDamage;
        knockback = pKnockback;
    }

    public GunpowderCharge(float pRadius, float pDamage, Properties pProperties) {
        super(pProperties);
        radius = pRadius;
        damage = pDamage;
        knockback = 0.5f;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", ATTRIBUTE_MODIFIER_FORMAT.format(damage), Component.translatable("attribute.name.generic.attack_damage")).withStyle(ChatFormatting.DARK_GREEN)));
        tooltip.add(CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", ATTRIBUTE_MODIFIER_FORMAT.format(knockback), Component.translatable("attribute.name.generic.attack_knockback")).withStyle(ChatFormatting.DARK_GREEN)));
        tooltip.add(CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", ATTRIBUTE_MODIFIER_FORMAT.format(radius), Component.translatable("attribute.valoria.attack_radius")).withStyle(ChatFormatting.DARK_GREEN)));
    }

    public float getRadius() {
        return radius;
    }

    public float getDamage() {
        return damage;
    }

    public float getKnockback() {
        return knockback;
    }
}
