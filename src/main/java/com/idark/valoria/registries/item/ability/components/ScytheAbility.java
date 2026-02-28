package com.idark.valoria.registries.item.ability.components;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.AbilityComponent;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class ScytheAbility extends AbilityComponent {
    public ImmutableList<MobEffectInstance> effects = ImmutableList.of();
    public float screenShakeIntensity = 0.35f;
    public int screenShakeDuration = 4;
    public float chance = 1;
    public Interp screenShakeEasing = Interp.circleOut;
    public ParticleOptions particleOptions = ParticleTypes.POOF;
    public final ArcRandom arcRandom = Tmp.rnd;
    public static final AbilityType<ScytheAbility> TYPE = new AbilityType<>(new ResourceLocation(Valoria.ID, "scythe")) {
        @Override
        public ScytheAbility createInstance() {
            return new ScytheAbility();
        }
    };

    public ScytheAbility(){
        super(TYPE);
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack, CastType castType){
        return Seq.with(
        new TextComponent(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY)),
        new TextComponent(Component.literal("Use " + castType.name()).withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    @Override
    public int execute(ServerPlayer player, Level level, ItemStack stack){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS.get());

        ValoriaUtils.radiusHit(level, stack, player, particleOptions, hitEntities, pos, 0, player.getRotationVector().y, radius);
        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, effects, chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(screenShakeDuration, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 0, 30).intensity(screenShakeIntensity).interp(screenShakeEasing));
        return 0;
    }
}