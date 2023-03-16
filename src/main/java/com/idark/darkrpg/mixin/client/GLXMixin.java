package com.idark.darkrpg.mixin.client;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
