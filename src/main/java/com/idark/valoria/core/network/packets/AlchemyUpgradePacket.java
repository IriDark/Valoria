package com.idark.valoria.core.network.packets;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;

public class AlchemyUpgradePacket extends RateLimitedPacket{
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

    public void execute(ServerPlayer player){
        Level level = player.level();
        if (player.containerMenu instanceof AlchemyStationMenu heavyMenu) {
            player.closeContainer();
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
    }
}
