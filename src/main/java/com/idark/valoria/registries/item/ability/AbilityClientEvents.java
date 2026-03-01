package com.idark.valoria.registries.item.ability;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.item.ability.AbilityHelper.*;
import com.idark.valoria.registries.item.ability.itemComponents.*;
import com.mojang.datafixers.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import org.lwjgl.glfw.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class AbilityClientEvents{
    public static int currentIndex = 0;
    private static ItemStack lastHoveredStack = ItemStack.EMPTY;
    private static ItemStack cachedTooltipStack = ItemStack.EMPTY;
    private static CompoundTag cachedTooltipNBT = null;
    private static List<ActiveAbility> cachedAbilities = new ArrayList<>();

    @SubscribeEvent
    public static void onKeyInput(ScreenEvent.KeyPressed event){
        if (event.getScreen() instanceof AbstractContainerScreen<?> gui){
            Slot hoveredSlot = gui.getSlotUnderMouse();
            if(hoveredSlot != null && hoveredSlot.hasItem()){
                ItemStack stack = hoveredSlot.getItem();
                if(stack.hasTag() && stack.getTag().contains("valoria_abilities")){
                    setLastHoveredStack(stack);
                    if(event.getKeyCode() == GLFW.GLFW_KEY_DOWN || event.getKeyCode() == GLFW.GLFW_KEY_LEFT){
                        currentIndex--;
                    }else if(event.getKeyCode() == GLFW.GLFW_KEY_UP || event.getKeyCode() == GLFW.GLFW_KEY_RIGHT){
                        currentIndex++;
                    }

                    event.setCanceled(true);
                }
            } else {
                resetLastHovered();
            }
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(ScreenEvent.MouseScrolled.Pre event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> gui){
            Slot hoveredSlot = gui.getSlotUnderMouse();
            if(hoveredSlot != null && hoveredSlot.hasItem()){
                ItemStack stack = hoveredSlot.getItem();
                if(stack.hasTag() && stack.getTag().contains("valoria_abilities")){
                    setLastHoveredStack(stack);
                    if(event.getScrollDelta() < 0){
                        currentIndex--;
                    }else if(event.getScrollDelta() > 0){
                        currentIndex++;
                    }

                    event.setCanceled(true);
                }
            } else {
                resetLastHovered();
            }
        }
    }

    private static void setLastHoveredStack(ItemStack stack){
        if(!ItemStack.isSameItemSameTags(stack, lastHoveredStack)){
            lastHoveredStack = stack.copy();
            currentIndex = 0;
        }
    }

    private static void resetLastHovered(){
        lastHoveredStack = ItemStack.EMPTY;
        currentIndex = 0;
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.InteractionKeyMappingTriggered event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!stack.hasTag() || !stack.getTag().contains("valoria_abilities")) return;

        boolean isShift = player.isShiftKeyDown();
        boolean isLeftClick = event.isAttack();
        boolean isRightClick = event.isUseItem();

        CastType type = null;
        if (isLeftClick) {
            type = isShift ? CastType.SHIFT_LEFT_CLICK : CastType.LEFT_CLICK;
        } else if (isRightClick) {
            type = isShift ? CastType.SHIFT_RIGHT_CLICK : CastType.RIGHT_CLICK;
        }

        if (type != null) {
            AbilityComponent ability = AbilityHelper.getAbility(stack, type);
            if (ability != null) {
                PacketHandler.sendToServer(new CastAbilityPacket(type.ordinal()));
                event.setCanceled(ability.cancelVanillaBehaviour);
            }
        }
    }

    @SubscribeEvent
    public static void onTooltipGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        if (!stack.hasTag() || !stack.getTag().contains("valoria_abilities")) return;

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        CompoundTag currentNBT = stack.getTag();
        if (stack != cachedTooltipStack || !Objects.equals(currentNBT, cachedTooltipNBT)) {
            cachedTooltipStack = stack;
            cachedTooltipNBT = currentNBT != null ? currentNBT.copy() : null;
            cachedAbilities = AbilityHelper.getActiveAbilities(stack);
        }

        List<ActiveAbility> abilities = cachedAbilities;
        if (abilities.isEmpty()) return;

        List<Either<FormattedText, TooltipComponent>> elements = event.getTooltipElements();
        int insertIndex = 1;
        int size = abilities.size();
        int page = (currentIndex % size + size) % size;

        AbilityHelper.ActiveAbility active = abilities.get(page);

        elements.add(insertIndex++, Either.right(new TextComponent(Component.empty())));
        elements.add(insertIndex++, Either.right(new SeparatorComponent(Component.literal("◆ Abilities [ " + (page + 1) + " / " + size + " ] ◆"))));
        elements.add(insertIndex++, Either.right(new AbilityPaginationComponent(abilities, page)));
        elements.add(insertIndex++, Either.right(new TextComponent(Component.empty())));

        Seq<TooltipComponent> components = active.ability().getTooltips(stack, active.type());
        for (TooltipComponent component : components) {
            elements.add(insertIndex, Either.right(component));
            insertIndex++;
        }
    }
}