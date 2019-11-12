package org.wysaid.nativePort;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class CGEImageHandler {
    protected long mNativeAddress = nativeCreateHandler();

    protected native void nativeBindTargetFBO(long j);

    protected native long nativeCreateHandler();

    protected native void nativeDrawResult(long j);

    protected native Bitmap nativeGetResultBitmap(long j);

    protected native boolean nativeInitWithBitmap(long j, Bitmap bitmap);

    protected native boolean nativeInitWithSize(long j, int i, int i2);

    protected native void nativeProcessWithFilter(long j, long j2);

    protected native void nativeProcessingFilters(long j);

    protected native void nativeRelease(long j);

    protected native void nativeRevertImage(long j);

    protected native void nativeSetAsTarget(long j);

    protected native void nativeSetDrawerFlipScale(long j, float f, float f2);

    protected native void nativeSetDrawerRotation(long j, float f);

    protected native void nativeSetFilterIntensity(long j, float f, boolean z);

    protected native boolean nativeSetFilterIntensityAtIndex(long j, float f, int i, boolean z);

    protected native boolean nativeSetFilterWithConfig(long j, String str, boolean z, boolean z2);

    protected native void nativeSwapBufferFBO(long j);

    static {
        NativeLibraryLoader.load();
    }

    public boolean initWithBitmap(Bitmap bmp) {
        if (bmp == null) {
            return false;
        }
        if (bmp.getConfig() != Config.ARGB_8888) {
            bmp = bmp.copy(Config.ARGB_8888, false);
        }
        return nativeInitWithBitmap(this.mNativeAddress, bmp);
    }

    public boolean initWithSize(int width, int height) {
        return nativeInitWithSize(this.mNativeAddress, width, height);
    }

    public Bitmap getResultBitmap() {
        return nativeGetResultBitmap(this.mNativeAddress);
    }

    public void setDrawerRotation(float rad) {
        nativeSetDrawerRotation(this.mNativeAddress, rad);
    }

    public void setDrawerFlipScale(float x, float y) {
        nativeSetDrawerFlipScale(this.mNativeAddress, x, y);
    }

    public void setFilterWithConfig(String config) {
        nativeSetFilterWithConfig(this.mNativeAddress, config, true, true);
    }

    public void setFilterWithConfig(String config, boolean shouldClearOlder, boolean shouldProcess) {
        nativeSetFilterWithConfig(this.mNativeAddress, config, shouldClearOlder, shouldProcess);
    }

    public void setFilterIntensity(float intensity) {
        nativeSetFilterIntensity(this.mNativeAddress, intensity, true);
    }

    public void setFilterIntensity(float intensity, boolean shouldProcess) {
        nativeSetFilterIntensity(this.mNativeAddress, intensity, shouldProcess);
    }

    public boolean setFilterIntensityAtIndex(float intensity, int index, boolean shouldProcess) {
        return nativeSetFilterIntensityAtIndex(this.mNativeAddress, intensity, index, shouldProcess);
    }

    public void drawResult() {
        nativeDrawResult(this.mNativeAddress);
    }

    public void bindTargetFBO() {
        nativeBindTargetFBO(this.mNativeAddress);
    }

    public void setAsTarget() {
        nativeSetAsTarget(this.mNativeAddress);
    }

    public void swapBufferFBO() {
        nativeSwapBufferFBO(this.mNativeAddress);
    }

    public void revertImage() {
        nativeRevertImage(this.mNativeAddress);
    }

    public void processFilters() {
        nativeProcessingFilters(this.mNativeAddress);
    }

    public void processWithFilter(long filterAddress) {
        nativeProcessWithFilter(this.mNativeAddress, filterAddress);
    }

    public void release() {
        if (this.mNativeAddress != 0) {
            nativeRelease(this.mNativeAddress);
            this.mNativeAddress = 0;
        }
    }
}
