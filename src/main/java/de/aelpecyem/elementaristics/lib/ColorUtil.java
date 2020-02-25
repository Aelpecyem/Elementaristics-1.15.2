package de.aelpecyem.elementaristics.lib;

import java.awt.*;

public class ColorUtil {
    public static boolean isDark(float r, float g, float b){
        return isDark(r, g, b, 0.45F);
    }

    public static boolean isDark(float r, float g, float b, float threshold) {
        return r < threshold && g < threshold && b < threshold;
    }

    public static Color blend(Color c0, Color c1, double weightOne, double weightTwo) {
        double weight0 = weightOne;
        double weight1 = weightTwo;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
    }

    public static int toDecimal(Color color) {
        return (color.getRed() << 16) + (color.getGreen() << 8) + (color.getBlue());
    }
}
