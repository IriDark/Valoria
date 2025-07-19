package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class EtherealSwordItem extends ValoriaSword implements RadiusItem, OverlayRenderItem{
    private static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/soul_bar.png");
    public ArcRandom arcRandom = Tmp.rnd;
    public int max;
    public int current;

    public EtherealSwordItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        this(tier, attackDamageIn, attackSpeedIn, 0, 25, builderIn);
    }

    public EtherealSwordItem(Tier tier, float attackDamageIn, float attackSpeedIn, int current, int max, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.max = max;
        this.current = current;
    }

    public ItemStack getDefaultInstance(){
        return setSword(super.getDefaultInstance());
    }

    public ItemStack setSword(ItemStack pStack){
        pStack.getOrCreateTag().putInt("Souls", this.current);
        return pStack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.ethereal_sword").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.souls", getCurrentSouls(stack))
        .append(" / ")
        .append(String.valueOf(getMaxSouls()))
        .withStyle(ChatFormatting.GRAY)
        );

        if(getCurrentSouls(stack) >= getMaxSouls()){
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.rmb", getCurrentSouls(stack)).withStyle(ChatFormatting.GREEN));
        }
    }

    public static void addCharge(ItemStack stack, int charge){
        CompoundTag nbt = stack.getOrCreateTag();
        int charges = nbt.getInt("charge");
        nbt.putInt("charge", charges + charge);
    }

    public int getMaxSouls(){
        return max;
    }

    public int getCurrentSouls(ItemStack pStack){
        return pStack.getOrCreateTag().getInt("Souls");
    }

    public void setCount(int count, ItemStack pStack){
        pStack.removeTagKey("Souls");
        pStack.getOrCreateTag().putInt("Souls", count);
    }

    public void removeCount(int count, ItemStack pStack){
        pStack.getOrCreateTag().putInt("Souls", this.getCurrentSouls(pStack) - count);
    }

    public void addCount(int count, ItemStack pStack, Player player){
        if(pStack.getOrCreateTag().getInt("Souls") + count <= getMaxSouls()){
            pStack.getOrCreateTag().putInt("Souls", getCurrentSouls(pStack) + count);
            player.level().playSound(null, player.getOnPos(), getCollectSound(), SoundSource.PLAYERS, 1, player.level().random.nextFloat());
        }
    }

    public SoundEvent getCollectSound(){
        return SoundsRegistry.SOUL_COLLECT.get();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return super.canApplyAtEnchantingTable(stack, enchant) && enchant != Enchantments.FIRE_ASPECT;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        var stack = pPlayer.getItemInHand(pUsedHand);
        boolean flag = pPlayer.getCooldowns().isOnCooldown(stack.getItem());
        if(!flag && getCurrentSouls(stack) >= getMaxSouls()) {
            pPlayer.addEffect(new MobEffectInstance(EffectsRegistry.SOUL_BURST.get(), 220, 0));
            pPlayer.getCooldowns().addCooldown(stack.getItem(), 600);
            this.setCount(0, stack);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ResourceLocation getTexture(){
        return BAR;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY){
        int xCord = ClientConfig.SOUL_BAR_X.get() + offsetX;
        int yCord = ClientConfig.SOUL_BAR_Y.get() + offsetY;
        int progress = 24;

        progress /= (float)getMaxSouls() / (float)tag.getInt("Souls");

        gui.blit(BAR, xCord, yCord, 0, 0, 32, 32, 64, 64);
        gui.blit(BAR, xCord, yCord - 4 + (32 - progress), 0, 28 + (32 - progress), 32, progress, 64, 64);
    }
}