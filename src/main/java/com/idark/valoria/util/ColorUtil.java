package com.idark.valoria.util;

import java.awt.*;

// todo move to lib
public class ColorUtil {
    public static int getAlpha(int packedColor) {
        return packedColor >>> 24;
    }

    public static int getRed(int packedColor) {
        return packedColor >> 16 & 255;
    }

    public static int getGreen(int packedColor) {
        return packedColor >> 8 & 255;
    }

    public static int getBlue(int packedColor) {
        return packedColor & 255;
    }

    public static int packColor(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int packColor(float red, float green, float blue) {
        return ((int) (red * 255.0F) & 255) << 16 | ((int) (green * 255.0F) & 255) << 8 | (int) (blue * 255.0F) & 255;
    }

    public static int packColor(float alpha, float red, float green, float blue) {
        return ((int) (alpha * 255.0F) & 255) << 24 | ((int) (red * 255.0F) & 255) << 16 | ((int) (green * 255.0F) & 255) << 8 | (int) (blue * 255.0F) & 255;
    }

    public static int hexToDecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    public static int colorToDecimal(Color color) {
        return Integer.parseInt(getHex(color), 16);
    }

    public static String getHex(int r, int g, int b) {
        Color color = new Color(r, g, b);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }

        return hex;
    }

    public static String getHex(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }

        return hex;
    }

    public static Color valueOf(String hex) {
        int offset = hex.charAt(0) == '#' ? 1 : 0;
        int r = parseHex(hex, offset, offset + 2);
        int g = parseHex(hex, offset + 2, offset + 4);
        int b = parseHex(hex, offset + 4, offset + 6);
        int a = hex.length() - offset != 8 ? 255 : parseHex(hex, offset + 6, offset + 8);
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    private static int parseHex(String string, int from, int to) {
        int total = 0;
        for (int i = from; i < to; i++) {
            char c = string.charAt(i);
            total += Character.digit(c, 16) * (i == from ? 16 : 1);
        }
        return total;
    }

}
