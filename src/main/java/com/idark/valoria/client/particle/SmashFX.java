package com.idark.valoria.client.particle;

import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.comps.phys.*;
import pro.komaru.tridot.util.math.*;

@OnlyIn(Dist.CLIENT)
public class SmashFX {

    public static void play(Level level, Player player, Vec3 pos, SmashType type) {
        double groundY = player.getY() + 0.05f;

        // Base ground crack decal
        ParticleBuilder.create(ParticleRegistry.CRUSHED_GROUND.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setScaleData(GenericParticleData.create(1, 1).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(175)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(0, 0.8f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        // Block debris crumbs
        if (!level.getBlockState(player.blockPosition().below()).is(Blocks.AIR)) {
            var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), level.getBlockState(player.blockPosition().below()));
            ParticleBuilder.create(opt)
                .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                .setSpinData(SpinParticleData.create(0.15f, 0, 0).randomOffset().randomSpin(0.15f).setEasing(Interp.sineIn).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.02f, 0).build())
                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                .setLifetime(150)
                .setFriction(1.08f)
                .randomVelocity(0.45, 0.75, 0.45)
                .randomOffset(0.125, 0.125)
                .setGravity(0.75f)
                .repeat(level, pos.x, pos.y, pos.z, 16);
        }

        // Branch per smash style
        switch (type) {
            case BLACK_GOLD -> playBlackGold(level, player, pos, groundY);
            case INFERNAL -> playInfernal(level, player, pos, groundY);
            case VOID -> playVoid(level, player, pos, groundY);
            case COLLAPSE -> playCollapse(level, player, pos, groundY);
            default -> playDefault(level, player, pos, groundY);
        }
    }

