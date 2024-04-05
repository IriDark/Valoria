package com.idark.valoria;

import com.idark.valoria.api.unlockable.UnlockUtils;
import com.idark.valoria.api.unlockable.Unlockable;
import com.idark.valoria.api.unlockable.Unlockables;
import com.idark.valoria.client.gui.screen.book.unlockable.ItemUnlockable;
import com.idark.valoria.core.capability.IUnlockable;
import com.idark.valoria.core.capability.UnloackbleCap;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.UnlockableUpdatePacket;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@SuppressWarnings("removal")
public class Events {

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player)
            event.addCapability(new ResourceLocation(Valoria.MOD_ID, "pages"), new UnloackbleCap());
    }

    @SubscribeEvent
    public void disableBlock(ShieldBlockEvent event) {
        if (event.getDamageSource().getDirectEntity() instanceof Player player) {
            LivingEntity mob = event.getEntity();
            ItemStack weapon = player.getMainHandItem();
            if (!weapon.isEmpty() && weapon.is(TagsRegistry.CAN_DISABLE_SHIELD) && mob instanceof Player attacked) {
                attacked.disableShield(true);
            }
        }
    }
    
    @SubscribeEvent
    public void critDamage(CriticalHitEvent event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(ItemsRegistry.RUNE_OF_ACCURACY.get(), event.getEntity()).isPresent()) {
            if (RandomUtil.percentChance(0.1f)) {
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), (float) (event.getEntity().getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f));
            }
        }
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