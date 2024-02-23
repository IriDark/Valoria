package com.idark.valoria;

import com.idark.valoria.api.unlockable.UnlockUtils;
import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.api.unlockable.Unlockables;
import com.idark.valoria.capability.IUnlockable;
import com.idark.valoria.capability.UnloackbleCap;
import com.idark.valoria.client.gui.screen.book.unlockable.ItemUnlockable;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.UnlockableUpdatePacket;
import com.idark.valoria.registries.TagsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.stream.Stream;

public class Events {

//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event) {
//        event.getAmount();
//    }

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player)
            event.addCapability(new ResourceLocation(Valoria.MOD_ID, "pages"), new UnloackbleCap());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IUnlockable> PAGE = IUnlockable.INSTANCE;

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(PAGE).ifPresent((k) -> event.getOriginal().getCapability(PAGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide) {
            Player player = event.player;
            List<ItemStack> items = player.inventoryMenu.getItems();
            for (Unlockable unlockable : Unlockables.getUnlockables()) {
                if (unlockable instanceof ItemUnlockable itemKnowledge) {
                    if (itemKnowledge.canReceived(items)) {
                        UnlockUtils.addUnlockable(player, unlockable);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket((Player) event.getEntity()));
            }
        }
    }
}