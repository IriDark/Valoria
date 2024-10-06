package com.idark.valoria.registries.level.configurations;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.*;

public class SuspiciousStateConfiguration implements FeatureConfiguration {
    public static final Codec<SuspiciousStateConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(
                    Codec.list(SuspiciousStateConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter((p_161027_) -> p_161027_.targetStates),
                    ResourceLocation.CODEC.fieldOf("loot").forGetter((p_161020_) -> p_161020_.loot),
                    Codec.intRange(0, 64).fieldOf("tries").forGetter((p_161025_) -> p_161025_.tries))
            .apply(instance, SuspiciousStateConfiguration::new)
    );

    public final List<SuspiciousStateConfiguration.TargetBlockState> targetStates;
    public final int tries;
    public final ResourceLocation loot;

    public SuspiciousStateConfiguration(List<SuspiciousStateConfiguration.TargetBlockState> targetStates, int tries, ResourceLocation loot) {
        this.targetStates = targetStates;
        this.tries = tries;
        this.loot = loot;
    }

    public SuspiciousStateConfiguration(List<TargetBlockState> targetBlockStates, ResourceLocation loot, int tries) {
        this(targetBlockStates, tries, loot);
    }

    public static SuspiciousStateConfiguration.TargetBlockState target(RuleTest target, BlockState state) {
        return new SuspiciousStateConfiguration.TargetBlockState(target, state);
    }

    public static class TargetBlockState {
        public static final Codec<SuspiciousStateConfiguration.TargetBlockState> CODEC =
                RecordCodecBuilder.create((instance) -> instance
                        .group(
                                RuleTest.CODEC.fieldOf("target").forGetter((p_161043_) -> p_161043_.target),
                                BlockState.CODEC.fieldOf("state").forGetter((p_161041_) -> p_161041_.state)
                        )
                        .apply(instance, TargetBlockState::new)
                );

        public final RuleTest target;
        public final BlockState state;

        TargetBlockState(RuleTest target, BlockState state) {
            this.target = target;
            this.state = state;
        }
    }
}
