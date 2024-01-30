package com.idark.valoria.util.math;
import java.util.Random;

public class RandUtils {

	static  Random random = new Random();
	public static boolean doWithChance(double chance) {
        double randomNumber = random.nextDouble() * 100;
        return randomNumber <= chance;
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