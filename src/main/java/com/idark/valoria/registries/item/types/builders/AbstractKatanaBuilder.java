package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.core.particles.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.*;

import java.awt.*;

public abstract class AbstractKatanaBuilder<T extends KatanaItem>{
    public Tier tier = ItemTierRegistry.NONE;
    public Item.Properties itemProperties;
    public ResourceLocation texture = new ResourceLocation(Valoria.ID, "textures/gui/overlay/speedlines.png");
    public SoundEvent dashSound = SoundsRegistry.SWIFTSLICE.get();
    public SoundEvent cooldownSound = SoundsRegistry.RECHARGE.get();
    public SoundEvent chargedSound;
    public Color dashColor;
    public boolean usePacket = false;
    public boolean hasLargeModel = true;
    public float attackDamageIn;
    public float attackSpeedIn;
    public float chance = 1;
    public int overlayTime = 35;
    public int cooldownTime = 75;
    public int chargeTime = 0;
    public float dashDist = 1.0f;
    public ImmutableList<MobEffectInstance> effects = ImmutableList.of();
    public ParticleOptions particleOptions = ParticleTypes.POOF;

    public AbstractKatanaBuilder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
        this.attackDamageIn = attackDamageIn;
        this.attackSpeedIn = attackSpeedIn;
        this.itemProperties = itemProperties;
    }

    public AbstractKatanaBuilder<T> setTier(Tier tier){
        this.tier = tier;
        return this;
    }

    /**
     * @param event Sound that will be played when dash is performed
     */
    public AbstractKatanaBuilder<T> setDashSound(SoundEvent event){
        this.dashSound = event;
        return this;
    }

    /**
     * @param event Sound that will be played after cooldown ending
     */
    public AbstractKatanaBuilder<T> setCooldownSound(SoundEvent event){
        this.cooldownSound = event;
        return this;
    }

    /**
     * Currently a bit buged, called two times instead of one, but anyway :d
     *
     * @param event Sound that will be played when Katana is ready to perform dash
     */
    public AbstractKatanaBuilder<T> setChargedSound(SoundEvent event){
        this.chargedSound = event;
        return this;
    }

    /**
     * @param particleOptions Particle trail that will appear after dashing
     */
    public AbstractKatanaBuilder<T> setParticles(ParticleOptions particleOptions){
        this.particleOptions = particleOptions;
        return this;
    }

    /**
     * Particle trail that will appear after dashing, but sent through a DashParticlePacket
     *
     * @param color Particle color
     */
    public AbstractKatanaBuilder<T> usePacket(Color color){
        this.usePacket = true;
        this.dashColor = color;
        return this;
    }

    public AbstractKatanaBuilder<T> removeLargeModelCheck(){
        this.hasLargeModel = false;
        return this;
    }

    /**
     * @param chance   Chance of applying effects to target
     * @param pEffects Effects that will be applied to target
     */
    public AbstractKatanaBuilder<T> setEffects(float chance, MobEffectInstance... pEffects){
        this.chance = chance;
        this.effects = ImmutableList.copyOf(pEffects);
        return this;
    }

    /**
     * @param pEffects Effects that will be applied to target
     */
    public AbstractKatanaBuilder<T> setEffects(MobEffectInstance... pEffects){
        this.effects = ImmutableList.copyOf(pEffects);
        return this;
    }

    public AbstractKatanaBuilder<T> setTimeToCharge(int useTime){
        this.chargeTime = useTime;
        return this;
    }

    public AbstractKatanaBuilder<T> setOverlayTime(int time){
        this.overlayTime = time;
        return this;
    }

    public AbstractKatanaBuilder<T> setCooldownTime(int cooldownTime){
        this.cooldownTime = cooldownTime;
        return this;
    }

    public AbstractKatanaBuilder<T> setDashDistance(float distance){
        this.dashDist = distance;
        return this;
    }

    /**
     * @param texture a ResourceLocation of texture that will be shown after dash is performed
     */
    public AbstractKatanaBuilder<T> setOverlay(ResourceLocation texture){
        this.texture = texture;
        return this;
    }

    /**
     * @return Build of KatanaItem with all the configurations you set :p
     */
    public abstract T build();
}
