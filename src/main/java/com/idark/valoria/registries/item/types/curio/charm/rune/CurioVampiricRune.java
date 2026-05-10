package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class CurioVampiricRune extends AbstractRuneItem implements CurioOnKillItem, CurioOnAttackItem {
    private final float healPercent;
    private final float chance;
    public CurioVampiricRune(float healPercent, float healChance, Properties properties){
        super(properties);
        this.healPercent = healPercent;
        this.chance = healChance;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        int healPercent = (int)(this.healPercent * 100f);
        int healChance = (int)(this.chance * 100f);
        tooltip.add(Component.translatable("tooltip.valoria.vampirism", healPercent, healChance, healPercent).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public RuneType runeType(){
        return RuneType.VAMPIRIC;
    }

    @Override
    public void onAttack(ItemStack stack, LivingEntity target, DamageSource source, float damage){
        var attacker = source.getDirectEntity();
        if(Tmp.rnd.chance(chance)){
            if(attacker instanceof ServerPlayer player){
                Vec3 pos = target.position().add(0, target.getBbHeight() / 2f, 0);
                PacketHandler.sendToTracking(player.serverLevel(), BlockPos.containing(pos), new VampirismParticlePacket(player.getUUID(), pos.x(), pos.y(), pos.z()));

                player.heal(damage * healPercent);
            }
        }
    }

    @Override
    public void onKill(ItemStack stack, ServerPlayer killer, LivingEntity target){
        Vec3 pos = target.position().add(0, target.getBbHeight() / 2f, 0);
        PacketHandler.sendToTracking(killer.serverLevel(), BlockPos.containing(pos), new VampirismParticlePacket(killer.getUUID(), pos.x(), pos.y(), pos.z()));
        killer.heal(killer.getMaxHealth() * healPercent);
    }
}