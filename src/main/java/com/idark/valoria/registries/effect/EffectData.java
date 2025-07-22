package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;

import java.util.function.*;

//todo
public record EffectData(Supplier<MobEffectInstance> instance, float chance) {
        public EffectData(Supplier<MobEffectInstance> instance, float chance) {
            this.instance = instance;
            this.chance = chance;
        }

        public static EffectData createData(Supplier<MobEffectInstance> instance) {
            return new EffectData(instance, 1);
        }

        public static EffectData createData(Supplier<MobEffectInstance> instance, float chance) {
            return new EffectData(instance, chance);
        }

        public Supplier<MobEffectInstance> getInstance() {
            return this.instance;
        }

        public float getChance() {
            return this.chance;
        }
    }
