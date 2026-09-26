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

public class EyeNecklaceItem extends CurioAccessoryItem implements TooltipComponentItem{
    public EyeNecklaceItem(NecklaceBuilder builder) {
        super(builder);
    }

    private static final String DARK_ACTIVE_TAG = "IsDarkActive";
    private static final String EYE_STATE_TAG = "EyeState";

    public static boolean isDarkActive(ItemStack stack){
        return DataComponentsRegistry.getBool(stack, DataComponentsRegistry.DARK_ACTIVE.get(), DARK_ACTIVE_TAG);
    }

    public static int getEyeState(ItemStack stack){
        return DataComponentsRegistry.getInt(stack, DataComponentsRegistry.EYE_STATE.get(), EYE_STATE_TAG);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pLevel.isClientSide()) return;

        if(pEntity.tickCount % 100 == 0){
            boolean isDarkNow = pLevel.getMaxLocalRawBrightness(pEntity.blockPosition()) < 8;
            if(isDarkNow != isDarkActive(pStack)){
                pStack.set(DataComponentsRegistry.DARK_ACTIVE, isDarkNow);
            }
        }

        if (pEntity.tickCount % 2 == 0) {
            int currentState = getEyeState(pStack);
            boolean isDark = isDarkActive(pStack);
            if (isDark && currentState < 3) {
                pStack.set(DataComponentsRegistry.EYE_STATE, currentState + 1);
            } else if (!isDark && currentState > 0) {
                pStack.set(DataComponentsRegistry.EYE_STATE, currentState - 1);
            }
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(new AbilityComponent(Component.translatable("tooltip.valoria.eye_necklace").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/eye.png")));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        if (!isDarkActive(stack)) {
            return super.getAttributeModifiers(slotContext, uuid, stack);
        }

        Multimap<Holder<Attribute>, AttributeModifier> m = LinkedHashMultimap.create();
        if (builder instanceof NecklaceBuilder neckBuilder){
            neckBuilder.negativeAttributeMap.forEach((attribute, data) -> {
                m.put(attribute, new AttributeModifier(uuid, data.value(), data.operation()));
            });
        }

        return m;
    }

    public static class NecklaceBuilder extends AbstractCurioBuilder<EyeNecklaceItem, NecklaceBuilder>{
        public Multimap<Holder<Attribute>, AttributeData> negativeAttributeMap = HashMultimap.create();

        public NecklaceBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        public NecklaceBuilder addNegativeAttrs(Multimap<Holder<Attribute>, AttributeData> map){
            negativeAttributeMap.putAll(map);
            return this;
        }

        public NecklaceBuilder setNegativeAttrs(Multimap<Holder<Attribute>, AttributeData> map){
            negativeAttributeMap = map;
            return this;
        }

        public NecklaceBuilder addNegativeAttr(Holder<Attribute> attribute, AttributeData mod){
            negativeAttributeMap.put(attribute, mod);
            return this;
        }

        @Override
        public EyeNecklaceItem build(){
            return new EyeNecklaceItem(this);
        }
    }
}
