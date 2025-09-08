package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.advancements.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.level.levelgen.structure.*;
import pro.komaru.tridot.util.*;

public class StructureLocatorItem extends Item{
    TagKey<Structure> tagKey;
    Col col;
    public StructureLocatorItem(TagKey<Structure> structure, Properties pProperties){
        super(pProperties);
        this.tagKey = structure;
        this.col = Col.white;
    }

    public StructureLocatorItem(Col color, TagKey<Structure> structure, Properties pProperties){
        super(pProperties);
        this.tagKey = structure;
        this.col = color;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand){
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        if(pLevel instanceof ServerLevel serverlevel){
            BlockPos blockpos = serverlevel.findNearestMapStructure(tagKey, pPlayer.blockPosition(), 100, false);
            if(blockpos != null){
                StructureLocatorEntity locator = new StructureLocatorEntity(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                locator.setItem(itemstack);
                locator.setColor(col);
                locator.signalTo(blockpos);
                pLevel.gameEvent(GameEvent.PROJECTILE_SHOOT, locator.position(), GameEvent.Context.of(pPlayer));
                pLevel.addFreshEntity(locator);
                if(pPlayer instanceof ServerPlayer){
                    CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)pPlayer, blockpos);
                }

                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.levelEvent(null, 1003, pPlayer.blockPosition(), 0);
                if(!pPlayer.getAbilities().instabuild){
                    itemstack.shrink(1);
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                pPlayer.swing(pHand, true);
                return InteractionResultHolder.success(itemstack);
            }
        }

        return InteractionResultHolder.consume(itemstack);
    }
}