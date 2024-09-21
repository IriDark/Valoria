package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
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

import java.util.*;

public class SoulCollectorItem extends Item implements IOverlayItem{
    public int max;
    public int current;
    public static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/soul_collector.png");
    public SoulCollectorItem(Properties pProperties){
        super(pProperties);
        this.max = 50;
        this.current = 0;
    }

    public SoulCollectorItem(int max, Properties pProperties){
        super(pProperties);
        this.max = max;
        this.current = 0;
    }

    public SoulCollectorItem(int max, int current, Properties pProperties){
        super(pProperties);
        this.max = max;
        this.current = current;
    }

    public ItemStack getDefaultInstance() {
        return setCollector(super.getDefaultInstance());
    }

    public ItemStack setCollector(ItemStack pStack) {
        pStack.getOrCreateTag().putInt("Souls", this.current);
        return pStack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.souls", getCurrentSouls(stack))
        .append(" / ")
        .append(String.valueOf(getMaxSouls()))
        .withStyle(ChatFormatting.GRAY)
        );
    }

    public boolean isBarVisible(ItemStack pStack) {
        return getCurrentSouls(pStack) > 0 && getCurrentSouls(pStack) < getMaxSouls();
    }

    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)getCurrentSouls(pStack) * 13.0F / (float)getMaxSouls());
    }

    public int getBarColor(ItemStack pStack) {
        return Pal.oceanic.getRGB();
    }

    @Override
    public ResourceLocation getTexture(){
        return BAR;
    }

    public int getMaxSouls() {
        return max;
    }

    public int getCurrentSouls(ItemStack pStack) {
        return pStack.getOrCreateTag().getInt("Souls");
    }

    public void setCount(int count, ItemStack pStack) {
        pStack.removeTagKey("Souls");
        pStack.getOrCreateTag().putInt("Souls", count);
    }

    public void addCount(int count, ItemStack pStack, Player player) {
        if(pStack.getOrCreateTag().getInt("Souls") >= getMaxSouls() - 1) {
            player.getInventory().removeItem(pStack);
            player.addItem(ItemsRegistry.SOUL_COLLECTOR.get().getDefaultInstance());
        } else{
            pStack.getOrCreateTag().putInt("Souls", getCurrentSouls(pStack) + count);
        }
    }

    //todo
    public SoundEvent getTransformSound() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY){
        int xCord = ClientConfig.MISC_UI_X.get() + offsetX;
        int yCord = ClientConfig.MISC_UI_Y.get() + offsetY;
        int progress = 22;
        progress /= (double) getMaxSouls() / (double) tag.getInt("Souls");
        gui.blit(BAR, xCord, yCord, 0, 0, 16, 32, 32, 32);
        gui.blit(BAR, xCord + 4, yCord + 26 - progress, 24, 22 - progress, 8, progress, 32, 32);
    }
}
