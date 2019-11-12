package org.wysaid.nativePort;

import android.graphics.Bitmap;
import android.graphics.PointF;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CGEFaceTracker {
    protected static boolean sIsTrackerSetup = false;
    protected FaceResult mFaceResult = new FaceResult();
    protected long mNativeAddress = nativeCreateFaceTracker();

    public static class FaceResult {
        public FloatBuffer faceKeyPoints = ByteBuffer.allocateDirect(528).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static class FaceResultSimple {
        public PointF jawPos;
        public PointF leftEyePos;
        public PointF mouthPos;
        public PointF nosePos;
        public PointF rightEyepos;
    }

    private static native void nativeSetupTracker(String str, String str2, String str3);

    protected native long nativeCreateFaceTracker();

    protected native boolean nativeDetectFaceWithBuffer(long j, ByteBuffer byteBuffer, int i, int i2, int i3, int i4, FloatBuffer floatBuffer);

    protected native float[] nativeDetectFaceWithSimpleResult(long j, Bitmap bitmap, boolean z);

    protected native void nativeRelease(long j);

    static {
        System.loadLibrary("FaceTracker");
    }

    public static boolean isTrackerSetup() {
        return sIsTrackerSetup;
    }

    private CGEFaceTracker() {
    }

    public static CGEFaceTracker createFaceTracker() {
        if (!sIsTrackerSetup) {
            nativeSetupTracker(null, null, null);
            sIsTrackerSetup = true;
        }
        return new CGEFaceTracker();
    }

    public void release() {
        if (this.mNativeAddress != 0) {
            nativeRelease(this.mNativeAddress);
            this.mNativeAddress = 0;
        }
    }

    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

    public FaceResultSimple detectFaceWithSimpleResult(Bitmap bmp, boolean drawFeature) {
        float[] result = nativeDetectFaceWithSimpleResult(this.mNativeAddress, bmp, drawFeature);
        if (result == null) {
            return null;
        }
        FaceResultSimple faceResultSimple = new FaceResultSimple();
        faceResultSimple.leftEyePos = new PointF(result[0], result[1]);
        faceResultSimple.rightEyepos = new PointF(result[2], result[3]);
        faceResultSimple.nosePos = new PointF(result[4], result[5]);
        faceResultSimple.mouthPos = new PointF(result[6], result[7]);
        faceResultSimple.jawPos = new PointF(result[8], result[9]);
        return faceResultSimple;
    }

    public FaceResult getFaceResult() {
        return this.mFaceResult;
    }

    public boolean detectFaceWithGrayBuffer(ByteBuffer buffer, int width, int height, int bytesPerRow) {
        return nativeDetectFaceWithBuffer(this.mNativeAddress, buffer, width, height, 1, bytesPerRow, this.mFaceResult.faceKeyPoints);
    }

    public boolean detectFaceWithBGRABuffer(ByteBuffer buffer, int width, int height, int bytesPerRow) {
        return nativeDetectFaceWithBuffer(this.mNativeAddress, buffer, width, height, 4, bytesPerRow, this.mFaceResult.faceKeyPoints);
    }

    public boolean detectFaceWithBGRBuffer(ByteBuffer buffer, int width, int height, int bytesPerRow) {
        return nativeDetectFaceWithBuffer(this.mNativeAddress, buffer, width, height, 3, bytesPerRow, this.mFaceResult.faceKeyPoints);
    }
}
