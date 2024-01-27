package com.idark.valoria;

import com.idark.valoria.capability.IPage;
import com.idark.valoria.capability.PageProvider;
import com.idark.valoria.network.PacketHandler;
import com.idark.valoria.network.PageUpdatePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        //if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(Valoria.MOD_ID, "pages"), new PageProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        /*
        Capability<IPage> PAGE = IPage.INSTANCE;

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(PAGE).ifPresent((k) -> event.getOriginal().getCapability(PAGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new PageUpdatePacket(event.getEntity()));
        }
        */
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        /*
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new PageUpdatePacket((Player)event.getEntity()));
            }
        }
        */
    }
}