package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

import java.awt.*;

public class WickedAltarBlockEntity extends AbstractAltarBlockEntity{
    public WickedAltarBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntitiesRegistry.WICKED_ALTAR.get(), pos, state);
    }


    @Override
    public void summonParticles(int tick){
        double angle = (tick + progress * Math.PI) * 0.925;
        double y = angle * 0.01025;
        for(int a = 0; a < 3; a++){
            double radius = 0.5 * (1 - ((double)progress / progressMax)) * (1 - ((double)a * 2.5f));
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            ParticleBuilder.create(TridotParticles.SMOKE)
            .setColorData(ColorParticleData.create(Pal.softMagenta, Color.magenta).build())
            .setTransparencyData(GenericParticleData.create(0.425f, 0f).build())
            .setScaleData(GenericParticleData.create((((float)a * 0.125f)), 0.1f, 0).build())
            .setLifetime(35)
            .spawn(this.level, (this.worldPosition.getX() + 0.5f) + x, this.worldPosition.getY() + (1 - ((double)a / 1.25)) + y, (this.worldPosition.getZ() + 0.5f) + z);
        }

        ParticleBuilder.create(TridotParticles.WISP)
        .setColorData(ColorParticleData.create(Pal.softMagenta, Color.magenta).build())
        .setTransparencyData(GenericParticleData.create(0.125f, 0f).build())
        .setScaleData(GenericParticleData.create(((0.125f)), 0.1f, 0).build())
        .setLifetime(60)
        .spawn(this.level, (this.worldPosition.getX() + 0.5f), (this.worldPosition.getY() + 1.5f) + y, (this.worldPosition.getZ() + 0.5f));
    }

    @Override
    public SoundEvent getSummonSound(){
        return SoundsRegistry.WICKED_CRYSTAL_SUMMON.get();
    }

    @Override
    public void summonBoss(Level level){
        WickedCrystal boss = new WickedCrystal(EntityTypeRegistry.WICKED_CRYSTAL.get(), level);
        boss.moveTo(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 2.75f, this.worldPosition.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(boss);
    }
}