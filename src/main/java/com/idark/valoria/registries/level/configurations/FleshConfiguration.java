package com.idark.valoria.registries.level.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record FleshConfiguration(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float catalystChance) implements FeatureConfiguration{
    public static final Codec<FleshConfiguration> CODEC = RecordCodecBuilder.create((p_225444_) -> {
        return p_225444_.group(Codec.intRange(1, 32).fieldOf("charge_count").forGetter(FleshConfiguration::chargeCount), Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter(FleshConfiguration::amountPerCharge), Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter(FleshConfiguration::spreadAttempts), Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter(FleshConfiguration::growthRounds), Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter(FleshConfiguration::spreadRounds), IntProvider.CODEC.fieldOf("extra_rare_growths").forGetter(FleshConfiguration::extraRareGrowths), Codec.floatRange(0.0F, 1.0F).fieldOf("catalyst_chance").forGetter(FleshConfiguration::catalystChance)).apply(p_225444_, FleshConfiguration::new);
    });

    public FleshConfiguration(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float catalystChance){
        this.chargeCount = chargeCount;
        this.amountPerCharge = amountPerCharge;
        this.spreadAttempts = spreadAttempts;
        this.growthRounds = growthRounds;
        this.spreadRounds = spreadRounds;
        this.extraRareGrowths = extraRareGrowths;
        this.catalystChance = catalystChance;
    }

    public int chargeCount(){
        return this.chargeCount;
    }

    public int amountPerCharge(){
        return this.amountPerCharge;
    }

    public int spreadAttempts(){
        return this.spreadAttempts;
    }

    public int growthRounds(){
        return this.growthRounds;
    }

    public int spreadRounds(){
        return this.spreadRounds;
    }

    public IntProvider extraRareGrowths(){
        return this.extraRareGrowths;
    }

    public float catalystChance(){
        return this.catalystChance;
    }

}