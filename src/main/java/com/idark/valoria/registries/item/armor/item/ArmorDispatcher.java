package com.idark.valoria.registries.item.armor.item;

import mod.azure.azurelib.rewrite.animation.dispatch.command.*;
import mod.azure.azurelib.rewrite.animation.play_behavior.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class ArmorDispatcher{
    private static final AzCommand IDLE_COMMAND = AzCommand.create(
    "base_controller",
    "idle",
    AzPlayBehaviors.LOOP
    );

    public void idle(Entity entity, ItemStack itemStack) {
        IDLE_COMMAND.sendForItem(entity, itemStack);
    }
}