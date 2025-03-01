package com.idark.valoria.core.config;

import net.minecraftforge.common.*;
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

    static{
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public CommonConfig(ForgeConfigSpec.Builder builder){
        builder.comment("Bosses").push("bosses");
            builder.comment("Necromancer").push("necromancer");
            ATTACK_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("devourer_casting_time", 40);
            ATTACK_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("devourer_casting_interval", 150);
            ATTACK_NECROMANCER_DAMAGE = builder.comment("Damage of summoned devourer").define("devourer_damage", 5);

            SUMMON_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("summon_casting_time", 40);
            SUMMON_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("summon_casting_interval", 200);

            KNOCKBACK_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("knockback_casting_time", 20);
            KNOCKBACK_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("knockback_casting_interval", 65);
            KNOCKBACK_NECROMANCER_RADIUS = builder.comment("Radius of knockback").define("knockback_radius", 3);
            KNOCKBACK_NECROMANCER_RADIUS_STRONG = builder.comment("Radius of strong knockback casted when Necromancer HP is lower than 100").define("knockback_radius_strong", 6);

            TARGET_HEAL_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("target_heal_casting_time", 60);
            TARGET_HEAL_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("target_heal_casting_interval", 200);
            TARGET_HEAL_NECROMANCER_AMOUNT = builder.comment("Amount of healed hp").define("target_heal", 12f);

            SELF_HEAL_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("self_heal_casting_time", 60);
            SELF_HEAL_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("self_heal_casting_interval", 300);
            SELF_HEAL_NECROMANCER_AMOUNT = builder.comment("Amount of healed hp").define("self_heal", 25f);

            EFFECT_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("effect_casting_time", 20);
            EFFECT_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("effect_casting_interval", 150);

            WOLOLO_NECROMANCER_CASTING_TIME = builder.comment("Casting time").define("wololo_casting_time", 60);
            WOLOLO_NECROMANCER_CASTING_INTERVAL = builder.comment("Casting interval").define("wololo_casting_interval", 180);
            builder.pop();

            builder.comment("Wicked Crystal").push("wicked_crystal");
            SHIELDS_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("shields_casting_time", 25);
            SHIELDS_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("shields_casting_interval", 1200);
            SHIELDS_WICKED_CRYSTAL_COUNT_PHASE1 = builder.comment("Count of summoned shields").define("shields_count_phase_1", 2);
            SHIELDS_WICKED_CRYSTAL_COUNT_PHASE2 = builder.comment("Count of summoned shields").define("shields_count_phase_2", 4);
            SHIELDS_WICKED_CRYSTAL_LIMIT = builder.comment("Limit of shields").define("shields_limit",8);

            SUMMON_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("crystal_summon_casting_time", 20);
            SUMMON_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("crystal_summon_casting_interval", 425);

            RADIAL_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("radial_crystals_casting_time", 20);
            RADIAL_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("radial_crystals_casting_interval", 160);
            RADIAL_WICKED_CRYSTAL_DAMAGE = builder.comment("Damage of crystals").define("radial_crystals_damage", 10);

            CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_TIME = builder.comment("Casting time").define("crystal_storm_casting_time", 25);
            CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_INTERVAL = builder.comment("Casting interval").define("crystal_storm_casting_interval", 400);
            builder.pop();
        builder.pop();
    }
}