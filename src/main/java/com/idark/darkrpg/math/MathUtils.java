package com.idark.darkrpg.math;

import com.idark.darkrpg.math.AColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.*;

public class MathUtils {

    public static float interpolate(float a,float b,float t){
        return (1 - t) * a + t * b;
    }

    public static Vector2 interpolate(Vector2 a, Vector2 b, float t){
        return a.interpolate(b,t);
    }

    public static Vector3 interpolate(Vector3 a, Vector3 b, float t){
        return a.interpolate(b,t);
    }

    public static AColor interpolate(AColor a, AColor b, float t){
        return a.interpolate(b,t);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector2 clamp(Vector2 val, Vector2 min, Vector2 max) {
        return val.clamp(min,max);
    }

    public static Vector3 clamp(Vector3 val, Vector3 min, Vector3 max) {
        return val.clamp(min,max);
    }

    public static Vector2 clamp(Vector2 val, float min, float max) {
        return val.clamp(new Vector2(min,min),new Vector2(max,max));
    }

    public static Vector3 clamp(Vector3 val, float min, float max) {
        return val.clamp(new Vector3(min,min,min),new Vector3(max,max,max));
    }

    public static float clampedInterpolation(float value, float maxValue, float power){
        return clamp(interpolate(value,value*maxValue,power),-maxValue,maxValue);
    }

    public static boolean equalsApproximately(float a, float b){
        float diff = Math.abs(b - a);
        float tolerance = 0.1f/100 * b;
        return diff < tolerance;
    }

    public static boolean equalsApproximately(Vector2 a, Vector2 b){
        return equalsApproximately(a.x,b.x) && equalsApproximately(a.y,b.y);
    }

    public static boolean equalsApproximately(Vector3 a, Vector3 b){
        return equalsApproximately(a.x,b.x) && equalsApproximately(a.y,b.y) && equalsApproximately(a.z,b.z);
    }

    public static boolean equalsApproximately(AColor a, AColor b){
        return equalsApproximately(a.r,b.r) && equalsApproximately(a.g,b.g) && equalsApproximately(a.b,b.b) && equalsApproximately(a.a,b.a);
    }

    public static float rad(float angle) {
        return angle * (float) Math.PI / 180;
    }
	
	public static Vector3d direction(Entity entity){
        float rotationYaw = entity.rotationYaw, rotationPitch = entity.rotationPitch;
        float vx = -MathHelper.sin(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
        float vz = MathHelper.cos(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
        float vy = -MathHelper.sin(rad(rotationPitch));
        return new Vector3d(vx,vy,vz);
    }

    public static float range(float oldMin, float oldMax, float newMin, float newMax, float value){
        float oldRange = (oldMax - oldMin);
        float newValue;
        if (oldRange == 0) {
            newValue = newMin;
        }
        else {
            float newRange = (newMax - newMin);
            newValue = (((value - oldMin) * newRange) / oldRange) + newMin;
        }
        return newValue;
    }
}