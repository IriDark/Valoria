package com.idark.valoria.registries.item.types;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.LootUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DropItemProperty extends Item {
    public DropType type;

    public DropItemProperty(DropType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        worldIn.playSound(null, player.blockPosition(), SoundsRegistry.BAG_OPEN.get(), SoundSource.AMBIENT, 10f, 1f);
        if (player instanceof ServerPlayer serverPlayer) {
            Vec3 playerPos = serverPlayer.position();
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, heldStack);
            if (!serverPlayer.isCreative()) {
                heldStack.shrink(1);
            }

            switch (type) {
                case MINERS ->
                        LootUtil.DropLoot(serverPlayer, LootUtil.createLoot(new ResourceLocation(Valoria.ID, "items/miners_bag"), LootUtil.getGiftParameters((ServerLevel) worldIn, playerPos, serverPlayer)));
                case GEM ->
                        LootUtil.DropLoot(serverPlayer, LootUtil.createLoot(new ResourceLocation(Valoria.ID, "items/gem_bag"), LootUtil.getGiftParameters((ServerLevel) worldIn, playerPos, serverPlayer)));
            }

            return InteractionResultHolder.consume(heldStack);
        }

        return InteractionResultHolder.consume(heldStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
        list.add(1, Component.translatable("tooltip.valoria.treasure").withStyle(ChatFormatting.GRAY));
        list.add(2, Component.empty());
        list.add(3, Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
    }
}