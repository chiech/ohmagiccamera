package org.wysaid.algorithm;

public class AlgorithmUtil {
    public static float getNormalizeScaling(float x, float y, float z) {
        return (float) (1.0d / Math.sqrt((double) (((x * x) + (y * y)) + (z * z))));
    }

    public static float getNormalizeScaling(float x, float y, float z, float w) {
        return (float) (1.0d / Math.sqrt((double) ((((x * x) + (y * y)) + (z * z)) + (w * w))));
    }
}
