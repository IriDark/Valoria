package com.idark.valoria.registries.item.ability.components;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.AbilityComponent;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.gui.overlay.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.struct.data.*;

import java.lang.Math;

public class DashAbility extends AbilityComponent {
    public static final AbilityType<DashAbility> TYPE = new AbilityType<>(new ResourceLocation(Valoria.ID, "dash")) {
        @Override
        public DashAbility createInstance() {
            return new DashAbility();
        }
    };

    public DashAbility(){
        super(TYPE);
        this.icon = Valoria.loc("textures/gui/tooltips/dash.png");
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack, CastType castType){
        return Seq.with(
        new TextComponent(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY)),
        new TextComponent(Component.literal("Use " + castType.name).withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }

    public static double distance(double distance, Level level, Player player){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = Utils.Hit.hitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if(hitresult != null){
            switch(hitresult.getType()){
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public double getDashDistance(Player player){
        return 1;
    }

    public void performDash(Player player, ItemStack stack, double dashDistance) {
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.hurtMarked = true;
        player.push(dir.x, dir.y * 0.25, dir.z);
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        performDash(player, stack, dashDistance);
        if(level instanceof ServerLevel srv){
            PacketHandler.sendToTracking(srv, player.getOnPos(), new DashParticlePacket(player.getUUID(), 1, 0, 0, 0, Col.white.toJava()));
        }
    }

    @Override
    public int execute(ServerPlayer player, Level level, ItemStack stack) {
        RandomSource rand = level.getRandom();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        if(!player.isFallFlying()){
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            performDash(stack, level, player, pos, rand);
            level.playSound(null, player.getOnPos(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.PLAYERS, 1F, 1F);
            if(level.isClientSide){
                OverlayHandler.addInstance(new TimedOverlayInstance().setTexture(new ResourceLocation(Valoria.ID, "textures/gui/overlay/speedlines.png")).setShowTime(25).setFadeIn(0));
            }
        }

        return 60;
    }
}