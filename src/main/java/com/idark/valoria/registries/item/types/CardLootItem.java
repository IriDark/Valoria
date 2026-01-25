package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class CardLootItem extends LootItem implements ICustomAnimationItem{
    public ItemAnimation animation = new CardOpeningAnimation();

    public CardLootItem(ResourceLocation loot, Properties properties){
        super(loot, properties);
    }

    public UseAnim getUseAnimation(ItemStack pStack){
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack){
        return animation;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        if(!player.isShiftKeyDown() && hand != InteractionHand.OFF_HAND){
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.consume(itemstack);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(player instanceof ServerPlayer serverPlayer){
            Vec3 playerPos = serverPlayer.position();
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            if(!serverPlayer.isCreative()){
                stack.shrink(1);
            }

            List<ItemStack> generatedLoot = Utils.Items.createLoot(loot, Utils.Items.getGiftParameters((ServerLevel)level, playerPos, serverPlayer.getLuck(), serverPlayer));
            if(!generatedLoot.isEmpty()){
                MutableComponent message = Component.translatable("message.valoria.received").withStyle(ChatFormatting.GOLD);
                for(int i = 0; i < generatedLoot.size(); i++){
                    ItemStack item = generatedLoot.get(i);
                    message.append(Component.literal(" "))
                    .append(item.getHoverName().copy().withStyle(ChatFormatting.WHITE))
                    .append(Component.literal(" x" + item.getCount()).withStyle(ChatFormatting.YELLOW));

                    if(i < generatedLoot.size() - 1){
                        message.append(Component.literal(",").withStyle(ChatFormatting.GRAY));
                    }
                }

                Utils.Items.giveLoot(serverPlayer, generatedLoot);
                serverPlayer.displayClientMessage(message, true);
                level.playSound(null, player.blockPosition(), getOpenSound(), SoundSource.PLAYERS, 1f, 1f);
                return stack;
            }
        }

        if(level.isClientSide()) {
            ParticleBuilder.create(TridotParticles.TINY_WISP)
            .setColorData(ColorParticleData.create(Pal.strongRed).build())
            .randomVelocity(0.25f)
            .setScaleData(GenericParticleData.create(0.75f, 0, 0).build())
            .setLifetime(25)
            .setGravity(0.35f)
            .randomOffset(1.25)
            .repeat(level, player.getX(), player.getY(), player.getZ(), 16);
        }

        return stack;
    }

    public int getUseDuration(ItemStack stack){
        return 20;
    }

    @Override
    public SoundEvent getOpenSound(){
        return SoundEvents.EXPERIENCE_ORB_PICKUP;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.treasure").withStyle(ChatFormatting.GRAY));
        list.add(2, Component.empty());
        list.add(3, Component.translatable("tooltip.valoria.rmb").withStyle(DotStyle.of().font(Valoria.FONT)));
    }
}
