package org.wysaid.algorithm;

public class Matrix4x4 {
    public float[] data;

    Matrix4x4() {
        this.data = new float[16];
    }

    Matrix4x4(float[] _data) {
        this.data = _data;
    }

    public static Matrix4x4 makeIdentity() {
        return new Matrix4x4(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makeRotation(float rad, float x, float y, float z) {
        float normScaling = AlgorithmUtil.getNormalizeScaling(x, y, z);
        x *= normScaling;
        y *= normScaling;
        z *= normScaling;
        float cosRad=(float)Math.cos(rad);

        float cosp = 1.0f - ((float) Math.cos((double) rad));
        float sinRad = (float) Math.sin((double) rad);
        return new Matrix4x4(new float[]{((cosp * x) * x) + cosRad, ((cosp * x) * y) + (z * sinRad), ((cosp * x) * z) - (y * sinRad), 0.0f, ((cosp * x) * y) - (z * sinRad), ((cosp * y) * y) + cosRad, ((cosp * y) * z) + (x * sinRad), 0.0f, ((cosp * x) * z) + (y * sinRad), ((cosp * y) * z) - (x * sinRad), ((cosp * z) * z) + ((float) Math.cos((double) rad)), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makeXRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        return new Matrix4x4(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, cosRad, sinRad, 0.0f, 0.0f, -sinRad, cosRad, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makeYRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        return new Matrix4x4(new float[]{cosRad, 0.0f, -sinRad, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, sinRad, 0.0f, cosRad, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makeZRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        return new Matrix4x4(new float[]{cosRad, sinRad, 0.0f, 0.0f, -sinRad, cosRad, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makeTranslation(float x, float y, float z) {
        return new Matrix4x4(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, x, y, z, 1.0f});
    }

    public static Matrix4x4 makeScaling(float x, float y, float z) {
        return new Matrix4x4(new float[]{x, 0.0f, 0.0f, 0.0f, 0.0f, y, 0.0f, 0.0f, 0.0f, 0.0f, z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static Matrix4x4 makePerspective(float fovyRad, float aspect, float nearZ, float farZ) {
        float cotan = 1.0f / ((float) Math.tan((double) (fovyRad / 2.0f)));
        return new Matrix4x4(new float[]{cotan / aspect, 0.0f, 0.0f, 0.0f, 0.0f, cotan, 0.0f, 0.0f, 0.0f, 0.0f, (farZ + nearZ) / (nearZ - farZ), -1.0f, 0.0f, 0.0f, ((2.0f * farZ) * nearZ) / (nearZ - farZ), 0.0f});
    }

    public static Matrix4x4 makeFrustum(float left, float right, float bottom, float top, float nearZ, float farZ) {
        float ral = right + left;
        float rsl = right - left;
        float tsb = top - bottom;
        float tab = top + bottom;
        float fan = farZ + nearZ;
        float fsn = farZ - nearZ;
        return new Matrix4x4(new float[]{(2.0f * nearZ) / rsl, 0.0f, 0.0f, 0.0f, 0.0f, (2.0f * nearZ) / tsb, 0.0f, 0.0f, ral / rsl, tab / tsb, (-fan) / fsn, -1.0f, 0.0f, 0.0f, ((-2.0f * farZ) * nearZ) / fsn, 0.0f});
    }

    public static Matrix4x4 makeOrtho(float left, float right, float bottom, float top, float nearZ, float farZ) {
        float ral = right + left;
        float rsl = right - left;
        float tsb = top - bottom;
        float tab = top + bottom;
        float fan = farZ + nearZ;
        float fsn = farZ - nearZ;
        return new Matrix4x4(new float[]{2.0f / rsl, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / tsb, 0.0f, 0.0f, 0.0f, 0.0f, -2.0f / fsn, 0.0f, (-ral) / rsl, (-tab) / tsb, (-fan) / fsn, 1.0f});
    }

    protected static float[] _mul(float[] d1, float[] d2) {
        return new float[]{(((d1[0] * d2[0]) + (d1[4] * d2[1])) + (d1[8] * d2[2])) + (d1[12] * d2[3]), (((d1[1] * d2[0]) + (d1[5] * d2[1])) + (d1[9] * d2[2])) + (d1[13] * d2[3]), (((d1[2] * d2[0]) + (d1[6] * d2[1])) + (d1[10] * d2[2])) + (d1[14] * d2[3]), (((d1[3] * d2[0]) + (d1[7] * d2[1])) + (d1[11] * d2[2])) + (d1[15] * d2[3]), (((d1[0] * d2[4]) + (d1[4] * d2[5])) + (d1[8] * d2[6])) + (d1[12] * d2[7]), (((d1[1] * d2[4]) + (d1[5] * d2[5])) + (d1[9] * d2[6])) + (d1[13] * d2[7]), (((d1[2] * d2[4]) + (d1[6] * d2[5])) + (d1[10] * d2[6])) + (d1[14] * d2[7]), (((d1[3] * d2[4]) + (d1[7] * d2[5])) + (d1[11] * d2[6])) + (d1[15] * d2[7]), (((d1[0] * d2[8]) + (d1[4] * d2[9])) + (d1[8] * d2[10])) + (d1[12] * d2[11]), (((d1[1] * d2[8]) + (d1[5] * d2[9])) + (d1[9] * d2[10])) + (d1[13] * d2[11]), (((d1[2] * d2[8]) + (d1[6] * d2[9])) + (d1[10] * d2[10])) + (d1[14] * d2[11]), (((d1[3] * d2[8]) + (d1[7] * d2[9])) + (d1[11] * d2[10])) + (d1[15] * d2[11]), (((d1[0] * d2[12]) + (d1[4] * d2[13])) + (d1[8] * d2[14])) + (d1[12] * d2[15]), (((d1[1] * d2[12]) + (d1[5] * d2[13])) + (d1[9] * d2[14])) + (d1[13] * d2[15]), (((d1[2] * d2[12]) + (d1[6] * d2[13])) + (d1[10] * d2[14])) + (d1[14] * d2[15]), (((d1[3] * d2[12]) + (d1[7] * d2[13])) + (d1[11] * d2[14])) + (d1[15] * d2[15])};
    }

    public Matrix4x4 multiply(Matrix4x4 mat) {
        return new Matrix4x4(_mul(this.data, mat.data));
    }

    public Matrix4x4 multiplyBy(Matrix4x4 mat) {
        this.data = _mul(this.data, mat.data);
        return this;
    }

    public Matrix4x4 clone() {
        return new Matrix4x4((float[]) this.data.clone());
    }
}
