package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;

import java.util.*;

public class SoulCollectorItem extends Item implements OverlayRenderItem, ISoulItem{
    public int max;
    public int base;
    public static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/soul_collector.png");

    public SoulCollectorItem(Properties pProperties){
        super(pProperties.durability(5));
        this.max = 50;
        this.base = 0;
    }

    public SoulCollectorItem(int max, Properties pProperties){
        super(pProperties.durability(5));
        this.max = max;
        this.base = 0;
    }

    public SoulCollectorItem(int max, int base, Properties pProperties){
        super(pProperties.durability(5));
        this.max = max;
        this.base = base;
    }

    public ItemStack getDefaultInstance(){
        return setSoulItem(super.getDefaultInstance());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.soul_collector").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.souls", getCurrentSouls(stack)).append(" / ").append(String.valueOf(getMaxSouls())).withStyle(ChatFormatting.GRAY).append("\uE253").withStyle(style -> style.withFont(Valoria.FONT)));
    }

    public boolean isBarVisible(ItemStack pStack){
        return barVisible(pStack);
    }

    public int getBarWidth(ItemStack pStack){
        return barWidth(pStack);
    }

    public int getBarColor(ItemStack pStack){
        return barColor();
    }

    @Override
    public ResourceLocation getTexture(){
        return BAR;
    }

    public int getBaseSouls(){
        return base;
    }

    public int getMaxSouls(){
        return max;
    }

    public void consumeSouls(int count, ItemStack pStack){
        int souls = Math.max(this.getCurrentSouls(pStack) - count, 0);
        pStack.getOrCreateTag().putInt("Souls", souls);
        if(souls == 0) {
            ItemStack itemstack = pStack.copy();
            CompoundTag compoundtag = pStack.getTag();
            if (compoundtag != null) {
                compoundtag.remove("Souls");
                compoundtag.putInt("Souls", souls);
                itemstack.setTag(compoundtag.copy());
            }
        }
    }

    public void addCount(int count, ItemStack pStack, Player player){
        if(pStack.getOrCreateTag().getInt("Souls") + count >= getMaxSouls() - 1){
            player.getInventory().removeItem(pStack);
            ValoriaUtils.addPlayerItem(player.level(), player, ItemsRegistry.soulCollector.get().getDefaultInstance());
            player.level().playSound(null, player.getOnPos(), getTransformSound(), SoundSource.PLAYERS, 1, player.level().random.nextFloat());
        }else{
            pStack.getOrCreateTag().putInt("Souls", getCurrentSouls(pStack) + count);
            player.level().playSound(null, player.getOnPos(), getCollectSound(), SoundSource.PLAYERS, 1, player.level().random.nextFloat());
        }
    }

    public SoundEvent getTransformSound(){
        return SoundsRegistry.SOUL_COLLECT_FULL.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY){
        int xCord = ClientConfig.MISC_UI_X.get() + offsetX;
        int yCord = ClientConfig.MISC_UI_Y.get() + offsetY;
        int progress = 22;

        progress /= (double)getMaxSouls() / (double)tag.getInt("Souls");
        gui.blit(BAR, xCord, yCord, 0, 0, 24, 48, 48, 48);
        gui.blit(BAR, xCord + 6, yCord + 39 - (int) (progress * 1.5), 36, 33 - (int) (progress * 1.5), 12, (int) (progress * 1.5), 48, 48);
    }
}
