package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class ValoriaCurioItem extends Item implements ICurioItem{
    public ArcRandom arcRandom = Tmp.rnd;
    public boolean rmbEquip;

    public ValoriaCurioItem(Properties properties){
        super(properties);
        this.rmbEquip = true;
    }

    public int immunityTime() {
        return 0;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        ICurioItem.super.curioTick(slotContext, stack);
        if (slotContext.getWearer().level() instanceof ServerLevel server && server.getServer().getTickCount() % 20 != 0) return;
        if(stack.is(TagsRegistry.FIRE_IMMUNE)) {
            slotContext.getWearer().extinguishFire();
        }

        if(stack.is(TagsRegistry.POISON_IMMUNE) && slotContext.getWearer().hasEffect(MobEffects.POISON)) {
            slotContext.getWearer().removeEffect(MobEffects.POISON);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(stack.is(TagsRegistry.GRANTS_IMMUNITIES)){
            tooltip.add(Component.translatable("tooltip.valoria.immunity", MobEffects.POISON.getDisplayName()).withStyle(ChatFormatting.GRAY));
            if(stack.is(TagsRegistry.POISON_IMMUNE)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", MobEffects.POISON.getDisplayName()).withStyle(Styles.nature)));
            }

            if(stack.is(TagsRegistry.BLEEDING_IMMUNE)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", EffectsRegistry.BLEEDING.get().getDisplayName()).withStyle(Styles.create(Pal.strongRed))));
            }

            if(stack.is(TagsRegistry.FIRE_IMMUNE_TIMED)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable("tooltip.tridot.value", Blocks.FIRE.getName()).withStyle(Styles.create(Pal.infernalBright))
                    .append(Component.literal(" ")
                    .append(Component.translatable("tooltip.valoria.timed", ValoriaUtils.formatDuration(immunityTime() * 20, 1))).withStyle(Styles.create(Pal.lightishGray))
                )));
            } else if(stack.is(TagsRegistry.FIRE_IMMUNE_TIMED)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", Blocks.FIRE.getName()).withStyle(Styles.create(Pal.infernalBright))));
            }
        }

        if(stack.is(TagsRegistry.INFLICTS_FIRE)){
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.inflicts_fire").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        return ValoriaUtils.onePerTypeEquip(slotContext, stack);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return rmbEquip;
    }
}