package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class CurioAccessoryItem extends AbstractTieredAccessory implements ICurioTexture, TooltipComponentItem{
    public Builder<? extends AbstractCurioBuilder<CurioAccessoryItem, ?>> builder;
    public CurioAccessoryItem(Builder<? extends AbstractCurioBuilder<CurioAccessoryItem, ?>> builder){
        super(builder.tier, builder.itemProperties);
        this.builder = builder;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        if(builder.equipSound != null) return new ICurio.SoundInfo(builder.equipSound, builder.volume, builder.pitch);
        return super.getEquipSound(slotContext, stack);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity){
        if(builder.texPath != null && builder.dependsOnStack) {
            return getTexture(builder.texPath, stack);
        }

        return builder.texPath;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        super.curioTick(slotContext, stack);
        Player player = (Player)slotContext.entity();
        if(player.level().isClientSide() && ValoriaClient.JEWELRY_BONUSES_KEY.isDown()) applyEffects(player, stack);
    }

    public void applyEffects(Player player, ItemStack stack){
        if(!player.getCooldowns().isOnCooldown(stack.getItem())){
            if(!builder.effects.isEmpty()){
                for(MobEffectInstance effectInstance : builder.effects){
                    player.addEffect(new MobEffectInstance(effectInstance));
                    player.getCooldowns().addCooldown(stack.getItem(), effectInstance.getDuration() + 300);
                    accessoryHurt(player, stack, tier);
                }
            }
        }
    }

    private ResourceLocation getTexture(ResourceLocation texPath, ItemStack stack){
        return new ResourceLocation(texPath.getNamespace(), texPath.getPath() + ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath() + ".png");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> m = LinkedHashMultimap.create();
        this.builder.attributeMap.forEach((attrSupplier, data) -> {
            AttributeModifier modifier1 = new AttributeModifier(uuid, "Attribute Modifier", data.value(), data.operation());
            m.put(attrSupplier.get(), modifier1);
        });

        this.builder.slotModifiers.forEach((slot, data) -> {
            CuriosApi.addSlotModifier(m, slot, uuid, data.value(), data.operation());
        });

        return m;
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        Seq<TooltipComponent> seq = Seq.with();
        if(!builder.effects.isEmpty()){
            seq.addAll(
            new EffectsListComponent(builder.effects.list(), Component.translatable("tooltip.tridot.applies").withStyle(ChatFormatting.GRAY)),
            new EmptyComponent(6),
            new TextComponent(Component.translatable("tooltip.valoria.jewelry_bonus", ValoriaClient.JEWELRY_BONUSES_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GREEN))
            );
        }

        return seq;
    }

    public static class Builder<B extends AbstractCurioBuilder<CurioAccessoryItem, B>> extends AbstractCurioBuilder<CurioAccessoryItem, B>{
        public ResourceLocation texPath;
        public boolean dependsOnStack = true;

        public Builder(Tier tier, Properties properties){
            super(tier, properties);
        }

        public B setTexPath(ResourceLocation texPath){
            this.texPath = texPath;
            return self();
        }

        public B setDependsOnStack(boolean dependsOnStack){
            this.dependsOnStack = dependsOnStack;
            return self();
        }

        @Override
        public CurioAccessoryItem build(){
            return new CurioAccessoryItem(this);
        }
    }
}
