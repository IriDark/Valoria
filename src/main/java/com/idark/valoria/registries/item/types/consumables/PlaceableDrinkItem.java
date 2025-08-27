package com.idark.valoria.registries.item.types.consumables;

import com.google.common.collect.*;
import com.mojang.datafixers.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.gameevent.*;

import javax.annotation.*;
import java.util.*;

public class PlaceableDrinkItem extends BlockItem{
    private final ItemStack item;
    private final ImmutableList<MobEffectInstance> effects;
    private static final Component NO_EFFECT = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);

    public PlaceableDrinkItem(Block block, int stackSize, Item pItem, MobEffectInstance... pEffects){
        super(block, new Properties().stacksTo(stackSize));
        this.item = pItem.getDefaultInstance();
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity){
        Player playerEntity = entity instanceof Player ? (Player)entity : null;
        if(!world.isClientSide){
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)playerEntity, stack);
            playerEntity.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            for(MobEffectInstance mobeffectinstance : effects){
                entity.addEffect(new MobEffectInstance(mobeffectinstance));
            }

            if(!playerEntity.getAbilities().instabuild){
                stack.shrink(1);
                playerEntity.getInventory().add(new ItemStack(item.getItem()));
            }
        }

        entity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack pStack){
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.DRINK;
    }

    public void addPotionTooltip(List<Component> pTooltips, float pDurationFactor){
        List<Pair<Attribute, AttributeModifier>> list = Lists.newArrayList();
        if(effects.isEmpty()){
            pTooltips.add(NO_EFFECT);
        }else{
            for(MobEffectInstance mobeffectinstance : effects){
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                MobEffect mobeffect = mobeffectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                if(!map.isEmpty()){
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()){
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobeffect.getAttributeModifierValue(mobeffectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if(mobeffectinstance.getAmplifier() > 0){
                    mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
                }

                if(!mobeffectinstance.endsWithin(20)){
                    mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, pDurationFactor));
                }

                pTooltips.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
            }
        }

        if(!list.isEmpty()){
            pTooltips.add(CommonComponents.EMPTY);
            pTooltips.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for(Pair<Attribute, AttributeModifier> pair : list){
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if(attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL){
                    d1 = attributemodifier2.getAmount();
                }else{
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if(d0 > 0.0D){
                    pTooltips.add(Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                }else if(d0 < 0.0D){
                    d1 *= -1.0D;
                    pTooltips.add(Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        this.addPotionTooltip(pTooltip, 1);
    }
}
