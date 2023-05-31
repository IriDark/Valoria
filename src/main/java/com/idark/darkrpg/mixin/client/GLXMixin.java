package com.idark.darkrpg.mixin.client;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.LongSupplier;

@Mixin({GLX.class})
public class GLXMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static LongSupplier _initGlfw() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLFW.glfwInit();
        return () -> (long) (GLFW.glfwGetTime() * 1.0E9D);
    }
}
