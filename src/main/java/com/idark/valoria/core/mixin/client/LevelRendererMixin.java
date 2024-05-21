package com.idark.valoria.core.mixin.client;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.level.*;
import org.joml.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin{

    @Unique
    private static final Matrix4f SUN_SCALE = new Matrix4f().scale(0F, 0F, 0F);
    @Unique
    private static final Matrix4f MOON_SCALE = new Matrix4f().scale(0F, 0F, 0F);

    @Unique
    private static boolean valoria$isValoriaSky(){
        Level world = Minecraft.getInstance().level;
        return world.dimension().toString().toLowerCase(Locale.ROOT).equals("resourcekey[minecraft:dimension / valoria:the_valoria]");
    }

    @ModifyVariable(
    method = "renderSky",
    slice = @Slice(
    from = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getTimeOfDay(F)F"),
    to = @At(ordinal = 0, value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
    ),
    at = @At(value = "CONSTANT", args = "floatValue=30.0"),
    ordinal = 1,
    require = 0
    )
    private Matrix4f SunScale(Matrix4f matrix){
        if(valoria$isValoriaSky()){
            matrix = new Matrix4f(matrix);
            matrix.mul(SUN_SCALE);
        }
        return matrix;
    }

    @ModifyVariable(
    method = "renderSky",
    slice = @Slice(
    from = @At(ordinal = 0, value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"),
    to = @At(ordinal = 1, value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
    ),
    at = @At(value = "CONSTANT", args = "floatValue=20.0"),
    ordinal = 1,
    require = 0
    )
    private Matrix4f MoonScale(Matrix4f matrix){
        if(valoria$isValoriaSky()){
            matrix.mul(MOON_SCALE);
        }
        return matrix;
    }
}