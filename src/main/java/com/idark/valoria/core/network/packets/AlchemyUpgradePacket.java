package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class AlchemyUpgradePacket{
    public int level;
    public AlchemyUpgradePacket(int level){
        this.level = level;
    }

    public static void encode(AlchemyUpgradePacket object, FriendlyByteBuf buffer){
        buffer.writeInt(object.level);
    }

    public static AlchemyUpgradePacket decode(FriendlyByteBuf buffer){
        return new AlchemyUpgradePacket(buffer.readVarInt());
    }

    public static void handle(AlchemyUpgradePacket packet, Supplier<Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;

            Level level = player.level();
            if (player.containerMenu instanceof AlchemyStationMenu heavyMenu) {
                player.closeContainer();
                level.playSound(null, heavyMenu.getPos(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS);
                BlockState state = level.getBlockState(heavyMenu.getPos());
                if(state.getBlock() instanceof AlchemyStationBlock stationBlock){
                    BlockState toState = switch(stationBlock.level) {
                        case 1 -> BlockRegistry.alchemyStationTier2.get().defaultBlockState();
                        case 2 -> BlockRegistry.alchemyStationTier3.get().defaultBlockState();
                        case 3 -> BlockRegistry.alchemyStationTier4.get().defaultBlockState();
                        default ->  BlockRegistry.alchemyStationTier1.get().defaultBlockState();
                    };

                    stationBlock.upgrade(heavyMenu.getPos(), state, level, toState);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
