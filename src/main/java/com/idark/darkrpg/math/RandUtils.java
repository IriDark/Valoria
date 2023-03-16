package com.idark.darkrpg.math;
import java.util.Random;

public class RandUtils {

    private static Random random = new Random();
    public static boolean doWithChance(float chance){
        double num = random.nextDouble() * 100;
        if (num <= chance){
            return true;
        }
        return false;
    }

    public static double random(){
        return Math.random();
    }

    public static int randomInt(int border){
        return random.nextInt(border);
    }

    public static int randomInt(int min,int max){
        return random.nextInt(max - min) + min;
    }

    public static float randomFloat(float min,float max){
        return min + random.nextFloat() * (max - min);
    }
}