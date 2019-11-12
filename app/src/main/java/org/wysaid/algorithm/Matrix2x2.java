package org.wysaid.algorithm;

public class Matrix2x2 {
    public float[] data;

    protected Matrix2x2() {
        this.data = new float[4];
    }

    protected Matrix2x2(float[] _data) {
        this.data = _data;
    }

    public static Matrix2x2 makeIdentity() {
        return new Matrix2x2(new float[]{1.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix2x2 makeRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        return new Matrix2x2(new float[]{cosRad, sinRad, -sinRad, cosRad});
    }

    protected static float[] _mul(float[] d1, float[] d2) {
        return new float[]{(d1[0] * d2[0]) + (d1[2] * d2[1]), (d1[1] * d2[0]) + (d1[3] * d2[1]), (d1[0] * d2[2]) + (d1[2] * d2[3]), (d1[1] * d2[2]) + (d1[3] * d2[3])};
    }

    public Matrix2x2 multiply(Matrix2x2 mat) {
        return new Matrix2x2(_mul(this.data, mat.data));
    }

    public Matrix2x2 multiplyBy(Matrix2x2 mat) {
        this.data = _mul(this.data, mat.data);
        return this;
    }

    public Matrix2x2 clone() {
        return new Matrix2x2((float[]) this.data.clone());
    }
}
