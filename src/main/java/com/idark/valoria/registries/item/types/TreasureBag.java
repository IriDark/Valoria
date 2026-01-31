package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class TreasureBag extends LootItem{
    public TreasureBag(ResourceLocation loot, Properties properties){
        super(loot, properties);
    }

    @Override
    public SoundEvent getOpenSound(){
        return SoundsRegistry.BAG_OPEN.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand){
        ItemStack heldStack = player.getItemInHand(hand);
        worldIn.playSound(null, player.blockPosition(), getOpenSound(), SoundSource.PLAYERS, 1f, 1f);

        if(player instanceof ServerPlayer serverPlayer){
            Vec3 playerPos = serverPlayer.position();
            List<ItemStack> generatedLoot = Utils.Items.createLoot(loot, Utils.Items.getGiftParameters((ServerLevel)worldIn, playerPos, serverPlayer.getLuck(), serverPlayer));
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, heldStack);
            if(!serverPlayer.isCreative()){
                heldStack.shrink(1);
            }

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
                return InteractionResultHolder.consume(heldStack);
            }
        }

        return InteractionResultHolder.consume(heldStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.treasure").withStyle(ChatFormatting.GRAY));
        list.add(2, Component.empty());
        list.add(3, Component.translatable("tooltip.valoria.rmb").withStyle(DotStyle.of().font(Valoria.FONT)));
    }
}