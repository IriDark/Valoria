package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class HoundItem extends SwordItem implements TooltipComponentItem{

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

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.valoria.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.bloodhound").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/blood_seeking.png")),
        new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 325);
        player.level().playSound(null, player.getOnPos(), SoundsRegistry.BLOODHOUND_ABILITY.get(), SoundSource.AMBIENT, 0.4f, 1.2f);
        Vec3 pos = new Vec3(player.getX(), player.getY() + 0.2f, player.getZ());
        List<LivingEntity> markedEntities = new ArrayList<>();
        markNearbyMobs(level, player, markedEntities, pos, 15);
        return stack;
    }

    public static void markNearbyMobs(Level pLevel, Player pPlayer, List<LivingEntity> hitEntities, Vec3 pos, float radius) {
        AABB boundingBox = new AABB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, boundingBox);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(pPlayer)) {
                hitEntities.add(livingEntity);
                if (!livingEntity.isAlive()) {
                    return;
                }

                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 160, 0, false, false, false));
                if(pLevel instanceof ServerLevel pServ){
                    PacketHandler.sendToTracking(pServ, pPlayer.getOnPos(), new LineToNearbyMobsParticlePacket(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), pPlayer.getRotationVector().y, 15, 255, 0, 0));
                }
            }
        }
    }
}