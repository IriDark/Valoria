package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

public class CelestialNecklaceItem extends CurioAccessoryItem implements TooltipComponentItem {
    private static final String NIGHT_ACTIVE_TAG = "IsNightActive";
    public CelestialNecklaceItem(CelestialBuilder builder) {
        super(builder);
    }

    public static boolean isNightActive(ItemStack stack){
        return DataComponentsRegistry.getBool(stack, DataComponentsRegistry.NIGHT_ACTIVE.get(), NIGHT_ACTIVE_TAG);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pLevel.isClientSide()) return;

        if(pEntity.tickCount % 100 == 0){
            boolean isNightNow = !pLevel.isDay();
            if(isNightNow != isNightActive(pStack)){
                pStack.set(DataComponentsRegistry.NIGHT_ACTIVE, isNightNow);
            }
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(new AbilityComponent(Component.translatable("tooltip.valoria.celestial").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/sun_moon.png")));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack){
        if(!isNightActive(stack)){
            return super.getAttributeModifiers(slotContext, uuid, stack);
        }

        Multimap<Holder<Attribute>, AttributeModifier> m = LinkedHashMultimap.create();
        if(builder instanceof CelestialBuilder neckBuilder){
            neckBuilder.nightAttributeMap.forEach((attribute, data) -> {
                m.put(attribute, new AttributeModifier(uuid, data.value(), data.operation()));
            });
        }

        return m;
    }

    public static class CelestialBuilder extends AbstractCurioBuilder<CelestialNecklaceItem, CelestialBuilder>{
        public Multimap<Holder<Attribute>, AttributeData> nightAttributeMap = HashMultimap.create();

        public CelestialBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        public CelestialBuilder addNightAttrs(Multimap<Holder<Attribute>, AttributeData> map){
            nightAttributeMap.putAll(map);
            return this;
        }

        public CelestialBuilder setNightAttrs(Multimap<Holder<Attribute>, AttributeData> map){
            nightAttributeMap = map;
            return this;
        }

        public CelestialBuilder addNightAttr(Holder<Attribute> attribute, AttributeData mod){
            nightAttributeMap.put(attribute, mod);
            return this;
        }

        @Override
        public CelestialNecklaceItem build(){
            return new CelestialNecklaceItem(this);
        }
    }
}
