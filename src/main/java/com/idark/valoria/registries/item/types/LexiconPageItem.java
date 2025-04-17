package com.idark.valoria.registries.item.types;

import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static com.idark.valoria.Valoria.loc;

public class LexiconPageItem extends Item{
    public Unlockable unlockable;
    public String lang;
    public boolean rand;

    public LexiconPageItem(Properties props, @NotNull Unlockable pUnlockable, String pPageName){
        super(props);
        this.unlockable = pUnlockable;
        this.lang = pPageName;
    }

    public LexiconPageItem(Properties props){
        super(props);
        rand = true;
    }

    public LexiconPageItem(Properties props, @NotNull Unlockable pUnlockable){
        super(props);
        this.unlockable = pUnlockable;
    }

    @Override
    public String getDescriptionId(){
        return Util.makeDescriptionId("item", loc("page"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        if(!world.isClientSide && player instanceof ServerPlayer serverPlayer){
            if(rand) {
                Unlockable rU = UnlockUtils.getRandom();
                if(rU != null && !UnlockUtils.isUnlocked(player, rU) && !onUnlock(rU)) {
                    player.getInventory().removeItem(stack);
                    UnlockUtils.add(serverPlayer, rU);
                }else{
                    world.playSound(null, player.getOnPos(), SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 0.7f, 0.2f);
                    player.displayClientMessage(Component.translatable("lexicon.valoria.obtained").withStyle(ChatFormatting.GRAY), true);
                    return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
                }
            } else {
                if(!UnlockUtils.isUnlocked(player, unlockable) && !onUnlock(unlockable)){
                    player.getInventory().removeItem(stack);
                    UnlockUtils.add(serverPlayer, unlockable);
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
                }else{
                    world.playSound(null, player.getOnPos(), SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 0.7f, 0.2f);
                    player.displayClientMessage(Component.translatable("lexicon.valoria.obtained").withStyle(ChatFormatting.GRAY), true);
                    return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
                }
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    private static boolean onUnlock(Unlockable unlockable) {
        return MinecraftForge.EVENT_BUS.post(new OnPageUnlocked(unlockable));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(lang != null && !lang.isEmpty()){
            tooltip.add(Component.translatable("tooltip.valoria.page").withStyle(ChatFormatting.GRAY).append(Component.translatable(lang).withStyle(ChatFormatting.BLUE)));
        }

        if(rand) {
            tooltip.add(Component.translatable("tooltip.valoria.page").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.valoria.random_page").withStyle(ChatFormatting.BLUE)));
        }
    }
}