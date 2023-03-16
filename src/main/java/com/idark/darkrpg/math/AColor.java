package com.idark.darkrpg.math;

import com.idark.darkrpg.math.MathUtils;

public class AColor {

    public static final AColor RED = new AColor(1f,0f,0f,1f);
    public static final AColor GREEN = new AColor(0f,1f,0f,1f);
    public static final AColor DARK_GREEN = new AColor(0.5f,0f,0f,0.5f);
    public static final AColor BLUE = new AColor(0f,0f,1f,1f);
    public static final AColor DARK_BLUE = new AColor(0f,0f,0.5f,1f);
    public static final AColor YELLOW = new AColor(1f,1f,0f,1f);
    public static final AColor GOLD = new AColor(1f,0.823529f,0.0392156f,1f);
    public static final AColor ORANGE = new AColor(1f,0.5f,0f,1f);
    public static final AColor AQUA = new AColor(0f,1f,1f,1f);
    public static final AColor CYAN = new AColor(0f,0.5f,0.5f,1f);
    public static final AColor PURPLE = new AColor(1f,0f,1f,1f);
    public static final AColor PINK = new AColor(1f,0.5f,1f,1f);
    public static final AColor WHITE = new AColor(1f,1f,1f,1f);
    public static final AColor BLACK = new AColor(0f,0f,0f,1f);

    public float r;
    public float g;
    public float b;
    public float a;


    public AColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int[] toArray(){
        int[] colors = new int[4];
        colors[0] = (int)(r*255);
        colors[1] = (int)(g*255);
        colors[2] = (int)(b*255);
        colors[3] = (int)(a*255);
        return colors;
    }

    public static AColor fromArray(int[] array){
        return convert(array[0],array[1],array[2],array[3]);
    }

    public static AColor convert(int r, int g, int b, int a) {
        return new AColor((float)(((float)r)/255f),(float)(((float)g)/255f),(float)(((float)b)/255f),(float)(((float)a)/255f));
    }


    public AColor interpolate(AColor color, float t){
        return new AColor(MathUtils.interpolate(r,color.r,t),MathUtils.interpolate(g,color.g,t),MathUtils.interpolate(b,color.b,t),MathUtils.interpolate(a,color.a,t));
    }
}