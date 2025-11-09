package com.idark.valoria.core.config;

import net.minecraftforge.common.*;
import net.minecraftforge.common.ForgeConfigSpec.*;
import org.apache.commons.lang3.tuple.*;

public class CommonConfig{
    public static ForgeConfigSpec.ConfigValue<Integer>
    ATTACK_NECROMANCER_CASTING_TIME, ATTACK_NECROMANCER_CASTING_INTERVAL, ATTACK_NECROMANCER_DAMAGE,
    SUMMON_NECROMANCER_CASTING_TIME, SUMMON_NECROMANCER_CASTING_INTERVAL,
    KNOCKBACK_NECROMANCER_CASTING_TIME, KNOCKBACK_NECROMANCER_CASTING_INTERVAL, KNOCKBACK_NECROMANCER_RADIUS, KNOCKBACK_NECROMANCER_RADIUS_STRONG,
    TARGET_HEAL_NECROMANCER_CASTING_TIME, TARGET_HEAL_NECROMANCER_CASTING_INTERVAL,
    SELF_HEAL_NECROMANCER_CASTING_TIME, SELF_HEAL_NECROMANCER_CASTING_INTERVAL,
    EFFECT_NECROMANCER_CASTING_TIME, EFFECT_NECROMANCER_CASTING_INTERVAL,
    WOLOLO_NECROMANCER_CASTING_TIME, WOLOLO_NECROMANCER_CASTING_INTERVAL,

    SHIELDS_WICKED_CRYSTAL_CASTING_TIME, SHIELDS_WICKED_CRYSTAL_CASTING_INTERVAL, SHIELDS_WICKED_CRYSTAL_COUNT_PHASE1, SHIELDS_WICKED_CRYSTAL_COUNT_PHASE2, SHIELDS_WICKED_CRYSTAL_LIMIT,
    SUMMON_WICKED_CRYSTAL_CASTING_TIME, SUMMON_WICKED_CRYSTAL_CASTING_INTERVAL,
    RADIAL_WICKED_CRYSTAL_CASTING_TIME, RADIAL_WICKED_CRYSTAL_CASTING_INTERVAL, RADIAL_WICKED_CRYSTAL_DAMAGE,
    CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_TIME, CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_INTERVAL;

    public static ForgeConfigSpec.ConfigValue<Float>
    TARGET_HEAL_NECROMANCER_AMOUNT, SELF_HEAL_NECROMANCER_AMOUNT;

    public static ForgeConfigSpec.ConfigValue<Boolean>
    FOOD_ROT, NIHILITY;

    static{
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public CommonConfig(ForgeConfigSpec.Builder builder){
        builder.comment("Gameplay").push("gameplay");
            NIHILITY = builder.comment("Nihility System in Valoria, Default: true)").define("nihility", true);
            FOOD_ROT = builder.comment("Food spoiling on entering Valoria dimension, Default: true)").define("foodRot", true);
            setupBosses(builder);
        builder.pop();
    }

    private void setupBosses(Builder builder){
        builder.comment("Bosses").push("bosses");
            builder.comment("Necromancer").push("necromancer");
            ATTACK_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("devourerCastingTime", 40);
            ATTACK_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("devourerCastingInterval", 150);
            ATTACK_NECROMANCER_DAMAGE = builder.comment("Damage of summoned devourer").define("devourerDamage", 5);

            SUMMON_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("summonCastingTime", 40);
            SUMMON_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("summonCastingInterval", 200);

            KNOCKBACK_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("knockbackCastingTime", 20);
            KNOCKBACK_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("knockbackCastingInterval", 65);
            KNOCKBACK_NECROMANCER_RADIUS = builder.comment("Radius of knockback").define("knockbackRadius", 3);
            KNOCKBACK_NECROMANCER_RADIUS_STRONG = builder.comment("Radius of strong knockback casted when Necromancer HP is lower than 100").define("knockbackRadiusStrong", 6);

            TARGET_HEAL_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("targetHealCastingTtime", 60);
            TARGET_HEAL_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("targetHealCastingInterval", 200);
            TARGET_HEAL_NECROMANCER_AMOUNT = builder.comment("Amount of healed hp").define("targetHeal", 12f);

            SELF_HEAL_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("selfHealCastingTime", 60);
            SELF_HEAL_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("selfHealCastingInterval", 300);
            SELF_HEAL_NECROMANCER_AMOUNT = builder.comment("Amount of healed hp").define("self_heal", 25f);

            EFFECT_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("effectCastingTime", 20);
            EFFECT_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("effectCastingInterval", 150);

            WOLOLO_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("wololo_casting_time", 60);
            WOLOLO_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("wololoCastingInterval", 180);
            builder.pop();

            builder.comment("Wicked Crystal").push("wicked_crystal");
            SHIELDS_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("shieldsCastingTime", 25);
            SHIELDS_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("shieldsCastingInterval", 1200);
            SHIELDS_WICKED_CRYSTAL_COUNT_PHASE1 = builder.comment("Count of summoned shields").define("shieldsCountPhase_1", 2);
            SHIELDS_WICKED_CRYSTAL_COUNT_PHASE2 = builder.comment("Count of summoned shields").define("shieldsCountPhase_2", 4);
            SHIELDS_WICKED_CRYSTAL_LIMIT = builder.comment("Limit of shields").define("shieldsLimit",8);

            SUMMON_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("crystalSummonCastingTime", 20);
            SUMMON_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("crystalSummonCastingInterval", 425);

            RADIAL_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("radialCrystalsCastingTime", 20);
            RADIAL_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("radialCrystalsCastingInterval", 160);
            RADIAL_WICKED_CRYSTAL_DAMAGE = builder.comment("Damage of crystals").define("radialCrystalsDamage", 4);

            CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("crystalStormCastingTime", 25);
            CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("crystalStormCastingInterval", 400);
            builder.pop();
        builder.pop();
    }
}