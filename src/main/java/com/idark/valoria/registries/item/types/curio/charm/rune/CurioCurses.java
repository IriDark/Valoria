package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.Supplier;

public class CurioCurses extends AbstractRuneItem implements TooltipComponentItem, CurioOnAttackItem{
    private final Supplier<ImmutableList<EffectApplier>> appliersSupplier;
    public float chance;

    public CurioCurses(Properties properties, float chance, int duration){
        super(properties);
        this.chance = chance;
        this.appliersSupplier = Suppliers.memoize(() -> this.getEffectAppliersFromTag(TagsRegistry.CURSES, duration));
    }

    // Calamity sounds used
    @Override
    public void onAttack(ItemStack stack, LivingEntity target, DamageSource source, float damage) {
        if (!target.level().isClientSide() && source.getEntity() instanceof ServerPlayer pServer) {
            if (Utils.Items.getAttackStrengthScale(pServer, 0.9f) && !pServer.getCooldowns().isOnCooldown(stack.getItem())) {
                applySingleCurse(pServer, target, this.getAppliers());
            }
        }
    }

    public ImmutableList<EffectApplier> getAppliers() {
        return this.appliersSupplier.get();
    }

    public void applySingleCurse(ServerPlayer wearer, LivingEntity pTarget, ImmutableList<EffectApplier> effects) {
        if (pTarget != null && !effects.isEmpty()) {
            float chance = this.chance;
            if (pTarget instanceof BossEntity || pTarget.getType().is(Tags.EntityTypes.BOSSES)) chance *= 0.25f;
            if (chance <= 0.0F) return;
            if (chance >= 1.0F || pTarget.getRandom().nextFloat() < chance) {
                EffectApplier chosen = effects.get(pTarget.getRandom().nextInt(effects.size()));
                pTarget.addEffect(chosen.createInstance());
                wearer.getCooldowns().addCooldown(this, 100);
                pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.EQUIP_CURSE.get(), SoundSource.AMBIENT, 0.5f, 1f);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public ImmutableList<EffectApplier> getEffectAppliersFromTag(TagKey<MobEffect> effectTag, int duration) {
        ImmutableList.Builder<EffectApplier> effects  = ImmutableList.builder();
        var optionalTag = BuiltInRegistries.MOB_EFFECT.getTag(effectTag);
        if (optionalTag.isPresent()) {
            for (Holder<MobEffect> holder : optionalTag.get()) {
                effects.add(EffectApplier.of(holder, duration));
            }
        }

        return effects.build();
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack){
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        if(stack.is(ItemsRegistry.voidSlateRuneCurses.get())) {
            atts.put(AttributeReg.NIHILITY_RESILIENCE, new AttributeModifier(uuid, -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return atts;
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(new EffectsListComponent(this.getAppliers(), Component.translatable("tooltip.valoria.curse_rune", String.format("%.1f%%", this.getAppliers().get(0).chance() * 100)).withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public RuneType runeType(){
        return RuneType.CURSES;
    }
}
