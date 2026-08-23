package com.idark.valoria.core.capability;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.registries.level.events.*;
import com.idark.valoria.util.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.TickEvent.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraftforge.eventbus.api.*;
import pro.komaru.tridot.api.render.text.DotStyleEffects.*;
import pro.komaru.tridot.client.gfx.text.*;

import java.util.*;

public class CapabilityEvents{

    @SubscribeEvent
    public void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event){
        Player player = event.getEntity();
        if(player instanceof ServerPlayer sp){
            ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
            Set<Unlockable> unlocked = UnlockUtils.getUnlocked(player);
            if(unlocked != null) all.removeAll(unlocked);
            for(Unlockable unknown : all){
                if(unknown instanceof OnDimensionChangeListener entityU) entityU.checkCondition(sp, event.getTo());
            }
        }

        if(event.getTo() == LevelGen.VALORIA_KEY){
            onValoriaEnter(player);
        }
    }

    public void onValoriaEnter(Player player){
        Level level = player.level();
        if(level instanceof ServerLevel s && player instanceof ServerPlayer sp){
            ResourceLocation loc = Valoria.loc("advancements/valoria/visit_the_valoria.json");
            Advancement adv = s.getServer().getAdvancements().getAdvancement(loc);
            if(adv == null || !sp.getAdvancements().getOrStartProgress(adv).isDone()) {
                player.displayClientMessage(Component.translatable("tooltip.valoria.nihility").withStyle(DotStyle.of().effects(WaveFX.of(0.25f, 0.1f), OutlineFX.of(Pal.amethyst, true))), true);
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event){
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if(!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer){
            if(ServerConfig.ENABLE_NIHILITY.get()){
                tickNihility(event, serverPlayer, player);
            }

            tickMagma(event, player);
            tickCodex(serverPlayer, player);
        }

        if(ServerConfig.ENABLE_NIHILITY.get()){
            if(player.level().isClientSide()){
                player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> NihilityEvent.clientTick(nihilityLevel, player));
            }
        }
    }

    private void tickNihility(PlayerTickEvent event, ServerPlayer serverPlayer, Player player){
        player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> {
            if(!player.getAbilities().instabuild && !player.isSpectator()){
                NihilityEvent.tick(nihilityLevel, serverPlayer);
            }
        });
    }

    private void tickCodex(ServerPlayer serverPlayer, Player player){
        if(player.tickCount % ServerConfig.CODEX_UPDATE_INTERVAL.get() * 20 == 0){
            ArrayList<Unlockable> all = new ArrayList<>(Unlockables.get());
            Set<Unlockable> unlocked = UnlockUtils.getUnlocked(serverPlayer);
            if(unlocked != null) all.removeAll(unlocked);
            for(Unlockable unknown : all){
                unknown.tick(serverPlayer);
            }
        }
    }

    private void tickMagma(PlayerTickEvent event, Player player){
        player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magmaLevel -> {
            if(!player.getAbilities().instabuild && !player.isSpectator()){
                MagmaEvent.tick(event, magmaLevel, player);
            }
        });
    }

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            event.addCapability(Valoria.loc("pages"), new UnlockableProvider());
            event.addCapability(Valoria.loc("nihility_level"), new NihilityLevelProvider());
            event.addCapability(Valoria.loc("magma_level"), new MagmaLevelProvider());
            if (!event.getObject().getCapability(PlayerAbilityProvider.PLAYER_ABILITIES).isPresent()) {
                event.addCapability(Valoria.loc("ability_tracker"), new PlayerAbilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = event.getServer();
        if (server.getTickCount() % 100 != 0) return;
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            Set<Unlockable> unlocked = UnlockUtils.getUnlocked(player);
            for(Unlockable unknown : Unlockables.get()){
                if (unlocked != null && unlocked.contains(unknown)) continue;
                if(unknown instanceof OnDungeonVisitListener entityU) entityU.checkCondition(player, player.serverLevel());
            }
        }
    }

    @SubscribeEvent
    public void onMobKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity victim = event.getEntity();
            Set<Unlockable> unlocked = UnlockUtils.getUnlocked(player);
            for(Unlockable unknown : Unlockables.get()){
                if (unlocked != null && unlocked.contains(unknown)) continue;
                if(unknown instanceof OnMobKilledListener entityU) entityU.checkCondition(player, victim);
            }
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerRespawnEvent ev){
        Player player = ev.getEntity();
        player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihilityLevel -> {
            nihilityLevel.setAmountFromServer(player, 0);
        });

        player.getCapability(IMagmaLevel.INSTANCE).ifPresent(nihilityLevel -> {
            nihilityLevel.setAmountFromServer(player, 0);
        });
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event){
        event.getOriginal().reviveCaps();
        boolean isServer = !event.getEntity().level().isClientSide;
        ServerPlayer serverPlayer = isServer ? (ServerPlayer)event.getEntity() : null;
        event.getEntity().getCapability(IUnlockable.INSTANCE).ifPresent(newStore ->
        event.getOriginal().getCapability(IUnlockable.INSTANCE).ifPresent(newStore::copyFrom)
        );
        if(isServer) PacketHandler.sendTo(serverPlayer, new UnlockableUpdatePacket(event.getEntity()));

        event.getEntity().getCapability(INihilityLevel.INSTANCE).ifPresent(newStore ->
        event.getOriginal().getCapability(INihilityLevel.INSTANCE).ifPresent(newStore::copyFrom)
        );
        if(isServer) PacketHandler.sendTo(serverPlayer, new NihilityPacket(new NihilityLevelProvider(), event.getEntity()));

        event.getEntity().getCapability(IMagmaLevel.INSTANCE).ifPresent(newStore ->
        event.getOriginal().getCapability(IMagmaLevel.INSTANCE).ifPresent(newStore::copyFrom)
        );
        if(isServer) PacketHandler.sendTo(serverPlayer, new MagmaPacket(new MagmaLevelProvider(), event.getEntity()));

        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerAbilityProvider.PLAYER_ABILITIES).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerAbilityProvider.PLAYER_ABILITIES).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }

        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide){
            if(event.getEntity() instanceof Player player){
                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new UnlockableUpdatePacket(player));
                player.getCapability(INihilityLevel.INSTANCE).ifPresent(nihility -> {
                    nihility.modifyAmount(player, 1);
                    nihility.decrease(player, 1);
                });

                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new NihilityPacket(new NihilityLevelProvider(), player));

                player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magma -> {
                    magma.modifyAmount(player, 1);
                    magma.decrease(player, 1);
                });

                PacketHandler.sendTo((ServerPlayer)event.getEntity(), new MagmaPacket(new MagmaLevelProvider(), player));

            }
        }
    }
}
