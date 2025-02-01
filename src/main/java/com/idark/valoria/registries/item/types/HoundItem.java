package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.utilities.*;

import java.util.*;

public class HoundItem extends SwordItem{

    public HoundItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public int getUseDuration(ItemStack stack){
        return 25;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 120);
        player.level().playSound(null, player.getOnPos(), SoundsRegistry.BLOODHOUND_ABILITY.get(), SoundSource.AMBIENT, 0.4f, 1.2f);
        Vec3 pos = new Vec3(player.getX(), player.getY() + 0.2f, player.getZ());
        List<LivingEntity> markedEntities = new ArrayList<>();
        if(level instanceof ServerLevel pServ){
            Utils.Hit.markNearbyMobs(level, player, markedEntities, pos, 0, player.getRotationVector().y, 15);
            PacketHandler.sendToTracking(pServ, player.getOnPos(), new LineToNearbyMobsParticlePacket(player.getX(), player.getY(), player.getZ(), player.getRotationVector().y, 15, 255, 0, 0));
        }

        return stack;
    }
}