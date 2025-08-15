package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.Item.*;
import net.minecraft.world.item.*;

public abstract class AbstractSpearBuilder<T extends SpearItem>{
    public Tier tier = ItemTierRegistry.NONE;
    public Properties itemProperties;
    public ImmutableList<MobEffectInstance> effects = ImmutableList.of();

    public float attackDamageIn;
    public float projectileDamageIn;
    public float attackSpeedIn;
    public float chance = 1;

    public boolean throwable = true;

    public AbstractSpearBuilder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
        this.attackDamageIn = attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeedIn = attackSpeedIn;
        this.projectileDamageIn = attackDamageIn + tier.getAttackDamageBonus() + 2;
        this.itemProperties = itemProperties;
    }
    public AbstractSpearBuilder<T> setThrowable(boolean throwable){
        this.throwable = throwable;
        return this;
    }

    public AbstractSpearBuilder<T> setTier(Tier tier){
        this.tier = tier;
        return this;
    }

    public AbstractSpearBuilder<T> setProjectileDamage(float projectileDamageIn){
        this.projectileDamageIn = projectileDamageIn;
        return this;
    }

    /**
     * @param chance   Chance of applying effects to target
     * @param pEffects Effects that will be applied to target
     */
    public AbstractSpearBuilder<T> setEffects(float chance, MobEffectInstance... pEffects){
        this.chance = chance;
        this.effects = ImmutableList.copyOf(pEffects);
        return this;
    }

    /**
     * @param pEffects Effects that will be applied to target
     */
    public AbstractSpearBuilder<T> setEffects(MobEffectInstance... pEffects){
        this.effects = ImmutableList.copyOf(pEffects);
        return this;
    }

    /**
     * @return Build of KatanaItem with all the configurations you set :p
     */
    public abstract T build();
}