    private static void playDefault(Level level, Player player, Vec3 pos, double groundY) {
        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.white, Pal.lightishGray).build())
            .setScaleData(GenericParticleData.create(0f, 4f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(50)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.8f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.white, Pal.lightishGray).build())
            .setScaleData(GenericParticleData.create(0f, 4f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(40)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.8f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.DUST)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.white, Pal.darkishGray).build())
            .setLifetime(75)
            .setFriction(1f)
            .randomOffset(0.05, 0)
            .randomVelocity(0.15, 0.025)
            .repeat(level, pos.x, groundY, pos.z, 40);

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(15, Pos3.init((float)pos.x, (float)pos.y, (float)pos.z), 3, 8).intensity(0.8f).interp(Interp.sineIn));
    }

    private static void playBlackGold(Level level, Player player, Vec3 pos, double groundY) {
        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.white, Pal.lightishGray).build())
            .setScaleData(GenericParticleData.create(0f, 4.2f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(50)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.85f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.DUST)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.white, Pal.lightishGray).build())
            .setLifetime(75)
            .setFriction(1f)
            .randomOffset(0.05, 0)
            .randomVelocity(0.18, 0.03)
            .repeat(level, pos.x, groundY, pos.z, 45);

        ParticleBuilder.create(TridotParticles.SQUARE)
            .setBehavior(SparkParticleBehavior.create().build())
            .setScaleData(GenericParticleData.create(0.0125f, 0.02f, 0).setEasing(Interp.bounce).build())
            .setLifetime(35)
            .setColorData(ColorParticleData.create(Col.white, Col.yellow).setEasing(Interp.bounce).build())
            .randomVelocity(0.125, 0.25, 0.125)
            .setHasPhysics(false)
            .repeat(level, pos.x, groundY, pos.z, 40);

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(16, Pos3.init((float)pos.x, (float)pos.y, (float)pos.z), 3, 8).intensity(0.85f).interp(Interp.sineIn));
    }

    private static void playInfernal(Level level, Player player, Vec3 pos, double groundY) {
        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.infernalBright, Pal.magmatic).build())
            .setScaleData(GenericParticleData.create(0f, 4.5f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(55)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.9f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.infernalBright, Pal.magmatic).build())
            .setScaleData(GenericParticleData.create(0f, 4.5f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(40)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.9f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.DUST)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.infernal, Pal.magmatic).build())
            .setLifetime(70)
            .setFriction(1f)
            .randomOffset(0.05, 0)
            .randomVelocity(0.2, 0.05)
            .repeat(level, pos.x, groundY, pos.z, 40);

        for (int i = 0; i < 20; i++) {
            double rx = (Math.random() - 0.5) * 2.5;
            double rz = (Math.random() - 0.5) * 2.5;
            level.addParticle(ParticleTypes.FLAME, pos.x + rx, groundY + 0.1, pos.z + rz, rx * 0.15, 0.2, rz * 0.15);
        }

        for (int i = 0; i < 8; i++) {
            double rx = (Math.random() - 0.5) * 1.5;
            double rz = (Math.random() - 0.5) * 1.5;
            level.addParticle(ParticleTypes.LAVA, pos.x + rx, groundY + 0.2, pos.z + rz, 0, 0.3, 0);
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(18, Pos3.init((float)pos.x, (float)pos.y, (float)pos.z), 4, 10).intensity(1.0f).interp(Interp.sineIn));
    }

    private static void playVoid(Level level, Player player, Vec3 pos, double groundY) {
        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Pal.verySoftPink).build())
            .setScaleData(GenericParticleData.create(0f, 4.5f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(50)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.9f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Pal.verySoftPink).build())
            .setScaleData(GenericParticleData.create(0f, 4.5f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(40)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.9f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.SMASH_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Pal.verySoftPink).build())
            .setScaleData(GenericParticleData.create(0f, 4.5f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(60)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1, 0.9f, 0).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.DUST)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Col.fromHex("220033")).build())
            .setLifetime(80)
            .setFriction(1f)
            .randomOffset(0.05, 0)
            .randomVelocity(0.18, 0.04)
            .repeat(level, pos.x, groundY, pos.z, 40);

        ParticleBuilder.create(TridotParticles.WISP)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Col.fromHex("220033")).build())
            .setLifetime(50)
            .randomOffset(0.8, 0.1)
            .randomVelocity(0.15, 0.25)
            .repeat(level, pos.x, groundY + 0.1, pos.z, 25);

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(18, Pos3.init((float)pos.x, (float)pos.y, (float)pos.z), 4, 10).intensity(1.0f).interp(Interp.sineIn));
    }

    private static void playCollapse(Level level, Player player, Vec3 pos, double groundY) {
        ParticleBuilder.create(ParticleRegistry.INWARD_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Col.cyan, Pal.majestyPurple).build())
            .setScaleData(GenericParticleData.create(4.5f, 0f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(30)
            .setFriction(0.85f)
            .setTransparencyData(GenericParticleData.create(0.2f, 1f, 0f).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.INWARD_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Col.cyan).build())
            .setScaleData(GenericParticleData.create(5f, 0f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(40)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1f, 0.7f, 0f).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.INWARD_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Col.cyan).build())
            .setScaleData(GenericParticleData.create(5f, 0f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(50)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1f, 0.7f, 0f).build())
            .spawn(level, pos.x, groundY, pos.z);

        ParticleBuilder.create(ParticleRegistry.INWARD_CIRCLE.get())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(Pal.majestyPurple, Col.cyan).build())
            .setScaleData(GenericParticleData.create(5f, 0f).build())
            .setSpriteData(SpriteParticleData.WITH_AGE)
            .setGravity(0)
            .setLifetime(25)
            .setFriction(0.9f)
            .setTransparencyData(GenericParticleData.create(1f, 0.7f, 0f).build())
            .spawn(level, pos.x, groundY, pos.z);

        for (int i = 0; i < 35; i++) {
            double angle = Math.random() * Math.PI * 2;
            double dist = 1.5 + Math.random() * 2.5;
            double rx = Math.cos(angle) * dist;
            double rz = Math.sin(angle) * dist;
            level.addParticle(ParticleTypes.PORTAL, pos.x + rx, groundY + 0.2 + Math.random() * 0.5, pos.z + rz, -rx * 0.3, 0.1, -rz * 0.3);
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(20, Pos3.init((float)pos.x, (float)pos.y, (float)pos.z), 4, 10).intensity(1.1f).interp(Interp.sineIn));
    }
}
